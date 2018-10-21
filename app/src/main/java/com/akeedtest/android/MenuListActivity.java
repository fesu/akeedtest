package com.akeedtest.android;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akeedtest.android.adapters.MenuListAdapter;
import com.akeedtest.android.adapters.RestaurantListAdapter;
import com.akeedtest.android.listeners.OnMenuSelectListener;
import com.akeedtest.android.models.MenuListModel;
import com.akeedtest.android.models.MenuListResponseModel;
import com.akeedtest.android.models.RestaurantListModel;
import com.akeedtest.android.webservices.GetMenuListService;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MenuListActivity extends AppCompatActivity implements OnMenuSelectListener{

    private long res_id;
    private RestaurantListModel restaurantListModel;
    private Activity activity;
    private ImageView iv_res_banner;

    ArrayList<MenuListModel> menuList;
    RecyclerView rcv_menu_list;
    ProgressBar progress_bar;
    TextView tv_empty_data;
    MenuListAdapter menuListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        if (null != getIntent())
            restaurantListModel = (RestaurantListModel) getIntent().getSerializableExtra(RestaurantListAdapter.EXTRA_RES_DETAILS);

        if (restaurantListModel != null){
            res_id = restaurantListModel.getRes_id();
        }

        initComponent();

        getMenuList();

        //Toast.makeText(activity, "Res ID : " + res_id, Toast.LENGTH_LONG).show();
    }

    private void initComponent() {
        activity = MenuListActivity.this;
        menuList = new ArrayList<>();
        rcv_menu_list = findViewById(R.id.rcv_menu_list);
        progress_bar = findViewById(R.id.progress_bar);
        tv_empty_data = findViewById(R.id.tv_empty_data);
        iv_res_banner = findViewById(R.id.iv_res_banner);

        View view = findViewById(R.id.view_shade);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_menu_list.setLayoutManager(linearLayoutManager);

        Glide.with(activity).load(restaurantListModel.getRes_image_url()).into(iv_res_banner);

        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int imageHeight = screenHeight/4;
        iv_res_banner.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, imageHeight));
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, imageHeight));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void getMenuList() {
        try {
            progress_bar.setVisibility(View.VISIBLE);
            rcv_menu_list.setVisibility(View.GONE);

            GetMenuListService getMenuListService = new GetMenuListService(activity, res_id);
            createGetMenuListRequest(getMenuListService);
            getMenuListService.makeRequest();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createGetMenuListRequest(GetMenuListService getMenuListService) {
        getMenuListService.setOnResponseListener(new GetMenuListService.OnResponseListener() {
            @Override
            public void onResponse(MenuListResponseModel menuListResponseModel) {
                try {
                    if (progress_bar != null && progress_bar.getVisibility() == View.VISIBLE)
                        progress_bar.setVisibility(View.GONE);

                    if (menuListResponseModel.isSuccess()){
                        menuList = menuListResponseModel.getMenuList();

                        if (menuList.size() == 0){
                            tv_empty_data.setVisibility(View.VISIBLE);
                            rcv_menu_list.setVisibility(View.GONE);
                            tv_empty_data.setText(R.string.empty_data);
                        }
                        else {

                            // Adding mock data as per the Restaurant ID, in real project curated menu list should come from
                            // server as per the Restaurant ID
                            ArrayList<MenuListModel> resMenu = new ArrayList<>();
                            for (MenuListModel menu : menuList){
                                if (menu.getRes_id() == res_id){
                                    resMenu.add(menu);
                                }
                            }

                            tv_empty_data.setVisibility(View.GONE);
                            rcv_menu_list.setVisibility(View.VISIBLE);

                            menuListAdapter = new MenuListAdapter(resMenu, MenuListActivity.this, activity);
                            menuListAdapter.notifyDataSetChanged();
                            rcv_menu_list.setAdapter(menuListAdapter);
                        }
                    }
                    else {
                        tv_empty_data.setVisibility(View.VISIBLE);
                        rcv_menu_list.setVisibility(View.GONE);
                        tv_empty_data.setText(R.string.empty_data);

                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(activity);
                        }

                        /*builder.setTitle("Cars")
                                .setMessage(menuListResponseModel.getResponseMessage())
                                .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        getMenuList();
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
    public void onMenuSelect(MenuListModel menuListModel) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
