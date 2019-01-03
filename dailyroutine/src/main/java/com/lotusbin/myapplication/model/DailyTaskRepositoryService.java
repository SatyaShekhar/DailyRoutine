package com.lotusbin.myapplication.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.lotusbin.myapplication.constants.DailyAppConstants;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DailyTaskRepositoryService {
    private DailyTaskDao dao;
    private LiveData<List<DailyTask>> dailyTasks;
    private LiveData<List<DailyTask>> dailyTasksByDay;


    public DailyTaskRepositoryService(Application application) {
        TemplateTaskManagerDataBase db = TemplateTaskManagerDataBase.getInstance(application);
        dao = db.getDailyTaskDao();
        dailyTasks = dao.getAllLiveDailyTask();
        dailyTasksByDay = dao.getAllLiveDailyTask(DailyAppConstants.getDateWithddMMYYYYFormat(new Date()));
    }


    public  void update(DailyTask task) {
            new UpdateDailyTasksAsyncTask(dao).execute(task);
    }

    public  void delete(DailyTask task) {
        new DeleteTemplateTaskAsyncTask(dao).execute(task);
    }

    public  void insert(DailyTask task) { new InsertDailyTaskAsyncTask(dao).execute(task);    }

    public LiveData<List<DailyTask>> getDailyTasks() {
        return dailyTasks;
    }

    public DailyTask findByTitle(String title, String dateInString) {
        AsyncTask<String, Void, DailyTask> asyncResult = new FindDailyTaskByNameAsyncTask(dao).execute(title, dateInString);

        try {
            return asyncResult.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new IllegalStateException("Unable to find the template task by title", e);
        }
    }

    public LiveData<List<DailyTask>> getDailyTasks(String taskForDay) {
        dailyTasksByDay = dao.getAllLiveDailyTask(taskForDay);
        return dailyTasksByDay;
    }

    public static class InsertDailyTaskAsyncTask extends AsyncTask<DailyTask, Void, Void> {

        private DailyTaskDao dao;
        public InsertDailyTaskAsyncTask(DailyTaskDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(DailyTask... templateTasks) {
            dao.insert(templateTasks[0]);
            return null;
        }
    }

    public static class FindDailyTaskByNameAsyncTask extends AsyncTask<String, Void, DailyTask> {

        private DailyTaskDao dao;
        public FindDailyTaskByNameAsyncTask(DailyTaskDao dao) {
            this.dao = dao;
        }
        @Override
        protected DailyTask doInBackground(String... templateTitleAndDate) {
             return dao.getDailyTask(templateTitleAndDate[0], templateTitleAndDate[1]);
        }
    }

    public static class DeleteTemplateTaskAsyncTask extends AsyncTask<DailyTask, Void, Void> {

        private DailyTaskDao dao;
        public DeleteTemplateTaskAsyncTask(DailyTaskDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(DailyTask ... dailyTasks) {
            dao.delete(dailyTasks[0]);
            return null;
        }
    }

    public static class UpdateDailyTasksAsyncTask extends AsyncTask<DailyTask, Void, Void> {

        private DailyTaskDao dao;
        public UpdateDailyTasksAsyncTask(DailyTaskDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(DailyTask ... dailyTasks) {
            dao.update(dailyTasks[0]);
            return null;
        }
    }

}
