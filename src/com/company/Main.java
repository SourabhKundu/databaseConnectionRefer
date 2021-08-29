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
            //filepath
            String deliveries = "src/com/company/deliveries.csv";
            String matches = "src/com/company/matches.csv";
            File file1 = new File(matches);
            FileReader fr1 = new FileReader(file1);
            File file2 = new File(deliveries);
            FileReader fr2 = new FileReader(file2);

//Reader
            BufferedReader br = new BufferedReader(fr1);

            BufferedReader br1 = new BufferedReader(fr2);


            //Hashmap
            HashMap<String,Integer> Matches_Played_perYear = new HashMap<String,Integer>();
            HashMap<String,Integer> Matches_won_Overall = new HashMap<String,Integer>();

            while((line=br.readLine())!=null){
                String[] match = line.split(splitBy);

                int count = Matches_Played_perYear.containsKey(match[1]) ? Matches_Played_perYear.get(match[1]) : 0;
                Matches_Played_perYear.put(match[1], count + 1);
                int count1 = Matches_won_Overall.containsKey(match[10]) ? Matches_won_Overall.get(match[10]) : 0;
                Matches_won_Overall.put(match[10], count1 + 1);
                            }
            Set<String> keys = Matches_Played_perYear.keySet();
            Set<String> keys2 = Matches_won_Overall.keySet();

            for(String i : keys) {
                if (i.startsWith("2")) {
                    System.out.println("Year "+i + " Total Matches Played:" + Matches_Played_perYear.get(i));
                }
                else{
                    continue;
                }
            }
            for(String j : keys2) {
                if (!j.startsWith("winner")) {
                    System.out.println("Team " + j + " Won " + Matches_won_Overall.get(j) + " matches.");
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
