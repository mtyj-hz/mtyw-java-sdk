/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.mtyw.storage.common;

import com.mtyw.storage.HttpMethod;
import com.mtyw.storage.constant.MFSSConstants;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.util.HttpUtil;
import com.mtyw.storage.util.LogUtils;
import org.apache.http.HttpMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.mtyw.storage.util.LogUtils.logException;

public abstract class ServiceClient {
    protected ClientHttpRequestConfiguration config;
    protected volatile URI uri;


    protected ServiceClient(ClientHttpRequestConfiguration config, URI uri) {

        this.config = config;
        this.uri = uri;
    }

    public ClientHttpRequestConfiguration getClientConfiguration() {

        return this.config;
    }


    public Response sendRequest(com.mtyw.storage.common.Request request, Context context)
            throws MtywApiException {

        // Sign the request if a signer provided.
        if (context.getSigner() != null) {
            context.getSigner().sign(request);
        }

        InputStream requestContent = request.getContent();
        if (requestContent != null && requestContent.markSupported()) {
            requestContent.mark(MFSSConstants.DEFAULT_STREAM_BUFFER_SIZE);
        }
        Response response = null;
        try {
            // Step 1. Build HTTP request with specified request parameters
            // and context.
            Request httpRequest = buildRequest(request, context);
            // Step 2. Send HTTP request to MFSS.
            String poolStatsInfo = config.isLogConnectionPoolStatsEnable() ? "Connection pool stats " + getConnectionPoolStats() : "";
            long startTime = System.currentTimeMillis();
            response = sendRequestCore(httpRequest, context);
            long duration = System.currentTimeMillis() - startTime;
            if (duration > config.getSlowRequestsThreshold()) {
                LogUtils.getLog().warn(formatSlowRequestLog(request, response, duration) + poolStatsInfo);
            }
            return response;
        } catch (Exception ex) {
            logException("[Unknown]Unable to execute HTTP request: ", ex);
            closeResponseSilently(response);
            throw new MtywApiException(ex.getMessage(), ex);
        } finally {
            try {
                request.close();
            } catch (IOException ex) {
                logException("Unexpected io exception when trying to close http request: ", ex);
                throw new MtywApiException("Unexpected io exception when trying to close http request: ", ex);
            }

        }

    }

    protected abstract Response sendRequestCore(Request request, Context context) throws IOException;

    private URI toURI(String endpoint) throws IllegalArgumentException {
        try {
            return new URI(endpoint);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public Request buildRequest(com.mtyw.storage.common.Request requestMessage, Context context) throws MtywApiException {

        Request request = new Request();
        request.setMethod(requestMessage.getMethod());

        request.setHeaders(requestMessage.getHeaders());
        // The header must be converted after the request is signed,
        // otherwise the signature will be incorrect.
        if (request.getHeaders() != null) {
            HttpUtil.convertHeaderCharsetToIso88591(request.getHeaders());
        }

        final String delimiter = "/";




        String uri = this.uri.toString();
        if (requestMessage.getUrl() != null && !requestMessage.getUrl().equals("")) {
            URI url = toURI(requestMessage.getUrl());
            if (url != null) {
                uri = url.toString();
            }
        }
        if (!uri.endsWith(delimiter) && (requestMessage.getResourcePath() == null || !requestMessage.getResourcePath().startsWith(delimiter))) {
            uri += delimiter;
        }
        if (requestMessage.getResourcePath() != null) {
            uri += requestMessage.getResourcePath();
        }
        //todo
        requestMessage.addParameter("accessKey","8888");
        String paramString = HttpUtil.paramToQueryString(requestMessage.getParameters(), MFSSConstants.DEFAULT_CHARSET_NAME);
        /*
         * For all non-POST requests, and any POST requests that already have a
         * payload, we put the encoded params directly in the URI, otherwise,
         * we'll put them in the POST request's payload.
         */
        boolean requestHasNoPayload = requestMessage.getContent() != null;
        boolean requestIsPost = requestMessage.getMethod() == HttpMethod.POST;
        boolean putParamsInUri = !requestIsPost || requestHasNoPayload;
        if (paramString != null && putParamsInUri) {
            uri += "?" + paramString;
        }

        request.setUrl(uri);

        if (requestIsPost && requestMessage.getContent() == null && paramString != null) {
            // Put the param string to the request body if POSTing and
            // no content.
            try {
                byte[] buf = paramString.getBytes(MFSSConstants.DEFAULT_CHARSET_NAME);
                ByteArrayInputStream content = new ByteArrayInputStream(buf);
                request.setContent(content);
                request.setContentLength(buf.length);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            request.setContent(requestMessage.getContent());
            request.setContentLength(requestMessage.getContentLength());
        }

        return request;
    }


    private void closeResponseSilently(Response response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException ioe) {
                /* silently close the response. */
            }
        }
    }

    private String formatSlowRequestLog(com.mtyw.storage.common.Request request, Response response, long useTimesMs) {
        return String.format(
                "Request cost %d seconds, url %s, resourcePath %s, " + "method %s,statusCode %d",
                useTimesMs / 1000, this.uri.toString(), request.getResourcePath(), request.getMethod(),
                response.getStatusCode());
    }

    public abstract void shutdown();

    public String getConnectionPoolStats() {
        return "";
    }


    /**
     * Wrapper class based on {@link HttpMessage} that represents HTTP request
     * message to MFSS.
     */
    public static class Request extends HttpMesssage {
        private String uri;
        private HttpMethod method;
        private boolean useUrlSignature = false;
        private boolean useChunkEncoding = false;

        public String getUri() {
            return this.uri;
        }

        public void setUrl(String uri) {
            this.uri = uri;
        }

        public HttpMethod getMethod() {
            return method;
        }

        public void setMethod(HttpMethod method) {
            this.method = method;
        }

        public boolean isUseUrlSignature() {
            return useUrlSignature;
        }

        public void setUseUrlSignature(boolean useUrlSignature) {
            this.useUrlSignature = useUrlSignature;
        }

        public boolean isUseChunkEncoding() {
            return useChunkEncoding;
        }

        public void setUseChunkEncoding(boolean useChunkEncoding) {
            this.useChunkEncoding = useChunkEncoding;
        }
    }
}
