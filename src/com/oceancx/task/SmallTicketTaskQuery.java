package com.oceancx.task;

/**
 * Created by oceancx on 15/8/14.
 */
public class SmallTicketTaskQuery extends MultiTaskQuery<TrafficParam,String> {
    public SmallTicketTaskQuery(TrafficParam trafficParam) {
        super(trafficParam);
    }

    @Override
    protected String query(TrafficParam trafficParam) {
        String result=sendHttpReq();
        return result;
    }

    private String sendHttpReq() {
        return null;
    }
}
