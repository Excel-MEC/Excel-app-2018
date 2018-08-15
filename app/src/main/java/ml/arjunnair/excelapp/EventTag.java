package ml.arjunnair.excelapp;

import android.util.Log;

import com.yalantis.filter.model.FilterModel;

import org.jetbrains.annotations.NotNull;

import static android.support.constraint.Constraints.TAG;

public class EventTag implements FilterModel {
    public static final int CAT_TYPE = 0, DATE_TYPE = 1;
    public static final String DEFAULT_TEXT = "All Categories";
    private int id;
    private int type;

    public EventTag(int id, int type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHighlightColor() {
        switch(type) {
            case DATE_TYPE:
                return Event.DATES_COLOR[id];
            case CAT_TYPE:
                return Event.HIGHLIGHT_COLOR[id];
        }
        return Event.DEFAULT_HIGHLIGHT_COLOR;
    }


    public int getBackgroundColor() {
        switch(type) {
            case DATE_TYPE:
                return Event.DATES_COLOR[id];
            case CAT_TYPE:
                return Event.BACKGROUND_COLOR[id];
        }
        return Event.DEFAULT_BACKGROUND_COLOR;
    }

    @NotNull
    @Override
    public String getText() {
        switch(type) {
            case CAT_TYPE:
                return Event.CATEGORIES[id];
            case DATE_TYPE:
                Log.d(TAG, "getText: " + Event.DATES[id]);
                return Event.DATES[id];
        }
        return DEFAULT_TEXT;
    }
}
