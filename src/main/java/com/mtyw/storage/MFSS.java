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
import com.mtyw.storage.model.request.filecoin.FilecoinInfoReq;
import com.mtyw.storage.model.request.filecoin.RetrieveReq;
import com.mtyw.storage.model.request.filecoin.UploadFileCoinFileReq;
import com.mtyw.storage.model.request.ipfs.UploadIpfsFileReq;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.filecoin.*;
import com.mtyw.storage.model.response.ipfs.*;

import java.io.InputStream;
import java.util.List;


public interface MFSS {


    /**
     * Shuts down the MFSS instance (release all resources) The MFSS instance is
     * not usable after its shutdown() is called.
     */
    void shutdown();

    /**
     * ipfs创建文件夹
     * @param parentpath 父目录
     * @param dirname 文件名
     * @return
     */

    ResultResponse  createIpfsdir(String parentpath, String dirname) throws MtywApiException;;

    /**
     * ipfs断点续传
     * @param uploadIpfsFileReq 上传信息请求
     * @param callBackReceiveRequestid 接收上传uploadid信息回调， 中途上传上传断开，断点续传需要用到接口返回的uploadid，可以先存储到数据库中
     * @param callBackFinish 上传完成后回调上传状态
     * @return
     */
    ResultResponse<String> uploadIpfsFile(UploadIpfsFileReq uploadIpfsFileReq, CallBack callBackReceiveRequestid, CallBack callBackFinish) throws MtywApiException;;
    /**
     * ipfs断点续传 同名文件存在则覆盖
     * @param uploadIpfsFileReq 上传信息请求
     * @param callBackReceiveRequestid 接收上传uploadid信息回调， 中途上传上传断开，断点续传需要用到接口返回的uploadid，可以先存储到数据库中
     * @param callBackFinish 上传完成后回调上传状态
     * @return
     */
    ResultResponse<String> uploadIpfsCoverFile(UploadIpfsFileReq uploadIpfsFileReq
            , CallBack callBackReceiveRequestid
            , CallBack callBackFinish) throws MtywApiException;;

    /**
     * 下载ipfs文件
     * @param filePath  文件路径
     * @param saveDir   存储目录
     */
    ResultResponse<Void> downloadIpfsFile(String filePath, String saveDir);

    /**
     * 下载ipfs文件
     * @param filePath  文件路径
     */
    ResultResponse<InputStream> downloadIpfsFile(String filePath);

    /**
     *
     * @param filePath
     * @param expiration
     * @return
     */
    ResultResponse<String> generateDownloadIpfsFilesignUrl(String filePath, Long expiration);


    /**
     * 文件检查
     * @param filepath  文件路径
     */
    ResultResponse<ObjectGetRes> objectGet(String filepath);

    /**
     * 获取所有节点
     */
    ResultResponse<List<RegionRes>> getAllRegionList();

    /**
     * 备份文件
     * @param filepath  文件路径
     */
    ResultResponse<FileDetailRes> backupManagement(String filepath);

    /**
     * 获取所有可用节点
     * @param size  目标大小
     */
    ResultResponse<List<RegionRes>> getUsableRegionList(Long size);

    /**
     * 检索ipfs文件和文件目录
     * @param filepath  文件路径
     * @param fileName  文件名
     * @param regionId  节点id
     */
    ResultResponse<FileInfoRes> searchIpfsDirectorylist(String filepath, String fileName, String regionId);

    /**
     * 获取ipfs文件和文件目录
     * @param filepath  文件路径
     */
    ResultResponse<FileInfoRes> getIpfsDirectorylist(String filepath);

    /**
     * 删除ipfs文件
     * @param filepath  文件路径
     * @param nodeids   节点id数组
     */
    ResultResponse<Boolean> deleteIpfsfile(String filepath, List<String> nodeids);

    /**
     * 批量删除ipfs文件
     * @param filepathlist  文件路径列表
     */
    ResultResponse<Boolean> deleteIpfsfileList(List<String> filepathlist);

    /**
     * 复制文件
     * @param filepath  文件路径
     * @param regionids 节点id数组
     */
    ResultResponse<Boolean> copyfile(String filepath, List<Integer> regionids);

    /**
     * 移动文件
     * @param filepath  文件路径
     * @param nodeId    节点id
     * @param regionId  机房id
     */
    ResultResponse<Boolean> movefile(String filepath, String nodeId, Integer regionId);

    /**
     * 文件移动到文件目录
     * @param toDirectoryPath   目标文件路径
     * @param filepath          文件路径
     */
    ResultResponse<Boolean> movefileToDirectory(String toDirectoryPath, String filepath);

    /**
     * 重命名文件夹
     * @param fromDirectoryPath 源文件夹路径
     * @param toDirectoryPath   目标文件路径
     */
    ResultResponse<Boolean> renameDirectory(String fromDirectoryPath, String toDirectoryPath);

    /**
     * 上传文件到filecoin
     * @param uploadIpfsFileRequest 文件信息
     * @param callBackReceiveRequestid 接收上传uploadid信息回调， 中途上传上传断开，断点续传需要用到接口返回的uploadid，可以先存储到数据库中
     * @param callBackFinish 上传完成后回调上传状态
     * @return
     * @throws MtywApiException
     */
    ResultResponse uploadFilecoinFile(UploadFileCoinFileReq uploadIpfsFileRequest, CallBack callBackReceiveRequestid, CallBack callBackFinish) throws MtywApiException;

    /**
     * 下载filCoin文件
     * @param cid       文件cid
     * @param uploadid  上传id
     * @param saveDir   存储路径
     * @return
     */
    ResultResponse<Void> downloadFileCoinFile(String cid, String uploadid, String saveDir);

    /**
     * 下载filCoin文件
     * @param cid       文件cid
     * @param uploadid  上传id
     * @return
     */
    ResultResponse<InputStream> downloadFileCoinFile(String cid, String uploadid);

    /**
     * filecoin冷备购买天数接口
     * @return
     */
    ResultResponse<List<FilecoinDateRes>> filecoinDatelist() throws MtywApiException;;

    /**
     * 冷备计算价格
     * @param calculatePriceReq 计算价格参数信息
     * @return
     */
    ResultResponse<FileBalanceRes> calculatePrice(CalculatePriceReq calculatePriceReq) throws MtywApiException;;

    /**
     * 计算检索价格
     * @param size 文件大小，需与实际一致，否则检索会验证错误
     * @return
     */
    ResultResponse<FileRetrieveBalanceRes> calculateRetrievePrice(Long size) throws MtywApiException;;

    /**
     * 获取节点列表
     * @return 可用节点信息
     */
    ResultResponse<List<NodeRes>> getFilecoinNodelist() throws MtywApiException;;

    /**
     * 获取filiecoin文件列表
     * @param page 页数信息
     * @param limit 每页个数
     * @return
     */
    ResultResponse<List<FileCoinRes>> getFilecoinDirectorylist(Integer page, Integer limit) throws MtywApiException;;

    /**
     * 检索
     * @param retrieveReq 检索参数
     * @return 是否成功
     * @throws MtywApiException
     */
    ResultResponse<Boolean> retrieve(RetrieveReq retrieveReq) throws MtywApiException;

    /**
     * filecoin轮询获取文件信息
     * @param filecoinInfoReq 文件信息
     * @return
     * @throws MtywApiException
     */
    ResultResponse<FilecoinInfoRes> fileInfo(FilecoinInfoReq filecoinInfoReq) throws MtywApiException;


}
