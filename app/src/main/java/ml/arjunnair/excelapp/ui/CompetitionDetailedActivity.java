package ml.arjunnair.excelapp.ui;

import android.app.ProgressDialog;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ml.arjunnair.excelapp.R;
import ml.arjunnair.excelapp.models.CompetitionDetailed;
import ml.arjunnair.excelapp.retrofit_api.ApiService;
import ml.arjunnair.excelapp.retrofit_api.RetroClient;
import ml.arjunnair.excelapp.util.InternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class CompetitionDetailedActivity extends AppCompatActivity {

    private CompetitionDetailed competition;
    ViewPager viewPager;
    ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_detailed);

        Integer competitionId = getIntent().getIntExtra("competitionId", -1);
        String competitionIdString = Integer.toString(competitionId);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (InternetConnection.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(CompetitionDetailedActivity.this);
            dialog.setTitle("Getting Competition info");
            dialog.setMessage("wait maybe");
            dialog.show();

//            Creating an object of our api interface
            ApiService api = RetroClient.getApiService();

            /**
             * Calling JSON
             */
            Log.d(TAG, "onCreate: "+ competitionIdString + competitionId);
            Call<CompetitionDetailed> call = api.getCompetitionDetailed(competitionIdString);

            call.enqueue(new Callback<CompetitionDetailed>() {
                @Override
                public void onResponse(Call<CompetitionDetailed> call, Response<CompetitionDetailed> response) {
                    //Dismiss Dialog
                    Log.d(TAG, "onResponse: Starting to request api");
                    dialog.dismiss();

                    if(response.isSuccessful()) {

                        competition = response.body();

                        updateViewItems();



                    } else {
                        Log.d(TAG, "onResponse: Error when gathering details");
                        Toast.makeText(CompetitionDetailedActivity.this, "Error when gathering details", Toast.LENGTH_SHORT).show();
//                        Snackbar.make(parentView, "Something wrong", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<CompetitionDetailed> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            Toast.makeText(this, "No network", Toast.LENGTH_SHORT).show();
//            Snackbar.make(parentView, "No network", Snackbar.LENGTH_LONG).show();
        }



        viewPager = findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void updateViewItems() {

        ConstraintLayout layout = findViewById(R.id.layout);
        TabLayout tab_Layout = findViewById(R.id.tab_layout);
        ImageView logo = findViewById(R.id.logo);
        TextView title = findViewById(R.id.title);
        TextView department = findViewById(R.id.department);
        TextView category = findViewById(R.id.category);

        title.setText(competition.getName());
        department.setText(competition.getDepartment());
        category.setText(competition.getCategory());

        Picasso.with(getApplicationContext()).load(competition.getImg())
                .placeholder(R.drawable.excel_logo2)
                .error(R.drawable.excel_logo2)
                .into(logo);

        int bg_color = ContextCompat.getColor(getApplicationContext(), R.color.others_bg);
        switch (competition.getDepartment()) {
            case "Non-Tech":
                bg_color = ContextCompat.getColor(getApplicationContext(), R.color.non_tech_bg);
                break;
            case "Electronics":
                bg_color = ContextCompat.getColor(getApplicationContext(), R.color.electronics_bg);
                break;
            case "Robotics":
                bg_color = ContextCompat.getColor(getApplicationContext(), R.color.robotics_bg);
                break;
            case "Computer Science":
                bg_color = ContextCompat.getColor(getApplicationContext(), R.color.computer_science_bg);
        }
        layout.setBackgroundColor(bg_color);
        tab_Layout.setBackgroundColor(bg_color);


        CompetitionBasicDetailsFragment basicDetailsFragment = new CompetitionBasicDetailsFragment();
        Bundle basicDetails = new Bundle();
        basicDetails.putString("prize", competition.getPrize());
        basicDetails.putString("registration", competition.getRegistration());
        basicDetails.putString("date", competition.getDate());
        basicDetails.putString("venue", competition.getVenue());
        basicDetails.putInt("color", bg_color);
        basicDetailsFragment.setArguments(basicDetails);
        adapter.addFragment(basicDetailsFragment, "Overview");

        HTMLTextFragment aboutTextFragment = new HTMLTextFragment();
        Bundle aboutDetails = new Bundle();
        aboutDetails.putString("text", competition.getAbout());
        aboutTextFragment.setArguments(aboutDetails);
        adapter.addFragment(aboutTextFragment, "About");

        HTMLTextFragment rulesTextFragment = new HTMLTextFragment();
        Bundle rulesDetails = new Bundle();
        rulesDetails.putString("text", competition.getRules());
        rulesTextFragment.setArguments(rulesDetails);
        adapter.addFragment(rulesTextFragment, "Rules");

        HTMLTextFragment contactsTextFragment = new HTMLTextFragment();
        Bundle contactsDetails = new Bundle();
        contactsDetails.putString("text", competition.getContact());
        contactsTextFragment.setArguments(contactsDetails);
        adapter.addFragment(contactsTextFragment, "Contact");


        adapter.notifyDataSetChanged();
    }


    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
