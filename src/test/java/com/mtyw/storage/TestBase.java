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

import com.mtyw.storage.model.request.filecoin.UploadFileCoinFileRequest;
import com.mtyw.storage.model.response.ResultResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
public class TestBase {
    
    protected static MFSSClient mfssClient;
    

    
    @Before
    public void setUp() throws Exception {
        getMFSSClient();
    }

    @After
    public void tearDown() throws Exception {
        cleanUp();
    }
    
    public static MFSSClient getMFSSClient() {
        if (mfssClient == null) {
            mfssClient = new MFSSClient("http://10.0.0.3:8040/api","1","2");
        }
        return mfssClient;
    }
    @Test
    public  void createBucket() {
        ResultResponse resultResponse = mfssClient.createdir("/","sdktest1");
        int aa =1;
        return ;
    }
    @Test
    public  void downloadIpfsFile() {

        ResultResponse resultResponse = mfssClient.downloadIpfsFile("/部署.md");
    }
    @Test
    public  void uploadfile() {
        UploadFileCoinFileRequest uploadIpfsFileRequest = new UploadFileCoinFileRequest();
        uploadIpfsFileRequest.setFileName("sdk文件.txt");
        InputStream inputStream = null;
        Long length = 0l;
        try{
            inputStream = new FileInputStream("/Users/chenxiaoli/jihuo.txt");
            length = Long.valueOf(inputStream.available());
        }catch (Exception e) {

        }
        uploadIpfsFileRequest.setFileSize(length);

        uploadIpfsFileRequest.setInputStream(inputStream);
        uploadIpfsFileRequest.setDays(180);
        uploadIpfsFileRequest.setUnitPrice(BigDecimal.valueOf(0.0002275));
        uploadIpfsFileRequest.setMinPrice(BigDecimal.valueOf(1));
        uploadIpfsFileRequest.setNodeId("rere");


        ResultResponse resultResponse = mfssClient.uploadFilecoinFile(uploadIpfsFileRequest,null);
        int aa =1;
        return ;
    }

    public static void cleanUp() {
        if (mfssClient != null) {
            mfssClient.shutdown();
            mfssClient = null;
        }
    }
}

