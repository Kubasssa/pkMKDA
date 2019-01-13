package com.example.kuba.databasetest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FragmentStats extends Fragment
{
    ProgressBar mCaloriesProgress;
    ProgressBar mCarbsProgress;
    ProgressBar mFatProgress;
    ProgressBar mProteinProgress;
    TextView mCaloriesText;
    TextView mCarbsText;
    TextView mFatText;
    TextView mProteinText;
    TextView mCaloriesPercent;
    TextView mCarbsPercent;
    TextView mFatPercent;
    TextView mProteinPercent;

    DatabaseHelper helper;

    private int caloriesEaten;
    private double crabsEaten;
    private double fatEaten;
    private double proteinEaten;
    private int dailyLimit;
    public double percentOfEatenFood =0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_frag_stats, container, false);
        initLayoutItems(view);

        helper = new DatabaseHelper(view.getContext());
       // System.out.println(helper.getAlreadyEatenCalories());  //tu 0 wywala @Antek i będzie wywalać, bo źle funkcja napisana xd
        mCaloriesProgress.setMax(helper.getSumOfCalories());

        mCaloriesProgress.setProgress(1116);

        //mCaloriesProgress.setProgress(50,true); requires min api 24
        percentOfEatenFood += (mCaloriesProgress.getProgress()*100 / helper.getSumOfCalories());

        mCaloriesPercent.setText(percentOfEatenFood+"%");
        if(mCaloriesProgress.getProgress()>helper.getSumOfCalories())
        {
            mCaloriesProgress.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            mCaloriesPercent.setText("Limit exceeded.");
        }

        mCaloriesText.setText("Calories: "+helper.getSumOfCalories());

        return view;
    }

    void initLayoutItems(View view)
    {

        mCaloriesProgress = view.findViewById(R.id.progress_calories);
        mCaloriesText = view.findViewById(R.id.text_calories);
        mCaloriesPercent = view.findViewById(R.id.procent_calories);

        mCarbsProgress = view.findViewById(R.id.progress_carbs);
        mCarbsText = view.findViewById(R.id.text_carbs);
        mCarbsPercent = view.findViewById(R.id.procent_carbs);

        mFatProgress = view.findViewById(R.id.progress_fat);
        mFatText = view.findViewById(R.id.text_fat);
        mFatPercent = view.findViewById(R.id.procent_fat);

        mProteinProgress = view.findViewById(R.id.progress_protein);
        mProteinText = view.findViewById(R.id.text_protein);
        mProteinPercent = view.findViewById(R.id.procent_protein);

        mCarbsText.setText("Carbs:");
        mFatText.setText("Fat:");
        mProteinText.setText("Proteins:");

    }
}
