package com.mtyw.storage.common;

import com.mtyw.storage.HttpMethod;
import com.mtyw.storage.constant.MFSSConstants;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.util.HttpHeaders;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.nio.charset.Charset;
import java.util.Map.Entry;

class HttpRequestFactory {

    public HttpRequestBase createHttpRequest(ServiceClient.Request request, Context context) {
        String uri = request.getUri();
        HttpRequestBase httpRequest;
        HttpMethod method = request.getMethod();
        if (method == HttpMethod.POST) {
            HttpPost postMethod = new HttpPost(uri);
            if (request.getContent() != null) {
                if (request.getHeaders().containsKey(HttpHeaders.CONTENT_TYPE) && request.getHeaders().get(HttpHeaders.CONTENT_TYPE).contains("boundary")) {
                    //HttpEntity builder
                    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                    //字符编码
                    builder.setCharset(Charset.forName("UTF-8"));
                    //boundary
                    builder.setBoundary(MFSSConstants.HTTP_BODUNARY);
                    //multipart/form-data
                    builder.addBinaryBody("file",request.getContent());//相当于<input name='file' type='file'/>
                    HttpEntity entity = builder.build();
                    postMethod.setEntity(entity);
                }else {
                    postMethod.setEntity(new RepeatableInputStreamEntity(request));
                }
            }
            httpRequest = postMethod;
        }else if (method == HttpMethod.GET) {
            httpRequest = new HttpGet(uri);
        } else {
            throw new MtywApiException("Unknown HTTP method name: " + method.toString());
        }

        configureRequestHeaders(request, context, httpRequest);

        return httpRequest;
    }


    private void configureRequestHeaders(ServiceClient.Request request, Context context,
            HttpRequestBase httpRequest) {

        for (Entry<String, String> entry : request.getHeaders().entrySet()) {
            if (entry.getKey().equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH)
                    || entry.getKey().equalsIgnoreCase(HttpHeaders.HOST)) {
                continue;
            }
            httpRequest.addHeader(entry.getKey(), entry.getValue());
        }
    }
}
