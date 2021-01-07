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

package com.mtyw.storage.constant;

/**
 * Miscellaneous constants used for MFSS client service.
 */
public final class MFSSConstants {

    public static final String DEFAULT_CHARSET_NAME = "utf-8";

    public static final String DEFAULT_OBJECT_CONTENT_TYPE = "application/json";

    public static final String HTTP_OBJECT_CONNECTION = "Keep-Alive";

    public static  final String HTTP_BODUNARY="----WebKitFormBoundaryRykOn5CIfLK3mb3m";

    public static final String DEFAULT_MULTIPART_CONTENT_TYPE = "multipart/form-data;boundary="+HTTP_BODUNARY;

    public static final Integer EXPIRETIME   = 300000;

    public static final int KB = 1024;
    public static final int DEFAULT_BUFFER_SIZE = 8 * KB;
    public static final int DEFAULT_STREAM_BUFFER_SIZE = 512 * KB;


    public static final long DEFAULT_FILE_SIZE_LIMIT = 1 * 1024 * 1024 ;

    public static final int OBJECT_NAME_MAX_LENGTH = 1024;

    public static final String LOGGER_PACKAGE_NAME = "com.mtyw.sdk";

    public static final String PROTOCOL_HTTP = "http://";
    public static final String PROTOCOL_HTTPS = "https://";
}
