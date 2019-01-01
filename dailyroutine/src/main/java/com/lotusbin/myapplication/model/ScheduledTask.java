package com.lotusbin.myapplication.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.lotusbin.myapplication.constants.NotificationTypes;

import java.util.Date;

@Entity(tableName = "SCHEDULED_TASK")
public class ScheduledTask {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String notificationType;
    private long startTimeInLong;
    private long endTimeInLong;
    private int notificationPriority;
    private boolean enableNotification;
    private boolean oneTimeNotification;

    private int notificationDuration; // Based on the notification type duration will be evaluated

    @Ignore
    private boolean itemSelected;

    public ScheduledTask(){}

    public ScheduledTask(String title, String description, Date exectionTime) {
        this.title = title;
        this.description = description;
        this.startTimeInLong = exectionTime.getTime();
        enableNotification = false;
        notificationType = NotificationTypes.General.name();
    }

    public boolean isItemSelected() {
        return itemSelected;
    }
    public void changeItemSelected() {
        itemSelected = ! itemSelected;
    }
    public long getEndTimeInLong() {        return endTimeInLong;    }
    public void setEndTimeInLong(long endTimeInLong) {        this.endTimeInLong = endTimeInLong;    }
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
    public long getStartTimeInLong() {
        return startTimeInLong;
    }
    public void setStartTimeInLong(long startTimeInLong) {
        this.startTimeInLong = startTimeInLong;
    }
    public int getNotificationPriority() {
        return notificationPriority;
    }
    public void setNotificationPriority(int notificationPriority) { this.notificationPriority = notificationPriority;    }
    public boolean isEnableNotification() {
        return enableNotification;
    }
    public void setEnableNotification(boolean enableNotification) {        this.enableNotification = enableNotification;    }
    public boolean isOneTimeNotification() {
        return oneTimeNotification;
    }
    public void setOneTimeNotification(boolean oneTimeNotification) {        this.oneTimeNotification = oneTimeNotification;    }
    public String getNotificationType() {        return notificationType;    }
    public void setNotificationType(String notificationType) {        this.notificationType = notificationType;    }
    public int getNotificationDuration() {
        return notificationDuration;
    }
    public void setNotificationDuration(int notificationDuration) {        this.notificationDuration = notificationDuration;    }
    public void setItemSelected(boolean itemSelected) {
        this.itemSelected = itemSelected;
    }
}
