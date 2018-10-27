package ml.arjunnair.excelapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ml.arjunnair.excelapp.R;
import ml.arjunnair.excelapp.models.ScheduleEvent;

public class ScheduleEventsAdapter extends RecyclerView.Adapter<ScheduleEventsAdapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<ScheduleEvent> scheduleEventsList;

    // Constructors
    public ScheduleEventsAdapter(Context mContext, ArrayList<ScheduleEvent> scheduleEventsList) {
        this.mContext = mContext;
        this.scheduleEventsList = scheduleEventsList;
    }
    public ArrayList<ScheduleEvent> getScheduleEvents() {
        return scheduleEventsList;
    }

    public void setScheduleEvents(ArrayList<ScheduleEvent> eventList) {
        this.scheduleEventsList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final ScheduleEvent scheduleEvent = scheduleEventsList.get(position);

        holder.title.setText(scheduleEvent.getName());
        holder.startTime.setText(scheduleEvent.getStart().toString());
        holder.endTime.setText(scheduleEvent.getEnd().toString());
        holder.category.setText(scheduleEvent.getDepartment());
        holder.venue.setText(scheduleEvent.getVenue());
    }


    @Override
    public int getItemCount() {
        return scheduleEventsList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout layout;
        public TextView startTime;
        public TextView endTime;
        public TextView title;
        public TextView category;
        public TextView venue;

        private MyViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.layout);
            startTime = view.findViewById(R.id.start_time);
            endTime = view.findViewById(R.id.end_time);
            title = view.findViewById(R.id.title);
            category = view.findViewById(R.id.category);
            venue = view.findViewById(R.id.venue);
        }
    }
}
