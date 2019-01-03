package com.lotusbin.myapplication.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class DailyTaskViewModel extends AndroidViewModel {
    private DailyTaskRepositoryService service;
    public DailyTaskViewModel(@NonNull Application application) {
        super(application);
        this.service = new DailyTaskRepositoryService(application);
    }

    public  void update(DailyTask task) {
        service.update(task);
    }

    public  void delete(DailyTask task) {
        service.delete(task);
    }

    public  void insert(DailyTask task) {
        service.insert(task);
    }

    public LiveData<List<DailyTask>> getDailyTasks() {
        return service.getDailyTasks();
    }


    @Nullable
    public DailyTask findByTitle(String title, String dateInString) {
        return service.findByTitle(title, dateInString);
    }

    public LiveData<List<DailyTask>> getDailyTasks(String taskForDay) {
        return service.getDailyTasks(taskForDay);
    }
}
