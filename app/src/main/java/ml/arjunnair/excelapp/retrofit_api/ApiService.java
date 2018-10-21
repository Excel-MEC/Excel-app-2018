package ml.arjunnair.excelapp.retrofit_api;

import java.util.ArrayList;

import ml.arjunnair.excelapp.models.Competition;
import ml.arjunnair.excelapp.models.CompetitionDetailed;
import ml.arjunnair.excelapp.models.Event;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author Pratik Butani.
 */
public interface ApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("competition")
    Call<ArrayList<Competition>> getCompetitions();

    @GET("competition/{id}")
    Call<CompetitionDetailed> getCompetitionDetailed(@Path("id") String id);

    @GET("event")
    Call<ArrayList<Event>> getEvents();
}
