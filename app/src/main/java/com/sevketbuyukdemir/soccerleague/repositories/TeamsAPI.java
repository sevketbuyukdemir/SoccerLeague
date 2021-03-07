package com.sevketbuyukdemir.soccerleague.repositories;

import com.sevketbuyukdemir.soccerleague.models.SoccerTeam;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TeamsAPI {
    /**
     * TODO
     * Absolute path for get Soccer Teams information. This function call by MainActivityViewModel
     * for get teams.json file from my fake server you must change this "teams" path according to
     * your API.
     * Annotation GET is HTTP REST API GET function provided by Retrofit2 REST API
     * @return Call<List<SoccerTeam>>
     */
    @GET("teams")
    Call<List<SoccerTeam>> getTeamList();
}