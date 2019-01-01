package com.lotusbin.myapplication.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DailyTaskDao {

    @Insert
    void insert(DailyTask dailyTask);

    @Update
    void update(DailyTask dailyTask);

    @Delete
    void delete(DailyTask dailyTask);

    @Query("SELECT * FROM DAILY_TASK ORDER BY title DESC")
    LiveData<List<DailyTask>> getAllLiveDailyTask();

    @Query("SELECT * FROM DAILY_TASK ORDER BY title DESC")
    List<DailyTask> getDailyNoLive();

    @Query("SELECT * FROM DAILY_TASK WHERE title = :taskTitle AND task_for_date = :dateInString")
    DailyTask getDailyTask(String taskTitle, String dateInString);

    @Query("SELECT * FROM DAILY_TASK WHERE task_for_date = :dateInString ORDER BY title")
    LiveData<List<DailyTask>> getAllLiveDailyTask(String dateInString);
}
