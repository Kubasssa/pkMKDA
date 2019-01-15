package com.example.kuba.databasetest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kuba.databasetest.objects.DatabaseHelper;

import java.math.BigDecimal;

public class FragmentStats extends Fragment
{
    ProgressBar mCaloriesProgress;
    ProgressBar mCarbsProgress;
    ProgressBar mFatProgress;
    ProgressBar mProteinsProgress;
    TextView mCaloriesText;
    TextView mCarbsText;
    TextView mFatText;
    TextView mProteinsText;
    TextView mCaloriesPercent;
    TextView mCarbsPercent;
    TextView mFatPercent;
    TextView mProteinsPercent;
    Button editButton;

    DatabaseHelper helper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.layout_frag_stats, container, false);

        /** init database and layout items**/
        initDatabse(view);
        initLayoutItems(view);

        /** progress bars management **/
        initCaloriesBar();
        initCarbsBar();
        initFatBar();
        initProteinsBar();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Vibrator vb = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(10);
                Intent intent = new Intent(view.getContext(), ActivityEditProfile.class);
                startActivity(intent);
            }
        });

        return view;
    }

    void initDatabse(View view)
    {
        helper = new DatabaseHelper(view.getContext());
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

        mProteinsProgress = view.findViewById(R.id.progress_protein);
        mProteinsText = view.findViewById(R.id.text_protein);
        mProteinsPercent = view.findViewById(R.id.procent_protein);

        editButton =  view.findViewById(R.id.edit_button);
    }

    void initCaloriesBar()
    {
        mCaloriesText.setText("Calories: "+helper.getAlreadyEatenCalories()+"/"+helper.getCaloriesToEat());
        mCaloriesProgress.setMax(helper.getCaloriesToEat()+1);
        mCaloriesProgress.setProgress(helper.getAlreadyEatenCalories());
        double percentOfEatenCalories = (mCaloriesProgress.getProgress()*100 / helper.getCaloriesToEat());
        mCaloriesPercent.setText(percentOfEatenCalories+"%");

        if(mCaloriesProgress.getProgress()>helper.getCaloriesToEat())
        {
            mCaloriesProgress.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            mCaloriesPercent.setText("Limit exceeded.");
        }
    }

    void initCarbsBar()
    {
        mCarbsText.setText("Carbs: "+helper.getAlreadyEatenCarbs()+"/"+helper.getCarbsToEat());
        mCarbsProgress.setMax((int)(helper.getCarbsToEat()+1));
        mCarbsProgress.setProgress((int)helper.getAlreadyEatenCarbs());
        double percentOfEatenCarbs = (mCarbsProgress.getProgress()*100 / helper.getCarbsToEat());
        percentOfEatenCarbs = roundDoubleUp(percentOfEatenCarbs);
        mCarbsPercent.setText(percentOfEatenCarbs+"%");
        if(mCarbsProgress.getProgress()>helper.getCarbsToEat())
        {
            mCarbsProgress.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            mCarbsPercent.setText("Limit exceeded.");
        }
    }

    void initFatBar()
    {
        mFatText.setText("Fat: "+helper.getAlreadyEatenFat()+"/"+helper.getFatToEat());
        mFatProgress.setMax((int)(helper.getFatToEat()+1));
        mFatProgress.setProgress((int)helper.getAlreadyEatenFat());
        double percentOfEatenFat = (mFatProgress.getProgress()*100 / helper.getFatToEat());
        percentOfEatenFat = roundDoubleUp(percentOfEatenFat);
        mFatPercent.setText(percentOfEatenFat+"%");
        if(mFatProgress.getProgress()>helper.getFatToEat())
        {
            mFatProgress.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            mFatPercent.setText("Limit exceeded.");
        }
    }

    void initProteinsBar()
    {
        mProteinsText.setText("Proteins: "+helper.getAlreadyEatenProteins()+"/"+helper.getProteinsToEat());
        mProteinsProgress.setMax((int)(helper.getProteinsToEat()+1));
        mProteinsProgress.setProgress((int)helper.getAlreadyEatenProteins());
        double percentOfEatenProteins = (mProteinsProgress.getProgress()*100 / helper.getProteinsToEat());
        percentOfEatenProteins = roundDoubleUp(percentOfEatenProteins);
        mProteinsPercent.setText(percentOfEatenProteins+"%");
        if(mProteinsProgress.getProgress()>helper.getProteinsToEat())
        {
            mProteinsProgress.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
            mProteinsPercent.setText("Limit exceeded.");
        }
    }

    double roundDoubleUp(double value)
    {
        BigDecimal i = new BigDecimal(value);
        i = i.setScale(2, BigDecimal.ROUND_HALF_UP);
        value = i.doubleValue();
        return value;
    }


}
