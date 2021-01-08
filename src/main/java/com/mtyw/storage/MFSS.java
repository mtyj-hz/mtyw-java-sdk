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
import com.mtyw.storage.model.request.filecoin.CalculatePriceReq;
import com.mtyw.storage.model.request.filecoin.RetrieveReq;
import com.mtyw.storage.model.request.filecoin.UploadFileCoinFileReq;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.filecoin.*;
import com.mtyw.storage.model.response.ipfs.*;

import java.util.List;


public interface MFSS {


    /**
     * Shuts down the MFSS instance (release all resources) The MFSS instance is
     * not usable after its shutdown() is called.
     */
    void shutdown();

    /**
     * ipfs创建文件夹
     */

    ResultResponse  createdir(String parentpath, String dirname);

    /**
     * 上传文件到filecoin
     * @param uploadIpfsFileRequest 文件相关信息
     * @param callBack
     * @return
     * @throws MtywApiException
     */
    ResultResponse uploadFilecoinFile(UploadFileCoinFileReq uploadIpfsFileRequest, CallBack callBack) throws MtywApiException;

    /**
     * filecoin冷备购买天数接口
     * @return
     */
    ResultResponse<List<FilecoinDateRes>> filecoinDatelist();
    /**
     *  冷备计算价格
     */
    ResultResponse<FileBalanceRes> calculatePrice(CalculatePriceReq calculatePriceReq);

    ResultResponse<FileRetrieveBalanceRes> calculateRetrievePrice(Long size);

    ResultResponse<List<NodeRes>> getFilecoinNodelist();

    ResultResponse<List<FileCoinRes>> getFilecoinDirectorylist(Integer page, Integer limit);

    ResultResponse<Boolean> retrieve(RetrieveReq retrieveReq) throws MtywApiException;


    ResultResponse<Void> downloadIpfsFile(String filePath, String saveDir);

//    ResultResponse<Boolean> spaceInspection(Long size);

    ResultResponse<ObjectGetRes> objectGet(String filepath);

    ResultResponse<List<RegionRes>> getAllRegionList();

    ResultResponse<FileDetailRes> backupManagement(String filepath);

    ResultResponse<List<RegionRes>> getUsableRegionList(Long size);

    ResultResponse<FileInfoRes> searchIpfsDirectorylist(String filepath, String fileName, String regionId);

    ResultResponse<FileInfoRes> getIpfsDirectorylist(String filepath);

    // post
    ResultResponse<Boolean> deleteIpfsfile(String filepath, List<String> nodeids);

    // post
    ResultResponse<Boolean> deleteIpfsfileList(List<String> filepathlist);

    // post
    ResultResponse<Boolean> copyfile(String filepath, List<Integer> regionids);

    // post
    ResultResponse<Boolean> movefile(String filepath, String nodeId, Integer regionId);

    // post
    ResultResponse<Boolean> movefileToDirectory(String toDirectoryPath, String filepath);

    // post
    ResultResponse<Boolean> renameDirectory(String fromDirectoryPath, String toDirectoryPath);



}
