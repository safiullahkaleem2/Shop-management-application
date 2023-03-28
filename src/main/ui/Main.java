package ui;

import java.io.FileNotFoundException;
// this class runs Shop management application

public class Main {
    public static void main(String[] args) {
        try {
            new ShopManagement();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
