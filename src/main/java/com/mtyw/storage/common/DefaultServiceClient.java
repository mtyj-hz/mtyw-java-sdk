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

import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.util.HttpHeaders;
import com.mtyw.storage.util.HttpUtil;
import com.mtyw.storage.util.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Default implementation of {@link ServiceClient}.
 */
public class DefaultServiceClient extends ServiceClient {
    protected static HttpRequestFactory httpRequestFactory = new HttpRequestFactory();
	private static Method setNormalizeUriMethod = null;

    protected CloseableHttpClient httpClient;
    protected HttpClientConnectionManager connectionManager;
    protected RequestConfig requestConfig;

    public DefaultServiceClient(ClientHttpRequestConfiguration config, URI uri) {
        super(config, uri);
        this.connectionManager = createHttpClientConnectionManager();
        this.httpClient = createHttpClient(this.connectionManager);
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setConnectTimeout(config.getConnectionTimeout());
        requestConfigBuilder.setSocketTimeout(config.getSocketTimeout());
        requestConfigBuilder.setConnectionRequestTimeout(config.getConnectionRequestTimeout());

        //Compatible with HttpClient 4.5.9 or later
        if (setNormalizeUriMethod != null) {
            try {
                setNormalizeUriMethod.invoke(requestConfigBuilder, false);
            }catch (Exception e) {
            }
        }

        this.requestConfig = requestConfigBuilder.build();
    }

