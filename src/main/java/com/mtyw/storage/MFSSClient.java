package com.mtyw.storage;

import com.mtyw.storage.common.CallBack;
import com.mtyw.storage.common.ClientHttpRequestConfiguration;
import com.mtyw.storage.common.DefaultServiceClient;
import com.mtyw.storage.common.ServiceClient;
import com.mtyw.storage.internal.FileCoinOperation;
import com.mtyw.storage.internal.IpfsFileOperation;
import com.mtyw.storage.model.request.filecoin.UploadFileCoinFileRequest;
import com.mtyw.storage.model.request.ipfs.CreateDirRequest;
import com.mtyw.storage.model.response.ResultResponse;

import java.net.URI;
import java.net.URISyntaxException;

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
        createDirRequest.setDirectoryName(parentpath);
        createDirRequest.setParentpath(dirname);
        ResultResponse response  = ipfsFileOperation.createDir(createDirRequest);
        return response;
    }
    @Override
    public ResultResponse uploadFilecoinFile(UploadFileCoinFileRequest uploadIpfsFileRequest, CallBack callBack){
        ResultResponse response  = fileCoinOperation.uploadFilecoinFile(uploadIpfsFileRequest, new CallBack(null,null));
        return response;
    }
}
