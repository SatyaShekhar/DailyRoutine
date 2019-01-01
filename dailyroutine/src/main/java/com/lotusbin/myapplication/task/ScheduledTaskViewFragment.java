package com.lotusbin.myapplication.task;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.model.ScheduledTask;
import com.lotusbin.myapplication.model.TemplateTaskViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduledTaskViewFragment extends Fragment {

  private TemplateTaskViewModel taskViewModel;
  private ScheduleTaskHolderAdapter nodeAdapter;

  private Button buttonScheduleTaskAddNew;
  private Spinner spinnerScheduleTaskYear, spinnerScheduleTaskMonth, spinnerScheduleTaskWeek;

  public ScheduledTaskViewFragment() {  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.schedule_task_view_fragment, container, false);
      initalizeModel();
      initializeAndManageRecyclerView(view);
      buttonScheduleTaskAddNew = view.findViewById(R.id.buttonScheduleTaskAddNew);
      buttonScheduleTaskAddNew.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentTransaction transaction = ((TabWithFrameActivity) getActivity()).getLocalCreatedFragmentManager().beginTransaction();
              ScheduledTaskAddFragment fragment = new ScheduledTaskAddFragment();
              transaction.replace(R.id.our_frame_id, fragment);
              transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
              transaction.commit();
          }
      });
      initializeAndManageSpinner(view);
      return view;
  }

    private void initializeAndManageSpinner(View view) {
        spinnerScheduleTaskYear = view.findViewById(R.id.spinnerScheduleTaskYear);

        // TODO should be derieved from data base by supported years
        String[] years = {"2018", "2019", "All"};
        ArrayAdapter<String> arrayAdapterForYear = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, years);
        arrayAdapterForYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerScheduleTaskYear.setAdapter(arrayAdapterForYear);
        spinnerScheduleTaskYear.setSelection(0); // As 0th index for current year


        List<String> months = new ArrayList<>();
        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        months.add("Aug");
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");
        months.add("All");
        ArrayAdapter<String> arrayAdapterForMonth = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, months.toArray(new String[13]));
        arrayAdapterForMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerScheduleTaskMonth = view.findViewById(R.id.spinnerScheduleTaskMonth);
        spinnerScheduleTaskMonth.setAdapter(arrayAdapterForMonth);
        spinnerScheduleTaskMonth.setSelection(12); // As 12th index for all


        ArrayAdapter<String> arrayAdapterForWeek = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,new String[]{"All"});
        arrayAdapterForWeek.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerScheduleTaskWeek = view.findViewById(R.id.spinnerScheduleTaskWeek);
        spinnerScheduleTaskWeek.setAdapter(arrayAdapterForWeek);
        spinnerScheduleTaskWeek.setSelection(0); // Only one index 0 for all

    }

    private void initializeAndManageRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_item_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(nodeAdapter);

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
    }

    private void initalizeModel() {
        nodeAdapter = new ScheduleTaskHolderAdapter(this);
        taskViewModel = ViewModelProviders.of(requireActivity()).get(TemplateTaskViewModel.class);
        taskViewModel.getScheduledTasks().observe(requireActivity(), new Observer<List<ScheduledTask>>() {
            @Override
            public void onChanged(@Nullable List<ScheduledTask> templateTasks) {
                nodeAdapter.setItems(templateTasks);
            }
        });
    }
}
