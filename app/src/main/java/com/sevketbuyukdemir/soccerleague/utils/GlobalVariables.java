package com.sevketbuyukdemir.soccerleague.utils;

import com.sevketbuyukdemir.soccerleague.models.SoccerTeam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Global variables class contains data to prevent same calculations in different classes,
 * activities and fragments. This class variables are public and static.
 *
 * REMINDER : Static mean that has been allocated "statically", meaning that its lifetime is the
 * entire run of the program.
 */
public final class GlobalVariables {
    /**
     * Control internet connection string using by network service.
     */
    public static final String INT_CONN_CONTROL_BROADCAST_STR_FOR_ACTION = "INTERNET_CHECK";
    /**
     * Hold as static and public list Soccer Team list.
     * This list filled by MainActivityViewModel with Retrofit2 API from Remote web service.
     */
    public static List<SoccerTeam> soccerTeamList;
    /**
     * This HashMap filled by a thread which is triggered by MainActivityViewModel observer.
     * Look at the Main Activity init() function.
     */
    public static HashMap<Integer, int[][]> fixture_by_ids;

    /**
     * 1- Create arraylist which contains Strings which filled by Soccer Team names.
     * 2- Create FixtureProvider object with the arraylist.
     * 3- Use fixtureProvider.prepareFixture() function to fill fixture_by_ids HashMap.
     */
    public static void createFixtureByID(){
        ArrayList<String> team_names = new ArrayList<>();
        for(int i = 0; i < soccerTeamList.size(); i++){
            team_names.add(soccerTeamList.get(i).getTeamName());
        }
        FixtureProvider fixtureProvider = new FixtureProvider(team_names);
        fixture_by_ids = fixtureProvider.prepareFixture();
    }
}
