package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static final int match_Id = 0;
    public static final int season = 1;
    public static final int city= 2;
    public static final int date = 3;
    public static final int team_1 = 4;
    public static final int team_2 = 5;
    public static final int tossWinner = 6;
    public static final int tossDecision = 7;
    public static final int result = 8;
    public static final int dl_Applied = 9;
    public static final int winByRuns = 11;
    public static final int winByWickets = 12;
    public static final int playerOfTheMatch = 13;
    public static final int venue = 14;
    public static final int winner = 10;
    public static final int inning = 1;
    public static final int battingTeam = 2;
    public static final int extraRuns = 16;
    public static final int bowler = 8;
    public static final int totalRuns = 17;


    public static void main(String[] args) throws IOException {
        List<Matches> matchesData=getMatchesData();
        List<Deliveries> deliveryData=getDeliveriesData();
        Map<Integer,String> matchID_Season=getMatchID_Season(matchesData);
        noOfMatchesWonByTeams(matchesData);
        noOfMatchesPlayedPerYear(matchesData);
        extraRunsPerTeamIn2016(deliveryData,matchID_Season);
        topEconomicalBowlerIn2015(deliveryData,matchID_Season);

    }
    private static List<Matches> getMatchesData() throws IOException {
        String path="src/com/company/matches.csv";
        List<Matches> matchesData=new ArrayList<>();

        BufferedReader br=new BufferedReader(new FileReader(path));

        String line;
        int l=0;

        while((line=br.readLine())!=null)
        {
            if(l==0)
            {
                l=1;
                continue;
            }

            String[] data=line.split(",");

            Matches match=new Matches();

            match.setMatch_Id(Integer.parseInt(data[match_Id]));
            match.setSeason(data[season]);
            match.setCity(data[city]);
            match.setDate(data[date]);
            match.setTeam1(data[team_1]);
            match.setTeam2(data[team_2]);
            match.setToss_winner(data[tossWinner]);
            match.setToss_decision(data[tossDecision]);
            match.setResult(data[result]);
            match.setDl_applied(Integer.parseInt(data[dl_Applied]));
            if(data[winner].equals("") || data[winner]==null) match.setWinner("Draw");
            else match.setWinner(data[winner]);
            match.setWin_By_Runs(Integer.parseInt(data[winByRuns]));
            match.setWin_By_wickets(Integer.parseInt(data[winByWickets]));
            match.setPlayerOfTheMatch(data[playerOfTheMatch]);
            match.setVenue(data[venue]);

            matchesData.add(match);
        }
        return matchesData;
    }
    private static List<Deliveries> getDeliveriesData() throws IOException {
        List<Deliveries> deliveriesData=new ArrayList<>();

        String path="src/com/company/deliveries.csv";
        BufferedReader br=new BufferedReader(new FileReader(path));

        String line="";
        int l=0;
        while((line= br.readLine())!=null)
        {
            String[] data=line.split(",");
            if(l==0)
            {
                l=1;
                continue;
            }

            Deliveries delivery=new Deliveries();
            delivery.setMatch_id(Integer.parseInt(data[match_Id]));
            delivery.setInning(Integer.parseInt(data[inning]));
            delivery.setBatting_team(data[battingTeam]);
            delivery.setExtra_runs(Integer.parseInt(data[extraRuns]));
            delivery.setBowler(data[bowler]);
            delivery.setTotal_runs(Integer.parseInt(data[totalRuns]));

            deliveriesData.add(delivery);
        }
        return deliveriesData;
    }
    private static void noOfMatchesWonByTeams(List<Matches> matchesData) {
        Map<String, Map<String,Integer>> noOfMatchesWonByTeams=new HashMap<>();

        for(Matches list:matchesData)
        {
            if(noOfMatchesWonByTeams.containsKey(list.getWinner()))
            {
                Map<String,Integer> seasonWiseWinningCount=noOfMatchesWonByTeams.get(list.getWinner());

                if(seasonWiseWinningCount.containsKey(list.getSeason()))
                {
                    int get=seasonWiseWinningCount.get(list.getSeason());
                    seasonWiseWinningCount.put(list.getSeason(),get+1);
                }
                else
                {
                    seasonWiseWinningCount.put(list.getSeason(),1);
                }
                noOfMatchesWonByTeams.put(list.getWinner(),seasonWiseWinningCount);
            }
            else
            {
                Map<String,Integer> seasonWiseWin=new HashMap<>();
                seasonWiseWin.put(list.getSeason(),1);
                noOfMatchesWonByTeams.put(list.getWinner(),seasonWiseWin);
            }
        }
        for (String keys : noOfMatchesWonByTeams.keySet())
        {
            //System.out.println(keys + ":"+ noOfMatchesWonByTeams.get(keys));
        }
    }
    private static void noOfMatchesPlayedPerYear(List<Matches> matchesData) {
        Map<String,Integer> noOfMatchesPlayedPerYear=new HashMap<>();

        for(Matches list:matchesData)
        {
            if(noOfMatchesPlayedPerYear.containsKey(list.getSeason()))
            {
                int get=noOfMatchesPlayedPerYear.get(list.getSeason());
                noOfMatchesPlayedPerYear.put(list.getSeason(),get+1);
            }
            else
            {
                noOfMatchesPlayedPerYear.put(list.getSeason(),1);
            }
        }
        System.out.println("No. of matches played per year :");
        for (String keys : noOfMatchesPlayedPerYear.keySet())
        {
            System.out.println(keys + ":"+ noOfMatchesPlayedPerYear.get(keys));
        }
    }


    private static Map<Integer, String> getMatchID_Season(List<Matches> matchesData) {
        Map<Integer,String> matches=new HashMap<>();

        for(Matches list:matchesData)
        {
            matches.put(list.getMatch_Id(),list.getSeason());
        }

        return matches;
    }
    private static void extraRunsPerTeamIn2016(List<Deliveries> deliveryData, Map<Integer, String> matchID_season) {
        Map<String,Integer> extraRunsPerTeam=new HashMap<>();

        for(Deliveries list:deliveryData)
        {
            if(matchID_season.get(list.getMatch_id()).equals("2016"))
            {
                if(extraRunsPerTeam.containsKey(list.getBatting_team()))
                {
                    int get=extraRunsPerTeam.get(list.getBatting_team());
                    extraRunsPerTeam.put(list.getBatting_team(), get+list.getExtra_runs());
                }
                else
                {
                    extraRunsPerTeam.put(list.getBatting_team(), list.getExtra_runs());
                }
            }
        }
        System.out.println("In 2016, extra runs per team : ");
        for (String keys : extraRunsPerTeam.keySet())
        {
            System.out.println(keys + ":"+ extraRunsPerTeam.get(keys));
        }
    }

    private static void topEconomicalBowlerIn2015(List<Deliveries> deliveryData, Map<Integer, String> matchID_season) {
        Map<String, int[]> map = new HashMap<>();

        for (Deliveries list : deliveryData) {
            if (matchID_season.get(list.getMatch_id()).equals("2015")) {
                if (map.containsKey(list.getBowler())) {
                    int[] get = map.get(list.getBowler());
                    get[0] += 1;
                    get[1] += list.getTotal_runs();
                    map.put(list.getBowler(), get);
                } else {
                    int[] get = new int[2];
                    get[0] = 1;
                    get[1] = list.getTotal_runs();
                    map.put(list.getBowler(), get);
                }
            }
        }

        String topEconomyBowler = "";
        float min = Float.MAX_VALUE;
        Map<String, Float> top5EconomyBowler = new HashMap<String, Float>();

        for (Map.Entry<String, int[]> it : map.entrySet()) {
            int[] get = it.getValue();
            float over = get[0] / 6;
            float economy = get[1] / over;

            if (min > economy) {
                min = economy;
                topEconomyBowler = it.getKey();
                top5EconomyBowler.put(topEconomyBowler, min);
            }
        }
        System.out.println("Top 7 economy bowler in 2015: ");
        for (String keys : top5EconomyBowler.keySet()) {
            System.out.println(keys + ":" + top5EconomyBowler.get(keys));
        }





    }





    }
