package com.lotusbin.myapplication.constants;

import java.util.ArrayList;
import java.util.List;

public enum NotificationTypes {
    /*OneTime,
    Yearly,
    Monthly,
    Weekly,
    Daily,
    SpecificInterval,
    WeekDays,
    WeekEnds;*/
    General,
    Event,
    DateOfBirth;
    public static String[] getAsStringArray() {
        List<String> types = new ArrayList<>();

        for(NotificationTypes notificationType: NotificationTypes.values()) {
            types.add(notificationType.name());
        }
        return types.toArray(new String[types.size()]);
    }
}
