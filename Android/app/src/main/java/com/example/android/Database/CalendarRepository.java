package com.example.android.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.android.Calendar;

import java.util.Date;
import java.util.List;

public class CalendarRepository{
    private final CalendarDAO calendarDAO;


    public CalendarRepository(Application application){
        database db = database.getDatabase(application);
        calendarDAO = db.calendarDAO();
    }


    public void insertCalendarEvent(Calendar calendar) {
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                calendarDAO.insertCalendarEvent(calendar);
            }
        });
    }


    public void updateCalendarEvent(String date, String breakfast, String lunch, String dinner, int userid) {
        database.executor.execute(new Runnable() {
            @Override
            public void run() {
                calendarDAO.updateCalendarEvent(date, breakfast, lunch, dinner, userid);
            }
        });
    }


    public LiveData<Calendar> getCalendarEvent(int userId, String date) {
        return calendarDAO.getCalendarEvent(userId, date);
    }
}
