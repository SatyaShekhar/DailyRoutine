package com.lotusbin.myapplication.task;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.model.DailyTask;
import com.lotusbin.myapplication.model.comparators.DailyTaskComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DailyTaskHolderAdapter extends RecyclerView.Adapter<DailyTaskHolderAdapter.DailyTaskHolder> {

    public List<DailyTask> dailyTasks = new ArrayList<>();
    private TabWithFrameActivity activity;

    public DailyTask removeItem(int position) {
        DailyTask task = dailyTasks.remove(position);
        notifyDataSetChanged();
        return task;
    }

    @NonNull
    @Override
    public DailyTaskHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.daily_task_items_card_layout, viewGroup, false);
        return new DailyTaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyTaskHolder nodeHolder, final int i) {
        DailyTask item  = dailyTasks.get(i);
        nodeHolder.priority.setText("Priority : [" + Integer.toString(item.getPriority()) + "] Current Status : [" + item.getStatus() + "]" );
        nodeHolder.textViewTitle.setText(item.getTitle());
        nodeHolder.description.setText(item.getDescription());
        nodeHolder.editAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = activity.getLocalCreatedFragmentManager().beginTransaction();
                DailyTaskEditFragment fragment = new  DailyTaskEditFragment();
                fragment.setDailyTask(dailyTasks.get(i));
                transaction.replace(R.id.our_frame_id, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dailyTasks.size();
    }

    public void setDailyTasks(List<DailyTask> dailyTasks) {
        this.dailyTasks.clear();
        this.dailyTasks.addAll(dailyTasks);
        Collections.sort(this.dailyTasks, new DailyTaskComparator());
        notifyDataSetChanged();
    }

    public void setActivity(TabWithFrameActivity activity) {
        this.activity = activity;
    }

    class DailyTaskHolder extends  RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView description;
        private TextView priority;
        private TextView editAction;

        public DailyTaskHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            description = itemView.findViewById(R.id.text_view_sub_title);
            priority = itemView.findViewById(R.id.text_view_status);
            editAction = itemView.findViewById(R.id.menuViewButton);
        }
    }
}
