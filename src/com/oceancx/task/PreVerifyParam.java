package com.oceancx.task;

import java.util.List;

/**
 * Created by oceancx on 15/8/14.
 */
public class PreVerifyParam {
    private Integer type;
    private HotelParam hotelParam;
    private List<TrafficParam> trafficParamList;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public HotelParam getHotelParam() {
        return hotelParam;
    }

    public void setHotelParam(HotelParam hotelParam) {
        this.hotelParam = hotelParam;
    }

    public List<TrafficParam> getTrafficParam() {
        return trafficParamList;
    }

    public void setTrafficParamList(List<TrafficParam> trafficParamList) {
        this.trafficParamList = trafficParamList;
    }

}
