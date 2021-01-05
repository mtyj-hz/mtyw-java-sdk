package com.mtyw.storage.common;

import com.mtyw.storage.encryption.RequestSigner;

import java.util.LinkedList;
import java.util.List;

/**
 * HTTP request context.
 */
public class Context {

    /* Request signer */
    private RequestSigner signer;
    private String accesskey ;


    public RequestSigner getSigner() {
        return signer;
    }

    public void setSigner(RequestSigner signer) {
        this.signer = signer;
    }

    public String getAccesskey() {
        return accesskey;
    }

    public void setAccesskey(String accesskey) {
        this.accesskey = accesskey;
    }

}
