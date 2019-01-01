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
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.constants.DailyAppConstants;
import com.lotusbin.myapplication.model.DailyTask;
import com.lotusbin.myapplication.model.TemplateTask;
import com.lotusbin.myapplication.model.TemplateTaskViewModel;

import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskTempletViewFragment extends Fragment {

  private TemplateTaskViewModel taskViewModel;
  private TemplateTaskHolderAdapter nodeAdapter;
  private Button addTemplateTaskToDailyTaskId, gotoAddTemplateTaskButton;
  public TaskTempletViewFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.task_templet_view_fragment, container, false);
        nodeAdapter = new TemplateTaskHolderAdapter(this);

        taskViewModel = ViewModelProviders.of(getActivity()).get(TemplateTaskViewModel.class);
        taskViewModel.getTemplateTasks().observe(getActivity(), new Observer<List<TemplateTask>>() {
            @Override
            public void onChanged(@Nullable List<TemplateTask> templateTasks) {
                //Toast.makeText(getActivity(), "Data changed with  length " + templateTasks.size(), Toast.LENGTH_SHORT).show();
                nodeAdapter.setItems(templateTasks);
            }
        });

      addTemplateTaskToDailyTaskId = view.findViewById(R.id.addTemplateTaskToDailyTaskId);

      addTemplateTaskToDailyTaskId.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              TabWithFrameActivity activity = (TabWithFrameActivity) getActivity();
              String currentAlreadySelectedDate = activity.getStringDailyFragmentCurrentDate();
              if (currentAlreadySelectedDate == null) {
                  currentAlreadySelectedDate = DailyAppConstants.getDateWithddMMYYYYFormat(new Date());
              }
              int count = 0, addedCount = 0;
              for(TemplateTask item :nodeAdapter.items) {
                  if (!item.isItemSelected()) {
                      continue;
                  }
                  count++;
                  DailyTask task = taskViewModel.findDailyByTitle(item.getTitle(), currentAlreadySelectedDate);
                  if (task != null) {
                      continue;
                  }
                  task = new DailyTask(item.getTitle(), item.getDescription(), item.getPriority());
                  task.setTaskForDay(currentAlreadySelectedDate);
                  taskViewModel.insert(task);
                  addedCount++;
              }
              Toast.makeText(getContext(), "You have " + count + " selected dailyTasks and " + addedCount + " added to daily page for " + currentAlreadySelectedDate , Toast.LENGTH_SHORT).show();
          }
      });
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

      gotoAddTemplateTaskButton = view.findViewById(R.id.addtaskbutton);
      gotoAddTemplateTaskButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              FragmentTransaction transaction = ((TabWithFrameActivity) getActivity()).getLocalCreatedFragmentManager().beginTransaction();
              AddDailyTemplateFragment fragment = new  AddDailyTemplateFragment();
              transaction.replace(R.id.our_frame_id, fragment);
              transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
              transaction.commit();
          }
      });
     return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.daily_template_actions_menu, menu);
    }
}
