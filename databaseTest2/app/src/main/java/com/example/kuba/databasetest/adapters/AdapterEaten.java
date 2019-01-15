package com.example.kuba.databasetest.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kuba.databasetest.objects.Item;
import com.example.kuba.databasetest.R;

import java.util.ArrayList;

public class AdapterEaten extends RecyclerView.Adapter<AdapterEaten.ViewHolder>
{
    private ArrayList<Item> mItemList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView mProductName;
        ImageView mDeleteProduct;
        //RelativeLayout mParentLayout;
        CardView mParentLayout;
        TextView mProductPortion;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
            super(itemView);
            mProductName = itemView.findViewById(R.id.eaten_product_name);
            mDeleteProduct = itemView.findViewById(R.id.delete_eaten_product);
            mParentLayout = itemView.findViewById(R.id.layout_item_eaten);
            mProductPortion = itemView.findViewById(R.id.eaten_portion);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) //position must be valid
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mDeleteProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }


    public AdapterEaten(ArrayList<Item> itemList)
    {
        this.mItemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_eaten, parent, false);
        ViewHolder holder = new ViewHolder(view, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) //final int position
    {
        Item currentItem = mItemList.get(position);

        holder.mProductName.setText(currentItem.getText1());
        holder.mProductPortion.setText(currentItem.getText2());

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

//    public class ViewHolder extends RecyclerView.ViewHolder
//    {
//        TextView productName;
//        ImageButton productDelete;
//        RelativeLayout parentLayout;
//        public ViewHolder(View itemView)
//        {
//            super(itemView);
//            productName = itemView.findViewById(R.id.eaten_product_name);
//            productDelete = itemView.findViewById(R.id.delete_eaten_product);
//            parentLayout = itemView.findViewById(R.id.layout_item_eaten);
//
//        }
//    }

}
