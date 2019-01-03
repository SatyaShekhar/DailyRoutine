package com.lotusbin.myapplication.task;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.constants.DailyAppConstants;
import com.lotusbin.myapplication.constants.TaskValidator;
import com.lotusbin.myapplication.model.DailyTask;
import com.lotusbin.myapplication.model.TemplateTaskViewModel;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyTaskAddFragment extends Fragment {
    private TextView newTitleTView, newDescTView, newPriorityTView, newHeadingTView;
    private Button buttonAdd, addNewDailyTaskBackButton;
    private TemplateTaskViewModel taskViewModel;

    public DailyTaskAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_task_edit, container, false);

        taskViewModel = ViewModelProviders.of(getActivity()).get(TemplateTaskViewModel.class);

        newTitleTView = view.findViewById(R.id.addNewDailyTaskTitle);
        newDescTView = view.findViewById(R.id.addNewDailyTaskDescription);
        newPriorityTView = view.findViewById(R.id.addNewDailyTaskPriority);
        newHeadingTView = view.findViewById(R.id.addNewDailyTaskHeading);

        String dateString = ((TabWithFrameActivity) getActivity()).getStringDailyFragmentCurrentDate();

        if (dateString == null) {
            dateString = DailyAppConstants.getDateWithddMMYYYYFormat(new Date());
        }

        final String createDateString = dateString;
        newHeadingTView.setText("Add the task (" + createDateString + ")");

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

                if(!TaskValidator.idValid(title, priority)) {
                    Toast.makeText(getContext(), "Title and Priority should be given  and Priority can only be integer." , Toast.LENGTH_SHORT).show();
                    return;
                }
                DailyTask dailyTask = new DailyTask(title, description, Integer.valueOf(priority));
                dailyTask.setTaskForDay(createDateString);
                taskViewModel.insert(dailyTask);
                FragmentTransaction transaction = ((TabWithFrameActivity) getActivity()).getLocalCreatedFragmentManager().beginTransaction();
                transaction.replace(R.id.our_frame_id, new DailyTaskViewFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
        });
        return view;
    }

}
