package ml.arjunnair.excelapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import com.yalantis.filter.model.FilterModel;

import org.jetbrains.annotations.NotNull;

import static android.support.constraint.Constraints.TAG;

public class Event {
    public static final String[] CATEGORIES = {
            "CSE",
            "ECE",
            "EEE",
            "BME",
    };
    public static final String[] DATES = {
            "16th",
            "17th",
            "18th",
    };

    public static final int[] HIGHLIGHT_COLOR = {
            R.color.cse_highlight_color,
            R.color.ece_highlight_color,
            R.color.eee_highlight_color,
            R.color.bme_highlight_color,
    };


    public static final int[] BACKGROUND_COLOR = {
            R.color.cse_background_color,
            R.color.ece_background_color,
            R.color.eee_background_color,
            R.color.bme_background_color,
    };

    public static final int[] DATES_COLOR = {
            R.color.date1_color,
            R.color.date2_color,
            R.color.date3_color,
    };

    public static final int DEFAULT_HIGHLIGHT_COLOR = R.color.default_highlight_color;
    public static final int DEFAULT_BACKGROUND_COLOR = R.color.default_background_color;

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

    public String getDate() {
        return DATES[dateId];
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Event)) return false;

        Event event = (Event) obj;

        // Only name and category is checked
        return this.name.equals(event.getName()) && this.getCategory().equals(event.getCategory());
    }

    //    Returns brighter color value
    public int highlightColor(Context context) {
        return ContextCompat.getColor(context, HIGHLIGHT_COLOR[categoryId]);
    }

    public int backgroundColor(Context context) {
        return ContextCompat.getColor(context, BACKGROUND_COLOR[categoryId]);
    }

    public boolean hasTag(EventTag tag) {
        if (tag.getType() == EventTag.CAT_TYPE && categoryId == tag.getId())
            return true;
        return tag.getType() == EventTag.DATE_TYPE && dateId == tag.getId();
    }
}
