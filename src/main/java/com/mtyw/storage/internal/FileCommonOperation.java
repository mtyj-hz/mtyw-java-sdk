package com.mtyw.storage.internal;

import com.mtyw.storage.common.*;
import com.mtyw.storage.encryption.MFSSRequestSigner;
import com.mtyw.storage.encryption.RequestSigner;
import com.mtyw.storage.exception.MtywApiException;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.parser.ResponseParser;
import com.mtyw.storage.parser.ResponserManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static com.mtyw.storage.parser.ResponserManager.commonresponseParser;
import static com.mtyw.storage.util.LogUtils.logException;

/**
 * Abstract base class that provides some common functionalities for MFSS
 */
public abstract class FileCommonOperation {

    protected ServiceClient client;
    private String accesskey;
    private String accesssecret;



    protected FileCommonOperation(ServiceClient client, String accesskey, String accesssecret) {
        this.client = client;
        this.accesskey = accesskey;
        this.accesssecret = accesssecret;

    }

    protected <T> ResultResponse<T> excute(Request request,
                                           Class<T> responseClass, ResponseParser responseParser) throws MtywApiException {

        request.getHeaders().putAll(client.getClientConfiguration().getDefaultHeaders());

        Context context = createDefaultContext(accesskey,accesssecret);
        Response response = client.sendRequest(request,context);
        try {
            return responseParser.parse(response, responseClass);
        } catch (IOException rpe) {
            logException("Unable to parse response error: ", rpe);
            throw new MtywApiException("Unable to parse response error",rpe);
        }
    }

    protected <T> ResultResponse<List<T>> excutelist(Request request,
                                                Class<T> responseClass, ResponseParser responseParser) throws MtywApiException {

        request.getHeaders().putAll(client.getClientConfiguration().getDefaultHeaders());

        Context context = createDefaultContext(accesskey,accesssecret);
        Response response = client.sendRequest(request,context);
        try {
            return responseParser.parseList(response, responseClass);
        } catch (IOException rpe) {
            logException("Unable to parse response error: ", rpe);
            throw new MtywApiException("Unable to parse response error",rpe);
        }
    }
    protected <T> ResultResponse<T>  commonParserExcute(Request request, Class<T> responseClass) throws MtywApiException {

        return excute(request, responseClass, commonresponseParser);
    }

    protected <T> ResultResponse<List<T>>  commonParserExcutelist(Request request, Class<T> responseClass) throws MtywApiException {

        return excutelist(request, responseClass, commonresponseParser);
    }

    protected Context createDefaultContext(String accesskey, String accesssecret) {
        Context context = new Context();
        context.setSigner(createSigner(accesskey, accesssecret));
        context.setAccesskey(accesskey);
        return context;
    }

    public static RequestSigner createSigner(String accesskey, String accesssecret) {
        return new MFSSRequestSigner(accesskey, accesssecret);
    }

    protected ServiceClient.Request buildRequest(Request request) throws MtywApiException {
        request.getHeaders().putAll(client.getClientConfiguration().getDefaultHeaders());
        Context context = createDefaultContext(accesskey, accesssecret);
        return client.buildRequest(request,context);
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

    public String getAccesssecret() {
        return accesssecret;
    }

    public void setAccesssecret(String accesssecret) {
        this.accesssecret = accesssecret;
    }

    /**
     * zero copy
     */
    protected void downloadFile(String url, String saveDir, String fileName) throws IOException {
        try (InputStream ins = new URL(url).openStream()) {
            Path target = Paths.get(saveDir, fileName);
            Files.createDirectories(target.getParent());
            Files.copy(ins, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
