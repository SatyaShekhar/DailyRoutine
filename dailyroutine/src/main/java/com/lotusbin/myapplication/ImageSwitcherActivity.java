package com.lotusbin.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

public class ImageSwitcherActivity extends AppCompatActivity {

    private ImageSwitcher imageSwitcher;
    private Button prev,next;
    public static List<Integer> imageNames = new ArrayList<>();
    public static int index = 0;
    static {
        imageNames.add(R.drawable.image_1);
        imageNames.add(R.drawable.image_2);
        imageNames.add(R.drawable.image_3);
        imageNames.add(R.drawable.image_4);
        imageNames.add(R.drawable.image_5);
        imageNames.add(R.drawable.image_6);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switcher);

        imageSwitcher = findViewById(R.id.myISwitcher);
        prev = findViewById(R.id.button);
        next = findViewById(R.id.button2);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == 0) {
                    index = imageNames.size() - 1;
                } else {
                    index--;
                }
                imageSwitcher.setImageResource(imageNames.get(index));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == (imageNames.size() - 1 )) {
                    index = 0;
                } else {
                    index ++;
                }
                imageSwitcher.setImageResource(imageNames.get(index));
            }
        });
    }
}


