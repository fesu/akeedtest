package com.akeedtest.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akeedtest.android.MenuListActivity;
import com.akeedtest.android.R;
import com.akeedtest.android.listeners.OnRestaurantSelectListener;
import com.akeedtest.android.models.RestaurantListModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder> {

    public static final String EXTRA_RES_DETAILS = "EXTRA_RES_DETAILS";

    private List<RestaurantListModel> restaurantList;
    private OnRestaurantSelectListener onRestaurantSelectListener;
    private Context context;

    public RestaurantListAdapter(List<RestaurantListModel> restaurantList, OnRestaurantSelectListener onRestaurantSelectListener,
                                 Context context) {
        this.restaurantList = restaurantList;
        this.onRestaurantSelectListener = onRestaurantSelectListener;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_res_banner;
        TextView tv_res_name, tv_res_area, tv_res_cuisines, tv_duration;
        //LinearLayout ln_container;

        MyViewHolder(View view) {
            super(view);
            iv_res_banner = view.findViewById(R.id.iv_res_banner);
            tv_res_name = view.findViewById(R.id.tv_res_name);
            tv_res_area = view.findViewById(R.id.tv_res_area);
            tv_res_cuisines = view.findViewById(R.id.tv_res_cuisines);
            tv_duration = view.findViewById(R.id.tv_duration);
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

        Glide.with(context).load(restaurantListModel.getRes_image_url()).into(holder.iv_res_banner);
        holder.tv_res_name.setText(restaurantListModel.getRes_name());
        holder.tv_res_area.setText(restaurantListModel.getArea());
        holder.tv_duration.setText(restaurantListModel.getDelivery_time());

        String cuisine = restaurantListModel.getCuisines()[0];
        holder.tv_res_cuisines.setText(cuisine);

        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int imageHeight = screenHeight/6;
        holder.iv_res_banner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imageHeight));
        //int width = displayMetrics.widthPixels;

        holder.iv_res_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CommonUtilities.getDefaultFragmentManager().popBackStack();
                onRestaurantSelectListener.onRestaurantSelect(restaurantListModel);

                Intent intent = new Intent(context, MenuListActivity.class);
                intent.putExtra(EXTRA_RES_DETAILS, restaurantListModel);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}
