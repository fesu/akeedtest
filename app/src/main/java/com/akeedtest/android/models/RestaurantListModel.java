package com.akeedtest.android.models;

import java.io.Serializable;
import java.util.Arrays;

public class RestaurantListModel implements Serializable {
    private long res_id;
    private String res_name;
    private String res_image_url;
    private String area;
    private String[] cuisines;
    private String listing_type;
    private String delivery_time;

    public RestaurantListModel() {
    }

    public long getRes_id() {
        return res_id;
    }

    public void setRes_id(long res_id) {
        this.res_id = res_id;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRes_image_url() {
        return res_image_url;
    }

    public void setRes_image_url(String res_image_url) {
        this.res_image_url = res_image_url;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String[] getCuisines() {
        return cuisines;
    }

    public void setCuisines(String[] cuisines) {
        this.cuisines = cuisines;
    }

    public String getListing_type() {
        return listing_type;
    }

    public void setListing_type(String listing_type) {
        this.listing_type = listing_type;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    @Override
    public String toString() {
        return "RestaurantListModel{" +
                "res_id=" + res_id +
                ", res_name='" + res_name + '\'' +
                ", res_image_url='" + res_image_url + '\'' +
                ", area='" + area + '\'' +
                ", cuisines=" + Arrays.toString(cuisines) +
                ", listing_type='" + listing_type + '\'' +
                ", delivery_time='" + delivery_time + '\'' +
                '}';
    }
}
