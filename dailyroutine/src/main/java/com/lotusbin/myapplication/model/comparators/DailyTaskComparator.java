package com.lotusbin.myapplication.model.comparators;

import com.lotusbin.myapplication.constants.TaskStatus;
import com.lotusbin.myapplication.model.DailyTask;

import java.util.Comparator;

/**
 * Comparison logic for daily task <br>
 *     1. 1st Compare by State
 *
 * @author satya60.shekhar@gmail.com
 */
public class DailyTaskComparator implements Comparator<DailyTask> {

    @Override
    public int compare(DailyTask o1, DailyTask o2) {
        int status = TaskStatus.valueOf(o1.getStatus()).compareTo(TaskStatus.valueOf(o2.getStatus()));
        if (status != 0) {
            return status;
        }
        return Integer.valueOf(o1.getPriority()).compareTo(o2.getPriority());
    }
}
