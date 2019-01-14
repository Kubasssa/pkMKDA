package com.example.kuba.databasetest;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class FragmentEaten extends Fragment
{
    private ArrayList<Item> fragmentEatenItemList;

    private RecyclerView fragmentEatenRecyclerView;
    private AdapterEaten fragmentEatenAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button buttonAddFromDatabase;
    DatabaseHelper database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_frag_eaten, container, false);
        database = new DatabaseHelper(view.getContext());

        /***** init all *****/
        initEatenProducts();
        initRecyclerView(view);
        initButton(view);

        /***** onClick actions *****/
        buttonAddFromDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.ma_fragment_container, new FragmentDatabase());
                ft.commit();
                Vibrator vb = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(10);

            }
        });
        fragmentEatenAdapter.setOnItemClickListener(new AdapterEaten.OnItemClickListener()
        {
            @Override
            public void onDeleteClick(int position)
            {
                removeItem(position);
            }
        });
        return view;
    }

    private void initRecyclerView(View view)
    {
        fragmentEatenRecyclerView = view.findViewById(R.id.fe_recycler_view); //may crash here
        //layoutManager = new LinearLayoutManager(getActivity());
        layoutManager = new LinearLayoutManagerWrapper(getActivity(),LinearLayoutManager.VERTICAL,false);
        fragmentEatenAdapter = new AdapterEaten(fragmentEatenItemList);
        fragmentEatenRecyclerView.setLayoutManager(layoutManager);
        fragmentEatenRecyclerView.setAdapter(fragmentEatenAdapter);
    }

    private void initButton(View view)
    {
        buttonAddFromDatabase = (Button)view.findViewById(R.id.addButton);
    }

    private void initEatenProducts()
    {
        Cursor y = database.getAllEatenData();
        ArrayList<String> productNames = new ArrayList<>();
        ArrayList<String> productPortion = new ArrayList<>();
        ArrayList<Integer> productCalories = new ArrayList<>();
        ArrayList<Double> productCrabs = new ArrayList<>();
        ArrayList<Double> productFat = new ArrayList<>();
        ArrayList<Double> productProteins = new ArrayList<>();
        while (y.moveToNext())
        {
            productNames.add(y.getString(0));
            productPortion.add(y.getString(1));
            productCalories.add(Integer.parseInt(y.getString(2)));
            productCrabs.add(Double.parseDouble(y.getString(3)));
            productFat.add(Double.parseDouble(y.getString(4)));
            productProteins.add(Double.parseDouble(y.getString(5)));
        }

        fragmentEatenItemList = new ArrayList<>();
        for (int i=0;i<(productNames.size());i++)
        {
            fragmentEatenItemList.add(i,new Item(productNames.get(i), productPortion.get(i), productCalories.get(i), productCrabs.get(i), productFat.get(i), productProteins.get(i)));
        }
    }

    public void removeItem(int position)
    {
        database.showShitInDatabase();
        System.out.println(position);

        /***** subtract values from 'calories' table *****/
        database.addAlreadyEatenCalories((-1)*fragmentEatenItemList.get(position).getCalories());
        database.addAlreadyEatenCarbs((-1.0)*fragmentEatenItemList.get(position).getCarbs());
        database.addAlreadyEatenFat((-1.0)*fragmentEatenItemList.get(position).getFat());
        database.addAlreadyEatenProteins((-1.0)*fragmentEatenItemList.get(position).getProteins());

        /**** delete item from temporary array list*****/
        fragmentEatenItemList.remove(position);
        System.out.println("(Adapter)Position deleted: " + position);

        /**** delete item from database*****/
        database.deleteData(position + 1);

        /**** update adapter****/
        fragmentEatenAdapter.notifyItemRemoved(position);
    }

}

