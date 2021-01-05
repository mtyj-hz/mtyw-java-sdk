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

package com.mtyw.storage.parser;

import com.mtyw.storage.common.Response;
import com.mtyw.storage.model.response.ResultResponse;

import java.io.IOException;

/**
 * Used to convert an result stream to a java object.
 */
public interface ResponseParser {
    /**
     * Converts the result from stream to a java object.
     * 
     * @param response
     *            The http response message.
     * @return The java Type T object that the result stands for.
     * @throws
     *
     */
    public <T> ResultResponse<T>   parse(Response response, Class<T> tClass) throws IOException;
}