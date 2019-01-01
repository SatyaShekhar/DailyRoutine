package com.lotusbin.myapplication.constants;

public enum TaskStatus {
    InProgress,
    Pending,
    NotPossible,
    Skipped,
    Completed;

    public static String[] getAsStringArray() {
        return new String[] {InProgress.name(),Pending.name(),NotPossible.name(),
                Skipped.name(), Completed.name()};
    }
}
