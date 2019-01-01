package com.lotusbin.myapplication.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TemplateTaskRepositoryService {
    private TemplateTaskDao dao;
    private LiveData<List<TemplateTask>> templateTasks;

    public TemplateTaskRepositoryService(Application application) {
        TemplateTaskManagerDataBase db = TemplateTaskManagerDataBase.getInstance(application);
        dao = db.getTemplateTaskDao();
        templateTasks = dao.getAllTemplateTeask();
    }


    public  void update(TemplateTask task) {
            new UpdateTemplateTaskAsyncTask(dao).execute(task);
    }

    public  void delete(TemplateTask task) {
        new DeleteTemplateTaskAsyncTask(dao).execute(task);
    }

    public  void insert(TemplateTask task) {
        new InsertTemplateTaskAsyncTask(dao).execute(task);
    }

    public LiveData<List<TemplateTask>> getTemplateTasks() {
        return templateTasks;
    }

    public TemplateTask findByTitle(String title) {
        AsyncTask<String, Void, TemplateTask> asyncResult = new FindTemplateTaskByNameAsyncTask(dao).execute(title);

        try {
            return asyncResult.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new IllegalStateException("Unable to find the template task by title", e);
        }
    }

    public static class InsertTemplateTaskAsyncTask extends AsyncTask<TemplateTask, Void, Void> {

        private TemplateTaskDao dao;
        public InsertTemplateTaskAsyncTask(TemplateTaskDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(TemplateTask... templateTasks) {
            dao.insert(templateTasks[0]);
            return null;
        }
    }

    public static class FindTemplateTaskByNameAsyncTask extends AsyncTask<String, Void, TemplateTask> {

        private TemplateTaskDao dao;
        public FindTemplateTaskByNameAsyncTask(TemplateTaskDao dao) {
            this.dao = dao;
        }
        @Override
        protected TemplateTask doInBackground(String... templateTitle) {
             return dao.getTemplateTask(templateTitle);
        }
    }

    public static class DeleteTemplateTaskAsyncTask extends AsyncTask<TemplateTask, Void, Void> {

        private TemplateTaskDao dao;
        public DeleteTemplateTaskAsyncTask(TemplateTaskDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(TemplateTask... templateTasks) {
            dao.delete(templateTasks[0]);
            return null;
        }
    }

    public static class UpdateTemplateTaskAsyncTask extends AsyncTask<TemplateTask, Void, Void> {

        private TemplateTaskDao dao;
        public UpdateTemplateTaskAsyncTask(TemplateTaskDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(TemplateTask... templateTasks) {
            dao.update(templateTasks[0]);
            return null;
        }
    }

}
