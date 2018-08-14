package ml.arjunnair.excelapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

        private Context mContext;
        private List<Event> eventList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public RelativeLayout layout;
            public TextView name;
            public ImageView thumbnail;
            public ImageView highlightBulb;

            public MyViewHolder(View view) {
                super(view);
                layout = (RelativeLayout) view.findViewById(R.id.card_layout);
                name = (TextView) view.findViewById(R.id.title);
                thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
                highlightBulb = (ImageView) view.findViewById(R.id.highlight_bulb);
            }
        }

        public EventsAdapter(Context mContext, List<Event> eventList) {
            this.mContext = mContext;
            this.eventList = eventList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.event_card, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            Event event = eventList.get(position);
            holder.name.setText(event.getName());

            int highlightColor = mContext.getResources().getColor(event.highlightColor());
            int backgroundColor = mContext.getResources().getColor(event.backgroundColor());
            ((GradientDrawable)holder.highlightBulb.getDrawable()).setColor(highlightColor);
            ((GradientDrawable)holder.layout.getBackground()).setColor(backgroundColor);

            Glide.with(mContext).load(event.getThumbnail()).into(holder.thumbnail);

        }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

}
