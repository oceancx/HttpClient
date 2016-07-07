package com.oceancx.task;

import java.util.List;

/**
 * Created by oceancx on 15/8/15.
 */
public class PreverifyHotelTask extends MultiTask<String ,String> {

    public PreverifyHotelTask(List<String> strings) {
        super(strings);
    }

    @Override
    protected MultiTaskQuery<String, String> createQuery(String s) {
        return null;
    }
}
