package com.lotusbin.myapplication.model.comparators;

import com.lotusbin.myapplication.model.TemplateTask;

import java.util.Comparator;

/**
 * Comparison logic for daily task <br>
 *     1. 1st Compare by State
 *
 * @author satya60.shekhar@gmail.com
 */
public class TemplateTaskComparator implements Comparator<TemplateTask> {

    @Override
    public int compare(TemplateTask o1, TemplateTask o2) {

        return Integer.valueOf(o1.getPriority()).compareTo(o2.getPriority());
    }
}
