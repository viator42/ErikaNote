package com.viator42.erikanote.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CommonUtils {
    public static Calendar getTimeAfterInSecs(int secs)
    {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND,secs);
        return cal;
    }

    //字符串是否为空值
    public static boolean isValueEmpty(String str)
    {
        return str == null || str.isEmpty() || str.equals("null");
    }

    //当前时间
    public static long getCurrentTimestamp()
    {
        return System.currentTimeMillis();
    }

    // 时间戳转换为日期字符串
    public static String timestampToDate(long timestamp)
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(timestamp);

    }

    // 时间戳转换为日期+时间字符串
    public static String timestampToDatetime(long timestamp)
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(timestamp);

    }

    //获得当天0点时间
    public static long getDayStart(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
    //获得当天24点时间
    public static long getDayEnds(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
    //获得本周一0点时间
    public static long getTimesWeekStart(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return cal.getTimeInMillis();
    }
    //获得本周日24点时间
    public static long getTimesWeekEnds(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        return cal.getTime().getTime()+ (7 * 24 * 60 * 60 * 1000);
    }
    //获得本月第一天0点时间
    public static long getMonthStart(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTimeInMillis();

    }
    //获得本月最后一天24点时间
    public static long getMonthEnds(){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0,0);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime().getTime()+ (7 * 24 * 60 * 60 * 1000);
    }

    //获得第二天的指定时间
    public static long getTimeNextDay(int feqValue){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        cal.set(Calendar.HOUR, feqValue);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime().getTime();
    }

    //获得下周的指定时间
    public static long getTimeNextWeek(int feqValue){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR) + 1);
        cal.set(Calendar.DAY_OF_WEEK, feqValue);
        cal.set(Calendar.HOUR, 15);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime().getTime();
    }

    //获得下个月的指定时间
    public static long getTimeNextMonth(int feqValue){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DAY_OF_MONTH, feqValue);
        cal.set(Calendar.HOUR, 15);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        return cal.getTime().getTime();
    }

    //计算两个时间点的间隔
    public static String timeInterval(long startTime, long endTime)
    {
        StringBuffer result = new StringBuffer();
        long interval = endTime - startTime;
        if(interval < 0)
        {
            return null;
        }
        long dayTime = 3600 * 24 *1000;
        if(interval > dayTime);
        {
            long dayTimeCount = (interval / dayTime);
            if(dayTimeCount > 0)
            {
                result.append(dayTimeCount + "天");
                interval = interval % dayTime;
            }
        }
        long hourTime = 3600 * 1000;
        if(interval > hourTime)
        {
            long hourTimeCount = (interval / hourTime);
            if(hourTimeCount > 0)
            {
                result.append(hourTimeCount + "小时");
                interval = interval % hourTime;
            }
        }
        long minuteTime = 60 * 1000;
        if(interval > minuteTime)
        {
            long minuteTimeCount = (interval / minuteTime);
            if(minuteTimeCount > 0)
            {
                result.append(minuteTimeCount + "分");
            }
            else
            {
                result.append("小于一分钟");
            }
        }

        return result.toString();
    }

}
