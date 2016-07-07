package com.oceancx.task;

import java.util.List;

/**
 * Created by oceancx on 15/8/14.
 */
public class PreVerifyTaskQuery extends MultiTaskQuery<PreVerifyParam, PreVerifyResult> {
    private static final Integer HOTEL_PREVERIFY = 0X01;

    public PreVerifyTaskQuery(PreVerifyParam preVerifyParam) {
        super(preVerifyParam);
    }

    @Override
    protected PreVerifyResult query(PreVerifyParam preVerifyParam) {
        /**
         * 每一个param对应一个item
         * 首先取出type 然后根据type 取出参数
         */
        HotelParam hotelParam = preVerifyParam.getHotelParam();
        List<TrafficParam> trafficParamList = preVerifyParam.getTrafficParam();

        String hotelResutl= sendHotelHttpReq(hotelParam);

        MultiTask<TrafficParam,String > trafficMultiTask=new MultiTask<TrafficParam,String>(trafficParamList) {
            @Override
            protected MultiTaskQuery<TrafficParam, String> createQuery(TrafficParam trafficParam) {
                return new SmallTicketTaskQuery(trafficParam);
            }
        };
        trafficMultiTask.start(true);
        List<String> ticketResults = trafficMultiTask.getResults();




        return null;
    }

    private String sendHotelHttpReq(HotelParam hotelParam) {

        return null;
    }
}
