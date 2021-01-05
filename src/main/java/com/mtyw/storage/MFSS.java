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

package com.mtyw.storage;

import com.mtyw.storage.common.CallBack;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.model.request.filecoin.UploadFileCoinFileRequest;
import com.mtyw.storage.model.response.ResultResponse;


public interface MFSS {


    /**
     * Shuts down the MFSS instance (release all resources) The MFSS instance is
     * not usable after its shutdown() is called.
     */
    void shutdown();

    /**
     * 
     */
    // public Boolean uploadFile(String filePath) throws ApplicationException;

    ResultResponse  createdir(String parentpath, String dirname);

    ResultResponse uploadFilecoinFile(UploadFileCoinFileRequest uploadIpfsFileRequest, CallBack callBack) throws MtywApiException;




}
