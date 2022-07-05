package com.example.android.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.android.Calendar;

import java.util.Date;
import java.util.List;

public class CalendarRepository{
    private final CalendarDAO calendarDAO;

    private final LiveData<List<Calendar>> calendarEventList;

    public CalendarRepository(Application application, int userId){
        database db = database.getDatabase(application);
        calendarDAO = db.calendarDAO();
        calendarEventList = calendarDAO.getCalendarEvent(userId);
    }


    public void insertCalendarEvent(Calendar calendar) {
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                calendarDAO.insertCalendarEvent(calendar);
            }
        });
    }


    public void updateCalendarEvent(Date date, String breakfast, String lunch, String dinner, int userid) {
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                calendarDAO.updateCalendarEvent(date, breakfast, lunch, dinner, userid);
            }
        });
    }


    public LiveData<List<Calendar>> getCalendarEvent(int userid) {
        return calendarEventList;
    }
}
