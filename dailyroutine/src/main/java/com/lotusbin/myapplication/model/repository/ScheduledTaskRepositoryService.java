package com.lotusbin.myapplication.model.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.lotusbin.myapplication.model.ScheduledTask;
import com.lotusbin.myapplication.model.ScheduledTaskDao;
import com.lotusbin.myapplication.model.TemplateTaskManagerDataBase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ScheduledTaskRepositoryService {
    private ScheduledTaskDao dao;
    private LiveData<List<ScheduledTask>> scheduledTasks;


    public ScheduledTaskRepositoryService(Application application) {
        TemplateTaskManagerDataBase db = TemplateTaskManagerDataBase.getInstance(application);
        dao = db.getScheduledTaskDao();
        scheduledTasks = dao.getAllScheduleTasks();
        // TODO dailyTasksByDay = dao.getAllLiveDailyTask(DailyAppConstants.getDateWithddMMYYYYFormat(new Date()));
        // TODO this can be extended to display the scheduled tasks by year/month/week/day
    }


    public  void update(ScheduledTask task) {
            new UpdateScheduledTaskAsyncTask(dao).execute(task);
    }
    public  void delete(ScheduledTask task) {
        new DeleteScheduledTaskAsyncTask(dao).execute(task);
    }
    public  void insert(ScheduledTask task) { new InsertScheduledTaskAsyncTask(dao).execute(task);    }
    public LiveData<List<ScheduledTask>> getScheduledTasks() {
        return scheduledTasks;
    }

    public ScheduledTask findByTitle(String title, Long dateInMillisec) {
        AsyncTask<String, Void, ScheduledTask> asyncResult = new FindScheduledTaskByNameAsyncTask(dao).execute(title, dateInMillisec.toString());

        try {
            return asyncResult.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new IllegalStateException("Unable to find the template task by title", e);
        }
    }



    public static class InsertScheduledTaskAsyncTask extends AsyncTask<ScheduledTask, Void, Void> {

        private ScheduledTaskDao dao;
        public InsertScheduledTaskAsyncTask(ScheduledTaskDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(ScheduledTask... templateTasks) {
            dao.insert(templateTasks[0]);
            return null;
        }
    }

    public static class FindScheduledTaskByNameAsyncTask extends AsyncTask<String, Void, ScheduledTask> {

        private ScheduledTaskDao dao;
        public FindScheduledTaskByNameAsyncTask(ScheduledTaskDao dao) {
            this.dao = dao;
        }
        @Override
        protected ScheduledTask doInBackground(String... templateTitleAndDate) {
             return dao.getDailyTask(templateTitleAndDate[0], Long.valueOf(templateTitleAndDate[1]));
        }
    }

    public static class DeleteScheduledTaskAsyncTask extends AsyncTask<ScheduledTask, Void, Void> {

        private ScheduledTaskDao dao;
        public DeleteScheduledTaskAsyncTask(ScheduledTaskDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(ScheduledTask ... dailyTasks) {
            dao.delete(dailyTasks[0]);
            return null;
        }
    }

    public static class UpdateScheduledTaskAsyncTask extends AsyncTask<ScheduledTask, Void, Void> {

        private ScheduledTaskDao dao;
        public UpdateScheduledTaskAsyncTask(ScheduledTaskDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(ScheduledTask ... dailyTasks) {
            dao.update(dailyTasks[0]);
            return null;
        }
    }

}
