package com.lotusbin.myapplication.task;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.lotusbin.myapplication.MyNotificatinReceiver;
import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.TODOListFragment;
import com.lotusbin.myapplication.jobs.DailyAppJobSchedulerService;

import java.util.Calendar;

public class TabWithFrameActivity extends AppCompatActivity {

    public static final int JOB_ID_SCHEDULED = 786549;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;

    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;

    /**
     * Putting the date here (the date of daily fragment date). It is not the best way to put the data her but for now
     * this look the simplest way to do this and can be refactored in future.
     */
    private String stringDailyFragmentCurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_with_frame);

        // Schedule job
        ComponentName componentName = new ComponentName(this, DailyAppJobSchedulerService.class);
        JobInfo.Builder jobInfoBuilder = new JobInfo.Builder(JOB_ID_SCHEDULED, componentName);

        jobInfoBuilder.setPeriodic(5000);
        jobInfoBuilder.setPersisted(true);
        jobInfoBuilder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        jobInfo = jobInfoBuilder.build();
        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        // Notification testing

        tabLayout = findViewById(R.id.our_tab_with_frame_id);
        fragmentManager = getSupportFragmentManager();
        frameLayout = findViewById(R.id.our_frame_id);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.our_frame_id, new DailyTaskViewFragment());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new DailyTaskViewFragment();
                        break;
                    case 1:
                        fragment = new TaskTempletViewFragment();
                        break;
                    case 2:
                        fragment =  new ScheduledTaskViewFragment();
                        break;
                    case 3:
                        notificationTesting();
                        fragment = new TODOListFragment();
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.our_frame_id, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void notificationTesting() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 52);

        Intent intent = new Intent(getApplicationContext(), MyNotificatinReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 3456, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSortByName:
            break;
            case R.id.menuSortByPriority:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public JobScheduler getJobScheduler() {
        return jobScheduler;
    }

    public JobInfo getJobInfo() {
        return jobInfo;
    }
    public void setStringDailyFragmentCurrentDate(String stringDailyFragmentCurrentDate) {
        this.stringDailyFragmentCurrentDate = stringDailyFragmentCurrentDate;
    }
    public String getStringDailyFragmentCurrentDate() {
        return stringDailyFragmentCurrentDate;
    }
    public FragmentManager getLocalCreatedFragmentManager() {
        return fragmentManager;
    }
}
