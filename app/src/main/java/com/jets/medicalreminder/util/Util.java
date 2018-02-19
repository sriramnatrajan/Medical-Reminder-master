package com.jets.medicalreminder.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;

import com.jets.medicalreminder.alarm.AlarmActivity;
import com.jets.medicalreminder.beans.Medicine;

public class Util {

    // Constructor
    private Util() {
    }


    public enum Interval {
        MONTHS(DateUtils.DAY_IN_MILLIS * 30), WEEKS(DateUtils.WEEK_IN_MILLIS), DAYS(DateUtils.DAY_IN_MILLIS),
        HOURS(DateUtils.HOUR_IN_MILLIS), MINUTES(DateUtils.MINUTE_IN_MILLIS);

        public final long milliSeconds;

        Interval(long milliSeconds) {
            this.milliSeconds = milliSeconds;
        }

        public String toString() {
            String origin = super.toString();
            return (origin.substring(0, 1) + origin.substring(1).toLowerCase(
                    Locale.getDefault()));
        }
    }

    // Set Alarm
    public static void setAlarm(Context context, Medicine medicine) {
        // If the start time is in the past, set the alarm to the nearest incoming time
        long startTime=medicine.getStartTime();
        long interval = medicine.getMidTime();
        long endTime=medicine.getEndTime();
        Log.d("Medicine","startTime= "+startTime +"intervel= "+interval+" endTime= "+endTime);
        long dateStart=medicine.getStartDate();
        long[] valuees={startTime,interval,endTime};
        long startDateTime = medicine.getStartDateTime();
        while (startDateTime <= System.currentTimeMillis()) {
            startDateTime += interval;
        }

        /*Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.putExtra(Constants.EXTRA_MEDICINE_ID, medicine.getId());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, medicine.getId(), alarmIntent,
                Intent.FLAG_ACTIVITY_NEW_TASK);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //manager.set(AlarmManager.RTC_WAKEUP, startDateTime, pendingIntent);
        //manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, startDateTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        manager.set(AlarmManager.RTC_WAKEUP, startTime, pendingIntent);*/

        /*AlarmManager manager1 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager1.set(AlarmManager.RTC_WAKEUP, interval, pendingIntent);

        AlarmManager manager2 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager2.set(AlarmManager.RTC_WAKEUP, endTime, pendingIntent);*/

        setOrCancelRepeatingAlarm(context, medicine.getId(), 101, startTime, true);
        setOrCancelRepeatingAlarm(context, medicine.getId(), 102, interval, true);
        setOrCancelRepeatingAlarm(context, medicine.getId(), 103, endTime, true);
    }

    private static void setOrCancelRepeatingAlarm(Context context, int medicine_id, int id, long time, boolean bSet) {
        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.putExtra(Constants.EXTRA_MEDICINE_ID, medicine_id);
        //ToDo: put extra for before/after food status
        if(bSet) {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, id, alarmIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);
           // manager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            //ToDo: Enable below snippet for recurring alarm
       manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pendingIntent);
        }else{
            PendingIntent pendingIntent = PendingIntent.getActivity(context, id, alarmIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);
        }
    }

    // Cancel Alarm
    public static void cancelAlarm(Context context, Medicine medicine) {
        /*Intent alarmIntent = new Intent(context, AlarmActivity.class);
         PendingIntent pendingIntent = PendingIntent.getActivity(context, medicine.getId(), alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);*/
        setOrCancelRepeatingAlarm(context, medicine.getId(), 101, 0, false);
        setOrCancelRepeatingAlarm(context, medicine.getId(), 102, 0, false);
        setOrCancelRepeatingAlarm(context, medicine.getId(), 103, 0, false);
    }


    // To Date String
    public static String toDateString(long time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(time);
    }

    // To Date
    public static long toMilliseconds(String dateString, String format) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate(dateString, format));
        return calendar.getTimeInMillis();
    }


    public static Date toDate(String dateString, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.parse(dateString);
    }
}
