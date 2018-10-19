package com.akeedtest.android.models;

import java.io.Serializable;

public class MenuListModel implements Serializable {
    private long item_id;
    private long res_id;
    private String item_name;
    private String item_type;
    private int item_price;
    private boolean is_nonveg;

    public MenuListModel() {
    }

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    public long getRes_id() {
        return res_id;
    }

    public void setRes_id(long res_id) {
        this.res_id = res_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public int getItem_price() {
        return item_price;
    }

    public void setItem_price(int item_price) {
        this.item_price = item_price;
    }

    public boolean isIs_nonveg() {
        return is_nonveg;
    }

    public void setIs_nonveg(boolean is_nonveg) {
        this.is_nonveg = is_nonveg;
    }

    @Override
    public String toString() {
        return "MenuListModel{" +
                "item_id=" + item_id +
                ", res_id=" + res_id +
                ", item_name='" + item_name + '\'' +
                ", item_type='" + item_type + '\'' +
                ", item_price=" + item_price +
                ", is_nonveg=" + is_nonveg +
                '}';
    }
}
