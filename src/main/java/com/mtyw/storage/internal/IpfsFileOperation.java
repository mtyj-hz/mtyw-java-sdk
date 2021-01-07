package com.mtyw.storage.internal;


import com.mtyw.storage.HttpMethod;
import com.mtyw.storage.common.*;
import com.mtyw.storage.constant.MFSSConstants;
import com.mtyw.storage.constant.ResourePathConstant;
import com.mtyw.storage.model.request.ipfs.CreateDirRequest;
import com.mtyw.storage.model.request.ipfs.DownloadIpfsFileRequest;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.ipfs.*;
import com.mtyw.storage.util.HttpHeaders;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.mtyw.storage.constant.MFSSConstants.DEFAULT_MULTIPART_CONTENT_TYPE;

public class IpfsFileOperation extends FileCommonOperation {

    public IpfsFileOperation(ServiceClient client, String accessKey, String accessSecret) {
        super(client, accessKey, accessSecret);
    }

    public ResultResponse<Boolean> createDir(CreateDirRequest createDirRequest) {
        Request request = new MFSSRequestBuilder<>(createDirRequest, false).build();
        request.setResourcePath(ResourePathConstant.CREATEDIR_RESOURCE);
        request.setMethod(HttpMethod.POST);
        ResultResponse<Boolean> resultResponse = commonParserExcute(request, Boolean.class);
        return resultResponse;
    }

    public ResultResponse<Void> downloadIpfsFile(String filePath, String saveDir) {
        Request request = new MFSSRequestBuilder<>("filepath", filePath).build();
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

    public ResultResponse<FileInspectRes> ipfsInspectsign(String filepath) {
        Request request = new MFSSRequestBuilder<>("filepath", filepath).build();
        request.setResourcePath(ResourePathConstant.IPFS_INSPECT_SIGN);
        return commonParserExcute(request, FileInspectRes.class);
    }


    /**
     * zero copy
     */
    private void downloadFile(String url, String saveDir, String fileName) throws IOException {
        try (InputStream ins = new URL(url).openStream()) {
            Path target = Paths.get(saveDir, fileName);
            Files.createDirectories(target.getParent());
            Files.copy(ins, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public ResultResponse<List<RegionRes>> getAllRegionList() {
        Request request = new MFSSRequestBuilder<>().build();
        request.setResourcePath(ResourePathConstant.GET_ALL_REGION_LIST);
        return commonParserExcutelist(request, RegionRes.class);
    }

    public ResultResponse<FileDetailRes> backupManagement(String filepath) {
        Request request = new MFSSRequestBuilder<>("filepath", filepath).build();
        request.setResourcePath(ResourePathConstant.BACKUP_MANAGEMENT);
        return commonParserExcute(request, FileDetailRes.class);
    }

    public ResultResponse<List<RegionRes>> getUsableRegionList(Long size) {
        Request request = new MFSSRequestBuilder<>("size", size).build();
        request.setResourcePath(ResourePathConstant.GET_USABLE_REGION_LIST);
        return commonParserExcutelist(request, RegionRes.class);
    }

    public ResultResponse<FileInfoRes> searchIpfsDirectorylist(String filepath, String fileName, String regionId) {
        Map<String, String> param = new LinkedHashMap<>();
        param.put("filepath", filepath);
        param.put("fileName", fileName);
        param.put("regionId", regionId);
        Request request = new MFSSRequestBuilder<>(param).build();
        request.setResourcePath(ResourePathConstant.SEARCH_IPFS_DIRECTORYLIST);
        return commonParserExcute(request, FileInfoRes.class);
    }

    public ResultResponse<FileInfoRes> getIpfsDirectorylist(String filepath) {
        Request request = new MFSSRequestBuilder<>("filepath", filepath).build();
        request.setResourcePath(ResourePathConstant.GET_IPFS_DIRECTORYLIST);
        return commonParserExcute(request, FileInfoRes.class);
    }

    public ResultResponse<Boolean> deleteIpfsfile(String filepath, List<String> nodeids) {
        MFSSRequestBuilder<String> requestBuilder = new MFSSRequestBuilder<>("filepath", filepath);
        requestBuilder.setInputStream(MFSSRequestBuilder.objToInputstream(nodeids));
        Request request = requestBuilder.build();
        request.setMethod(HttpMethod.POST);
        request.setResourcePath(ResourePathConstant.DELETE_IPFSFILE);
        return commonParserExcute(request, Boolean.class);
    }

    public ResultResponse<Boolean> deleteIpfsfileList(List<String> filepathlist) {
        Request request = new MFSSRequestBuilder<>(filepathlist, true).build();
        request.setMethod(HttpMethod.POST);
        request.setResourcePath(ResourePathConstant.DELETE_IPFSFILE_LIST);
        return commonParserExcute(request, Boolean.class);
    }

    public ResultResponse<Boolean> copyfile(String filepath, List<Integer> regionids) {
        MFSSRequestBuilder<String> requestBuilder = new MFSSRequestBuilder<>("filepath", filepath);
        requestBuilder.setInputStream(MFSSRequestBuilder.objToInputstream(regionids));
        Request request = requestBuilder.build();
        request.setMethod(HttpMethod.POST);
        request.setResourcePath(ResourePathConstant.COPYFILE);
        return commonParserExcute(request, Boolean.class);
    }

    public ResultResponse<Boolean> movefile(String filepath, String nodeId, Integer regionId) {
        Map<String, String> param = new LinkedHashMap<>();
        param.put("filepath", filepath);
        param.put("nodeId", nodeId);
        param.put("regionId", regionId.toString());
        Request request = new MFSSRequestBuilder<>(param).build();
        request.setMethod(HttpMethod.POST);
        request.setResourcePath(ResourePathConstant.MOVEFILE);
        return commonParserExcute(request, Boolean.class);
    }

    public ResultResponse<Boolean> movefileToDirectory(String toDirectoryPath, String filepath) {
        Map<String, String> param = new LinkedHashMap<>();
        param.put("toDirectoryPath", toDirectoryPath);
        param.put("filepath", filepath);
        Request request = new MFSSRequestBuilder<>(param).build();
        request.setMethod(HttpMethod.POST);
        request.setResourcePath(ResourePathConstant.MOVEFILE_TO_DIRECTORY);
        return commonParserExcute(request, Boolean.class);
    }

    public ResultResponse<Boolean> renameDirectory(String fromDirectoryPath, String toDirectoryPath) {
        Map<String, String> param = new LinkedHashMap<>();
        param.put("fromDirectoryPath", fromDirectoryPath);
        param.put("toDirectoryPath", toDirectoryPath);
        Request request = new MFSSRequestBuilder<>(param).build();
        request.setMethod(HttpMethod.POST);
        request.setResourcePath(ResourePathConstant.RENAME_DIRECTORY);
        return commonParserExcute(request, Boolean.class);
    }
}
