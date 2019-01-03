package com.lotusbin.myapplication.constants;

public class TaskValidator {
    public static boolean idValid(String title, String priority) {
        if (title == null || title.isEmpty() || priority == null || priority.isEmpty()) {
            return false;
        }

        try {
            Integer.valueOf(priority);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
