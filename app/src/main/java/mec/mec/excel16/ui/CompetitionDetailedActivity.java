package mec.mec.excel16.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import mec.mec.excel16.R;
import mec.mec.excel16.models.CompetitionDetailed;
import mec.mec.excel16.retrofit_api.ApiService;
import mec.mec.excel16.retrofit_api.RetroClient;
import mec.mec.excel16.util.InternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class CompetitionDetailedActivity extends AppCompatActivity {

    private CompetitionDetailed competition;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private SharedPreferences prefs;
    private String competitionIdString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_detailed);

        prefs = getPreferences(MODE_PRIVATE);

        // Getting competition id for which details should be shown
        Integer competitionId = getIntent().getIntExtra("competitionId", -1);
        competitionIdString = Integer.toString(competitionId);


        // Binding view pager and tab layout
        viewPager = findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);


        // Back Button should quit the activity
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

                        saveCompetitionDetailedData();
                        updateViewItems();

                    } else {
                        Log.d(TAG, "onResponse: Error when gathering details");
                        if (!retrieveCompetitionDetailedData()) {
                            Toast.makeText(CompetitionDetailedActivity.this, "Error when gathering details", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CompetitionDetailed> call, Throwable t) {
                    retrieveCompetitionDetailedData();
                    dialog.dismiss();
                }
            });

        } else {
            if(!retrieveCompetitionDetailedData()) {
                Toast.makeText(this, "No network", Toast.LENGTH_SHORT).show();
            }
        }


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
        adapter.addFragment(basicDetailsFragment, "Home");

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

    private boolean retrieveCompetitionDetailedData() {
        Gson gson = new Gson();
        String json = prefs.getString("CompetitionDetailed"+competitionIdString, "");
        if (!json.isEmpty()) {
            competition = gson.fromJson(json, CompetitionDetailed.class);

            updateViewItems();

            Toast.makeText(getApplicationContext(), R.string.retrieved_saved_data_warning, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void saveCompetitionDetailedData() {
        // Save data to LocalStorage
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(competition);
        prefsEditor.putString("CompetitionDetailed"+competitionIdString, json);
        prefsEditor.apply();
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
