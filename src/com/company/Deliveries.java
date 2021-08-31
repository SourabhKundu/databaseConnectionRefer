package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Deliveries {
    public static void main(String[] args) throws FileNotFoundException {
        String deliveries = "src/com/company/deliveries.csv";
        File deliveryFile = new File(deliveries);
        FileReader deliverFileReader = new FileReader(deliveryFile);

        BufferedReader delivery_Reader = new BufferedReader(deliverFileReader);
    }
}
