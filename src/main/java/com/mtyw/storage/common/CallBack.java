package com.mtyw.storage.common;


import com.mtyw.storage.model.request.ipfs.UploadIpfsCallbackReq;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CallBack {
    /**
     * 类的实例 初始化
     * eg. TestClass tcb = new TestClass();
     */
    Object ownobj;
    /**
     *  选中某个类的某个方法
     * eg. tcb.getClass().getMethod("MethodA", UploadIpfsCallbackReq.class)
     */
    Method execute;
    /**
     * 方法的传参
     */
    UploadIpfsCallbackReq args;
    public CallBack(Object o, Method method) {
        ownobj = o;
        execute = method;
    }
    public Object invoke(UploadIpfsCallbackReq args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (args == null || ownobj == null || execute == null) {
            return null;
        }
        return execute.invoke(ownobj, args);
    }

    public Object getOwnobj() {
        return ownobj;
    }

    public void setOwnobj(Object ownobj) {
        this.ownobj = ownobj;
    }

    public Method getExecute() {
        return execute;
    }

    public void setExecute(Method execute) {
        this.execute = execute;
    }

    public UploadIpfsCallbackReq getArgs() {
        return args;
    }

    public void setArgs(UploadIpfsCallbackReq args) {
        this.args = args;
    }
}
