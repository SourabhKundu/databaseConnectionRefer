package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static final int MATCH_ID = 0;
    public static final int SEASON = 1;
    public static final int MATCH_WINNER = 10;
    public static final int PLAYER_OF_MATCH = 13;

    public static final int DELIVERY_MATCH_ID = 0;
    public static final int BOWLING_TEAM = 3;
    public static final int BALL = 5;
    public static final int BOWLER = 8;
    public static final int WIDE_RUNS = 10;
    public static final int NOBALL_RUNS = 13;
    public static final int BATSMAN_RUNS = 15;
    public static final int EXTRA_RUNS = 16;
    public static final int TOTAL_RUNS = 17;


    public static void main(String[] args) throws IOException {
        List<Matches> matchesData = getMatchesData();
        List<Deliveries> deliveryData = getDeliveriesData();
        findNoOfMatchesWonByTeams(matchesData);
        noOfMatchesPlayedPerYear(matchesData);
        extraRunsPerTeamIn2016(deliveryData,matchID_Season);
        topEconomicalBowlerIn2015(deliveryData,matchID_Season);

    }
    private static List<Matches> getMatchesData() throws IOException {
        String path="src/com/company/matches.csv";
        List<Matches> matchesData=new ArrayList<>();

        BufferedReader br=new BufferedReader(new FileReader(path));

        String line;
        int skipLine=0;

        while((line=br.readLine())!=null)
        {
            if(skipLine==0)
            {
                skipLine=1;
                continue;
            }

            String[] singleMatch = line.split(",");
            Matches match=new Matches();

            match.setMatch_Id(Integer.parseInt(singleMatch[MATCH_ID]));
            match.setSeason(Integer.parseInt(singleMatch[SEASON]));
            if(singleMatch[MATCH_WINNER].equals("") || singleMatch[MATCH_WINNER]==null) match.setWinner("Draw");
            else match.setWinner(singleMatch[MATCH_WINNER]);
            match.setPlayerOfTheMatch(singleMatch[PLAYER_OF_MATCH]);
            matchesData.add(match);
        }
        return matchesData;
    }
    private static List<Deliveries> getDeliveriesData() throws IOException {
        List<Deliveries> deliveriesData=new ArrayList<>();

        String path="src/com/company/deliveries.csv";
        BufferedReader br=new BufferedReader(new FileReader(path));

        String line="";
        int skipLine=0;
        while((line= br.readLine())!=null)
        {
            String[] deliveryData = line.split(",");
            if(skipLine==0)
            {
                skipLine=1;
                continue;
            }

            Deliveries delivery=new Deliveries();
            delivery.setMatch_id(Integer.parseInt(deliveryData[DELIVERY_MATCH_ID]));
            delivery.setBatting_team(deliveryData[BOWLING_TEAM]);
            delivery.setBowler(deliveryData[BOWLER]);
            delivery.setInning(Integer.parseInt(deliveryData[BALL]));
            delivery.setExtra_runs(Integer.parseInt(deliveryData[EXTRA_RUNS]));
            delivery.setExtra_runs(Integer.parseInt(deliveryData[WIDE_RUNS]));
            delivery.setExtra_runs(Integer.parseInt(deliveryData[NOBALL_RUNS]));
            delivery.setTotal_runs(Integer.parseInt(deliveryData[TOTAL_RUNS]));

            deliveriesData.add(delivery);
        }
        return deliveriesData;
    }
    private static void findNoOfMatchesWonByTeams(List<Matches> matchesData) {
        Map<Integer, Integer> matchesPerSeason = new TreeMap<Integer, Integer>();

        for(Matches match :matchesData)
        {
            try {
                matchesPerSeason.put((match.getSeason()), matchesPerSeason.get(match.getSeason()) + 1);
            } catch (Exception e) {
                matchesPerSeason.put(match.getSeason(), 1);
            }
        }
        System.out.println("Number of matches played per year of all the years in IPL");
        for (Map.Entry<Integer, Integer> item : matchesPerSeason.entrySet()) {
            System.out.print(item.getKey());
            System.out.print(" - ");
            System.out.println(item.getValue());
        }
    }

    private static void findNoOfMatchesPlayedPerYear(List<Matches> matchesData) {
        Map<String, Integer> noOfMatchesPlayedPerYear = new HashMap<>();

        for (Matches match : matchesData) {
            try {
                noOfMatchesPlayedPerYear.put((match.getWinner()), noOfMatchesPlayedPerYear.get(match.getWinner()) + 1);
            } catch (Exception e) {
                noOfMatchesPlayedPerYear.put(match.getWinner(), 1);
            }
        }
        System.out.println("No. of matches played per year :");
        for (Map.Entry<String, Integer> item : noOfMatchesPlayedPerYear.entrySet()) {
            if (!item.getKey().equals("")) {
                System.out.println("Team "+item.getKey()+" Won "+item.getValue()+" matches" );
            }
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
        System.out.println("Top 7 economy bowlers in 2015: ");
        for (String keys : top5EconomyBowler.keySet()) {
            System.out.println(keys + ":" + top5EconomyBowler.get(keys));
        }





    }





    }
