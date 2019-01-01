package com.lotusbin.myapplication.task;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.constants.LogTopics;
import com.lotusbin.myapplication.model.TemplateTask;
import com.lotusbin.myapplication.model.TemplateTaskViewModel;

public class AddTemplateTaskActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView priorityTextView;
    private Button addNewTemplateTaskButton;


    private TemplateTaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template_task);

        taskViewModel = ViewModelProviders.of(this).get(TemplateTaskViewModel.class);

        titleTextView = findViewById(R.id.addTemplateTaskTitle);
        descriptionTextView = findViewById(R.id.addTemplateTaskDescription);
        priorityTextView = findViewById(R.id.addTemplateTaskPriority);
        addNewTemplateTaskButton = findViewById(R.id.addTemplateTaskAddButton);

        addNewTemplateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleTextView.getText().toString();
                String priority = priorityTextView.getText().toString();
                TemplateTask templateTask = taskViewModel.findByTitle(title);
                if (templateTask != null) {
                    Toast.makeText(getApplicationContext(), "Template already present for the same title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (title == null || priority == null || title.isEmpty() || priority.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Title or priority is not entered (please provide and try again)", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(LogTopics.ACTIVITY_MSG, "Going to create new Title Task");
                taskViewModel.insert(new TemplateTask(title, descriptionTextView.getText().toString(), Integer.valueOf(priority)));
                Intent intent = new Intent(getApplicationContext(), TabWithFrameActivity.class);
                startActivity(intent);


            }
        });

    }
}
