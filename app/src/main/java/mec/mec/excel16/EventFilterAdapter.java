package mec.mec.excel16;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.yalantis.filter.adapter.FilterAdapter;
import com.yalantis.filter.widget.FilterItem;

import org.jetbrains.annotations.NotNull;

import java.util.List;

class EventFilterAdapter extends FilterAdapter<EventTag> {

    private Context mContext;
    EventFilterAdapter(Context mContext, @NotNull List<? extends EventTag> items) {
        super(items);
        this.mContext = mContext;
    }

    @NotNull
    @Override
    public FilterItem createView(int position, EventTag item) {
        FilterItem filterItem = new FilterItem(mContext);

        int highlightColor = mContext.getResources().getColor(item.getHighlightColor());
        int backgroundColor = mContext.getResources().getColor(item.getBackgroundColor());
        filterItem.setStrokeColor(highlightColor);
        filterItem.setTextColor(highlightColor);
        filterItem.setCheckedTextColor(ContextCompat.getColor(mContext, android.R.color.white));
        filterItem.setColor(ContextCompat.getColor(mContext, android.R.color.white));
        filterItem.setCheckedColor(backgroundColor);
        filterItem.setText(item.getText());
        filterItem.deselect();

        return filterItem;
    }
}
