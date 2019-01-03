package com.lotusbin.myapplication.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lotusbin.myapplication.model.repository.ScheduledTaskRepositoryService;

import java.util.List;

public class TemplateTaskViewModel extends AndroidViewModel {
    private TemplateTaskRepositoryService templateTaskRepositoryService;
    private DailyTaskRepositoryService dailyTaskRepositoryService;
    private ScheduledTaskRepositoryService scheduledTaskRepositoryService;

    public TemplateTaskViewModel(@NonNull Application application) {
        super(application);
        this.templateTaskRepositoryService = new TemplateTaskRepositoryService(application);
        this.dailyTaskRepositoryService = new DailyTaskRepositoryService(application);
        this.scheduledTaskRepositoryService = new ScheduledTaskRepositoryService(application);

    }

    public  void update(TemplateTask task) {
        templateTaskRepositoryService.update(task);
    }
    public  void delete(TemplateTask task) {
        templateTaskRepositoryService.delete(task);
    }
    public  void insert(TemplateTask task) {
        templateTaskRepositoryService.insert(task);
    }
    public LiveData<List<TemplateTask>> getTemplateTasks() { return templateTaskRepositoryService.getTemplateTasks();  }

    public  void insert(DailyTask task) {   dailyTaskRepositoryService.insert(task);    }
    public  void update(DailyTask task) {
        dailyTaskRepositoryService.update(task);
    }
    public  void delete(DailyTask task) {
        dailyTaskRepositoryService.delete(task);
    }

    public  void update(ScheduledTask task) {
        scheduledTaskRepositoryService.update(task);
    }
    public  void delete(ScheduledTask task) {
        scheduledTaskRepositoryService.delete(task);
    }
    public  void insert(ScheduledTask task) {
        scheduledTaskRepositoryService.insert(task);
    }
    public LiveData<List<ScheduledTask>> getScheduledTasks() { return scheduledTaskRepositoryService.getScheduledTasks();  }

    @Nullable
    public DailyTask findDailyByTitle(String title, String dateInString) {return dailyTaskRepositoryService.findByTitle(title, dateInString);}

    @Nullable
    public TemplateTask findByTitle(String title) {   return templateTaskRepositoryService.findByTitle(title);    }
}
