package ml.arjunnair.excelapp.ui;


import android.app.ProgressDialog;
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

import ml.arjunnair.excelapp.R;
import ml.arjunnair.excelapp.adapter.ScheduleEventsAdapter;
import ml.arjunnair.excelapp.models.ScheduleEvent;
import ml.arjunnair.excelapp.retrofit_api.ApiService;
import ml.arjunnair.excelapp.retrofit_api.RetroClient;
import ml.arjunnair.excelapp.util.GridSpacingItemDecoration;
import ml.arjunnair.excelapp.util.InternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;

public class ScheduleEventsFragment extends Fragment {

    private String dayNum= "day_one";

    private RecyclerView recyclerView;
    private View parentView;
    private SharedPreferences mPrefs;

    private ArrayList<ScheduleEvent> scheduleEvents;
    private ScheduleEventsAdapter adapter;


    public ScheduleEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle basicDetails = getArguments();
        if (basicDetails != null) {
            dayNum = basicDetails.getString("day_num");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPrefs = getActivity().getPreferences(MODE_PRIVATE);

        scheduleEvents = new ArrayList<>();

        parentView = view.getRootView();
        recyclerView = getView().findViewById(R.id.recycler_view);


        adapter = new ScheduleEventsAdapter(getActivity(), scheduleEvents);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(4), false));
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
            dialog.setTitle("Getting Schedule info");
            dialog.setMessage("wait maybe");
            dialog.show();

//            Creating an object of our api interface
            ApiService api = RetroClient.getApiService();

            /**
             * Calling JSON
             */
//            Call<ArrayList<ScheduleEvent>> call = api.getScheduleOne();
            Call<ArrayList<ScheduleEvent>> call = api.getSchedule(dayNum);


            /**
             * Enqueue Callback will be call when get response...
             */
            call.enqueue(new Callback<ArrayList<ScheduleEvent>>() {
                @Override
                public void onResponse(Call<ArrayList<ScheduleEvent>> call, Response<ArrayList<ScheduleEvent>> response) {
                    //Dismiss Dialog
                    Log.d(TAG, "onResponse: ");
                    dialog.dismiss();

                    if(response.isSuccessful()) {

                        scheduleEvents = response.body();
                        Log.d(TAG, "onResponse: success");

                        saveScheduleEventsData();

                        // Update adapter with the fetched data
                        adapter.setScheduleEvents(scheduleEvents);
                        adapter.notifyDataSetChanged();

                    } else {
                        if (!retrieveScheduleEventsData()) {
                            Snackbar.make(parentView, "Something wrong", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ScheduleEvent>> call, Throwable t) {
                    retrieveScheduleEventsData();
                    dialog.dismiss();
                    Log.d(TAG, "onFailure: failed" + t.getMessage());
                }
            });

        } else {
            if (!retrieveScheduleEventsData()) {
                Snackbar.make(parentView, "No network", Snackbar.LENGTH_LONG).show();
            }
        }

    }

    private boolean retrieveScheduleEventsData() {
        Gson gson = new Gson();
        String json = mPrefs.getString("ScheduleEventsList", "");
        if (!json.isEmpty()) {
            Type type = new TypeToken< List<ScheduleEvent> >() {}.getType();
            scheduleEvents = gson.fromJson(json, type);

            // Update adapter with the fetched data
            adapter.setScheduleEvents(scheduleEvents);
            adapter.notifyDataSetChanged();

            Toast.makeText(getActivity(), R.string.retrieved_saved_data_warning, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void saveScheduleEventsData() {
        // Save data to LocalStorage
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(scheduleEvents);
        prefsEditor.putString("ScheduleEventsList", json);
        prefsEditor.apply();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
