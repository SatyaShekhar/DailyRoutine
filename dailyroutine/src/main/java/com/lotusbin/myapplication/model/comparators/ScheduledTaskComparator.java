package com.lotusbin.myapplication.model.comparators;

import com.lotusbin.myapplication.model.ScheduledTask;

import java.util.Comparator;

/**
 * Comparison logic for {@link ScheduledTask} <br>
 *     1. 1st Compare by State
 *
 * @author satya60.shekhar@gmail.com
 */
public class ScheduledTaskComparator implements Comparator<ScheduledTask> {

    @Override
    public int compare(ScheduledTask o1, ScheduledTask o2) {
        return Long.valueOf(o1.getStartTimeInLong()).compareTo(o2.getStartTimeInLong());
    }
}
