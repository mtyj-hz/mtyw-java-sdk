package com.mtyw.storage;

import com.mtyw.storage.common.CallBack;
import com.mtyw.storage.common.ClientHttpRequestConfiguration;
import com.mtyw.storage.common.DefaultServiceClient;
import com.mtyw.storage.common.ServiceClient;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.internal.FileCoinOperation;
import com.mtyw.storage.internal.IpfsFileOperation;
import com.mtyw.storage.model.request.filecoin.CalculatePriceReq;
import com.mtyw.storage.model.request.filecoin.RetrieveReq;
import com.mtyw.storage.model.request.filecoin.UploadFileCoinFileReq;
import com.mtyw.storage.model.request.ipfs.CreateDirRequest;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.filecoin.*;
import com.mtyw.storage.model.response.ipfs.FileDetailRes;
import com.mtyw.storage.model.response.ipfs.FileInfoRes;
import com.mtyw.storage.model.response.ipfs.FileInspectRes;
import com.mtyw.storage.model.response.ipfs.RegionRes;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.mtyw.storage.util.LogUtils.logException;

/**
 * @Author: xiaoli
 * @Date: 2020/12/29 10:46 上午
 */
public class MFSSClient implements MFSS{
    /* The default service client */
    private ServiceClient serviceClient;
    private String accesskey;
    private String accesssecret;
    /* The miscellaneous MFSS operations */
    private IpfsFileOperation ipfsFileOperation;
    private FileCoinOperation fileCoinOperation;


    public MFSSClient(String url, String accessKey ,String accessScret)  {
        this(url, accessKey,accessScret, null);
    }
    public MFSSClient(String url, String accessKey ,String accessScret, ClientHttpRequestConfiguration config) {
        config = config == null ? new ClientHttpRequestConfiguration() : config;
        URI uri = toURI(url);
        this.serviceClient = new DefaultServiceClient(config, uri);
        this.accesskey = accessKey;
        this.accesssecret = accessScret;
        initOperations();
    }

    private void initOperations() {
        this.ipfsFileOperation = new IpfsFileOperation(this.serviceClient, accesskey,accesssecret);
        this.fileCoinOperation = new FileCoinOperation(this.serviceClient, accesskey,accesssecret);

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
    public ResultResponse createdir(String parentpath, String dirname) {
        CreateDirRequest createDirRequest = new CreateDirRequest();
        createDirRequest.setDirectoryName(dirname);
        createDirRequest.setParentpath(parentpath);
        ResultResponse response  = ipfsFileOperation.createDir(createDirRequest);
        return response;
    }
    @Override
    public ResultResponse<String>  uploadFilecoinFile(UploadFileCoinFileReq uploadIpfsFileRequest, CallBack callBack) throws MtywApiException {
        ResultResponse<String>  response  = fileCoinOperation.uploadFilecoinFile(uploadIpfsFileRequest, new CallBack(null,null));
        return response;
    }

    @Override
    public ResultResponse<Void> downloadIpfsFile(String filePath, String saveDir) {
        return ipfsFileOperation.downloadIpfsFile(filePath, saveDir);
    }

    @Override
    public ResultResponse<List<FilecoinDateRes>> filecoinDatelist(){
        ResultResponse<List<FilecoinDateRes>> resultResponse = fileCoinOperation.filecoinDatelist();
        return resultResponse;
    }

    @Override
    public ResultResponse<FileBalanceRes> calculatePrice(CalculatePriceReq calculatePriceReq){
        ResultResponse<FileBalanceRes> resResultResponse = fileCoinOperation.calculatePrice(calculatePriceReq);
        return resResultResponse;
    }
    @Override
    public ResultResponse<List<NodeRes>> getFilecoinNodelist(){
        return fileCoinOperation.getFilecoinNodelist();
    }

    @Override
    public ResultResponse<List<FileCoinRes>> getFilecoinDirectorylist(Integer page, Integer limit){
        return fileCoinOperation.getFilecoinDirectorylist( page, limit);
    }
    @Override
    public ResultResponse<FileRetrieveBalanceRes> calculateRetrievePrice(Long size){
        ResultResponse<FileRetrieveBalanceRes> resResultResponse = fileCoinOperation.calculateRetrievePrice( size);
        return resResultResponse;
    }
    @Override
    public ResultResponse<Boolean> retrieve(RetrieveReq retrieveReq) throws MtywApiException{
        ResultResponse<Boolean> resResultResponse = fileCoinOperation.retrieve( retrieveReq);
        return resResultResponse;
    }

    @Override
    public ResultResponse<FileInspectRes> ipfsInspectsign(String filepath) {
        return null;
    }

    @Override
    public ResultResponse<List<RegionRes>> getAllRegionList() {
        return null;
    }

    @Override
    public ResultResponse<FileDetailRes> backupManagement(String filepath) {
        return null;
    }

    @Override
    public ResultResponse<List<RegionRes>> getUsableRegionList(Long size) {
        return null;
    }

    @Override
    public ResultResponse<FileInfoRes> searchIpfsDirectorylist(String filepath, String fileName, String regionId) {
        return null;
    }

    @Override
    public ResultResponse<FileInfoRes> getIpfsDirectorylist(String filepath) {
        return null;
    }

    @Override
    public ResultResponse<Boolean> deleteIpfsfile(String filepath, List<String> nodeids) {
        return null;
    }

    @Override
    public ResultResponse<Boolean> deleteIpfsfileList(List<String> filepathlist) {
        return null;
    }

    @Override
    public ResultResponse<Boolean> copyfile(String filepath, List<Integer> regionids) {
        return null;
    }

    @Override
    public ResultResponse<Boolean> movefile(String filepath, String nodeid, Integer regionid) {
        return null;
    }

    @Override
    public ResultResponse<Boolean> movefileToDirectory(String directorypath, String filepath) {
        return null;
    }

    @Override
    public ResultResponse<Boolean> renameDirectory(String fromDirectoryPath, String toDirectoryPath) {
        return null;
    }
}
