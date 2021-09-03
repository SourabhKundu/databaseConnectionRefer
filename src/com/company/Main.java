package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.*;

public class Main {
    public static final int MATCH_ID = 0;
    public static final int SEASON = 1;
    public static final int MATCH_WINNER = 10;
    public static final int TOSS_WINNER = 6;

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
        findNoOfMatchesPlayedPerYear(matchesData);
        findExtraRunsPerTeamIn2016(deliveryData,matchesData);
        findEconomicalBowlerIn2015(deliveryData,matchesData);
        findTossWonByEachTeamIn2017(matchesData);

    }
    private static List<Matches> getMatchesData() throws IOException {
        String path = "src/com/company/matches.csv";
        List<Matches> matchesData = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;
        int skipLine = 0;

        while((line = br.readLine()) != null)
        {
            if(skipLine == 0)
            {
                skipLine = 1;
                continue;
            }

            String[] singleMatch = line.split(",");
            Matches match = new Matches();

            match.setMatch_Id(Integer.parseInt(singleMatch[MATCH_ID]));
            match.setSeason(Integer.parseInt(singleMatch[SEASON]));
            if(singleMatch[MATCH_WINNER].equals("") || singleMatch[MATCH_WINNER]==null) match.setWinner("Draw");
            else match.setWinner(singleMatch[MATCH_WINNER]);
            match.setToss_winner(singleMatch[TOSS_WINNER]);
            matchesData.add(match);
        }
        return matchesData;
    }

    private static List<Deliveries> getDeliveriesData() throws IOException {
        List<Deliveries> deliveriesData = new ArrayList<>();

        String path = "src/com/company/deliveries.csv";
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line = "";
        int skipLine = 0;
        while((line = br.readLine()) != null)
        {
            String[] deliveryData = line.split(",");
            if(skipLine == 0)
            {
                skipLine = 1;
                continue;
            }

            Deliveries delivery=new Deliveries();
            delivery.setMatch_id(Integer.parseInt(deliveryData[DELIVERY_MATCH_ID]));
            delivery.setBowling_team(deliveryData[BOWLING_TEAM]);
            delivery.setBowler(deliveryData[BOWLER]);
            delivery.setInning(Integer.parseInt(deliveryData[BALL]));
            delivery.setExtra_runs(Integer.parseInt(deliveryData[EXTRA_RUNS]));
            delivery.setExtra_runs(Integer.parseInt(deliveryData[WIDE_RUNS]));
            delivery.setExtra_runs(Integer.parseInt(deliveryData[NOBALL_RUNS]));
            delivery.setBatsman_runs(Integer.parseInt(deliveryData[BATSMAN_RUNS]));
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
            System.out.println(item.getKey()+" - "+ item.getValue());
        }
        System.out.println("-----------------------------------------------------------------");
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
        System.out.println("No. of matches won  by each Team of all the IPL years :");
        for (Map.Entry<String, Integer> item : noOfMatchesPlayedPerYear.entrySet()) {
            if (!item.getKey().equals("Draw")) {
                System.out.println("Team "+item.getKey()+" Won "+item.getValue()+" matches" );
            }
        }
        System.out.println("-------------------------------------------------------------------------------");
    }

    private static void findExtraRunsPerTeamIn2016(List<Deliveries> deliveryData, List<Matches> matchesData) {
        Map<String, Integer> extraRunsPerTeam = new HashMap<>();
        List<Integer> matchId = new ArrayList<Integer>();

        for (Matches match : matchesData) {
            if (match.getSeason() == 2016) {
                matchId.add(match.getMatch_Id());
            }
        }
        for (Deliveries delivery : deliveryData) {
            if (matchId.contains(delivery.getMatch_id())) {
                try {
                    extraRunsPerTeam.put(delivery.getBowling_team(), extraRunsPerTeam.get(delivery.getBowling_team()) + delivery.getExtra_runs());
                } catch (Exception e) {
                    extraRunsPerTeam.put(delivery.getBowling_team(), delivery.getExtra_runs());
                }
            }
        }
        System.out.println("Extra runs given by each team in year 2016");
        for (Map.Entry<String, Integer> item : extraRunsPerTeam.entrySet()) {
            if (!item.getKey().equals("")) {
                System.out.println(item.getKey()+" team gave "+item.getValue()+" runs.");
            }
        }
        System.out.println("--------------------------------------------------------------------------");
    }

    private static void findEconomicalBowlerIn2015(List<Deliveries> deliveryData, List<Matches> matchesData) {
        Map<String, Integer> bowlersRuns = new HashMap<String, Integer>();
        Map<String, Integer> bowlersOvers = new HashMap<String, Integer>();
        Map<Double, String> bowlersEconomy = new TreeMap<Double, String>();
        List<Integer> matchIds = new ArrayList<Integer>();
        for (Matches item : matchesData) {
            if (item.getSeason() == 2015) {
                matchIds.add(item.getMatch_Id());
            }
        }
        for (Deliveries item : deliveryData) {
            if (matchIds.contains(item.getMatch_id())) {
                if (bowlersRuns.get(item.getBowler()) != null) {
                    bowlersRuns.put(item.getBowler(), bowlersRuns.get(item.getBowler()) + item.getWide_runs() + item.getNoBall_runs() + item.getBatsman_runs());
                } else {
                    bowlersRuns.put(item.getBowler(), item.getWide_runs() + item.getNoBall_runs() + item.getBatsman_runs());
                }
                if (bowlersOvers.get(item.getBowler()) == null) {
                    bowlersOvers.put(item.getBowler(), 0);
                } else if (bowlersOvers.get(item.getBowler()) != null && item.getBall() == 6) {

                    bowlersOvers.put(item.getBowler(), bowlersOvers.get(item.getBowler()) + 1);
                }
            }
        }
        for (Map.Entry<String, Integer> item : bowlersRuns.entrySet()) {
            bowlersEconomy.put(Double.parseDouble(String.valueOf(bowlersRuns.get(item.getKey()))) / bowlersOvers.get(item.getKey()), item.getKey());
        }

        System.out.println("Top economical bowler in IPL 2015");

        for (Map.Entry<Double, String> item : bowlersEconomy.entrySet()) {
            System.out.println(item.getValue());
            System.out.println("--------------------------------------------------------------------------------");
            break;
        }
    }
    private static void findTossWonByEachTeamIn2017 (List<Matches> matchesData){
        Map<String, Integer> tossWonByTeam = new HashMap<>();


        for (Matches item : matchesData) {
            if (item.getSeason() == 2017) {
                try {
                    tossWonByTeam.put((item.getToss_winner()), tossWonByTeam.get(item.getToss_winner()) + 1);
                } catch (Exception e) {
                    tossWonByTeam.put(item.getToss_winner(), 1);
                }
            }
        }
        System.out.println("Toss Won by each team in 2017 :");

        for (Map.Entry<String, Integer> item : tossWonByTeam.entrySet()){
            System.out.println(item.getKey()+" team won "+item.getValue()+" tosses.");
        }
    }
}



