package com.expense_tracker.expense_tracker.Service;

import java.util.Calendar;

import org.springframework.stereotype.Service;

@Service
public class DateService {
    
    // Helper method to get the start and end of the current day
    public java.sql.Date[] getStartAndEndOfDay() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    java.util.Date startOfDayUtil = calendar.getTime();
    java.sql.Date startOfDay = new java.sql.Date(startOfDayUtil.getTime());

    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    java.util.Date endOfDayUtil = calendar.getTime();
    java.sql.Date endOfDay = new java.sql.Date(endOfDayUtil.getTime());

    return new java.sql.Date[]{startOfDay, endOfDay};
}

public java.sql.Date[] getStartAndEndOfLast7Days() {
    Calendar calendar = Calendar.getInstance();

    // End of today
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    java.util.Date endOfDayUtil = calendar.getTime();
    java.sql.Date endOfDay = new java.sql.Date(endOfDayUtil.getTime());

    // Start of 7 days ago
    calendar.add(Calendar.DAY_OF_YEAR, -6); // Subtract 6 days to get the start of 7 days ago
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    java.util.Date startOf7DaysAgoUtil = calendar.getTime();
    java.sql.Date startOf7DaysAgo = new java.sql.Date(startOf7DaysAgoUtil.getTime());

    return new java.sql.Date[]{startOf7DaysAgo, endOfDay};
}

public java.sql.Date[] getStartAndEndOfLast30Days() {
    Calendar calendar = Calendar.getInstance();

    // End of today
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    java.util.Date endOfDayUtil = calendar.getTime();
    java.sql.Date endOfDay = new java.sql.Date(endOfDayUtil.getTime());

    // Start of 30 days ago
    calendar.add(Calendar.DAY_OF_YEAR, -29); // Subtract 30 days to get the start of 7 days ago
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    java.util.Date startOf7DaysAgoUtil = calendar.getTime();
    java.sql.Date startOf7DaysAgo = new java.sql.Date(startOf7DaysAgoUtil.getTime());

    return new java.sql.Date[]{startOf7DaysAgo, endOfDay};
}

}
