package com.lotusbin.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

public class ViewStubActivity extends AppCompatActivity {

    private Button show, hide;
    private ViewStub viewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);

        show = findViewById(R.id.button3);
        hide = findViewById(R.id.button4);
        viewStub = findViewById(R.id.view_stub_id);
        viewStub.inflate();
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStub.setVisibility(ViewStub.VISIBLE);
            }
        });


        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewStub.setVisibility(ViewStub.GONE);
            }
        });

    }
}
