package com.mtyw.storage.internal;


import com.mtyw.storage.common.*;
import com.mtyw.storage.constant.ResourePathConstant;
import com.mtyw.storage.model.request.ipfs.CreateDirRequest;
import com.mtyw.storage.model.request.ipfs.DownloadIpfsFileRequest;
import com.mtyw.storage.model.request.ipfs.DownloadIpfsFileSignRequest;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.ipfs.FileDownloadResponse;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class IpfsFileOperation extends FileCommonOperation {

    public IpfsFileOperation(ServiceClient client, String accessKey, String accessSecret) {
        super(client, accessKey, accessSecret);
    }

    public ResultResponse createDir(CreateDirRequest createDirRequest) {
        Request request = new MFSSRequestBuilder<>(createDirRequest,false).build();
        request.setResourcePath(ResourePathConstant.CREATEDIR_RESOURCE);
        request.addHeader("token", "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5MCIsImlhdCI6MTYwNzQyNTIyMn0.HGmoPPVMPfewPTdGFN4J3MnSAJBOx6GPb3eoV8fpdIE");
        ResultResponse resultResponse = commonParserExcute(request, ResultResponse.class);
        return resultResponse;
    }

    public ResultResponse<Void> downloadIpfsFile(String filePath, String saveDir) {
        DownloadIpfsFileSignRequest param = new DownloadIpfsFileSignRequest(filePath);
        Request request = new MFSSRequestBuilder<>(param, false).build();
        request.setResourcePath(ResourePathConstant.DOWNLOAD_IPFS_SIGN);
        ResultResponse<FileDownloadResponse> response = commonParserExcute(request, FileDownloadResponse.class);
        if (!response.isSuccess()) {
            return ResultResponse.fail(response.getMsg());
        }
        FileDownloadResponse data = response.getData();
        DownloadIpfsFileRequest download = new DownloadIpfsFileRequest(data);
        Request dr = new MFSSRequestBuilder<>(download, false).build();
        dr.setResourcePath(ResourePathConstant.DOWNLOAD_IPFS);
        dr.setUrl(data.getNodeAddr());
        ServiceClient.Request httpRequest = buildRequest(dr);
        try {
            downloadFile(httpRequest.getUri(), saveDir, data.getFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultResponse.suc();
    }

    /**
     * zero copy
     */
    public void downloadFile(String url, String saveDir, String fileName) throws IOException {
        try (InputStream ins = new URL(url).openStream()) {
            Path target = Paths.get(saveDir, fileName);
            Files.createDirectories(target.getParent());
            Files.copy(ins, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }


}
