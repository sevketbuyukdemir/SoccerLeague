package com.sevketbuyukdemir.soccerleague.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.sevketbuyukdemir.soccerleague.models.SoccerTeam;
import com.sevketbuyukdemir.soccerleague.repositories.RetrofitInstance;
import com.sevketbuyukdemir.soccerleague.repositories.TeamsAPI;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewModel to send request to API with Retrofit2 API which is a HTTP REST API.
 */
public class MainActivityViewModel extends ViewModel {
    /**
     * Changeable live data which contains Soccer Team list
     */
    private MutableLiveData<List<SoccerTeam>> soccerTeams;

    public MainActivityViewModel(){
        soccerTeams = new MutableLiveData<>();
    }

    /**
     * Return immutable data which contains list of Soccer Teams.
     * Because we want to send data as unchangeable.
     * @return LiveData<List<SoccerTeam>>
     */
    public LiveData<List<SoccerTeam>> getSoccerTeams(){
        return soccerTeams;
    }

    public void getTeams() {
        /**
         * Get RetrofitInstance and create and reference to reach API
         */
        TeamsAPI apiService = RetrofitInstance.getRetrofitClient().create(TeamsAPI.class);
        /**
         * Call getTeamList(); function from TeamsAPI interface.
         * look at the interface for detailed information.
         */
        Call<List<SoccerTeam>> call = apiService.getTeamList();
        call.enqueue(new Callback<List<SoccerTeam>>() {
            @Override
            public void onResponse(Call<List<SoccerTeam>> call, Response<List<SoccerTeam>> response) {
                /**
                 * Fill Soccer Team list when response is received
                 */
                List<SoccerTeam> soccerTeamResponse = response.body();
                soccerTeams.postValue(soccerTeamResponse);
            }

            @Override
            public void onFailure(Call<List<SoccerTeam>> call, Throwable t) {
                /**
                 * if response can not received by app post null to Soccer Team list
                 */
                soccerTeams.postValue(null);
            }
        });
    }
}
