package ml.arjunnair.excelapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import ml.arjunnair.excelapp.R;
import ml.arjunnair.excelapp.models.Competition;
import ml.arjunnair.excelapp.ui.CompetitionDetailedActivity;
import ml.arjunnair.excelapp.ui.SplashActivity;

import static android.support.constraint.Constraints.TAG;

public class CompetitionsAdapter extends RecyclerView.Adapter<CompetitionsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Competition> competitionList;

    // Constructors
    public CompetitionsAdapter(Context mContext, List<Competition> competitionList) {
        this.mContext = mContext;
        this.competitionList = competitionList;
    }
    public List<Competition> getEvents() {
        return competitionList;
    }

    public void setCompetitions(List<Competition> eventList) {
        this.competitionList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.competition_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final Competition competition = competitionList.get(position);



        holder.title.setText(competition.getName());
        holder.department.setText(competition.getDepartment());
        holder.category.setText(competition.getCategory());

        int bg_color = ContextCompat.getColor(mContext, R.color.others_bg);
        switch (competition.getDepartment()) {
            case "Non-Tech":
                bg_color = ContextCompat.getColor(mContext, R.color.non_tech_bg);
                break;
            case "Electronics":
                bg_color = ContextCompat.getColor(mContext, R.color.electronics_bg);
                break;
            case "Robotics":
                bg_color = ContextCompat.getColor(mContext, R.color.robotics_bg);
                break;
            case "Computer Science":
                bg_color = ContextCompat.getColor(mContext, R.color.computer_science_bg);
        }
        holder.layout.setBackgroundColor(bg_color);

        Picasso.with(mContext).load(competition.getImg())
                .placeholder(R.drawable.excel_logo2)
                .error(R.drawable.excel_logo2)
                .into(holder.logo);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CompetitionDetailedActivity.class);
                intent.putExtra("competitionId", competition.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return competitionList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        public ImageView logo;
        public TextView title;
        public TextView department;
        public TextView category;

        private MyViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.layout);
            logo = view.findViewById(R.id.imageView);
            title = view.findViewById(R.id.title);
            department= view.findViewById(R.id.department);
            category = view.findViewById(R.id.category);
        }
    }
}

