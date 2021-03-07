package com.sevketbuyukdemir.soccerleague.models;

import com.google.gson.annotations.SerializedName;

/**
 * This data class represent a SoccerTeam object which contains information about a Soccer Team.
 * I use this class as model class in MVVM architecture and give SerializedNames according to
 * my API. (My api is fake api which works only local area network in home wifi.). This serialized
 * names come from my teams.json file. And used by Retrofit2 API.
 */
public class SoccerTeam {
    // TODO change SerializedNames with your api
    /**
     * id information necessary for prepare fixture easily.
     * I don't want try with Strings.
     */
    @SerializedName("id")
    private int id;

    /**
     * Name of team names for show teams to user.
     */
    @SerializedName("name")
    private String teamName;

    /**
     * Team Icon is Soccer Team icon. This is good for better UI.
     */
    @SerializedName("photo")
    private String teamIconURL;

    /**
     * Getters and Setters function for this model
     *
     */

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamIconURL() {
        return teamIconURL;
    }

    public void setTeamIconURL(String teamIconURL) {
        this.teamIconURL = teamIconURL;
    }

}
