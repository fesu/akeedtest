package com.akeedtest.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akeedtest.android.R;
import com.akeedtest.android.listeners.OnRestaurantSelectListener;
import com.akeedtest.android.models.RestaurantListModel;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder> {

    private List<RestaurantListModel> restaurantList;
    private OnRestaurantSelectListener onRestaurantSelectListener;

    public RestaurantListAdapter(List<RestaurantListModel> restaurantList, OnRestaurantSelectListener onRestaurantSelectListener) {
        this.restaurantList = restaurantList;
        this.onRestaurantSelectListener = onRestaurantSelectListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_car_name;
        LinearLayout ln_container;

        MyViewHolder(View view) {
            super(view);
            //tv_car_name = view.findViewById(R.id.tv_car_name);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final RestaurantListModel restaurantListModel = restaurantList.get(position);
        //holder.tv_car_name.setText(restaurantListModel.getCar_model());

        holder.ln_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CommonUtilities.getDefaultFragmentManager().popBackStack();
                onRestaurantSelectListener.onRestaurantSelect(restaurantListModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}
