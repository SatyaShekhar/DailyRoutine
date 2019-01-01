package com.lotusbin.myapplication.task;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lotusbin.myapplication.R;
import com.lotusbin.myapplication.constants.LogTopics;
import com.lotusbin.myapplication.constants.TaskValidator;
import com.lotusbin.myapplication.model.TemplateTask;
import com.lotusbin.myapplication.model.TemplateTaskViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddDailyTemplateFragment extends Fragment {

    private TextView editTextDailyTemplateTitle, editTextDailyTemplatePriority, editTextDailyTemplateDescription;
    private Button editTextDailyTemplateEditButton, addTextDailyTemplateBackButton;
    private TemplateTaskViewModel taskViewModel;

    public AddDailyTemplateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_daily_template, container, false);

        editTextDailyTemplateTitle   = view.findViewById(R.id.addTextDailyTemplateTitle);
        editTextDailyTemplatePriority = view.findViewById(R.id.addTextDailyTemplatePriority);
        editTextDailyTemplateDescription  = view.findViewById(R.id.addTextDailyTemplateDescription);


        editTextDailyTemplateEditButton  = view.findViewById(R.id.addTextDailyTemplateEditButton);
        addTextDailyTemplateBackButton  = view.findViewById(R.id.addTextDailyTemplateBackButton);
        taskViewModel = ViewModelProviders.of(getActivity()).get(TemplateTaskViewModel.class);
        editTextDailyTemplateEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editTextDailyTemplateTitle.getText().toString();
                String description = editTextDailyTemplateDescription.getText().toString();
                String priorityString = editTextDailyTemplatePriority.getText().toString();

                TemplateTask templateTask = taskViewModel.findByTitle(title);
                if (templateTask != null) {
                    Toast.makeText(getContext(), "Template already present for the same title", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TaskValidator.idValid(title, priorityString)) {
                    Toast.makeText(getContext(), "Title or priority (valid number) is not entered (please provide and try again)", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(LogTopics.ACTIVITY_MSG, "Going to create new Title Task");
                Integer priority = Integer.valueOf(priorityString);
                taskViewModel.insert(new TemplateTask(title, description, priority));

                FragmentTransaction transaction = ((TabWithFrameActivity) getActivity()).getLocalCreatedFragmentManager().beginTransaction();
                transaction.replace(R.id.our_frame_id, new TaskTempletViewFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();

            }
        });
        addTextDailyTemplateBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = ((TabWithFrameActivity) getActivity()).getLocalCreatedFragmentManager().beginTransaction();
                transaction.replace(R.id.our_frame_id, new TaskTempletViewFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
            }
        });
        return view;
    }
}
