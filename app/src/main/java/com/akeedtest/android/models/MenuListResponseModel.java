package com.akeedtest.android.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuListResponseModel implements Serializable {
    private boolean success;
    private ArrayList<MenuListModel> menuList;

    public MenuListResponseModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<MenuListModel> getMenuList() {
        return menuList;
    }

    public void setMenuList(ArrayList<MenuListModel> menuList) {
        this.menuList = menuList;
    }

    @Override
    public String toString() {
        return "MenuListResponseModel{" +
                "success=" + success +
                ", menuList=" + menuList +
                '}';
    }
}
