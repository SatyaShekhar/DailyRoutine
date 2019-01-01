package com.lotusbin.myapplication.task;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.constants.DailyAppConstants;
import com.lotusbin.myapplication.constants.TaskStatus;
import com.lotusbin.myapplication.constants.TaskValidator;
import com.lotusbin.myapplication.model.DailyTask;
import com.lotusbin.myapplication.model.TemplateTaskViewModel;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyTaskEditFragment extends Fragment {

    private TextView newTitleTView, newDescTView, newPriorityTView, newHeadingTView;
    private Button buttonAdd, addNewDailyTaskBackButton;
    private Spinner spinnerToEditTaskCurrentStatus;
    private TemplateTaskViewModel taskViewModel;

    private ArrayAdapter<String> arrayAdapter;
    DailyTask dailyTask;

    // TaskStatus private String[] statusOptions = {};

    public DailyTaskEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_task_edit_task, container, false);

        taskViewModel = ViewModelProviders.of(getActivity()).get(TemplateTaskViewModel.class);

        newTitleTView = view.findViewById(R.id.addNewDailyTaskTitle);
        newTitleTView.setText(dailyTask.getTitle());
        newDescTView = view.findViewById(R.id.addNewDailyTaskDescription);
        newDescTView.setText(dailyTask.getDescription());
        newPriorityTView = view.findViewById(R.id.addNewDailyTaskPriority);
        newPriorityTView.setText(Integer.toString(dailyTask.getPriority()));
        newHeadingTView = view.findViewById(R.id.addNewDailyTaskHeading);

        // spinner
        spinnerToEditTaskCurrentStatus = view.findViewById(R.id.spinnerToEditTaskCurrentStatus);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, TaskStatus.getAsStringArray());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerToEditTaskCurrentStatus.setAdapter(arrayAdapter);
        spinnerToEditTaskCurrentStatus.setSelection(TaskStatus.valueOf(dailyTask.getStatus()).ordinal());


        String dateString = ((TabWithFrameActivity) getActivity()).getStringDailyFragmentCurrentDate();

        if (dateString == null) {
            dateString = DailyAppConstants.getDateWithddMMYYYYFormat(new Date());
        }

        final String createDateString = dateString;
        newHeadingTView.setText("EDIT TASK (" + createDateString + ")");

        buttonAdd = view.findViewById(R.id.addNewDailyTaskAddButton);
        addNewDailyTaskBackButton = view.findViewById(R.id.addNewDailyTaskBackButton);
        addNewDailyTaskBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((TabWithFrameActivity) getActivity()).getLocalCreatedFragmentManager().beginTransaction();
                transaction.replace(R.id.our_frame_id, new DailyTaskViewFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = newTitleTView.getText().toString();
                String description = newDescTView.getText().toString();
                String priority = newPriorityTView.getText().toString();
                String status = TaskStatus.getAsStringArray()[spinnerToEditTaskCurrentStatus.getSelectedItemPosition()];
                if(!TaskValidator.idValid(title, priority)) {
                    Toast.makeText(getContext(), "Title and Priority should be given  and Priority can only be integer." , Toast.LENGTH_SHORT).show();
                    return;
                }
                dailyTask.setTitle(title);
                dailyTask.setDescription(description);
                dailyTask.setPriority(Integer.valueOf(priority));
                dailyTask.setStatus(status);
                taskViewModel.update(dailyTask);
                FragmentTransaction transaction = ((TabWithFrameActivity) getActivity()).getLocalCreatedFragmentManager().beginTransaction();
                transaction.replace(R.id.our_frame_id, new DailyTaskViewFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
        });
        return view;
    }

    public void setDailyTask(DailyTask dailyTask) {
        this.dailyTask = dailyTask;
    }
}
