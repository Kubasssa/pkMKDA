package com.example.kuba.databasetest;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

public class FragmentEaten extends Fragment
{
    private ArrayList<Item> fragmentEatenItemList;

    private RecyclerView fragmentEatenRecyclerView;
    private AdapterEaten fragmentEatenAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton buttonAddFromDatabase;
    DatabaseHelper database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_frag_eaten, container, false);
        database = new DatabaseHelper(view.getContext());

        initEatenProducts();
        initRecyclerView();
        /***** ^ *****/
        fragmentEatenRecyclerView = view.findViewById(R.id.fe_recycler_view); //may crash here
        //layoutManager = new LinearLayoutManager(getActivity());
        layoutManager = new LinearLayoutManagerWrapper(getActivity(),LinearLayoutManager.VERTICAL,false);
        fragmentEatenAdapter = new AdapterEaten(fragmentEatenItemList);
        fragmentEatenRecyclerView.setLayoutManager(layoutManager);
        fragmentEatenRecyclerView.setAdapter(fragmentEatenAdapter);
        /***** ___ *****/
        initButton();
        /***** ^ *****/
        buttonAddFromDatabase = (ImageButton)/*getActivity()*/view.findViewById(R.id.fe_button);
        buttonAddFromDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.ma_fragment_container, new FragmentDatabase());
                ft.commit();
            }
        });
        /***** ___ *****/
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

    private void initRecyclerView()
    {

    }

    private void initButton() {

    }

    private void initEatenProducts()
    {
        Cursor y = database.getAllEatenData();
        //List<Integer> productId = new ArrayList<>();
        ArrayList<String> productNames = new ArrayList<>();
        ArrayList<String> productPortion = new ArrayList<>();
        ArrayList<Double> productCalories = new ArrayList<>();
        ArrayList<Double> productCrabs = new ArrayList<>();
        ArrayList<Double> productFat = new ArrayList<>();
        ArrayList<Double> productProteins = new ArrayList<>();
        while (y.moveToNext())
        {
            //productId.add(Integer.parseInt(y.getString(0)));
            productNames.add(y.getString(0));
            productPortion.add(y.getString(1));
            productCalories.add(Double.parseDouble(y.getString(2)));
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

    public void removeItem(int position) {
        /***** crash on deleting position other than highest *****/
        database.showShitInDatabase();
        System.out.println(position);

        /**** delete item from temporary array list*****/
        fragmentEatenItemList.remove(position);
        System.out.println("(Adapter)Position deleted: " + position);

        /**** delete item from database*****/
        database.deleteData(position + 1);

        /**** update adapter, think might me changed to add some animations****/
        //fragmentEatenAdapter.notifyDataSetChanged();
        fragmentEatenAdapter.notifyItemRemoved(position);
    }

}

