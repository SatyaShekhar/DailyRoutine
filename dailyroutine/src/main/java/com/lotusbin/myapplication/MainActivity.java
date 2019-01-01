package com.lotusbin.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private CheckBox checkBox;
    private AlertDialog.Builder alertDialogBuilder;
    private Spinner spinner;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        checkBox = findViewById(R.id.checkBox7);
        alertDialogBuilder = new AlertDialog.Builder(this);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder.setTitle("Are you interested in this purchase(Alert)")
                        .setMessage("You have done some action hence you are viewing this page (on checkBox7) (Message)");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Finish Job", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Job Done", Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Cancel this job", Toast.LENGTH_LONG).show();
                    }
                });

                alertDialogBuilder.setNeutralButton("SKIP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Just IGNORE action as We are not sure what to do ", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();

            }
        });

        String[] countries= {"Inida", "Pakistan", "Srilanka"};

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.countries,
                    android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Selected country is " + selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Nothing Selected", Toast.LENGTH_LONG).show();
            }
        });

        ArrayAdapter<CharSequence> stringArrayAdapter = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.select_dialog_item);
        String[] ary = {"Aeroplane", "America", "Apple", "Egg", "Elephant", "Eat", "Home"};
        ArrayAdapter<String> autoComAdpt = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item ,ary );
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(stringArrayAdapter);
        autoCompleteTextView.setTextColor(Color.RED);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
