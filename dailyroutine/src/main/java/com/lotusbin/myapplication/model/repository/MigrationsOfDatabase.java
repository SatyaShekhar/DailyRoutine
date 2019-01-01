package com.lotusbin.myapplication.model.repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

public class MigrationsOfDatabase {

    public static final Migration MIGRATION_VERSION_1_TO_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `SCHEDULED_TASK` (`id` INTEGER NOT NULL, `title` TEXT,  `description` TEXT,  `startTimeInLong` INTEGER NOT NULL," +
                    "`notificationPriority` INTEGER NOT NULL, `enableNotification` INTEGER NOT NULL, `oneTimeNotification` INTEGER NOT NULL," +
                    "`notificationType` TEXT,  `notificationDuration` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        }
    };

    public static final Migration MIGRATION_VERSION_2_TO_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `SCHEDULED_TASK` ADD COLUMN `endTimeInLong` INTEGER NOT NULL DEFAULT 0");
        }
    };
}
