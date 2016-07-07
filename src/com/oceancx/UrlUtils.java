package com.oceancx;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by oceancx on 15/8/6.
 */
public class UrlUtils {
    public static final String HOST = "http://120.132.77.8";
    public static final String CONTENT_TYPE="application/x-www-form-urlencoded";
    public static final String getList_1100 = "/mjApi/itinerary/type/getlist";
    public static final String getData_1101="/mjApi/itinerary/type/getdata";
    public static final String getVplan_v201="/mjApi/reqdata/mod/verification";
    public static final String getVplan_v302="";
    public static final String api_Login="/mjApiNew/user";
    public static final String api_SearchTraffic="/mjApi/reqdata/mod/dpm";

    public static UrlEncodedFormEntity convertSimplejsonToEntity(HashMap<String, String> map) {


        List<NameValuePair> list = new ArrayList<>();
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Iterator<Map.Entry<String, String>> iterator = entries.iterator(); iterator.hasNext(); ) {

            Map.Entry<String, String> entry = iterator.next();
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            list.add(pair);
            Global.Log(entry.getKey() + ":" + entry.getValue());

        }
        try {
            return new UrlEncodedFormEntity(list);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
