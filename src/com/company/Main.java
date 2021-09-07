package com.company;

import java.sql.*;

public class Main {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void main(String[] args) {
        findNoOfMatchesWonByTeams();
        findNoOfMatchesWonPerYearPerTeam();
        findExtraRunsPerTeamIn2016();
        findTopEconomicalBowlerIn2015();
        findAllPlayerOfTheMatchIn2017();
    }

    private static Connection get_Connection() {
        Connection connection = null;
        String postgreURL = "jdbc:postgresql://localhost:5432/sourabhkundu";
        String username = "postgres";
        String password = "Kundusahab@45";
        try {
            connection = DriverManager.getConnection(postgreURL, username, password);
        } catch (SQLException e) {
            System.out.println("Not connected");
            e.printStackTrace();
        }
        return connection;
    }

    private static void findNoOfMatchesWonByTeams() {
        String query = "SELECT season,count(*) FROM public.\"Matches\"\n" +
                "GROUP BY season;";
        connection = get_Connection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            System.out.println("Number of matches played per year of all the years in IPL");
            while (resultSet.next()) {
                System.out.println("" + resultSet.getInt(1) + "\t" + resultSet.getInt(2));
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------------------------");
    }

    private static void findNoOfMatchesWonPerYearPerTeam() {
        String query = "SELECT winner,count(*) FROM public.\"Matches\"\n" +
                "WHERE winner IS NOT null\n" +
                "GROUP BY winner;";
        connection = get_Connection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            System.out.println("No. of matches won  by each Team of all the IPL years :");
            while (resultSet.next()) {
                System.out.println("" + resultSet.getString(1) + "\t" + resultSet.getInt(2));
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------------------------");
    }

    private static void findExtraRunsPerTeamIn2016() {

        String query = "SELECT bowling_team,sum(extra_runs) FROM public.\"Deliveries\",public.\"Matches\"\n" +
                "WHERE public.\"Matches\".id= public.\"Deliveries\".match_id AND season=2016\n" +
                "GROUP BY bowling_team;";
        connection = get_Connection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            System.out.println("Extra runs given by each team in year 2016");
            while (resultSet.next()) {
                System.out.println("" + resultSet.getString(1) + "\t" + resultSet.getInt(2));

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------------------------");
    }

    private static void findTopEconomicalBowlerIn2015() {
        String query = "SELECT bowler,sum(total_runs)/count(ball)*6 AS economy FROM public.\"Matches\", public.\"Deliveries\"\n" +
                "WHERE public.\"Matches\".id = public.\"Deliveries\".match_id AND season=2015\n" +
                "GROUP BY bowler\n" +
                "ORDER BY economy;";
        connection = get_Connection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            System.out.println("Top economical bowler in IPL 2015");
            while (resultSet.next()) {
                System.out.println("" + resultSet.getString(1) + "\t" + resultSet.getFloat(2));
                break;
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------------------------");
    }

    private static void findAllPlayerOfTheMatchIn2017() {
        String query = "SELECT player_of_match FROM public.\"Matches\"" +
                "WHERE season = 2017";
        connection = get_Connection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            System.out.println("All Player of Match in year 2017 :");
            while (resultSet.next()) {
                System.out.println("" + resultSet.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

