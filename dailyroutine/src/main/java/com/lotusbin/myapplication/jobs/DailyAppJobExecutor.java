package com.lotusbin.myapplication.jobs;

import android.os.AsyncTask;

public class DailyAppJobExecutor extends AsyncTask <Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {
        return "Background long running task finishes";
    }
}
