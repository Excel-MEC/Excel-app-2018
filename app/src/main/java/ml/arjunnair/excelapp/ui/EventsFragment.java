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
import android.support.v7.widget.LinearLayoutManager;
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
import ml.arjunnair.excelapp.adapter.EventsAdapter;
import ml.arjunnair.excelapp.models.Event;
import ml.arjunnair.excelapp.retrofit_api.ApiService;
import ml.arjunnair.excelapp.retrofit_api.RetroClient;
import ml.arjunnair.excelapp.util.GridSpacingItemDecoration;
import ml.arjunnair.excelapp.util.InternetConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.support.constraint.Constraints.TAG;


public class EventsFragment extends Fragment {
    private RecyclerView recyclerView;
    private View parentView;
    private SharedPreferences mPrefs;

    private ArrayList<Event> eventsList;
    private EventsAdapter adapter;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPrefs = getActivity().getPreferences(MODE_PRIVATE);

        eventsList = new ArrayList<>();

        parentView = view.getRootView();
        recyclerView = getView().findViewById(R.id.recycler_view);


        adapter = new EventsAdapter(getActivity(), eventsList);



//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
            dialog.setTitle("Getting Events info");
            dialog.setMessage("wait maybe");
            dialog.show();

//            Creating an object of our api interface
            ApiService api = RetroClient.getApiService();

            /**
             * Calling JSON
             */
            Call<ArrayList<Event>> call = api.getEvents();


            /**
             * Enqueue Callback will be call when get response...
             */
            call.enqueue(new Callback<ArrayList<Event>>() {
                @Override
                public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                    //Dismiss Dialog
                    Log.d(TAG, "onResponse: ");
                    dialog.dismiss();

                    if(response.isSuccessful()) {

                        eventsList = response.body();

                        saveEventsData();

                        // Update adapter with the fetched data
                        adapter.setEvents(eventsList);
                        adapter.notifyDataSetChanged();

                    } else {
                        if (!retrieveEventsData()) {
                            Snackbar.make(parentView, "Something wrong", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                    retrieveEventsData();
                    dialog.dismiss();
                }
            });

        } else {
            if (!retrieveEventsData()) {
                Snackbar.make(parentView, "No network", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private boolean retrieveEventsData() {
        Gson gson = new Gson();
        String json = mPrefs.getString("EventsList", "");
        if (!json.isEmpty()) {
            Type type = new TypeToken< List<Event> >() {}.getType();
            eventsList = gson.fromJson(json, type);

            // Update adapter with the fetched data
            adapter.setEvents(eventsList);
            adapter.notifyDataSetChanged();

            Toast.makeText(getActivity(), R.string.retrieved_saved_data_warning, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void saveEventsData() {
        // Save data to LocalStorage
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(eventsList);
        prefsEditor.putString("EventsList", json);
        prefsEditor.apply();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
