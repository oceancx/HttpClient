package com.oceancx.task;

import java.util.List;

/**
 * 每一个ListParam代表一个item
 * 从此item中 取出 一个酒店 进行验证  取出n个交通 进行验证
 * 验证结果回来后 汇总
 * Created by oceancx on 15/8/14.
 */
public class PreVerifyTask extends MultiTask<PreVerifyParam,PreVerifyResult> {

    public PreVerifyTask(List<PreVerifyParam> preVerifyQueries) {
        super(preVerifyQueries);
    }

    @Override
    protected PreVerifyTaskQuery createQuery(PreVerifyParam preVerifyParam) {
        return new PreVerifyTaskQuery(preVerifyParam);
    }
}
