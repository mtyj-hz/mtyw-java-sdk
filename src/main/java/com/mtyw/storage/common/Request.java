package com.mtyw.storage.common;

import java.net.URI;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mtyw.storage.HttpMethod;

/**
 * Represent HTTP requests sent to MFSS.
 */
public class Request extends HttpMesssage {


    /* The resource path being requested */
    private String resourcePath;
    private String url;



    /* The HTTP method to use when sending this request */
    private HttpMethod method = HttpMethod.GET;

    /* Use a LinkedHashMap to preserve the insertion order. */
    private Map<String, String> parameters = new LinkedHashMap<String, String>();



    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }


    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters.clear();
        if (parameters != null && !parameters.isEmpty()) {
            this.parameters.putAll(parameters);
        }
    }

    public void addParameter(String key, String value) {
        this.parameters.put(key, value);
    }
    public void addParameters(Map<String, String> parameters) {
        this.parameters.putAll(parameters);
    }
    public void removeParameter(String key) {
        this.parameters.remove(key);
    }

    /**
     * Indicate whether the request should be repeatedly sent.
     */
    public boolean isRepeatable() {
        return this.getContent() == null || this.getContent().markSupported();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
