package ml.arjunnair.excelapp;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yalantis.filter.animator.FiltersListItemAnimator;
import com.yalantis.filter.listener.FilterListener;
import com.yalantis.filter.widget.Filter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ml.arjunnair.excelapp.util.GridSpacingItemDecoration;

import static android.support.constraint.Constraints.TAG;


public class EventFragment extends Fragment implements FilterListener<EventTag>{

    private RecyclerView recyclerView;
    private EventsAdapter adapter;
    private TextView emptyText;
    private List<Event> eventList;
    private List<Event> allEvents;
    Filter<EventTag> mFilter;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        emptyText = getView().findViewById(R.id.recycler_empty_text);

        allEvents = new ArrayList<>(getEvents());
        eventList = new ArrayList<>(allEvents);
        adapter = new EventsAdapter(getActivity(), eventList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


//        Filter setup
        mFilter = view.findViewById(R.id.filter);
        mFilter.setAdapter(new EventFilterAdapter(getActivity(), getTags()));
        mFilter.setListener(this);

        //the text to show when there's no selected items
        mFilter.setNoSelectedItemText("Filters ");
        mFilter.build();
    }

    private List<Event> getEvents() {
        return new ArrayList<Event>() {{
            add(new Event("Event Name", "Event Desc", 0, 0 , R.drawable.excel_logo2));
            add(new Event("Event Name2", "Event Desc2", 1, 1 , R.drawable.excel_logo2));
            add(new Event("Event Name3", "Event Desc3", 2, 2 , R.drawable.excel_logo2));
            add(new Event("Event Name4", "Event Desc4", 0, 0 , R.drawable.excel_logo2));
            add(new Event("Event Name5", "Event Desc5", 3, 2 , R.drawable.excel_logo2));

        }};
    }

    private List<Event> findByTags(List<EventTag> tags) {
        List<Event> events = new ArrayList<>();
        List<Event> events_date = new ArrayList<>(); // Events that satisfy the date criteria

        // Gets all events that satisfy the dates given then
        // sorts the ones which satisfy the category constraints
        for (Event event : allEvents) {
            for (EventTag tag : tags) {
                if (tag.getType() == EventTag.DATE_TYPE &&
                        event.hasTag(tag) &&
                        !events_date.contains(event)) {
                    events_date.add(event);
                }
            }
        }

        // Empty means no date was specified
        if (events_date.isEmpty()) events_date = allEvents;

        // Check for category constraint
        for (Event event : events_date) {
            for (EventTag tag : tags) {
                if (tag.getType() == EventTag.CAT_TYPE &&
                        event.hasTag(tag)) {
                    events.add(event);
                }
            }
        }

        return events;
    }

    private List<EventTag> getTags() {
        List<EventTag> tags = new ArrayList<>();

        // Clears all filters
        tags.add(new EventTag(-1, -1));

        for (int i = 0; i < Event.CATEGORIES.length; ++i) {
            tags.add(new EventTag(i, EventTag.CAT_TYPE));
        }

        for (int i = 0; i < Event.DATES.length; ++i) {
            tags.add(new EventTag(i, EventTag.DATE_TYPE));
        }

        return tags;
    }

    @Override
    public void onFiltersSelected(ArrayList<EventTag> filters) {
        List<Event> newEvents = findByTags(filters);
        List<Event> oldEvents = adapter.getEvents();
        adapter.setEvents(newEvents);
        calculateDiff(oldEvents, newEvents);

        if (newEvents.isEmpty()) emptyText.setVisibility(View.VISIBLE);
        else emptyText.setVisibility(View.INVISIBLE);
    }

    private void calculateDiff(final List<Event> oldList, final List<Event> newList) {
        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldList.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
            }
        }).dispatchUpdatesTo(adapter);
    }

    @Override
    public void onNothingSelected() {
        if (recyclerView != null) {
            adapter.setEvents(allEvents);
            adapter.notifyDataSetChanged();
        }

        emptyText.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFilterSelected(EventTag event) {
        if (event.getText().equals(EventTag.DEFAULT_TEXT)) {
            mFilter.deselectAll();
            mFilter.collapse();
        }
    }

    @Override
    public void onFilterDeselected(EventTag event) {

    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

