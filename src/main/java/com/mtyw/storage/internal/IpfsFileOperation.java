package com.mtyw.storage.internal;


import com.mtyw.storage.HttpMethod;
import com.mtyw.storage.common.*;
import com.mtyw.storage.constant.ResourePathConstant;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.model.request.ipfs.*;
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

import static com.mtyw.storage.constant.MFSSConstants.*;
import static com.mtyw.storage.util.LogUtils.logException;

public class IpfsFileOperation extends FileCommonOperation {

    public IpfsFileOperation(ServiceClient client, String accessKey, String accessSecret) {
        super(client, accessKey, accessSecret);
    }
    public ResultResponse<String> uploadIpfsFile(UploadIpfsFileReq uploadIpfsFileReq, CallBack callBackReceiveRequestid, CallBack callBackFinish) throws MtywApiException {
        Request request = new MFSSRequestBuilder<>(uploadIpfsFileReq,false).build();
        request.setResourcePath(ResourePathConstant.UPLOAD_IPFS_SIGN_RESOURCE);
        ResultResponse<UploadIpfsSignInfoDTO> uploadIpfsSignDTO = commonParserExcute(request, UploadIpfsSignInfoDTO.class);
        if (uploadIpfsSignDTO.isSuccess()) {
            if (callBackReceiveRequestid != null) {
                try{
                    callBackReceiveRequestid.invoke(callBackReceiveRequestid.getArgs());
                }catch (Exception e) {
                    logException("Unable to invoke callBackReceiveRequestid error: ", e.getMessage());
                }
            }
            UploadIpfsAddReq uploadFilecoinReq = new UploadIpfsAddReq();
            uploadFilecoinReq.setExpire(uploadIpfsSignDTO.getData().getExpiretime());
            uploadFilecoinReq.setFilepath(uploadIpfsSignDTO.getData().getFilepath());
            uploadFilecoinReq.setSign(uploadIpfsSignDTO.getData().getSign());
            uploadFilecoinReq.setTimestamp(uploadIpfsSignDTO.getData().getTimestamp());
            uploadFilecoinReq.setFilesize(uploadIpfsSignDTO.getData().getFilesize());
            uploadFilecoinReq.setNodeip(uploadIpfsSignDTO.getData().getNodeIp());
            uploadFilecoinReq.setRegions(uploadIpfsSignDTO.getData().getRegionid());
            uploadFilecoinReq.setUploadid(uploadIpfsSignDTO.getData().getUploadid());
            uploadFilecoinReq.setUserid(uploadIpfsSignDTO.getData().getUserid());
            Request uploadFilecoinSignReq = new MFSSRequestBuilder<>(uploadFilecoinReq,false).build();
            uploadFilecoinSignReq.setResourcePath(ResourePathConstant.UPLOAD_IPFS_ADD_RESOURCE);
            uploadFilecoinSignReq.addHeader(HttpHeaders.CONTENT_TYPE, DEFAULT_MULTIPART_CONTENT_TYPE);
            if (uploadIpfsFileReq.getUploadRequestId()!= null && uploadIpfsFileReq.getUploadRequestId() > 0) {
                UploadIpfsCheckpointReq uploadIpfsCheckpointReq = new UploadIpfsCheckpointReq();
                uploadIpfsCheckpointReq.setFilepath(uploadIpfsFileReq.getFilepath());
                uploadIpfsCheckpointReq.setUploadid(uploadIpfsFileReq.getUploadRequestId().intValue());
                Long checkpoint = getCheckpoint(uploadIpfsCheckpointReq);
                uploadFilecoinSignReq.addHeader(HttpHeaders.RANGE, String.format(RANGE_HEADER, checkpoint));
            }else {
                uploadFilecoinSignReq.addHeader(HttpHeaders.RANGE, String.format(RANGE_HEADER, 0));

            }
            uploadFilecoinSignReq.addHeader(HttpHeaders.CONNECTION, HTTP_OBJECT_CONNECTION);
            uploadFilecoinSignReq.setMethod(HttpMethod.POST);
            uploadFilecoinSignReq.setContentLength(uploadIpfsFileReq.getFileSize());
            uploadFilecoinSignReq.setContent(uploadIpfsFileReq.getInputStream());
            uploadFilecoinSignReq.setUrl(uploadIpfsSignDTO.getData().getNodeAddr());
            ResultResponse<String> resultResponse = commonParserExcute(uploadFilecoinSignReq, String.class);
            if (callBackFinish != null) {
                try{
                    callBackFinish.invoke(callBackFinish.getArgs());
                }catch (Exception e) {
                    logException("Unable to invoke callBackFinish error: ", e.getMessage());
                }
            }
            return resultResponse;
        }
        return ResultResponse.fail(uploadIpfsSignDTO.getCode(), uploadIpfsSignDTO.getMsg());
    }

    private Long getCheckpoint(UploadIpfsCheckpointReq uploadIpfsCheckpointReq) {
        Request request = new MFSSRequestBuilder<>(uploadIpfsCheckpointReq,false).build();
        request.setResourcePath(ResourePathConstant.UPLOAD_IPFS_CHECKPOINT_RESOURCE);
        ResultResponse<Long> checkpoint = commonParserExcute(request, Long.class);
        if (checkpoint.isSuccess()) {
            return checkpoint.getData();
        }
        return 0l;
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

    public ResultResponse<ObjectGetRes> objectGet(String filepath) {
        Request request = new MFSSRequestBuilder<>("filepath", filepath).build();
        request.setResourcePath(ResourePathConstant.IPFS_INSPECT_SIGN);
        ResultResponse<FileInspectRes> sign = commonParserExcute(request, FileInspectRes.class);
        if (!sign.isSuccess()) {
            return ResultResponse.fail(sign.getMsg());
        }
        FileInspectRes data = sign.getData();
        ObjectGetReq getReq = new ObjectGetReq(data.getNodeip(), data.getCid(), data.getSign(), data.getTimestamp());
        Request getR = new MFSSRequestBuilder<>(getReq, false).build();
        getR.setResourcePath(ResourePathConstant.OBJECT_GET);
        getR.setUrl(data.getNodeAddr());
        return commonParserExcute(getR, ObjectGetRes.class);
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
