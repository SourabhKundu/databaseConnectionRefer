package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Matches {
    public static void main(String[] args) throws FileNotFoundException {
        String matches = "src/com/company/matches.csv";
        File matchFile = new File(matches);
        FileReader matchFileReader = new FileReader(matchFile);
        BufferedReader matchReader = new BufferedReader(matchFileReader);
    }
}
