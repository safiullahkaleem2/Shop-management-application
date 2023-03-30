package ui;

import javax.swing.*;
import java.io.FileNotFoundException;
// this class runs Shop management application

public class Main {
    public static void main(String[] args) {
        try {
            new ShopManagement();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Unable to find file");
        }
    }
}
