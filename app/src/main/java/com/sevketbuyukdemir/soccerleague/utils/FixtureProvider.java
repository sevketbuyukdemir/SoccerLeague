package com.sevketbuyukdemir.soccerleague.utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class provide fixture for given teams.
 * prepareFixture() function return a HashMap which contains week by week fixture.
 * Week order (0...n) is key of HashMap.
 * For instance : i want to first week of fixture then get this from returned HashMap with:
 * HashMap<Integer, int[][]> fixture = prepareFixture();
 * int[][] first_week = fixture.get(0)
 */
public class FixtureProvider {
    /**
     * Hold SoccerTeam names in this class. And comes from class constructor.
     */
    private final ArrayList<String> teams;

    // Constructor
    public FixtureProvider(ArrayList<String> teams){
        this.teams = teams;
    }

    /**
     * This function is single public function in this class and return fixture HashMap
     * when its call.
     * @return
     */
    public HashMap<Integer, int[][]> prepareFixture(){
        HashMap<Integer, int[][]> weeks = new HashMap<>();
        int teamCount = teams.size();
        boolean isOdd = teams.size() % 2 != 0;
        // Add first matches
        weeks.put(0, prepareFirstWeek(isOdd, teamCount));
        for(int i = 1; i < weekCount(teamCount)/2; i++) {
            weeks.put(i, prepareFixtureNextWeek(weeks.get(i-1)));
        }
        // Add Revenges
        int totalWeekCount = weekCount(teamCount);
        int startingSize = weeks.size();
        for(int i = startingSize; i < totalWeekCount; i++) {
            int[][] firstMatchOfCurrentWeek = weeks.get(i-startingSize);
            assert firstMatchOfCurrentWeek != null;
            int[][] currentWeek = new int[firstMatchOfCurrentWeek.length][firstMatchOfCurrentWeek[0].length];
            for(int j = 0; j < currentWeek.length; j++) {
                currentWeek[j][0] = firstMatchOfCurrentWeek[j][1];
                currentWeek[j][1] = firstMatchOfCurrentWeek[j][0];
            }
            weeks.put(i, currentWeek);
        }
        // Return fixture hashMap
        return weeks;
    }

    private int weekCount(int teamCount) {
        if(teamCount%2 == 0) {
            return 2*(teamCount-1);
        }else {
            return (2*(teamCount-1))+4;
        }
    }

    private int[][] prepareFirstWeek(boolean isOdd, int teamCount){
        int[][] firstWeek;
        int team = 0;
        if(isOdd) {
            firstWeek = new int[((teamCount / 2) + 1)][2];
            for(int i = 0; i < firstWeek.length; i++) {
                for(int j = 0; j < firstWeek[0].length; j++) {
                    if((i == firstWeek.length-1)&&(j==1)) {
                        firstWeek[i][j] = -1;
                    } else {
                        team++;
                        firstWeek[i][j] = team;
                    }
                }
            }
        } else {
            firstWeek = new int[teamCount / 2][2];
            for(int i = 0; i < firstWeek.length; i++) {
                for(int j = 0; j < firstWeek[0].length; j++) {
                    team++;
                    firstWeek[i][j] = team;
                }
            }
        }
        return firstWeek;
    }

    private int[][] prepareFixtureNextWeek(int[][] previousWeek) {
        int[][] nextWeek = new int[previousWeek.length][previousWeek[0].length];
        nextWeek[0][0] = previousWeek[0][0];
        // ROWS
        for(int i = 0; i < previousWeek.length; i++) {
            // COLUMNS
            for(int j = 0; j < previousWeek[0].length; j++) {
                if((i==0)&&(j==0)) {
                    System.out.println("Pivot 0-0");
                } else if((i == 0) && (j == 1)) {
                    nextWeek[i][j] = previousWeek[i+1][j];
                } else if((i == 1) && (j == 0)) {
                    nextWeek[i][j] = previousWeek[i-1][j+1];
                }  else if((i == (previousWeek.length-1)) && (j == 0)) {
                    nextWeek[i][j] = previousWeek[i-1][j];
                } else if(j == 0) {
                    nextWeek[i][j] = previousWeek[i-1][j];
                } else if((i == (previousWeek.length-1))&& (j == 1)) {
                    nextWeek[i][j] = previousWeek[i][j-1];
                } else if(j == 1) {
                    nextWeek[i][j] = previousWeek[i+1][j];
                }
            }
        }
        return nextWeek;
    }
}
