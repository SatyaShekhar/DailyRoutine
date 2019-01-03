package com.lotusbin.myapplication.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ScheduledTaskDao {

    @Insert
    void insert(ScheduledTask scheduleTask);

    @Update
    void update(ScheduledTask scheduledTask);

    @Delete
    void delete(ScheduledTask scheduledTask);

    @Query("SELECT * FROM SCHEDULED_TASK ORDER BY title DESC")
    LiveData<List<ScheduledTask>> getAllScheduleTasks();

    @Query("SELECT * FROM SCHEDULED_TASK ORDER BY title DESC")
    List<ScheduledTask> getAllScheduleTaskNoLive();

    @Query("SELECT * FROM SCHEDULED_TASK WHERE title = :taskTitle AND startTimeInLong = :startTimeInLong")
    ScheduledTask getDailyTask(String taskTitle, long startTimeInLong);

    @Query("SELECT * FROM SCHEDULED_TASK WHERE startTimeInLong = :startTimeInLong ORDER BY startTimeInLong")
    LiveData<List<ScheduledTask>> getAllLiveDailyTask(long startTimeInLong);
}
