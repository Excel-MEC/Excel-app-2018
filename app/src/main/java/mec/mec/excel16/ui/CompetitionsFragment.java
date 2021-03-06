package mec.mec.excel16.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import mec.mec.excel16.adapter.CompetitionsAdapter;
import mec.mec.excel16.R;
import mec.mec.excel16.models.Competition;
import mec.mec.excel16.retrofit_api.ApiService;
import mec.mec.excel16.retrofit_api.RetroClient;
import mec.mec.excel16.util.GridSpacingItemDecoration;
import mec.mec.excel16.util.InternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;


public class CompetitionsFragment extends Fragment {

    private RecyclerView recyclerView;
    private View parentView;
    private SharedPreferences mPrefs;

    private ArrayList<Competition> competitionsList;
    private CompetitionsAdapter adapter;


    public CompetitionsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");

        return inflater.inflate(R.layout.fragment_competitions, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPrefs = getActivity().getPreferences(MODE_PRIVATE);

        competitionsList = new ArrayList<>();

        parentView = view.getRootView();
        recyclerView = getView().findViewById(R.id.recycler_view);


        adapter = new CompetitionsAdapter(getActivity(), competitionsList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(4), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        /**
         * Checking Internet Connection
         */
        if (InternetConnection.checkConnection(getActivity())) {
            final ProgressDialog dialog;
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Getting Competitions info");
            dialog.setMessage("wait maybe");
            dialog.show();

//            Creating an object of our api interface
            ApiService api = RetroClient.getApiService();

            /**
             * Calling JSON
             */
            Call<ArrayList<Competition>> call = api.getCompetitions();


            /**
             * Enqueue Callback will be call when get response...
             */
            call.enqueue(new Callback<ArrayList<Competition>>() {
                @Override
                public void onResponse(Call<ArrayList<Competition>> call, Response<ArrayList<Competition>> response) {
                    //Dismiss Dialog
                    Log.d(TAG, "onResponse: ");
                    dialog.dismiss();

                    if(response.isSuccessful()) {

                        competitionsList = response.body();

                        saveCompetitionsData();

                        // Update adapter with the fetched data
                        adapter.setCompetitions(competitionsList);
                        adapter.notifyDataSetChanged();

                    } else {
                        if (!retrieveCompetitionsData()) {
                            Snackbar.make(parentView, "Something wrong", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Competition>> call, Throwable t) {
                    retrieveCompetitionsData();
                    dialog.dismiss();
                }
            });

        } else {
            if (!retrieveCompetitionsData()) {
                Snackbar.make(parentView, "No network", Snackbar.LENGTH_LONG).show();
            }
        }

}

    private boolean retrieveCompetitionsData() {
        Gson gson = new Gson();
        String json = mPrefs.getString("CompetitionsList", "");
        if (!json.isEmpty()) {
            Type type = new TypeToken< List<Competition> >() {}.getType();
            competitionsList = gson.fromJson(json, type);

            // Update adapter with the fetched data
            adapter.setCompetitions(competitionsList);
            adapter.notifyDataSetChanged();

            Toast.makeText(getActivity(), R.string.retrieved_saved_data_warning, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void saveCompetitionsData() {
        // Save data to LocalStorage
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(competitionsList);
        prefsEditor.putString("CompetitionsList", json);
        prefsEditor.apply();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
