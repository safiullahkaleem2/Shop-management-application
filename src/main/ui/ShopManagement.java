package ui;

import model.*;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Double.parseDouble;
// Shop Management application

public class ShopManagement {
    private final JButton button1;
    private final JButton button2;
    private final JButton button3;
    private final JButton button4;
    private final JButton button5;
    private Creditors creditors;


    private Bank bank;
    private Inventory inventory;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame frame;
    private JButton button;


    // EFFECTS: runs the teller application
    public ShopManagement() throws FileNotFoundException {
        frame = new JFrame("Shop Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 800);
        inventory = Inventory.getInventory();
        creditors = new Creditors();
        // create the buttons
        button = new JButton("Check Bank details");
        button1 = new JButton("Manage Inventory");
        button2 = new JButton("Manage Creditors");
        button3 = new JButton("Perform Transaction");
        button4 = new JButton("Save file");
        button5 = new JButton("Quit");


        // create a panel to hold the buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbcHelper(gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // add the buttons to the panel
        addButtonsToPanel(buttonPanel, gbc);

        // add the button panel to the frame
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);


        mainActionListener();

        // show the frame
        frame.setVisible(true);
        initializer();

    }

    private void mainActionListener() {
        // add an action listener to the buttons
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BankUI bankUI = new BankUI();
                bankUI.checkBank();
            }
        });
        mainActionListenerHelper();
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(null, "Please enter your name:",
                        "Input", JOptionPane.PLAIN_MESSAGE);
                if (input != null) {
                    save(input);
                }


            }
        });
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void mainActionListenerHelper() {
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                InventoryUI inventoryUI = new InventoryUI(frame);
                inventoryUI.manageInventory();
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                CreditorsUI creditorsUI = new CreditorsUI(button, button1, button2,button3,button4,
                        button5, creditors,frame);
                creditorsUI.manageCreditors();;
            }
        });
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                TransactionUI transactionUI = new TransactionUI(button,button1,button2,button3,button4,button5,frame);
                transactionUI.manageTransaction();
            }
        });
    }

    private void addButtonsToPanel(JPanel buttonPanel, GridBagConstraints gbc) {
        buttonPanel.add(button, gbc);
        gbc.gridy++;
        buttonPanel.add(button1, gbc);
        gbc.gridy++;
        buttonPanel.add(button2, gbc);
        gbc.gridy++;
        buttonPanel.add(button3, gbc);
        gbc.gridy++;
        buttonPanel.add(button4, gbc);
        gbc.gridy++;
        buttonPanel.add(button5, gbc);
    }

    private void initializer() {
        int result = JOptionPane.showConfirmDialog(null, "Do you want to load a previously Saved"
                + " file?", "   Load", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            String input = JOptionPane.showInputDialog(null,
                    "Please enter your name:", "Input", JOptionPane.PLAIN_MESSAGE);
            if (input != null) {
                load(input);
            } else {
                initializeNewShop();
            }


        } else {
            initializeNewShop();
        }

    }


    private void initializeNewShop() {
        double balance = 0;
        boolean validInput = false;
        while (!validInput) {
            String input = JOptionPane.showInputDialog(null,
                    "Enter a valid balance:", "New Bank Account", JOptionPane.PLAIN_MESSAGE);
            try {
                balance = parseDouble(input);
                if (balance >= 0) {
                    validInput = true;
                } else {
                    conditionalCheckerRefactor(null,
                            "Invalid balance. Please enter a positive value.",
                            "Invalid Balance");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Invalid input. Please enter a valid number.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        starter(balance);
    }

    private void starter(double balance) {
        Bank.getBank(balance);
        bank = Bank.getBank();
        inventory = Inventory.getInventory();
        creditors = new Creditors();
    }

    // EFFECTS: manages transactions


    private void gbcHelper(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
    }



    private void conditionalCheckerRefactor(JFrame cashTransactionFrame, String itemNotPresentInInventory,
                                            String invalidItem) {
        JOptionPane.showMessageDialog(cashTransactionFrame,
                itemNotPresentInInventory,
                invalidItem,
                JOptionPane.ERROR_MESSAGE);
    }



    // EFFECTS: saves the workroom to file
    private void save(String name) {
        try {
            // create the folder if it doesn't exist
            File folder = new File("./data/" + name);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            jsonWriter = new JsonWriter("./data/" + name + "/inventory.json",
                    "./data/" + name + "/creditors.json","./data/" + name + "/bank.json");
            jsonWriter.open();
            jsonWriter.write(inventory,creditors,bank);

            jsonWriter.close();
            JOptionPane.showMessageDialog(null,"successfully saved data of " + name);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"unable to save data of " + name);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void load(String name) {
        try {
            jsonReader = new JsonReader("./data/" + name + "/inventory.json",
                    "./data/" + name + "/creditors.json","./data/" + name + "/bank.json");
            inventory = jsonReader.read();
            creditors = jsonReader.readC();
            bank = jsonReader.readB();
            JOptionPane.showMessageDialog(null,"successfully loaded data of " + name);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"unable to load data of " + name);
            initializeNewShop();
        }
    }


}
