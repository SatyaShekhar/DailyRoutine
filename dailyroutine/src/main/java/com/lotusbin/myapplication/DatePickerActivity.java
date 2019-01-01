package com.lotusbin.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerActivity extends AppCompatActivity {

    private DatePicker datePicker;
    private TextView textView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        datePicker = findViewById(R.id.date_picker_id);
        textView = findViewById(R.id.date_picker_text_view_id);
        button = findViewById(R.id.date_picker_button_id);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = "Current date [Year:" +
                        datePicker.getYear() + " Month : " + datePicker.getMonth() + " Day: " + datePicker.getDayOfMonth() + " ]";
                textView.setText(date);

            }
        });
    }
}
