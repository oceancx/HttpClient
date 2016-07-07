package com.oceancx.task;

import java.util.List;

/**
 * 交通预验证
 *
 * 根据List中的Route
 * Created by oceancx on 15/8/12.
 */
public class TrafficPreVerifyTask extends MultiTask<String,String> {

    public TrafficPreVerifyTask(List<String> strings) {
        super(strings);
    }

    @Override
    protected MultiTaskQuery<String, String> createQuery(String s) {
        return new TrafficPreVerifyQuery(s);
    }
}