    @Override
    public Response sendRequestCore(Request request, Context context) throws IOException {
        HttpRequestBase httpRequest = httpRequestFactory.createHttpRequest(request, context);
        HttpClientContext httpContext = createHttpContext();
        httpContext.setRequestConfig(this.requestConfig);

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpRequest, httpContext);
        } catch (ClientProtocolException ex) {
            httpRequest.abort();
            throw ex;
        }catch (IOException ex) {
            httpRequest.abort();
            throw ex;
        }
        return buildResponse(request, httpResponse);
    }

    protected static Response buildResponse(Request request, CloseableHttpResponse httpResponse)
            throws IOException {

        assert (httpResponse != null);

        Response response = new Response();
        response.setUrl(request.getUri());
        response.setHttpResponse(httpResponse);

        if (httpResponse.getStatusLine() != null) {
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        }

        if (httpResponse.getEntity() != null) {
            if (response.isSuccessful()) {
                response.setContent(httpResponse.getEntity().getContent());
            } else {
                readAndSetErrorResponse(httpResponse.getEntity().getContent(), response);
            }
        }
        for (Header header : httpResponse.getAllHeaders()) {
            if (HttpHeaders.CONTENT_LENGTH.equalsIgnoreCase(header.getName())) {
                response.setContentLength(Long.parseLong(header.getValue()));
            }
            response.addHeader(header.getName(), header.getValue());
        }
        HttpUtil.convertHeaderCharsetFromIso88591(response.getHeaders());

        return response;
    }

    private static void readAndSetErrorResponse(InputStream originalContent, Response response)
            throws IOException {
        byte[] contentBytes = IOUtils.readStreamAsByteArray(originalContent);
        response.setErrorResponseAsString(new String(contentBytes));
        response.setContent(new ByteArrayInputStream(contentBytes));
    }

    protected CloseableHttpClient createHttpClient(HttpClientConnectionManager connectionManager) {
        return HttpClients.custom().setConnectionManager(connectionManager)
                .disableContentCompression().disableAutomaticRetries().build();
    }

    protected HttpClientConnectionManager createHttpClientConnectionManager() {
        SSLConnectionSocketFactory sslSocketFactory = null;
        try {
            List<TrustManager> trustManagerList = new ArrayList<TrustManager>();
            X509TrustManager[] trustManagers = config.getX509TrustManagers();

            if (null != trustManagers) {
                trustManagerList.addAll(Arrays.asList(trustManagers));
            }

            // get trustManager using default certification from jdk
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore) null);
            trustManagerList.addAll(Arrays.asList(tmf.getTrustManagers()));

            final List<X509TrustManager> finalTrustManagerList = new ArrayList<X509TrustManager>();
            for (TrustManager tm : trustManagerList) {
                if (tm instanceof X509TrustManager) {
                    finalTrustManagerList.add((X509TrustManager) tm);
                }
            }
            CompositeX509TrustManager compositeX509TrustManager = new CompositeX509TrustManager(finalTrustManagerList);
            compositeX509TrustManager.setVerifySSL(config.isVerifySSLEnable());
            KeyManager[] keyManagers = null;
            if (config.getKeyManagers() != null) {
                keyManagers = config.getKeyManagers();
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, new TrustManager[]{compositeX509TrustManager}, config.getSecureRandom());

            HostnameVerifier hostnameVerifier = null;
            if (!config.isVerifySSLEnable()) {
                hostnameVerifier = new NoopHostnameVerifier();
            } else if (config.getHostnameVerifier() != null) {
                hostnameVerifier = config.getHostnameVerifier();
            } else {
                hostnameVerifier = new DefaultHostnameVerifier();
            }
            sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        } catch (Exception e) {
            throw new MtywApiException(e.getMessage());
        }

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register(Protocol.HTTP.toString(), PlainConnectionSocketFactory.getSocketFactory())
                .register(Protocol.HTTPS.toString(), sslSocketFactory).build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
        connectionManager.setDefaultMaxPerRoute(config.getMaxConnections());
        connectionManager.setMaxTotal(config.getMaxConnections());
        connectionManager.setValidateAfterInactivity(config.getValidateAfterInactivity());
        connectionManager.setDefaultSocketConfig(
                SocketConfig.custom().setSoTimeout(config.getSocketTimeout()).setTcpNoDelay(true).build());
        if (config.isUseReaper()) {
            IdleConnectionReaper.setIdleConnectionTime(config.getIdleConnectionTime());
            IdleConnectionReaper.registerConnectionManager(connectionManager);
        }
        return connectionManager;
    }

    protected HttpClientContext createHttpContext() {
        HttpClientContext httpContext = HttpClientContext.create();
        httpContext.setRequestConfig(this.requestConfig);
        return httpContext;
    }


    @Override
    public void shutdown() {
        IdleConnectionReaper.removeConnectionManager(this.connectionManager);
        this.connectionManager.shutdown();
    }

    @Override
    public String getConnectionPoolStats() {
        if (connectionManager != null && connectionManager instanceof PoolingHttpClientConnectionManager) {
            PoolingHttpClientConnectionManager conn = (PoolingHttpClientConnectionManager)connectionManager;
            return conn.getTotalStats().toString();
        }
        return "";
    }

    private static Method getClassMethd(Class<?> clazz, String methodName) {
        try {
            Method[] method = clazz.getDeclaredMethods();
            for (Method m : method) {
                if (!m.getName().equals(methodName)) {
                    continue;
                }
                return m;
            }
        } catch (Exception e) {
        }
        return null;
    }

    static {
        try {
            setNormalizeUriMethod = getClassMethd(
                    Class.forName("org.apache.http.client.config.RequestConfig$Builder"),
                    "setNormalizeUri");
        } catch (Exception e) {
        }
    }

    private class CompositeX509TrustManager implements X509TrustManager {

        private final List<X509TrustManager> trustManagers;
        private boolean verifySSL = true;

        public boolean isVerifySSL() {
            return this.verifySSL;
        }

        public void setVerifySSL(boolean verifySSL) {
            this.verifySSL = verifySSL;
        }

        public CompositeX509TrustManager(List<X509TrustManager> trustManagers) {
            this.trustManagers = trustManagers;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // do nothing
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            if (!verifySSL) {
                return;
            }
            for (X509TrustManager trustManager : trustManagers) {
                try {
                    trustManager.checkServerTrusted(chain, authType);
                    return; // someone trusts them. success!
                } catch (CertificateException e) {
                    // maybe someone else will trust them
                }
            }
            throw new CertificateException("None of the TrustManagers trust this certificate chain");
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            List<X509Certificate> certificates = new ArrayList<X509Certificate>();
            for (X509TrustManager trustManager : trustManagers) {
                X509Certificate[] accepts = trustManager.getAcceptedIssuers();
                if(accepts != null && accepts.length > 0) {
                    certificates.addAll(Arrays.asList(accepts));
                }
            }
            X509Certificate[] certificatesArray = new X509Certificate[certificates.size()];
            return certificates.toArray(certificatesArray);
        }
    }
}
