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
import com.akeedtest.android.listeners.OnMenuSelectListener;
import com.akeedtest.android.models.MenuListModel;
import com.akeedtest.android.models.RestaurantListModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    private List<MenuListModel> menuList;
    private OnMenuSelectListener onMenuSelectListener;
    private Context context;

    public MenuListAdapter(List<MenuListModel> menuList, OnMenuSelectListener onMenuSelectListener,
                           Context context) {
        this.menuList = menuList;
        this.onMenuSelectListener = onMenuSelectListener;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_is_non_veg;
        TextView tv_item_name, tv_item_price, tv_add;
        LinearLayout ln_menu_container;

        MyViewHolder(View view) {
            super(view);
            iv_is_non_veg = view.findViewById(R.id.iv_is_non_veg);
            tv_item_name = view.findViewById(R.id.tv_item_name);
            tv_item_price = view.findViewById(R.id.tv_item_price);
            tv_add = view.findViewById(R.id.tv_add);
            ln_menu_container = view.findViewById(R.id.ln_menu_container);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_list_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MenuListModel menuListModel = menuList.get(position);

        holder.tv_item_name.setText(menuListModel.getItem_name());
        holder.tv_item_price.setText("Rs. " + menuListModel.getItem_price());

        int drawable = menuListModel.isIs_nonveg()?R.drawable.dot_red : R.drawable.dot_green;
        holder.iv_is_non_veg.setImageResource(drawable);

        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CommonUtilities.getDefaultFragmentManager().popBackStack();
                onMenuSelectListener.onMenuSelect(menuListModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
