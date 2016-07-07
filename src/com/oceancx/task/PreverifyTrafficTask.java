package com.oceancx.task;

import java.util.List;

/**
 * 将交通请求打包 发送到服务器
 * Created by oceancx on 15/8/15.
 */
public class PreverifyTrafficTask extends MultiTask<String,String> {


    public PreverifyTrafficTask(List<String> strings) {
        super(strings);
    }

    @Override
    protected MultiTaskQuery<String, String> createQuery(String s) {
        return null;
    }
}
