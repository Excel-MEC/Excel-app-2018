package ml.arjunnair.excelapp;

import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import static android.support.constraint.Constraints.TAG;

public class Event {
    public static String[] CATEGORIES = {
            "CSE",
            "ECE",
            "EEE",
            "BME",
    };
    public static int[] DATES = {
            16,
            17,
            18,
    };

    private String name;
    private String desc;
    private int thumbnail;
    private int categoryId;
    private int dateId;

    public Event(String name, String desc, int categoryId, int dateId, int thumbnail) {
        this.name = name;
        this.desc = desc;
        this.categoryId = categoryId;
        this.dateId = dateId;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return CATEGORIES[categoryId];
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getDate() {
        return DATES[dateId];
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
    }

    public int highlightColor() {
        Log.d(TAG, "highlightColor: " + getCategory());
        switch (getCategory()) {
            case "CSE":
                return R.color.cse_highlight_color;
            case "ECE":
                return R.color.ece_highlight_color;
            case "BME":
                return R.color.bme_highlight_color;
            case "EEE":
                return R.color.eee_highlight_color;
            default:
                return R.color.default_highlight_color;
        }
    }

    public int backgroundColor() {
        Log.d(TAG, "bgColor: " + CATEGORIES[categoryId]);
        switch (CATEGORIES[categoryId]) {
            case "CSE":
                return R.color.cse_background_color;
            case "ECE":
                return R.color.ece_background_color;
            case "BME":
                return R.color.bme_background_color;
            case "EEE":
                return R.color.eee_background_color;
            default:
                return R.color.default_background_color;
        }
    }
}
