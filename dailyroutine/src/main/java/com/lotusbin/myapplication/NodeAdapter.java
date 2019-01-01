package com.lotusbin.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeHolder> {

    public List<Items> items = new ArrayList<>(itemsx);
    public static List<Items> itemsx = Arrays.asList(new Items("ABC"), new Items("QPW"), new Items("ABC"), new Items("QPW"),
            new Items("ABC"), new Items("QPW"), new Items("ABC"), new Items("QPW"),
            new Items("ABC"), new Items("QPW"), new Items("ABC"), new Items("QPW"),
            new Items("ABC"), new Items("QPW"), new Items("ABC"), new Items("QPW"),
            new Items("ABC"), new Items("QPW"), new Items("ABC"), new Items("QPW"),
            new Items("ABC"), new Items("QPW"), new Items("YYYYYYYYYYYY"), new Items("XXXXXXXXXXXXXXXX"));

    public void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    static class Items {
        String title;
        String description;
        int priority;
        public Items() {}
        public Items(String title) {
            this.title = title;
            this.description = "Sample description";
            this.priority = priority;
        }
    }
    @NonNull
    @Override
    public NodeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_items_card_layout, viewGroup, false);
        return new NodeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NodeHolder nodeHolder, int i) {
     Items item  = items.get(i);
     nodeHolder.priority.setText(Integer.toString(item.priority));
     nodeHolder.textViewTitle.setText(item.title);
     nodeHolder.description.setText(item.description);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class NodeHolder extends  RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView description;
        private TextView priority;

        public NodeHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.text_view_title);
            description = itemView.findViewById(R.id.text_view_sub_title);
            priority = itemView.findViewById(R.id.text_view_status);
        }
    }
}
