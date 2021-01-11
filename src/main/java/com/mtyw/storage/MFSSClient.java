package com.mtyw.storage;

import com.mtyw.storage.common.CallBack;
import com.mtyw.storage.common.ClientHttpRequestConfiguration;
import com.mtyw.storage.common.DefaultServiceClient;
import com.mtyw.storage.common.ServiceClient;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.internal.FileCoinOperation;
import com.mtyw.storage.internal.IpfsFileOperation;
import com.mtyw.storage.model.request.filecoin.CalculatePriceReq;
import com.mtyw.storage.model.request.filecoin.FilecoinInfoReq;
import com.mtyw.storage.model.request.filecoin.RetrieveReq;
import com.mtyw.storage.model.request.filecoin.UploadFileCoinFileReq;
import com.mtyw.storage.model.request.ipfs.CreateDirReq;
import com.mtyw.storage.model.request.ipfs.UploadIpfsFileReq;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.filecoin.*;
import com.mtyw.storage.model.response.ipfs.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.mtyw.storage.util.LogUtils.logException;

public class MFSSClient implements MFSS {
    /* The default service client */
    private ServiceClient serviceClient;
    private String accesskey;
    private String accesssecret;
    /* The miscellaneous MFSS operations */
    private IpfsFileOperation ipfsFileOperation;
    private FileCoinOperation fileCoinOperation;


    public MFSSClient(String url, String accessKey, String accessScret) {
        this(url, accessKey, accessScret, null);
    }

    public MFSSClient(String url, String accessKey, String accessScret, ClientHttpRequestConfiguration config) {
        config = config == null ? new ClientHttpRequestConfiguration() : config;
        URI uri = toURI(url);
        this.serviceClient = new DefaultServiceClient(config, uri);
        this.accesskey = accessKey;
        this.accesssecret = accessScret;
        initOperations();
    }

    private void initOperations() {
        this.ipfsFileOperation = new IpfsFileOperation(this.serviceClient, accesskey, accesssecret);
        this.fileCoinOperation = new FileCoinOperation(this.serviceClient, accesskey, accesssecret);

    }


    private URI toURI(String endpoint) throws IllegalArgumentException {
        try {
            return new URI(endpoint);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void shutdown() {
        try {
            serviceClient.shutdown();
        } catch (Exception e) {
            logException("shutdown throw exception: ", e);
        }
    }

    @Override
    public ResultResponse createIpfsdir(String parentpath, String dirname) {
        CreateDirReq createDirReq = new CreateDirReq();
        createDirReq.setDirectoryName(parentpath);
        createDirReq.setParentpath(dirname);
        ResultResponse response = ipfsFileOperation.createDir(createDirReq);
        return response;
    }


    @Override
    public ResultResponse<String> uploadFilecoinFile(UploadFileCoinFileReq uploadIpfsFileRequest, CallBack callBackReceiveRequestid, CallBack callBackFinish) throws MtywApiException {
        ResultResponse<String> response = fileCoinOperation.uploadFilecoinFile(uploadIpfsFileRequest, callBackReceiveRequestid, callBackFinish);
        return response;
    }

    @Override
    public ResultResponse<Void> downloadIpfsFile(String filePath, String saveDir) {
        return ipfsFileOperation.downloadIpfsFile(filePath, saveDir);
    }

    @Override
    public ResultResponse<List<FilecoinDateRes>> filecoinDatelist() {
        ResultResponse<List<FilecoinDateRes>> resultResponse = fileCoinOperation.filecoinDatelist();
        return resultResponse;
    }

    @Override
    public ResultResponse<FileBalanceRes> calculatePrice(CalculatePriceReq calculatePriceReq) {
        ResultResponse<FileBalanceRes> resResultResponse = fileCoinOperation.calculatePrice(calculatePriceReq);
        return resResultResponse;
    }

    @Override
    public ResultResponse<List<NodeRes>> getFilecoinNodelist() {
        return fileCoinOperation.getFilecoinNodelist();
    }

    @Override
    public ResultResponse<List<FileCoinRes>> getFilecoinDirectorylist(Integer page, Integer limit) {
        return fileCoinOperation.getFilecoinDirectorylist(page, limit);
    }

    @Override
    public ResultResponse<String> uploadIpfsFile(UploadIpfsFileReq uploadIpfsFileReq, CallBack callBackReceiveRequestid, CallBack callBackFinish) {
        ResultResponse<String> ipfsFile = ipfsFileOperation.uploadIpfsFile(uploadIpfsFileReq, callBackReceiveRequestid, callBackFinish);
        return ipfsFile;
    }

    @Override
    public ResultResponse<FileRetrieveBalanceRes> calculateRetrievePrice(Long size) {
        ResultResponse<FileRetrieveBalanceRes> resResultResponse = fileCoinOperation.calculateRetrievePrice(size);
        return resResultResponse;
    }

    @Override
    public ResultResponse<Boolean> retrieve(RetrieveReq retrieveReq) throws MtywApiException {
        ResultResponse<Boolean> resResultResponse = fileCoinOperation.retrieve(retrieveReq);
        return resResultResponse;
    }

    @Override
    public ResultResponse<FilecoinInfoRes> fileInfo(FilecoinInfoReq filecoinInfoReq) throws MtywApiException {
        ResultResponse<FilecoinInfoRes> resResultResponse = fileCoinOperation.fileInfo(filecoinInfoReq);
        return resResultResponse;
    }

    @Override
    public ResultResponse<ObjectGetRes> objectGet(String filepath) {
        return ipfsFileOperation.objectGet(filepath);
    }

    @Override
    public ResultResponse<List<RegionRes>> getAllRegionList() {
        return ipfsFileOperation.getAllRegionList();
    }

    @Override
    public ResultResponse<FileDetailRes> backupManagement(String filepath) {
        return ipfsFileOperation.backupManagement(filepath);
    }

    @Override
    public ResultResponse<List<RegionRes>> getUsableRegionList(Long size) {
        return ipfsFileOperation.getUsableRegionList(size);
    }

    @Override
    public ResultResponse<FileInfoRes> searchIpfsDirectorylist(String filepath, String fileName, String regionId) {
        return ipfsFileOperation.searchIpfsDirectorylist(filepath, fileName, regionId);
    }

    @Override
    public ResultResponse<FileInfoRes> getIpfsDirectorylist(String filepath) {
        return ipfsFileOperation.getIpfsDirectorylist(filepath);
    }

    @Override
    public ResultResponse<Boolean> deleteIpfsfile(String filepath, List<String> nodeids) {
        return ipfsFileOperation.deleteIpfsfile(filepath, nodeids);
    }

    @Override
    public ResultResponse<Boolean> deleteIpfsfileList(List<String> filepathlist) {
        return ipfsFileOperation.deleteIpfsfileList(filepathlist);
    }

    @Override
    public ResultResponse<Boolean> copyfile(String filepath, List<Integer> regionids) {
        return ipfsFileOperation.copyfile(filepath, regionids);
    }

    @Override
    public ResultResponse<Boolean> movefile(String filepath, String nodeId, Integer regionId) {
        return ipfsFileOperation.movefile(filepath, nodeId, regionId);
    }

    @Override
    public ResultResponse<Boolean> movefileToDirectory(String toDirectoryPath, String filepath) {
        return ipfsFileOperation.movefileToDirectory(toDirectoryPath, filepath);
    }

    @Override
    public ResultResponse<Boolean> renameDirectory(String fromDirectoryPath, String toDirectoryPath) {
        return ipfsFileOperation.renameDirectory(fromDirectoryPath, toDirectoryPath);
    }
}
