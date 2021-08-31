package com.company;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.lang.String;
import java.util.Set;

class ReadFile{

}


public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            String line = "";
            String splitBy = ",";




            //Hashmap
            HashMap<String,Integer> Matches_Played_perYear = new HashMap<String,Integer>();
            HashMap<String,Integer> Matches_won_Overall = new HashMap<String,Integer>();
            HashMap<String,Integer> Extra_runs = new HashMap<String,Integer>();
            HashMap<String,Integer> Economical_Bowler = new HashMap<String,Integer>();
            HashMap<String , Integer> Eco2 = new HashMap<String,Integer>();
            HashMap<String, Integer> Total_Runs = new HashMap<String,Integer>();


            while((line=br.readLine())!=null){
                String[] match = line.split(splitBy);

                int count = Matches_Played_perYear.containsKey(match[1]) ? Matches_Played_perYear.get(match[1]) : 0;
                Matches_Played_perYear.put(match[1], count + 1);
                int count1 = Matches_won_Overall.containsKey(match[10]) ? Matches_won_Overall.get(match[10]) : 0;
                Matches_won_Overall.put(match[10], count1 + 1);
                            }
            while((line= br1.readLine())!=null){
                String[] delivery = line.split(splitBy);
                //System.out.println(delivery[2]+"  :  "+delivery[16]);

                if(!delivery[16].startsWith("extra_runs") ) {
                    int matches_played = Integer.parseInt(delivery[0]);
                    int Runs3 = Integer.parseInt(delivery[17]);
                    int count5 = Total_Runs.containsKey(delivery[6]) ? Total_Runs.get(delivery[6]) :0;
                    Total_Runs.put(delivery[6], count5+Runs3);
                    if(577<=matches_played && matches_played<=636){
                    int Runs = Integer.parseInt(delivery[16]);

                    int count3 = Extra_runs.containsKey(delivery[2]) ? Extra_runs.get(delivery[2]) : 0;

                    Extra_runs.put(delivery[2], (count3 + Runs));
                }}
                else{
                    continue;
                }
                if (!delivery[17].startsWith("total")){
                    int matches_played1 = Integer.parseInt(delivery[0]);
                    if(519<=matches_played1 && matches_played1<=576) {

                        int Runs2 = Integer.parseInt(delivery[17]);
                        int count4 = Economical_Bowler.containsKey(delivery[8]) ? Economical_Bowler.get(delivery[8]) : 0;
                        Economical_Bowler.put(delivery[8], (count4+1) );
                        int count5 = Eco2.containsKey(delivery[8]) ? Eco2.get(delivery[8]): 0;

                        Eco2.put(delivery[8],count4+Runs2);


                    }
                }



            }
            Set<String> keys = Matches_Played_perYear.keySet();
            Set<String> keys2 = Matches_won_Overall.keySet();
            Set<String> keys3 = Extra_runs.keySet();
            Set<String> keys4 = Economical_Bowler.keySet();
            Set<String> Keys5 = Total_Runs.keySet();
            for (String f : Keys5){
                System.out.println("Batsman "+f+ "Made "+Total_Runs.get(f)+" runs.");
            }

            for(String l : keys4){
                int Economy = Economical_Bowler.get(l)/6;
                Economy = Eco2.get(l)/Economy;
                System.out.println("Bowler "+ l + " Runs "+Economy);
            }
            for(String k : keys3){
                //System.out.println("Team "+k+"  :  "+ Extra_runs.get(k));
            }

            for(String i : keys) {
                if (i.startsWith("2")) {
                    //System.out.println("Year "+i + " Total Matches Played:" + Matches_Played_perYear.get(i));
                }
                else{
                    continue;
                }
            }
            for(String j : keys2) {
                if (!j.startsWith("winner")) {
                    //System.out.println("Team " + j + " Won " + Matches_won_Overall.get(j) + " matches.");
                } else {
                    continue;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }



    }


}
