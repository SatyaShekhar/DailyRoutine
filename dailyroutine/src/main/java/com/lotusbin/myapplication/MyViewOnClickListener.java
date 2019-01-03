package com.lotusbin.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class MyViewOnClickListener implements  View.OnClickListener {

    int id;
    String title;
    String description;
    Context context;

    public MyViewOnClickListener(Context context, int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, "[id= " + id + ", title= " + title + ", description= "  + description, Toast.LENGTH_SHORT).show();
    }
}
