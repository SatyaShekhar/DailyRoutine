package com.lotusbin.myapplication.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TemplateTaskDao {

    @Insert
    void insert(TemplateTask templateTask);

    @Update
    void update(TemplateTask templateTask);

    @Delete
    void delete(TemplateTask templateTask);

    @Query("SELECT * FROM template_task ORDER BY title DESC")
    LiveData<List<TemplateTask>> getAllTemplateTeask();

    @Query("SELECT * FROM template_task ORDER BY title DESC")
    List<TemplateTask> getTemplateTaskNoLive();

    @Query("SELECT * FROM TEMPLATE_TASK WHERE title = :title")
    TemplateTask  getTemplateTask(String[] title);
}
