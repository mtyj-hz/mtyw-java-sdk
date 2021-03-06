package com.mtyw.storage.parser;

import com.alibaba.fastjson.JSONObject;
import com.mtyw.storage.common.Response;
import com.mtyw.storage.constant.MFSSConstants;
import com.mtyw.storage.model.response.ResultResponse;
import com.mtyw.storage.util.CommonUtil;

import java.io.IOException;
import java.util.List;

import static com.mtyw.storage.util.MFSSUtil.safeCloseResponse;

public final class ResponserManager {

    public static final CommonresponseParser commonresponseParser = new CommonresponseParser();


    public static final class CommonresponseParser implements ResponseParser {

        @Override
        public <T> ResultResponse<T>  parse(Response response, Class<T> tClass) throws IOException {
            try {

                String str = CommonUtil.inputstreamToString(response.getContent());
                if (str != null && !"".equals(str)) {
                    ResultResponse resultResponse =  JSONObject.parseObject(str, ResultResponse.class);
                    if (resultResponse.isSuccess()) {
                        if (resultResponse.getData() instanceof String) {
                            return resultResponse;
                        }
                        if (resultResponse.getData() instanceof Boolean) {
                            return resultResponse;
                        }
                        T ob = JSONObject.parseObject(resultResponse.getData().toString(), tClass);
                        resultResponse.setData(ob);
                    }
                    return resultResponse;
                }
                return null;
            } catch (IOException e){
                throw  e;
            } finally {
                safeCloseResponse(response);
            }
        }

        @Override
        public <T> ResultResponse<List<T>>   parseList(Response response, Class<T> tClass) throws IOException{          try {

            String str = CommonUtil.inputstreamToString(response.getContent());
            if (str != null && !"".equals(str)) {
                ResultResponse resultResponse =  JSONObject.parseObject(str, ResultResponse.class);
                List<T> ob = JSONObject.parseArray(resultResponse.getData().toString(), tClass);
                resultResponse.setData(ob);
                return resultResponse;
            }
            return null;
        } catch (IOException e){
            throw  e;
        } finally {
            safeCloseResponse(response);
        }
        }

    }


}
