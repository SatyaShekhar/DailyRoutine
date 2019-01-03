package com.lotusbin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MyListAdapter extends ArrayAdapter<String> {

    private Activity context;
    private String[] titles;
    private String[] descriptions;
    private Integer[] imageIds;

    public MyListAdapter(Activity context, String[] titles, String[] descriptions, Integer[] imageIds) {
        super(context, R.layout.my_list, titles);
        this.context = context;
        this.titles = titles;
        this.descriptions = descriptions;
        this.imageIds = imageIds;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = context.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.my_list, null, true);

        ImageView imageView = view.findViewById(R.id.image_view_id);
        TextView title = view.findViewById(R.id.text_view_title);
        TextView subTitle = view.findViewById(R.id.text_view_sub_title);
        TextView status = view.findViewById(R.id.text_view_status);
        TextView textView = view.findViewById(R.id.menuViewButton);
        textView.setOnClickListener(new MuButtonOnClickListener(getContext(), textView));
        if(position %2 ==0) {
                 status.setText("Status: PENDING, Priority: Default    ");
            } else {
            status.setText("Status: DONE, Priority: Default    ");
        }

        //imageView.setImageResource(imageIds[position]);
        imageView.setBackgroundColor(imageIds[position]);
        imageView.setBackgroundResource(imageIds[position]);
        title.setText("("+ (position + 1) + ") " + titles[position]);
        subTitle.setText(descriptions[position]);

        return view;
    }
}

class MuButtonOnClickListener implements View.OnClickListener {

    private Context context;
    private TextView textView;

    public  MuButtonOnClickListener (Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    @Override
    public void onClick(View v) {
            //Creating the instance of PopupMenu
            PopupMenu popup = new PopupMenu(context, textView);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    Toast.makeText(context,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
                }
            });
            popup.show();//showing popup menu
    }
}
