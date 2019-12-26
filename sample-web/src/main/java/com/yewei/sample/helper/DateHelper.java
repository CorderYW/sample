package com.yewei.sample.helper;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间判断
 * 
 * @author yewei
 */
public class DateHelper {


    /**
     * 获取时间
     * 
     * @param day 0代表当天 1代表明天 -1代表昨天
     * @return 指定时间凌晨
     */
    public static Date getDawnDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }


    /**
     * 获取时间
     *
     * @param day 0代表当天 1代表明天 -1代表昨天 -2代表前天 以此类推
     * @return 指定时间凌晨
     */
    public static Date getDawnDateUtcToBJ(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }


    public static Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 获取当前距离第二天0时还有多少秒.
     * 
     * @return
     */
    public static Long getCurrentDateSeconds() {
        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Long result = (calendar.getTime().getTime() - date.getTime()) / 1000;
        return result;
    }

    // public static void main(String[] args) {
    // System.out.println(getCurrentDateSeconds());
    // }
}
