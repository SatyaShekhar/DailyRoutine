package com.lotusbin.myapplication.task;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.alarm.AlarmBroadCastReveiver;
import com.lotusbin.myapplication.constants.DailyAppConstants;
import com.lotusbin.myapplication.constants.NotificationTypes;
import com.lotusbin.myapplication.model.ScheduledTask;
import com.lotusbin.myapplication.model.TemplateTaskViewModel;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduledTaskEditFragment extends Fragment {

    private TemplateTaskViewModel taskViewModel;

    private TextView editTextAddEventNameId, editTextAddEventDescriptionId;
    private TextView editTextAddEventStartDateID, editTextAddEventStartTimeID;
    private TextView editTextAddEventEndDateID, editTextAddEventEndTime;
    private Spinner spinnerAddEventTypeId, spinnerAddEventPriorityId;
    private Switch switchAddEventEnableNotificationId;
    private Button buttonAddEventBackToViewId, buttonAddEventCreateNewId;

    private ScheduledTask scheduledTask;

    public ScheduledTaskEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scheduled_task_edit, container, false);
        taskViewModel = ViewModelProviders.of(getActivity()).get(TemplateTaskViewModel.class);
        initialize(view);
        populateModel();
        handleClickBackButton();
        handleClickEditButton();
        return  view;
    }

    private void populateModel() {
        editTextAddEventNameId.setText(scheduledTask.getTitle());
        editTextAddEventDescriptionId.setText(scheduledTask.getDescription());

        int notificationPosition = NotificationTypes.valueOf(scheduledTask.getNotificationType()).ordinal();
        spinnerAddEventTypeId.setSelection(notificationPosition);

        editTextAddEventStartDateID.setText(DailyAppConstants.getDateWithddMMYYYYFormat(new Date(scheduledTask.getStartTimeInLong())));
        editTextAddEventStartTimeID.setText(DailyAppConstants.getDateWithddHHMMSSFormat(new Date(scheduledTask.getStartTimeInLong())));

        switchAddEventEnableNotificationId.setChecked(scheduledTask.isEnableNotification());
    }

    public void setScheduledTask(ScheduledTask scheduledTask) {
        this.scheduledTask = scheduledTask;
    }

    private void handleClickBackButton() {
        buttonAddEventBackToViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((TabWithFrameActivity) getActivity()).getLocalCreatedFragmentManager().beginTransaction();
                transaction.replace(R.id.our_frame_id, new ScheduledTaskViewFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
        });
    }

    private void handleClickEditButton() {
        buttonAddEventCreateNewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO validate

                scheduledTask.setTitle(editTextAddEventNameId.getText().toString());
                scheduledTask.setDescription(editTextAddEventDescriptionId.getText().toString());
                String notification = NotificationTypes.getAsStringArray()[spinnerAddEventTypeId.getSelectedItemPosition()];
                scheduledTask.setNotificationType(notification);

                String startDate = editTextAddEventStartDateID.getText().toString();
                String startTime = editTextAddEventStartTimeID.getText().toString();
                Date startTimeAsDate = DailyAppConstants.getTimeAsDate(startDate, startTime);
                scheduledTask.setStartTimeInLong(startTimeAsDate.getTime());


                handleNotificationEnableOrDisabledState();
                taskViewModel.update(scheduledTask);


                FragmentTransaction transaction = ((TabWithFrameActivity) getActivity()).getLocalCreatedFragmentManager().beginTransaction();
                transaction.replace(R.id.our_frame_id, new ScheduledTaskViewFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
        });

    }

    private void handleNotificationEnableOrDisabledState() {
        final int sdkInt = Build.VERSION.SDK_INT;
        if(switchAddEventEnableNotificationId.isChecked() == scheduledTask.isEnableNotification()) {
            return; // NO change no need to do any thing
        }
        //TODO check if the time is current or future than schedule else Show a poup up that its already
        // time crossed or can be part of validation that its an future event ... or don't do any thing
        if(switchAddEventEnableNotificationId.isChecked()) {
           scheduledTask.setEnableNotification(true);
           // TODO enable the alarm

            // TODO sample test code should be replaced wit hreal time
            createAlarm(new Date().getTime() + 5000, scheduledTask);
        } else {
            scheduledTask.setEnableNotification(false);
            // TODO it's disable notificaiton hence stop the alarm.

        }
    }

    private void createAlarm(long timeInMilSec, ScheduledTask scheduledTask) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmBroadCastReveiver.class);
        intent.putExtra("SCHEDULE_TASK", scheduledTask);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMilSec, AlarmManager.INTERVAL_DAY, pendingIntent);
    }
    private void initialize(View view) {
        editTextAddEventNameId = view.findViewById(R.id.editTextAddEventNameId);
        editTextAddEventDescriptionId = view.findViewById(R.id.editTextAddEventDescriptionId);
        editTextAddEventStartDateID = view.findViewById(R.id.editTextAddEventStartDateID);
        editTextAddEventStartTimeID  = view.findViewById(R.id.editTextAddEventStartTimeID);
        editTextAddEventEndDateID = view.findViewById(R.id.editTextAddEventEndDateID);
        editTextAddEventEndTime = view.findViewById(R.id.editTextAddEventEndTime);
        spinnerAddEventTypeId = view.findViewById(R.id.spinnerAddEventTypeId);
        spinnerAddEventPriorityId = view.findViewById(R.id.spinnerAddEventPriorityId);
        switchAddEventEnableNotificationId = view.findViewById(R.id.switchAddEventEnableNotificationId);

        buttonAddEventBackToViewId = view.findViewById(R.id.buttonAddEventBackToViewId);
        buttonAddEventCreateNewId = view.findViewById(R.id.buttonAddEventCreateNewId);


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, NotificationTypes.getAsStringArray());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAddEventTypeId.setAdapter(arrayAdapter);
        spinnerAddEventTypeId.setSelection(NotificationTypes.General.ordinal());

    }
}
