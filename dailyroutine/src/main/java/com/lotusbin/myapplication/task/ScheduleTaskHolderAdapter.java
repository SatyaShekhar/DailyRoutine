package com.lotusbin.myapplication.task;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.model.ScheduledTask;
import com.lotusbin.myapplication.model.comparators.ScheduledTaskComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ScheduleTaskHolderAdapter extends RecyclerView.Adapter<ScheduleTaskHolderAdapter.ScheduledTaskHolder> {

    public List<ScheduledTask> items = new ArrayList<>();
    private ScheduledTaskViewFragment scheduledTaskViewFragment;



    public ScheduleTaskHolderAdapter(ScheduledTaskViewFragment scheduledTaskViewFragment) {
        this.scheduledTaskViewFragment = scheduledTaskViewFragment;
    }

    public ScheduledTask removeItem(int position) {
        ScheduledTask task = items.remove(position);
        notifyDataSetChanged();
        return task;
    }

    @NonNull
    @Override
    public ScheduledTaskHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.scheduled_task_items_single_element, viewGroup, false);
        return new ScheduledTaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ScheduledTaskHolder nodeHolder, final int i) {
         final ScheduledTask item  = items.get(i);

         // TODO just with defaults

         nodeHolder.priority.setText("Scheduled : [" + new Date(item.getStartTimeInLong()) + "]" );
         nodeHolder.textViewTitle.setText(item.getTitle());
         nodeHolder.description.setText(item.getDescription());
         if (items.get(i).isItemSelected()) {
            nodeHolder.itemView.setBackgroundColor(Color.WHITE);
         } else {
             nodeHolder.itemView.setBackgroundColor(Color.WHITE);
         }
         nodeHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    items.get(i).changeItemSelected();
                    if (items.get(i).isItemSelected()) {
                        nodeHolder.itemView.setBackgroundColor(Color.GRAY);
                    } else {
                        nodeHolder.itemView.setBackgroundColor(Color.WHITE);
                    }
                }
            });


            nodeHolder.scheduleTaskActionMenuEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentTransaction transaction = ((TabWithFrameActivity) scheduledTaskViewFragment.getActivity()).getLocalCreatedFragmentManager().beginTransaction();
                    ScheduledTaskEditFragment fragment = new  ScheduledTaskEditFragment();
                    fragment.setScheduledTask(items.get(i));
                    transaction.replace(R.id.our_frame_id, fragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    transaction.commit();

                }
            });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<ScheduledTask> templateTasks) {
        items.clear();
        items.addAll(templateTasks);
        Collections.sort(items, new ScheduledTaskComparator());
        notifyDataSetChanged();
    }

    class ScheduledTaskHolder extends  RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView description;
        private TextView priority;
        private TextView scheduleTaskActionMenuEditButton;
        private boolean itemSelected = false;

        public ScheduledTaskHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            description = itemView.findViewById(R.id.text_view_sub_title);
            priority = itemView.findViewById(R.id.text_view_status);
            scheduleTaskActionMenuEditButton = itemView.findViewById(R.id.scheduleTaskActionMenuEditButton);
        }

        public void setItemSelected(boolean itemSelected) {
            this.itemSelected = itemSelected;
        }
        public void changeItemSelected() {
            this.itemSelected = ! this.itemSelected;
        }
    }
}
