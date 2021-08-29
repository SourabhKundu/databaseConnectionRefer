package com.company;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;




public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            String deliveries = "src/com/company/deliveries.csv";
            String matches = "src/com/company/matches.csv";
            File file1 = new File(matches);
            FileReader fr1 = new FileReader(file1);
            File file2 = new File(deliveries);
            FileReader fr2 = new FileReader(file2);


            BufferedReader br = new BufferedReader(fr1);
            BufferedReader br1 = new BufferedReader(fr2);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }


}
