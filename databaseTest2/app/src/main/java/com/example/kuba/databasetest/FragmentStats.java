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

    private int caloriesEaten;
    private double crabsEaten;
    private double fatEaten;
    private double proteinEaten;
    private int dailyLimit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_frag_stats, container, false);
        initLayoutItems(view);



        mCaloriesProgress.setProgress(120);
        //mCaloriesProgress.setProgress(50,true); requires min api 24
        mCaloriesPercent.setText(mCaloriesProgress.getProgress()+"%");
        if(mCaloriesProgress.getProgress()>100)
        {
            mCaloriesProgress.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            mCaloriesPercent.setText("Limit exceeded.");
        }

        return view;
    }

    void initLayoutItems(View view)
    {
        mCaloriesProgress = view.findViewById(R.id.progress_calories);
        mCaloriesProgress.setMax(101);
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

        mCaloriesText.setText("Calories:");
        mCarbsText.setText("Carbs:");
        mFatText.setText("Fat:");
        mProteinText.setText("Proteins:");

    }
}
