package com.lotusbin.myapplication.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.lotusbin.myapplication.constants.DailyAppConstants;
import com.lotusbin.myapplication.model.repository.MigrationsOfDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Database(entities = {TemplateTask.class, DailyTask.class, ScheduledTask.class}, version = 3)
public abstract class TemplateTaskManagerDataBase extends RoomDatabase {

    private static TemplateTaskManagerDataBase instance;

    public abstract TemplateTaskDao getTemplateTaskDao();
    public abstract DailyTaskDao getDailyTaskDao();
    public abstract ScheduledTaskDao getScheduledTaskDao();

    public static synchronized TemplateTaskManagerDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TemplateTaskManagerDataBase.class, "DAILY_TASK_MGMT")
                    //.addCallback(callBackPopulate)
                    .addMigrations(MigrationsOfDatabase.MIGRATION_VERSION_1_TO_2)
                    .addMigrations(MigrationsOfDatabase.MIGRATION_VERSION_2_TO_3)
                    .build();
        }
        new PopulateAsyncTask(instance).execute();
        return instance;

    }

    private static RoomDatabase.Callback callBackPopulate = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}

class PopulateAsyncTask extends AsyncTask<Void, Void, Void> {

    private TemplateTaskManagerDataBase db;

    public PopulateAsyncTask(TemplateTaskManagerDataBase db){
        this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        TemplateTaskDao templateTaskDao = db.getTemplateTaskDao();
        List<TemplateTask> tasks = templateTaskDao.getTemplateTaskNoLive();
        Log.i("TEMPLATE_TASK_STATUS", "Going to run async task to create task with " + tasks + " entries");
        if( tasks == null || tasks.isEmpty()) {
            Log.i("TEMPLATE_TASK_STATUS", "No existing task found .... going to create using dao service");
            templateTaskDao.insert(new TemplateTask("Morning Walk (3 KM)", "My day starts with a nice journey", 5));
            templateTaskDao.insert(new TemplateTask("Check on Office Pending actions", "Make sure you close pending dailyTasks in office", 5));
            templateTaskDao.insert(new TemplateTask("Review my work list for today", "Review the dailyTasks present in todays list and any other in the scheduled task view", 5));
            templateTaskDao.insert(new TemplateTask("Update my financial saving list", "Under the saving list section update your financial saving lists", 6));
            templateTaskDao.insert(new TemplateTask("Topics to learn for the day", "Read every day.. update today's reading list", 6));
            Log.i("TEMPLATE_TASK_STATUS", "5 Task creation successfully done");
        }
        ScheduledTaskDao scheduledTaskDao = db.getScheduledTaskDao();
        List<ScheduledTask> scheduledTasks = scheduledTaskDao.getAllScheduleTaskNoLive();
        if(scheduledTasks == null || scheduledTasks.isEmpty()) {
            // https://stackoverflow.com/questions/2654025/how-to-get-year-month-day-hours-minutes-seconds-and-milliseconds-of-the-cur
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
            int day = now.get(Calendar.DAY_OF_MONTH);
            Date taskNotificationTime = DailyAppConstants.getTimeAsDate(year, month, day, 8, 30, 0);

            ScheduledTask scheduledTask = new ScheduledTask("Plan GOALs of the day",
                    "Plan your activities for today", taskNotificationTime);
            scheduledTaskDao.insert(scheduledTask);
        }
        return null;
    }
}