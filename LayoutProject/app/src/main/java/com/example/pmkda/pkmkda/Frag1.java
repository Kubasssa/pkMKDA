package com.example.pmkda.pkmkda;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class Frag1 extends Fragment
{
    private static final String TAG = "Frag1";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    //ctrl+o - Override
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.d( TAG, "onCreateView: started.");

        initImageBitmaps();
        View view = inflater.inflate(R.layout.layout_frag1, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initImageBitmaps()
    {
        Log.d( TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://x9wsr1khhgk5pxnq1f1r8kye-wpengine.netdna-ssl.com/wp-content/themes/incredibleegg/assets/images/facts-left-egg.png");
        mNames.add("Egg");

        mImageUrls.add("https://res.cloudinary.com/hellofresh/image/upload/f_auto,fl_lossy,q_auto,w_640/v1/hellofresh_s3/image/55b9a2ec4dab7184418b4567.png");
        mNames.add("Potato");

        mImageUrls.add("https://mcdonalds.pl/images/products-og/big-mac.png");
        mNames.add("BigMac");

        mImageUrls.add("https://images-na.ssl-images-amazon.com/images/I/71gI-IUNUkL._SY355_.jpg");
        mNames.add("Banana");

        mImageUrls.add("https://www.clipartmax.com/png/middle/29-296754_breakfast-sausage-sausage-roll-hot-dog-clip-art-sausage-png.png");
        mNames.add("Sausage");

        mImageUrls.add("https://icon2.kisspng.com/20171221/xhq/orange-png-image-download-5a3bf19ec440d2.8308261015138779188039.jpg");
        mNames.add("Orange");

        mImageUrls.add("https://png.pngtree.com/element_pic/00/16/09/1657dba51d0da07.jpg");
        mNames.add("Pizza");

        mImageUrls.add("https://banner2.kisspng.com/20180313/uaw/kisspng-photo-on-a-milk-carton-photo-on-a-milk-carton-roya-vector-food-milk-5aa87557a12fa8.8677783115209895276602.jpg");
        mNames.add("Milk");

    }

    public void insertIntoDb(View v)
    {
        mNames.add("Fuck you");
        mImageUrls.add("https://cdn1.iconfinder.com/data/icons/hands-pt-1/100/006_-_hand_fuck_off-512.png");
    }
}
