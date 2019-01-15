package com.example.kuba.databasetest;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuba.databasetest.adapters.AdapterEaten;
import com.example.kuba.databasetest.adapters.LinearLayoutManagerWrapper;
import com.example.kuba.databasetest.objects.DatabaseHelper;
import com.example.kuba.databasetest.objects.Item;

import java.util.ArrayList;

public class FragmentEaten extends Fragment
{
    private ArrayList<Item> fragmentEatenItemList;
    private RecyclerView fragmentEatenRecyclerView;
    private AdapterEaten fragmentEatenAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button buttonAddFromDatabase;
    private Button buttonRemoveAll;
    DatabaseHelper database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_frag_eaten, container, false);
        database = new DatabaseHelper(view.getContext());

        /**** inits ****/
        initEatenProducts();
        initRecyclerView(view);
        initButton(view);

        /**** onClicks ****/
        buttonRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                removeAllItems();
                Toast.makeText(getActivity(),"Items removed",Toast.LENGTH_SHORT).show();
                //TODO: use something similar to code below instead ? ? ? ? ?
//                for(int i = (fragmentEatenItemList.size()-1); i>=0;i--)
//                {
//                   removeItem(i);
//                   try {Thread.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
//                }
            }
        });
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
            public void onItemClick(int position)
            {
                showEatenProductDetails(position);
            }

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
        buttonAddFromDatabase = (Button)view.findViewById(R.id.button_add);
        buttonRemoveAll = (Button)view.findViewById(R.id.button_remove_all);
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
            database.addAlreadyEatenCalories((-1) * fragmentEatenItemList.get(position).getCalories());
            database.addAlreadyEatenCarbs((-1.0) * fragmentEatenItemList.get(position).getCarbs());
            database.addAlreadyEatenFat((-1.0) * fragmentEatenItemList.get(position).getFat());
            database.addAlreadyEatenProteins((-1.0) * fragmentEatenItemList.get(position).getProteins());

            /**** delete item from temporary array list*****/
            fragmentEatenItemList.remove(position);
            System.out.println("(Adapter)Position deleted: " + position);

            /**** delete item from database*****/
            database.removeProductFromEaten(position + 1);

            /**** update adapter****/
            fragmentEatenAdapter.notifyItemRemoved(position);

    }

    public void removeAllItems()
    {
        for(int position = (fragmentEatenItemList.size()-1);position>=0;position--)
        {
            database.showShitInDatabase();
            System.out.println(position);

            /***** subtract values from 'calories' table *****/
            database.addAlreadyEatenCalories((-1) * fragmentEatenItemList.get(position).getCalories());
            database.addAlreadyEatenCarbs((-1.0) * fragmentEatenItemList.get(position).getCarbs());
            database.addAlreadyEatenFat((-1.0) * fragmentEatenItemList.get(position).getFat());
            database.addAlreadyEatenProteins((-1.0) * fragmentEatenItemList.get(position).getProteins());

            /**** delete item from temporary array list*****/
            fragmentEatenItemList.remove(position);
            System.out.println("(Adapter)Position deleted: " + position);

            /**** delete item from database*****/
            database.removeProductFromEaten(position + 1);

            /**** update adapter****/
            fragmentEatenAdapter.notifyItemRemoved(position);
        }

    }

    public void showEatenProductDetails(int position) {

        //TODO: make a class EatenProductBuilder later
        android.support.v7.app.AlertDialog.Builder eatenProductDetails = new android.support.v7.app.AlertDialog.Builder(getActivity());

        /** inflate view on the aleartdialog**/
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_eaten_product_dialog, null);
        eatenProductDetails.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        /** init textviews**/
        TextView mProductName = view.findViewById(R.id.eatenProductDialog_title);
        TextView mCalories = view.findViewById(R.id.eatenProductDialog_calories);
        TextView mCarbs = view.findViewById(R.id.eatenProductDialog_carbs);
        TextView mFat= view.findViewById(R.id.eatenProductDialog_fat);
        TextView mProteins = view.findViewById(R.id.eatenProductDialog_proteins);

        /** apply values **/
        String s;
        mProductName.setText(fragmentEatenItemList.get(position).getText1());
        s = ""+fragmentEatenItemList.get(position).getCalories();
        mCalories.setText(s+" kcal");
        s = ""+fragmentEatenItemList.get(position).getCarbs();
        mCarbs.setText(s+" g");
        s = ""+fragmentEatenItemList.get(position).getFat();
        mFat.setText(s+" g");
        s = ""+fragmentEatenItemList.get(position).getProteins();
        mProteins.setText(s+" g");

        eatenProductDetails.show();

        }


}

