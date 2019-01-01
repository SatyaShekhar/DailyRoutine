package com.lotusbin.myapplication;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lotusbin.myapplication.task.TabWithFrameActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class TODOListFragment extends Fragment {

    NotificationCompat.Builder notificationBuilder;
    public static final int UNIQUE_ID = 847523;

    private Button notificationActionButton;
    private Button jobschedulerStartButtonIdAtTodoList, jobschedulerStopButtonIdAtTodoList;

    public TODOListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todolist, container, false);
        jobSchedulerStartAndStopButtonInitialize(view);
        initializeNotificationActionButton(view);
        return view;
    }

    private void jobSchedulerStartAndStopButtonInitialize(View view) {

        jobschedulerStartButtonIdAtTodoList = view.findViewById(R.id.jobschedulerStartButtonIdAtTodoList);
        jobschedulerStartButtonIdAtTodoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabWithFrameActivity activity = (TabWithFrameActivity) getActivity();
                activity.getJobScheduler().schedule(activity.getJobInfo());
                Toast.makeText(activity, "Job scheduled successfully", Toast.LENGTH_SHORT).show();
            }
        });
        jobschedulerStopButtonIdAtTodoList = view.findViewById(R.id.jobschedulerStopButtonIdAtTodoList);
        jobschedulerStopButtonIdAtTodoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabWithFrameActivity activity = (TabWithFrameActivity) getActivity();
                activity.getJobScheduler().cancel(TabWithFrameActivity.JOB_ID_SCHEDULED);
                Toast.makeText(activity, "Job cancelled successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeNotificationActionButton(View view) {
        notificationActionButton = view.findViewById(R.id.notificationActionButton);
        notificationBuilder = new NotificationCompat.Builder(getActivity());
        notificationBuilder.setAutoCancel(true);

        notificationActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationBuilder.setSmallIcon(R.drawable.ic_notification);
                notificationBuilder.setTicker("This is a ticker");
                notificationBuilder.setWhen(System.currentTimeMillis());
                notificationBuilder.setContentText("This is the context text details to view");
                notificationBuilder.setContentTitle("This is the context title");


                Intent intent = new Intent(getActivity(), TabWithFrameActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notificationBuilder.setContentIntent(pendingIntent);

                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(UNIQUE_ID, notificationBuilder.build());
            }
        });
    }


}
