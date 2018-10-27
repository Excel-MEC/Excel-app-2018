package ml.arjunnair.excelapp.retrofit_api;

import java.util.ArrayList;

import ml.arjunnair.excelapp.models.Competition;
import ml.arjunnair.excelapp.models.CompetitionDetailed;
import ml.arjunnair.excelapp.models.Contact;
import ml.arjunnair.excelapp.models.Event;
import ml.arjunnair.excelapp.models.ScheduleEvent;
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

    @GET("contact")
    Call<ArrayList<Contact>> getContacts();

    @GET("day_one")
    Call<ArrayList<ScheduleEvent>> getScheduleOne();

    @GET("{day_num}")
    Call<ArrayList<ScheduleEvent>> getSchedule(@Path("day_num") String num);

}
