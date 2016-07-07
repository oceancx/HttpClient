package com.oceancx;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by oceancx on 15/8/19.
 */
public class DateUtil {
    //20141219_01:55

    /**
     * 时间日期工具类。
     *
     * @author dujun
     *
     */
    /**
     * 默认时区，HH:mm
     */
    private static final SimpleDateFormat HOUR_MIN_TO_DATE_FORMAT = new SimpleDateFormat(
            "HH:mm", Locale.getDefault());

    // 1代表: Colon 2 : Dash Underline
    private static final SimpleDateFormat yyyyMMdd_HHCmm = new SimpleDateFormat("yyyyMMdd_HH:mm" ,Locale.getDefault());

    /**
     * one hour time in sec.
     */
    public static final int SEC_ONE_HOUR = 60 * 60;

    public static final int SEC_8_HOUR = SEC_ONE_HOUR * 8;

    public static final int SEC_10_HOUR = SEC_ONE_HOUR * 10;
    private static Calendar cal= Calendar.getInstance(Locale.getDefault());
    public static String extrcMMdd(String yyyyMMdd_HHmm) {

        try {
            cal.setTime(yyyyMMdd_HHCmm.parse(yyyyMMdd_HHmm));
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String MM = complete2Digits(month);
            String dd = complete2Digits(day);
            Global.Log(MM+dd);
            return MM + dd;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
    public static String complete2Digits(int num) {
        if (num >= 100 || num < 0)
            throw new IllegalArgumentException("参数只能是2位数以内");
        return num < 10 ? "0" + num : num + "";
    }
    /**
     * @param time1
     * @param time2
     * @return 两个时间字符串的时间差，单位 秒 正数。 存在不可解析的时间字符，返回0；
     */
    public static final int getTimeInsecWith2TimeStrHHmm(String time1,
                                                         String time2) {
        int dur = 0;

        try {
            Date date1 = HOUR_MIN_TO_DATE_FORMAT.parse(time1);
            Date date2 = HOUR_MIN_TO_DATE_FORMAT.parse(time2);

            long d = Math.abs(date2.getTime() - date1.getTime());
            dur = (int) (d / 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dur;
    }

    public static final int getTime(String time) {
        int dur=0;
        try {
            Date date= yyyyMMdd_HHCmm.parse(time);
            cal.setTime(date);
            Global.Log("month:" + cal.get(Calendar.MONTH) +" day:"+cal.get(Calendar.DAY_OF_MONTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 1;
    }
    /**
     * @param durInSec    时间（秒）
     * @param maxFraction 小数点后最大几位。
     * @return
     */
    public static final String getFloatHourTime(int durInSec,int maxFraction) {
        float h = durInSec / (float) SEC_ONE_HOUR;
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setMaximumFractionDigits(maxFraction);

        return format.format(h);
    }



}
