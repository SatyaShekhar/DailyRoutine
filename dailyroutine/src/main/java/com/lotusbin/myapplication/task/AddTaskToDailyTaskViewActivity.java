package com.lotusbin.myapplication.task;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.constants.DailyAppConstants;
import com.lotusbin.myapplication.constants.TaskValidator;
import com.lotusbin.myapplication.model.DailyTask;
import com.lotusbin.myapplication.model.TemplateTaskViewModel;

public class AddTaskToDailyTaskViewActivity extends AppCompatActivity {

    private TextView newTitleTView, newDescTView, newPriorityTView, newHeadingTView;
    private Button buttonAdd;
    private TemplateTaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_to_daily_task_view);


        taskViewModel = ViewModelProviders.of(this).get(TemplateTaskViewModel.class);

        newTitleTView = findViewById(R.id.addNewDailyTaskTitle);
        newDescTView = findViewById(R.id.addNewDailyTaskDescription);
        newPriorityTView = findViewById(R.id.addNewDailyTaskPriority);
        newHeadingTView = findViewById(R.id.addNewDailyTaskHeading);
        final String createDateString = DailyAppConstants.getDateAsddMMYYYYFormatFromIntent(getIntent());
        newHeadingTView.setText("Add the task (" + createDateString + ")");

        buttonAdd = findViewById(R.id.addNewDailyTaskAddButton);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = newTitleTView.getText().toString();
                String description = newDescTView.getText().toString();
                String priority = newPriorityTView.getText().toString();

               if(!TaskValidator.idValid(title, priority)) {
                   Toast.makeText(getApplicationContext(), "Title and Priority should be given  and Priority can only be integer." , Toast.LENGTH_SHORT).show();
                   return;
               }
               DailyTask dailyTask = new DailyTask(title, description, Integer.valueOf(priority));
                dailyTask.setTaskForDay(createDateString);
                taskViewModel.insert(dailyTask);
                Intent intent = new Intent(getApplicationContext(), TabWithFrameActivity.class);
                startActivity(intent);
            }
        });

    }
}
