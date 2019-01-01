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
import com.lotusbin.myapplication.model.TemplateTask;
import com.lotusbin.myapplication.model.comparators.TemplateTaskComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TemplateTaskHolderAdapter extends RecyclerView.Adapter<TemplateTaskHolderAdapter.TemplateTaskHolder> {

    public List<TemplateTask> items = new ArrayList<>();

    TaskTempletViewFragment taskTempletViewFragment;
    public TemplateTaskHolderAdapter(TaskTempletViewFragment taskTempletViewFragment) {
        this.taskTempletViewFragment = taskTempletViewFragment;
    }


        public TemplateTask removeItem(int position) {
        TemplateTask task = items.remove(position);
        notifyDataSetChanged();
        return task;
    }

    @NonNull
    @Override
    public TemplateTaskHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_items_card_layout, viewGroup, false);




        return new TemplateTaskHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TemplateTaskHolder nodeHolder, final int i) {
        TemplateTask item  = items.get(i);
     nodeHolder.priority.setText("Priority : [" + Integer.toString(item.getPriority()) + "]" );
     nodeHolder.textViewTitle.setText(item.getTitle());
     nodeHolder.description.setText(item.getDescription());
     if (items.get(i).isItemSelected()) {
        nodeHolder.itemView.setBackgroundColor(Color.GRAY);
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


        nodeHolder.dailyActionMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = ((TabWithFrameActivity) taskTempletViewFragment.getActivity()).getLocalCreatedFragmentManager().beginTransaction();
                EditDailyTemplateFragment fragment = new  EditDailyTemplateFragment();
                fragment.setTemplateTask(items.get(i));
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

    public void setItems(List<TemplateTask> templateTasks) {
        items.clear();
        items.addAll(templateTasks);
        Collections.sort(items, new TemplateTaskComparator());
        notifyDataSetChanged();
    }

    class TemplateTaskHolder extends  RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView description;
        private TextView priority;
        private TextView dailyActionMenuView;
        private boolean itemSelected = false;

        public TemplateTaskHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            description = itemView.findViewById(R.id.text_view_sub_title);
            priority = itemView.findViewById(R.id.text_view_status);
            dailyActionMenuView = itemView.findViewById(R.id.dailyActionMenuViewButton);
        }

        public void setItemSelected(boolean itemSelected) {
            this.itemSelected = itemSelected;
        }

        public void changeItemSelected() {
            this.itemSelected = ! this.itemSelected;
        }
    }
}
