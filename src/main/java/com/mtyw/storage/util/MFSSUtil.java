package com.mtyw.storage.util;

import com.mtyw.storage.common.Response;

import java.io.IOException;

public class MFSSUtil {

    public static void safeCloseResponse(Response response) {
        try {
            response.close();
        } catch (IOException e) {
        }
    }
}
