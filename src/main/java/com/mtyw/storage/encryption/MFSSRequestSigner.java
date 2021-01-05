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

package com.mtyw.storage.encryption;

import com.mtyw.storage.common.Request;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.util.HttpHeaders;
import com.mtyw.storage.util.SignUtil;
import com.sun.tools.javac.code.Types;

public class MFSSRequestSigner implements RequestSigner {

    private String accesskey;
    private String accesssecret;



    public MFSSRequestSigner(String accesskey, String accesssecret) {
        this.accesskey = accesskey;
        this.accesssecret = accesssecret;
    }

    @Override
    public void sign(Request request) throws MtywApiException {
        String accessKeyId = this.accesskey;
        String secretAccessKey = this.accesssecret;

        if (accessKeyId.length() > 0 && secretAccessKey.length() > 0) {
            String signature;
            //todo
            //signature = SignUtil.sign(null,null,null,"");
            request.addHeader(HttpHeaders.AUTHORIZATION, "signtest");

        }
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getAccesssecret() {
        return accesssecret;
    }

    public void setAccesssecret(String accesssecret) {
        this.accesssecret = accesssecret;
    }
}
