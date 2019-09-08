package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import parser.OSMParser;

public class ui {
    
    public static void main(String[] args)  {
        
        Scanner scanner = new Scanner(System.in);
        OSMParser parser = new OSMParser();
        ArrayList<Integer> nodes;
        
        System.out.print("Enter name/path of OSM file to open: ");
        String osmFileName = scanner.nextLine();
        File osmFile =  new File(osmFileName);
        
        nodes = parser.parse(osmFile);
        //debug
        if (nodes != null)  {
            System.out.println("arraylist not null");
            for (Integer i : nodes) {
                System.out.println("node: " + i);
            }
        } else  {
            System.out.println("arraylist null");
        }
        
    }
    
}
