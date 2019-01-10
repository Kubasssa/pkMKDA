package com.example.kuba.databasetest;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.kuba.databasetest.R.menu.layout_search_menu;

public class FragmentDatabase extends Fragment
{
    private ArrayList<Item> fragmentDatabaseItemList;

    private RecyclerView fragmentDatabaseRecyclerView;
    private AdapterDatabase fragmentDatabaseAdapter;
    private RecyclerView.LayoutManager fragmentDatabaseLayoutManager;
    DatabaseHelper database;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_database, container, false);
        database = new DatabaseHelper(getContext()); //view.getContext

        setHasOptionsMenu(true);
        createItemList();
        buildRecyclerView();
        /***** ^ *****/
        fragmentDatabaseRecyclerView = view.findViewById(R.id.fd_recycler_view);
        fragmentDatabaseLayoutManager = new LinearLayoutManager(getContext());
        fragmentDatabaseAdapter = new AdapterDatabase(fragmentDatabaseItemList);

        fragmentDatabaseRecyclerView.setLayoutManager(fragmentDatabaseLayoutManager);
        fragmentDatabaseRecyclerView.setAdapter(fragmentDatabaseAdapter);

        fragmentDatabaseAdapter.setOnItemClickListener(new AdapterDatabase.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = ItemDetails.newInstance(fragmentDatabaseItemList.get(position));
                ft.replace(R.id.ma_fragment_container, fragment);
                ft.commit();
            }

            @Override
            public void onAddClick(int position)
            {
                addToEaten(position);
            }
        });


        /***** ______ *****/
        return view;
    }

    public void createItemList ()
    {
        Cursor x = database.getAllFoodData();
        /**** may need optimalization *****/
        List<String> productNames = new ArrayList<>();
        List<String> productPortion = new ArrayList<>();
        List<Double> productCalories = new ArrayList<>();
        List<Double> productCrabs = new ArrayList<>();
        List<Double> productFat = new ArrayList<>();
        List<Double> productProteins = new ArrayList<>();

        while (x.moveToNext())
        {
            productNames.add(x.getString(0));
            productPortion.add(x.getString(1));
            productCalories.add(Double.parseDouble(x.getString(2)));
            productCrabs.add(Double.parseDouble(x.getString(3)));
            productFat.add(Double.parseDouble(x.getString(4)));
            productProteins.add(Double.parseDouble(x.getString(5)));
        }

//        System.out.println(productNames);
//        System.out.println(productPortion);
//        System.out.println(productCalories);
//        System.out.println(productCrabs);
//        System.out.println(productFat);
//        System.out.println(productProteins);



        fragmentDatabaseItemList = new ArrayList<>();
        for (int i=0;i<(productNames.size());i++)
        {
            fragmentDatabaseItemList.add(new Item( productNames.get(i), productPortion.get(i), productCalories.get(i), productCrabs.get(i), productFat.get(i), productProteins.get(i)));

        }

    }

    public void buildRecyclerView()
    {

    }

    public void addToEaten(int position)
    {

        Item omnomnom = new Item(fragmentDatabaseItemList.get(position));
        database.eatProduct(omnomnom);
        Toast.makeText(getActivity(),"Data Inserted",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(layout_search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText)
            {
                fragmentDatabaseAdapter.getFilter().filter(searchText);
                return false;
            }
        });
    }
}
