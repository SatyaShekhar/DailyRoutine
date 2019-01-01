package com.lotusbin.myapplication.jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

public class DailyAppJobSchedulerService extends JobService {

    private DailyAppJobExecutor dailyAppJobExecutor;
    @Override
    public boolean onStartJob(final JobParameters params) {
        dailyAppJobExecutor = new DailyAppJobExecutor() {
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(), s , Toast.LENGTH_SHORT).show();
                // pass true as 2nd parameter if you want to reschedule
                jobFinished(params, false);
            }
        };

        dailyAppJobExecutor.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        dailyAppJobExecutor.cancel(true);
        // Return true if you want to execute again
        return false;
    }
}
