package com.viator42.erikanote.utils;

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

}
