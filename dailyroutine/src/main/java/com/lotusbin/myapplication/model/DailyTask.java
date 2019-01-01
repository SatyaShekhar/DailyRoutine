package com.lotusbin.myapplication.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "DAILY_TASK")
public class DailyTask {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private int priority;
    private String status;

    @ColumnInfo(name = "task_for_date")
    public String taskForDay;

    public DailyTask(){}


    @Override
    public String toString() {
        return "DailyTask[Status" + status + ", Priority:" + priority + ", title: " + title + "]";
    }

    public DailyTask(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = "Pending";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTaskForDay(String taskForDay) {  this.taskForDay = taskForDay;   }

    public String getTaskForDay() { return taskForDay;  }
}
