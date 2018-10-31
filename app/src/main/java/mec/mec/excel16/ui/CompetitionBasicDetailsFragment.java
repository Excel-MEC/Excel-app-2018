package mec.mec.excel16.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mec.mec.excel16.R;
import mec.mec.excel16.models.CompetitionDetailed;


public class CompetitionBasicDetailsFragment extends Fragment {

    private String prize_text = "Not fixed";
    private String registration_text = "Not fixed";
    private String date_text = "Not fixed";
    private String venue_text = "Not fixed";

    public CompetitionBasicDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle basicDetails = getArguments();
        if (basicDetails != null) {
            prize_text = basicDetails.getString("prize");
            registration_text = basicDetails.getString("registration");
            date_text = basicDetails.getString("date");
            venue_text = basicDetails.getString("venue");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_competition_basic_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView prize = view.findViewById(R.id.prize_text);
        TextView registration = view.findViewById(R.id.registration_text);
        TextView date = view.findViewById(R.id.date_text);
        TextView venue = view.findViewById(R.id.venue_text);

        prize.setText(prize_text);
        registration.setText(registration_text);
        date.setText(date_text);
        venue.setText(venue_text);
    }
}
