package com.lotusbin.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {


    private ListView listView;
    private String[] titles = {"Title 1", "Title Two", "Title Three", "Title Four", "Title five", "Title 1", "Title Two", "Title Three", "Title Four", "Title five", "Title 1", "Title Two", "Title Three", "Title Four", "Title five"};
    private String[] descriptions = {"Title 1's description",
            "Title Two's description details", "Title Three's description what to be done",
            "Title Four's description message", "Title five's description value",
            "Title 1's description",
            "Title Two's description details", "Title Three's description what to be done",
            "Title Four's description message", "Title five's description value",
            "Title 1's description",
            "Title Two's description details", "Title Three's description what to be done",
            "Title Four's description message", "Title five's description value"};

    private Integer[] imageIds = {R.drawable.ckecked_a,  R.drawable.face_books ,
            R.drawable.home, R.drawable.small_left_red_512, R.drawable.unchecked_a,
            R.drawable.ckecked_a,  R.drawable.face_books ,
            R.drawable.home, R.drawable.small_left_red_512, R.drawable.unchecked_a,
            R.drawable.ckecked_a,  R.drawable.face_books ,
            R.drawable.home, R.drawable.small_left_red_512, R.drawable.unchecked_a};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_templet_view_fragment);

        MyListAdapter myListAdapter  = new MyListAdapter(this, titles, descriptions, imageIds);

        listView = findViewById(R.id.list);
        listView.setAdapter(myListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Selected Item " + position + " with ID " + id, Toast.LENGTH_SHORT).show();
            }
        });

        /*scrollView = findViewById(R.id.scrollViewDemo);
        scrollView.addView(listView);*/

    }

    public void moveToCreateNewTemplateTask(View view) {
    }
}




