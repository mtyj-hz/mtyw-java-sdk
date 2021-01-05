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
import com.mtyw.storage.exception.MtExceptionEnum;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.util.CommonUtil;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.*;

/*
 * HTTP request  builder.
 */
public class MFSSRequestBuilder<T> {

    private HttpMethod method = HttpMethod.GET;
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> parameters = new LinkedHashMap<String, String>();
    private InputStream inputStream;
    private long inputSize = 0;

    public MFSSRequestBuilder(T ob) {
        Map<String, String> mapParams = objToMapParam(ob);
        this.parameters.putAll(mapParams);
    }
    public MFSSRequestBuilder() {
    }
    public HttpMethod getMethod() {
        return method;
    }

    public MFSSRequestBuilder setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }



    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public MFSSRequestBuilder setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public MFSSRequestBuilder addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public MFSSRequestBuilder setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }

    public MFSSRequestBuilder addParameter(String key, String value) {
        parameters.put(key, value);
        return this;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public MFSSRequestBuilder setInputStream(InputStream instream) {
        this.inputStream = instream;
        return this;
    }


    public Request build( ) {
        Map<String, String> sentHeaders = new HashMap<String, String>(this.headers);
        Map<String, String> sentParameters = new LinkedHashMap<String, String>(this.parameters);
        Request request = new Request();
        request.setHeaders(sentHeaders);
        request.setParameters(sentParameters);
        request.setMethod(this.method);
        request.setContent(this.inputStream);
        request.setContentLength(this.inputSize);
        return request;
    }
    /**
     * 对象转map
     * @param obj
     * @return
     */
    private static Map<String, String> objToMapParam(Object obj) {
        Boolean paramcheck = CommonUtil.isObjectFieldEmpty(obj) ;
        if (paramcheck) {
            throw new MtywApiException(MtExceptionEnum.ILLEGAL_PARAM);
        }

        Map<String, String> map = new HashMap<String, String>();
        Field[] fields = obj.getClass().getDeclaredFields();	// 获取f对象对应类中的所有属性域
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            if ("inputStream".equals(varName)) {
                continue;
            }
            try {
                boolean accessFlag = fields[i].isAccessible();	// 获取原来的访问控制权限
                fields[i].setAccessible(true);					// 修改访问控制权限
                Object o = fields[i].get(obj);					// 获取在对象f中属性fields[i]对应的对象中的变量
                if (o != null){
                    map.put(varName, o.toString());
                }
                fields[i].setAccessible(accessFlag);			// 恢复访问控制权限
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }
}
