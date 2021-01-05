package com.mtyw.storage.util;

import org.apache.http.ParseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

public class CommonUtil {

    public static String inputstreamToString(InputStream inputStream) throws IOException, ParseException {
        String str = null;
        if (inputStream == null) {
            return null;
        } else {
            try {
                ByteArrayOutputStream result = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    result.write(buffer, 0, length);
                }
                str = result.toString(StandardCharsets.UTF_8.name());
                return str;
            }finally {
                inputStream.close();
                return str;
            }
        }
    }

    /**
     * object is not null
     * @param object
     * @return
     */
    public static  boolean isObjectFieldEmpty(Object object) {
        boolean flag = false;
        if (object != null) {
            Class<?> entity = object.getClass();
            Field[] fields = entity.getDeclaredFields();//获取该类的所有成员变量（私有的）
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    if (field.get(object) == null || "".equals(field.get(object))) {
                        flag = true;
                        break;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}
