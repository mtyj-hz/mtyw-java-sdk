package com.mtyw.storage.internal;


import com.mtyw.storage.common.*;
import com.mtyw.storage.constant.ResourePathConstant;
import com.mtyw.storage.model.request.ipfs.CreateDirRequest;
import com.mtyw.storage.model.request.ipfs.DownloadIpfsFileRequest;
import com.mtyw.storage.model.request.ipfs.DownloadIpfsFileSignRequest;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.model.response.ipfs.FileDownloadResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

import java.io.*;

public class IpfsFileOperation extends FileCommonOperation {
    public IpfsFileOperation(ServiceClient client, String accesskey, String accesssecret) {
        super(client, accesskey, accesssecret);
    }

    public ResultResponse createDir(CreateDirRequest createDirRequest) {
        Request request = new MFSSRequestBuilder<>(createDirRequest,false).build();
        request.setResourcePath(ResourePathConstant.CREATEDIR_RESOURCE);
        request.addHeader("token", "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5MCIsImlhdCI6MTYwNzQyNTIyMn0.HGmoPPVMPfewPTdGFN4J3MnSAJBOx6GPb3eoV8fpdIE");
        ResultResponse resultResponse = commonParserExecute(request, ResultResponse.class);
        return resultResponse;
    }

    public ResultResponse downloadIpfsFile(String filePath) {
        DownloadIpfsFileSignRequest param = new DownloadIpfsFileSignRequest(filePath);
        Request request = new MFSSRequestBuilder<>(param).build();
        request.setResourcePath(ResourePathConstant.DOWNLOAD_IPFS_SIGN);
        request.addHeader("token", "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5MCIsImlhdCI6MTYwNzQyNTIyMn0.HGmoPPVMPfewPTdGFN4J3MnSAJBOx6GPb3eoV8fpdIE");
        ResultResponse<FileDownloadResponse> response = commonParserExecute(request, FileDownloadResponse.class);
        if (!response.isSuccess()) {
            return ResultResponse.fail(response.getMsg());
        }
        FileDownloadResponse data = response.getData();
        DownloadIpfsFileRequest download = new DownloadIpfsFileRequest(data);
        Request dr = new MFSSRequestBuilder<>(download).build();
        dr.setUrl(data.getNodeAddr());
        Response response2 = commonParserExecute(dr);
        try {
            HttpEntity entity = response2.getHttpResponse().getEntity();
            File desc = new File("/Users/hsw" + File.separator + data.getFilename());
//            File folder = desc.getParentFile();
//            folder.mkdirs();
            try (InputStream is = entity.getContent();
                 OutputStream os = new FileOutputStream(desc)) {
                byte[] bs = new byte[1024];
                int len = 0; //实际读取个数
                while ((len = is.read(bs)) > 0) {   //read返回 读取的字节数
                    os.write(bs, 0, len); //从0到len位置写入到数组bs中
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
