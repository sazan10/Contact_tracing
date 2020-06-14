package com.example.myapplication;

/**
 * Created by swainstha on 5/30/18.
 */
import android.content.ClipData;

/**
 * Created by swain on 3/1/18.
 */

public class RestaurantInfo {
    private String name;
    private boolean check;

    public RestaurantInfo(String name, Boolean check) {
        this.name = name;
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

}
