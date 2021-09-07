package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.lang.*;

public class Main {
    public static final int MATCH_ID = 1;
    public static final int SEASON = 2;
    public static final int MATCH_WINNER = 11;
    public static final int TOSS_WINNER = 7;

    public static final int DELIVERY_MATCH_ID = 0;
    public static final int BOWLING_TEAM = 3;
    public static final int BALL = 5;
    public static final int BOWLER = 8;
    public static final int WIDE_RUNS = 10;
    public static final int NOBALL_RUNS = 13;
    public static final int BATSMAN_RUNS = 15;
    public static final int EXTRA_RUNS = 16;
    public static final int TOTAL_RUNS = 17;


    public static void main(String[] args) throws IOException, SQLException {
        List<Match> matchData = getMatchData();
        List<Delivery> deliveryData = getDeliveryData();

        findNoOfMatchesWonByTeams(matchData);
        findNoOfMatchesWonPerYearPerTeam(matchData);
        findExtraRunsPerTeamIn2016(deliveryData, matchData);
        findTopEconomicalBowlerIn2015(deliveryData, matchData);
        findTossWonByEachTeamIn2017(matchData);
    }
    private static Connection getConnection(){
        Connection connection = null;
        String POSTGRE_URL = "jdbc:postgresql://localhost:5432/sourabhkundu";
        String USERNAME = "postgres";
        String PASSWORD = "Kundusahab@45";
        try {
            connection = DriverManager.getConnection(POSTGRE_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Not connected");
            e.printStackTrace();
        }
        return connection;
    }

    private static List<Match> getMatchData() throws  SQLException {
        List<Match> matchData = new ArrayList<>();

        /*String path = "src/com/company/matches.csv";
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line = "";
        br.readLine();*/
        Connection connection = getConnection();
        String query = "SELECT * FROM public.\"Matches\"";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt(MATCH_ID);
                int season = resultSet.getInt(SEASON);
                String winner = resultSet.getString(MATCH_WINNER);
                String tossWinner = resultSet.getString(TOSS_WINNER);
                Match match = new Match();

                match.setMatchId(id);
                match.setSeason(season);
                if (winner.equals(null)) {
                    match.setWinner("Draw");
                } else {
                    match.setWinner(winner);
                }
                match.setTossWinner(tossWinner);

                matchData.add(match);
            }
            connection.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return matchData;
    }

