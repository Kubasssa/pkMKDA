package com.example.kuba.databasetest.objects;

import java.io.Serializable;

public class Item implements Serializable
{
    /***** variables *****/
    private String mText1; //for layout_item_database_text_top_id
    private String mText2; //for layout_item_database_text_bottom_id

    private int mCalories;
    private double mCarbs;
    private double mFat;
    private double mProteins;

    public Item(Item item)
    {
        this.mText1 = item.getText1();
        this.mText2 = item.getText2();
        this.mCalories = item.getCalories();
        this.mCarbs = item.getCarbs();
        this.mFat = item.getFat();
        this.mProteins = item.getProteins();
    }

    public Item(String text1, String text2)
    {
        mText1 = text1;
        mText2 = text2;
    }

    public Item(String text1, String text2, int Calories, double Carbs, double Fat, double Proteins)
    {
        mText1 = text1;
        mText2 = text2;
        mCalories = Calories;
        mCarbs = Carbs;
        mFat = Fat;
        mProteins = Proteins;
    }

    public String getText1()
    {
        return mText1;
    }

    public String getText2()
    {
        return mText2;
    }

    public int getCalories()
    {
        return mCalories;
    }

    public double getCarbs()
    {
        return mCarbs;
    }

    public double getFat()
    {
        return mFat;
    }

    public double getProteins()
    {
        return mProteins;
    }

    public void setText1(String mText1) {
        this.mText1 = mText1;
    }

    public void setText2(String mText2) {
        this.mText2 = mText2;
    }

    public void setCalories(int mCalories) {
        this.mCalories = mCalories;
    }

    public void setCarbs(double mCarbs) {
        this.mCarbs = mCarbs;
    }

    public void setFat(double mFat) {
        this.mFat = mFat;
    }

    public void setProteins(double mProteins) {
        this.mProteins = mProteins;
    }




}
