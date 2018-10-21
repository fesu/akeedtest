package com.akeedtest.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.akeedtest.android.adapters.RestaurantListAdapter;
import com.akeedtest.android.listeners.OnRestaurantSelectListener;
import com.akeedtest.android.models.RestaurantListModel;
import com.akeedtest.android.models.RestaurantListResponseModel;
import com.akeedtest.android.webservices.GetRestaurantListService;

import java.util.ArrayList;

public class RestaurantListActivity extends AppCompatActivity implements OnRestaurantSelectListener{
    Activity activity;

    ArrayList<RestaurantListModel> restaurantList;
    RecyclerView rcv_restaurant_list;
    ProgressBar progress_bar;
    TextView tv_empty_data;
    RestaurantListAdapter restaurantListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        initComponent();

        getRestaurantList();
    }

    private void initComponent() {
        activity = RestaurantListActivity.this;
        restaurantList = new ArrayList<>();
        rcv_restaurant_list = findViewById(R.id.rcv_restaurant_list);
        progress_bar = findViewById(R.id.progress_bar);
        tv_empty_data = findViewById(R.id.tv_empty_data);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_restaurant_list.setLayoutManager(linearLayoutManager);
    }

    private void getRestaurantList() {
        try {
            progress_bar.setVisibility(View.VISIBLE);
            rcv_restaurant_list.setVisibility(View.GONE);

            GetRestaurantListService getRestaurantListService = new GetRestaurantListService(activity);
            createGetRestaurantListRequest(getRestaurantListService);
            getRestaurantListService.makeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createGetRestaurantListRequest(GetRestaurantListService getRestaurantListService) {
        getRestaurantListService.setOnResponseListener(new GetRestaurantListService.OnResponseListener() {
            @Override
            public void onResponse(RestaurantListResponseModel restaurantListResponseModel) {
                try {
                    if (progress_bar != null && progress_bar.getVisibility() == View.VISIBLE)
                        progress_bar.setVisibility(View.GONE);

                    if (restaurantListResponseModel.isSuccess()){
                        restaurantList = restaurantListResponseModel.getRestaurantList();

                        if (restaurantList.size() == 0){
                            tv_empty_data.setVisibility(View.VISIBLE);
                            rcv_restaurant_list.setVisibility(View.GONE);
                            tv_empty_data.setText(R.string.empty_data);
                        }
                        else {
                            tv_empty_data.setVisibility(View.GONE);
                            rcv_restaurant_list.setVisibility(View.VISIBLE);

                            restaurantListAdapter = new RestaurantListAdapter(restaurantList, RestaurantListActivity.this, activity);
                            restaurantListAdapter.notifyDataSetChanged();
                            rcv_restaurant_list.setAdapter(restaurantListAdapter);
                        }
                    }
                    else {
                        tv_empty_data.setVisibility(View.VISIBLE);
                        rcv_restaurant_list.setVisibility(View.GONE);
                        tv_empty_data.setText(R.string.empty_data);

                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(activity);
                        }

                        /*builder.setTitle("Cars")
                                .setMessage(restaurantListResponseModel.getResponseMessage())
                                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        getRestaurantList();
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();*/
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onRestaurantSelect(RestaurantListModel restaurantListModel) {

    }
}
