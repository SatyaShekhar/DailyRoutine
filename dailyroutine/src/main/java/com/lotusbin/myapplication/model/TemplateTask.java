package com.lotusbin.myapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "TEMPLATE_TASK")
public class TemplateTask {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private int priority;

    @Ignore
    private boolean itemSelected;

    public TemplateTask(){}

    public TemplateTask(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public boolean isItemSelected() {
        return itemSelected;
    }

    public void changeItemSelected() {
        itemSelected = ! itemSelected;
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

}
