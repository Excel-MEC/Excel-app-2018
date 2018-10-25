package ml.arjunnair.excelapp.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ml.arjunnair.excelapp.R;
import ml.arjunnair.excelapp.models.Event;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder>{
    private Context mContext;
    private ArrayList<Event> eventsList;

    // Constructors
    public EventsAdapter(Context mContext, ArrayList<Event> eventsList) {
        this.mContext = mContext;
        this.eventsList = eventsList;
    }
    public ArrayList<Event> getEvents() {
        return eventsList;
    }

    public void setEvents(ArrayList<Event> eventList) {
        this.eventsList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Event event = eventsList.get(position);



        holder.title.setText(event.getName());
        String htmlText = event.getDetails();
        if (htmlText != null) {
            Spanned sp = Html.fromHtml(htmlText);
            holder.content.setText(sp);
        }

        Picasso.with(mContext).load(event.getImg())
                .placeholder(R.drawable.excel_logo2)
                .error(R.drawable.excel_logo2)
                .into(holder.imageView);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.content.getVisibility() == View.GONE) {
                    holder.content.setVisibility(View.VISIBLE);
                    holder.expandButton.setImageResource(R.drawable.ic_collapse_arrow);
                } else {
                    holder.content.setVisibility(View.GONE);
                    holder.expandButton.setImageResource(R.drawable.ic_expand_arrow);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return eventsList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout;
        public ImageView imageView;
        public TextView title;
        public TextView content;
        public ImageView expandButton;

        private MyViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.layout);
            imageView = view.findViewById(R.id.image_view);
            expandButton = view.findViewById(R.id.expand_button);
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
        }
    }
}
