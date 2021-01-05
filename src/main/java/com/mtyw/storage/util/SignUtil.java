package com.mtyw.storage.util;

import com.mtyw.storage.constant.MFSSConstants;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: xiaoli
 * @Date: 2020/12/28 10:23 上午
 */
public class SignUtil {

    public final static String equalsymbol = "=";

    public static String sign( Map<String, Object> paramValues
            , List<String> ignoreParamNames
            , List<String> prefixParamNames
            , String secret) {
        StringBuilder sb = new StringBuilder();
        List<String> paramNames = new ArrayList<String>(paramValues.size());
        paramNames.addAll(paramValues.keySet());
        if (ignoreParamNames != null && ignoreParamNames.size() > 0) {
            for (String ignoreParamName : ignoreParamNames) {
                paramNames.remove(ignoreParamName);
            }
        }
        Collections.sort(paramNames);
        for (String prefixparamName : prefixParamNames) {
            sb.append(prefixparamName).append(equalsymbol).append(paramValues.get(prefixparamName));
        }
        for (String paramName : paramNames) {
            sb.append(paramName).append(equalsymbol).append(paramValues.get(paramName));
        }
        sb.append(secret);
        String sign =  getSHA256StrJava(sb.toString(), MFSSConstants.DEFAULT_CHARSET_NAME);
        return sign;

    }

    /**
 　　* 利用java原生的摘要实现SHA256加密
 　　* @param str 加密后的报文
 　　* @return
 　　*/
    public static String getSHA256StrJava(String str, String charset) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(charset));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 　　* 将byte转为16进制
     * 　　* @param bytes
     * 　　* @return
     *
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
