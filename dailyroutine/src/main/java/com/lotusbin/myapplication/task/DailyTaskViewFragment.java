package com.lotusbin.myapplication.task;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.constants.DailyAppConstants;
import com.lotusbin.myapplication.constants.LogTopics;
import com.lotusbin.myapplication.model.DailyTask;
import com.lotusbin.myapplication.model.DailyTaskViewModel;

import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DailyTaskViewFragment extends Fragment {

    private DailyTaskViewModel taskViewModel;
    private TextView viewStatusTextView;


    private Button addB, selectFromTemB, prevB, nextB;
    private Observer<List<DailyTask>> observerForDailyTask;

    public DailyTaskViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_task_view, container, false);
        final DailyTaskHolderAdapter nodeAdapter = new DailyTaskHolderAdapter();
        nodeAdapter.setActivity((TabWithFrameActivity) getActivity());
        taskViewModel = ViewModelProviders.of(getActivity()).get(DailyTaskViewModel.class);


        // Manage the task view (button panel)
        viewStatusTextView = view.findViewById(R.id.dailyTaskViewDateAndStatusMessage);
        TabWithFrameActivity activity = (TabWithFrameActivity) getActivity();
        String currentAlreadySelectedDate = activity.getStringDailyFragmentCurrentDate();
        if (currentAlreadySelectedDate == null) {
            currentAlreadySelectedDate = DailyAppConstants.getDateWithddMMYYYYFormat(new Date());
        }
        viewStatusTextView.setText(currentAlreadySelectedDate);

        LiveData<List<DailyTask>> tasks  = taskViewModel.getDailyTasks();
        List<DailyTask> tasksList = tasks.getValue();

        LiveData<List<DailyTask>> tasks2  = taskViewModel.getDailyTasks(viewStatusTextView.getText().toString());
        List<DailyTask> tasksList2 = tasks2.getValue();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_item_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(nodeAdapter);

        observerForDailyTask = new Observer<List<DailyTask>>() {
            @Override
            public void onChanged(@Nullable List<DailyTask> templateTasks) {
               // TODO gettting errro due to this toast Toast.makeText(getActivity(), "Data changed with  length " + templateTasks.size(), Toast.LENGTH_SHORT).show();
                nodeAdapter.setDailyTasks(templateTasks);
            }
        };

        taskViewModel.getDailyTasks(viewStatusTextView.getText().toString()).observe(getActivity(), observerForDailyTask);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = viewHolder.getAdapterPosition();
                taskViewModel.delete(nodeAdapter.removeItem(position));
                Toast.makeText(getActivity(),"Node deleted and contains dailyTasks with size " + nodeAdapter.getItemCount() , Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);



        // Manage the buttons
        addB = view.findViewById(R.id.dailyTaskViewAddNewButton);
        selectFromTemB = view.findViewById(R.id.dailyTaskViewSelectFromTemplateButton);
        prevB = view.findViewById(R.id.dailyTaskViewPreviousButton);
        nextB = view.findViewById(R.id.dailyTaskViewNextButton);

        prevB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDisplayDay = viewStatusTextView.getText().toString();
                taskViewModel.getDailyTasks(currentDisplayDay).removeObserver(observerForDailyTask);
                String previousDayInString = DailyAppConstants.getYesterday(currentDisplayDay);
                Log.i(LogTopics.ACTIVITY_MSG, "Current display date : " + currentDisplayDay + " previous day is " + previousDayInString);
                observerForDailyTask =  new Observer<List<DailyTask>>() {
                    @Override
                    public void onChanged(@Nullable List<DailyTask> templateTasks) {
                        nodeAdapter.setDailyTasks(templateTasks);
                    }
                };
                taskViewModel.getDailyTasks(previousDayInString).observe(getActivity(), observerForDailyTask);
                viewStatusTextView.setText(previousDayInString);
                TabWithFrameActivity activity = (TabWithFrameActivity) getActivity();
                activity.setStringDailyFragmentCurrentDate(previousDayInString);
            }
        });

        nextB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDisplayDay = viewStatusTextView.getText().toString();
                taskViewModel.getDailyTasks(currentDisplayDay).removeObserver(observerForDailyTask);
                String nextDayInString = DailyAppConstants.nextDay(currentDisplayDay);
                Log.i(LogTopics.ACTIVITY_MSG, "Current display date : " + currentDisplayDay + " next day is " + nextDayInString);
                observerForDailyTask =  new Observer<List<DailyTask>>() {
                    @Override
                    public void onChanged(@Nullable List<DailyTask> templateTasks) {
                           nodeAdapter.setDailyTasks(templateTasks);
                    }
                };
                taskViewModel.getDailyTasks(nextDayInString).observe(getActivity(), observerForDailyTask);
                viewStatusTextView.setText(nextDayInString);
                TabWithFrameActivity activity = (TabWithFrameActivity) getActivity();
                activity.setStringDailyFragmentCurrentDate(nextDayInString);
            }
        });

        selectFromTemB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getContext(), SelectDailyTaskFromTemplateActivity.class);
                TabWithFrameActivity activity = (TabWithFrameActivity) getActivity();
                TabLayout tabLayout = activity.findViewById(R.id.our_tab_with_frame_id);
                TabLayout.Tab tab = tabLayout.getTabAt(1); // TODO make a consant from it and make sure its reused
                tab.select();
               // activity.startActivity(intent);
//                FragmentTransaction transaction = activity.getLocalCreatedFragmentManager().beginTransaction();
//                transaction.replace(R.id.our_frame_id, new TaskTempletViewFragment());
//                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                transaction.commit();


            }
        });

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((TabWithFrameActivity) getActivity()).getLocalCreatedFragmentManager().beginTransaction();
                DailyTaskAddFragment fragment = new DailyTaskAddFragment();
                transaction.replace(R.id.our_frame_id, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
        });

        return view;
    }

}
