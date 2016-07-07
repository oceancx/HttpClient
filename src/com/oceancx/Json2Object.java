package com.oceancx;

/**
 * Created by oceancx on 15/8/11.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public class Json2Object {
    public static final String createJsonString(Object object) {
        String jsonString = JSON.toJSONString(object);
        return jsonString;
    }

    public static final JSONObject parseObject(String jsonString) {
        JSONObject ob = null;
        try {
            ob = JSON.parseObject(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ob;
    }

    public static final String createMapToString(Map m) {
        String s = JSONObject.toJSONString(m);
        return s;
    }

    public static final <T> T createJavaBean(String jsonString, Class<T> cls) {
        // Json to javabean
        if (jsonString==null|| jsonString.equals("") )
            return null;
        try {
            // 去掉这样结构的[{}]“[]”
            if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
                jsonString = jsonString.substring(1, jsonString.length() - 1);
            }

            int _idx1 = jsonString.indexOf("{");
            if (_idx1 == -1) {
                return null;
            } else if (_idx1 != 0) {
                jsonString = jsonString.substring(_idx1);
            }
            int _idx2 = jsonString.lastIndexOf("}");
            if (_idx2 == -1) {
                return null;
            } else if (_idx2 != jsonString.length() - 1) {
                jsonString = jsonString.substring(0, _idx2);
            }

            T t = JSON.parseObject(jsonString, cls);
            return t;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final <T> List<T> createJavaListBean(String jsonString,
                                                       Class<T> cls) {
        // 使用fastJson 解析.
        try {
            List<T> list = null;
            if (jsonString==null|| jsonString.equals(""))
                return null;

            if (!jsonString.startsWith("[") || !jsonString.endsWith("]")) {
                return null;
            }

            list = JSON.parseArray(jsonString, cls);
            return list;
        } catch (JSONException e) {
        }
        return null;
    }
}
