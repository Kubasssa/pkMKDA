package com.example.kuba.databasetest;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.kuba.databasetest.adapters.AdapterDatabase;
import com.example.kuba.databasetest.objects.DatabaseHelper;
import com.example.kuba.databasetest.objects.Item;
import com.example.kuba.databasetest.objects.ItemDetails;

import java.util.ArrayList;
import java.util.List;

import static com.example.kuba.databasetest.R.menu.layout_search_menu;

public class FragmentDatabase extends Fragment
{
    private ArrayList<Item> fragmentDatabaseItemList;

    private RecyclerView fragmentDatabaseRecyclerView;
    private AdapterDatabase fragmentDatabaseAdapter;
    private RecyclerView.LayoutManager fragmentDatabaseLayoutManager;
    Button mAddNewProduct;
    DatabaseHelper database;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.layout_frag_database, container, false); //changed view to final
        database = new DatabaseHelper(getContext()); //view.getContext

        /**** inits ****/
        setHasOptionsMenu(true);
        initButtons(view);
        createItemList();
        buildRecyclerView(view);

        /**** onClicks ****/
        mAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Vibrator vb = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(10);
                Intent intent = new Intent(view.getContext(), ActivityAddNewProduct.class);
                startActivity(intent);
            }
        });
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
                Vibrator vb = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vb.vibrate(10);
            }

        });

        return view;
    }

    public void initButtons(View view)
    {
        mAddNewProduct = view.findViewById(R.id.FragDatabase_button_add_new_product);
    }

    public void createItemList ()
    {
        Cursor x = database.getAllFoodData();
        /**** may need optimalization *****/
        List<String> productNames = new ArrayList<>();
        List<String> productPortion = new ArrayList<>();
        List<Integer> productCalories = new ArrayList<>();
        List<Double> productCrabs = new ArrayList<>();
        List<Double> productFat = new ArrayList<>();
        List<Double> productProteins = new ArrayList<>();

        while (x.moveToNext())
        {
            productNames.add(x.getString(0));
            productPortion.add(x.getString(1));
            productCalories.add(Integer.parseInt(x.getString(2)));
            productCrabs.add(Double.parseDouble(x.getString(3)));
            productFat.add(Double.parseDouble(x.getString(4)));
            productProteins.add(Double.parseDouble(x.getString(5)));
        }


        fragmentDatabaseItemList = new ArrayList<>();
        for (int i=0;i<(productNames.size());i++)
        {
            fragmentDatabaseItemList.add(new Item( productNames.get(i), productPortion.get(i), productCalories.get(i), productCrabs.get(i), productFat.get(i), productProteins.get(i)));

        }

    }

    public void buildRecyclerView(View view)
    {
        fragmentDatabaseRecyclerView = view.findViewById(R.id.fd_recycler_view);
        fragmentDatabaseLayoutManager = new LinearLayoutManager(getContext());
        fragmentDatabaseAdapter = new AdapterDatabase(fragmentDatabaseItemList);

        fragmentDatabaseRecyclerView.setLayoutManager(fragmentDatabaseLayoutManager);
        fragmentDatabaseRecyclerView.setAdapter(fragmentDatabaseAdapter);
    }

    public void addToEaten(int position)
    {
        Item omnomnom = new Item(fragmentDatabaseItemList.get(position));

        database.eatProduct(omnomnom);
        database.addAlreadyEatenCalories(omnomnom.getCalories());
        database.addAlreadyEatenCarbs(omnomnom.getCarbs());
        database.addAlreadyEatenFat(omnomnom.getFat());
        database.addAlreadyEatenProteins(omnomnom.getProteins());

        Toast.makeText(getActivity(),"OMNOMNOM",Toast.LENGTH_SHORT).show();
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







