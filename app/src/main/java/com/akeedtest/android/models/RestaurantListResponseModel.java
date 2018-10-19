package com.akeedtest.android.models;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantListResponseModel implements Serializable {
    private boolean success;
    private ArrayList<RestaurantListModel> restaurantList;

    public RestaurantListResponseModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<RestaurantListModel> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(ArrayList<RestaurantListModel> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @Override
    public String toString() {
        return "RestaurantListResponseModel{" +
                "success=" + success +
                ", restaurantList=" + restaurantList +
                '}';
    }
}