    private static List<Delivery> getDeliveryData() throws IOException {
        List<Delivery> deliveryData = new ArrayList<>();

        String path = "src/com/company/deliveries.csv";
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line = "";
        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");

            Delivery delivery = new Delivery();
            delivery.setMatchId(Integer.parseInt(data[DELIVERY_MATCH_ID]));
            delivery.setBowling_team(data[BOWLING_TEAM]);
            delivery.setBowler(data[BOWLER]);
            delivery.setInning(Integer.parseInt(data[BALL]));
            delivery.setExtraRuns(Integer.parseInt(data[EXTRA_RUNS]));
            delivery.setExtraRuns(Integer.parseInt(data[WIDE_RUNS]));
            delivery.setExtraRuns(Integer.parseInt(data[NOBALL_RUNS]));
            delivery.setBatsmanRuns(Integer.parseInt(data[BATSMAN_RUNS]));
            delivery.setTotalRuns(Integer.parseInt(data[TOTAL_RUNS]));

            deliveryData.add(delivery);
        }
        return deliveryData;
    }

    private static void findNoOfMatchesWonByTeams(List<Match> matchData) {
        Map<Integer, Integer> matchesWonPerSeason = new TreeMap<>();

        for (Match match : matchData) {
            if (matchesWonPerSeason.get(match.getSeason()) != null) {
                matchesWonPerSeason.put(match.getSeason(), matchesWonPerSeason.get(match.getSeason()) + 1);
            } else {
                matchesWonPerSeason.put(match.getSeason(), 1);
            }
        }
        System.out.println("Number of matches played per year of all the years in IPL");
        for (Map.Entry<Integer, Integer> item : matchesWonPerSeason.entrySet()) {
            System.out.println(item.getKey() + " - " + item.getValue());
        }
        System.out.println("-----------------------------------------------------------------");
    }

    private static void findNoOfMatchesWonPerYearPerTeam(List<Match> matchData) {
        Map<String, Integer> noOfMatchesWonPerYearPerTeam = new HashMap<>();

        for (Match match : matchData) {
            if (noOfMatchesWonPerYearPerTeam.get(match.getWinner()) != null) {
                noOfMatchesWonPerYearPerTeam.put(match.getWinner(), noOfMatchesWonPerYearPerTeam.get(match.getWinner()) + 1);
            } else {
                noOfMatchesWonPerYearPerTeam.put(match.getWinner(), 1);
            }
        }

        System.out.println("No. of matches won  by each Team of all the IPL years :");
        for (Map.Entry<String, Integer> item : noOfMatchesWonPerYearPerTeam.entrySet()) {
            if (!item.getKey().equals("Draw")) {
                System.out.println("Team " + item.getKey() + " Won " + item.getValue() + " matches");
            }
        }
        System.out.println("-------------------------------------------------------------------------------");
    }

    private static void findExtraRunsPerTeamIn2016(List<Delivery> deliveryData, List<Match> matchData) {
        Map<String, Integer> extraRunsPerTeam = new HashMap<>();
        List<Integer> matchId = new ArrayList<>();

        for (Match match : matchData) {
            if (match.getSeason() == 2016) {
                matchId.add(match.getMatchId());
            }
        }
        for (Delivery delivery : deliveryData) {
            if (matchId.contains(delivery.getMatchId())) {
                if (extraRunsPerTeam.get(delivery.getBowling_team()) != null) {
                    extraRunsPerTeam.put(delivery.getBowling_team(), extraRunsPerTeam.get(delivery.getBowling_team()) + delivery.getExtraRuns());
                } else {
                    extraRunsPerTeam.put(delivery.getBowling_team(), delivery.getExtraRuns());
                }
            }
        }
        System.out.println("Extra runs given by each team in year 2016");
        for (Map.Entry<String, Integer> item : extraRunsPerTeam.entrySet()) {
            if (!item.getKey().equals("")) {
                System.out.println(item.getKey() + " team gave " + item.getValue() + " runs.");
            }
        }
        System.out.println("--------------------------------------------------------------------------");
    }

    private static void findTopEconomicalBowlerIn2015(List<Delivery> deliveryData, List<Match> matchData) {
        Map<String, Integer> bowlersRuns = new HashMap<>();
        Map<String, Integer> bowlersOvers = new HashMap<>();
        Map<Double, String> bowlersEconomy = new TreeMap<>();
        List<Integer> matchIds = new ArrayList<>();
        for (Match item : matchData) {
            if (item.getSeason() == 2015) {
                matchIds.add(item.getMatchId());
            }
        }
        for (Delivery item : deliveryData) {
            if (matchIds.contains(item.getMatchId())) {
                if (bowlersRuns.get(item.getBowler()) != null) {
                    bowlersRuns.put(item.getBowler(), bowlersRuns.get(item.getBowler()) + item.getWideRuns() + item.getNoBallRuns() + item.getBatsmanRuns());
                } else {
                    bowlersRuns.put(item.getBowler(), item.getWideRuns() + item.getNoBallRuns() + item.getBatsmanRuns());
                }
                if (bowlersOvers.get(item.getBowler()) == null) {
                    bowlersOvers.put(item.getBowler(), 0);
                } else if (bowlersOvers.get(item.getBowler()) != null) {
                    bowlersOvers.put(item.getBowler(), bowlersOvers.get(item.getBowler()) + 1);
                }
            }
        }
        for (Map.Entry<String, Integer> item : bowlersRuns.entrySet()) {
            bowlersEconomy.put(Double.parseDouble(String.valueOf(bowlersRuns.get(item.getKey()))) / (bowlersOvers.get(item.getKey()) / 6), item.getKey());
        }

        System.out.println("Top economical bowler in IPL 2015");
        for (Map.Entry<Double, String> item : bowlersEconomy.entrySet()) {
            System.out.println(item.getValue() + " with an economy of " + item.getKey());
            System.out.println("--------------------------------------------------------------------------------");
            break;
        }
    }

    private static void findTossWonByEachTeamIn2017(List<Match> matchData) {
        Map<String, Integer> tossWonByTeamIn2017 = new HashMap<>();

        for (Match item : matchData) {
            if (item.getSeason() == 2017) {
                if (tossWonByTeamIn2017.get(item.getTossWinner()) != null) {
                    tossWonByTeamIn2017.put(item.getTossWinner(), tossWonByTeamIn2017.get(item.getTossWinner()) + 1);
                } else {
                    tossWonByTeamIn2017.put(item.getTossWinner(), 1);
                }
            }
        }
        System.out.println("Toss Won by each team in 2017 :");
        for (Map.Entry<String, Integer> item : tossWonByTeamIn2017.entrySet()) {
            System.out.println(item.getKey() + " team won " + item.getValue() + " tosses.");
        }
    }
}



