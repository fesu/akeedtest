package com.akeedtest.android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private RelativeLayout rl_header;
    private TabLayout tabs;
    private CardView card_cart;

    ArrayList<MenuListModel> menuList;
    RecyclerView rcv_menu_list;
    ProgressBar progress_bar;
    TextView tv_empty_data, tv_title, tv_delivery_time, tv_cart_price, tv_view_cart;
    MenuListAdapter menuListAdapter;
    MenuListResponseModel menuListResponseModel;
    int cartPrice = 0;

    String ITEM_SOUP = "Soup";
    String ITEM_STARTER = "Starter";
    String ITEM_MAIN_COURSE = "Main Course";
    String ITEM_DESSERT = "Dessert";

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

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabs.getSelectedTabPosition() == 0)
                    getMenuList(ITEM_SOUP);
                else if (tabs.getSelectedTabPosition() == 1)
                    getMenuList(ITEM_STARTER);
                else if (tabs.getSelectedTabPosition() == 2)
                    getMenuList(ITEM_MAIN_COURSE);
                else
                    getMenuList(ITEM_DESSERT);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getMenuList(ITEM_SOUP);

        //Toast.makeText(activity, "Res ID : " + res_id, Toast.LENGTH_LONG).show();
    }

    private void initComponent() {
        activity = MenuListActivity.this;
        menuList = new ArrayList<>();
        rcv_menu_list = findViewById(R.id.rcv_menu_list);
        progress_bar = findViewById(R.id.progress_bar);
        tv_empty_data = findViewById(R.id.tv_empty_data);
        tv_title = findViewById(R.id.tv_title);
        iv_res_banner = findViewById(R.id.iv_res_banner);
        rl_header = findViewById(R.id.rl_header);
        tv_delivery_time = findViewById(R.id.tv_delivery_time);
        tabs = findViewById(R.id.tabs);
        card_cart = findViewById(R.id.card_cart);
        tv_cart_price = findViewById(R.id.tv_cart_price);
        tv_view_cart = findViewById(R.id.tv_view_cart);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_menu_list.setLayoutManager(linearLayoutManager);

        Glide.with(activity).load(restaurantListModel.getRes_image_url()).into(iv_res_banner);

        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int imageHeight = screenHeight/4;
        //iv_res_banner.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, imageHeight));
        rl_header.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, imageHeight));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            //getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("");

        toolbar.setNavigationIcon(R.drawable.ic_back_arrow_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_title.setText(restaurantListModel.getRes_name());
        tv_delivery_time.setText(restaurantListModel.getDelivery_time());

        addTab(ITEM_SOUP);
        addTab(ITEM_STARTER);
        addTab(ITEM_MAIN_COURSE);
        addTab(ITEM_DESSERT);

        tv_view_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "Cart view clicked", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void addTab(String title) {
        tabs.addTab(tabs.newTab().setText(title));
        //mPagerAdapter.addTabPage(title);
    }

    private void getMenuList(String itemType) {
        try {
            progress_bar.setVisibility(View.VISIBLE);
            rcv_menu_list.setVisibility(View.GONE);

            GetMenuListService getMenuListService = new GetMenuListService(activity, res_id);
            createGetMenuListRequest(getMenuListService, itemType);
            if (menuList.size() < 1)
                getMenuListService.makeRequest();
            else
                setMenuData(itemType, menuListResponseModel);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createGetMenuListRequest(GetMenuListService getMenuListService, final String itemType) {
        getMenuListService.setOnResponseListener(new GetMenuListService.OnResponseListener() {
            @Override
            public void onResponse(MenuListResponseModel menuListResponseModel) {
                try {
                    setMenuData(itemType, menuListResponseModel);
                }
                catch (Exception e){
                    e.printStackTrace();
                }


            }
        });
    }

    private void setMenuData(String itemType, MenuListResponseModel menuListResponseModel) {
        this.menuListResponseModel = menuListResponseModel;
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
                    if (menu.getRes_id() == res_id && menu.getItem_type().equals(itemType)){
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
        }
    }

    @Override
    public void onMenuSelect(MenuListModel menuListModel) {
        //Toast.makeText(activity, menuListModel.getItem_name(),Toast.LENGTH_LONG).show();
        cartPrice += menuListModel.getItem_price() + 18;
        card_cart.setVisibility(View.VISIBLE);

        tv_cart_price.setText("Rs. " + cartPrice);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
