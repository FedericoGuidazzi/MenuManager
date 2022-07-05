package com.example.android.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.Calendar;

import java.util.List;

@Dao
public interface CalendarDAO {
    @Insert
    void insertCalendarEvent(Calendar calendar);

    @Query("UPDATE Calendar set breakfast=:breakfast, lunch=:lunch, dinner=:dinner where user=:userid and date=:date")
    void updateCalendarEvent(String date, String breakfast, String lunch, String dinner, int userid);

    @Query("SELECT * from Calendar where user=:userid and date=:date")
    LiveData<List<Calendar>> getCalendarEvent(int userid, String date);


}
