package ui;

import model.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
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
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
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
                checkBank();
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
                manageInventory();
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                manageCreditors();
            }
        });
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                manageTransaction();
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

    // MODIFIES: this
    // EFFECTS: directs to checking bank details
    private void checkBank() {
        if (Bank.getBank() == null) {
            Bank.getBank(0);
            bank = Bank.getBank();
        }
        JFrame barChartFrame = new JFrame("Bank Details");

        DefaultCategoryDataset bankData = new DefaultCategoryDataset();
        bankData.setValue(bank.getBalance(),"Amount","Balance");
        bankData.setValue(bank.getReceipts(),"Amount","Receipts");
        bankData.setValue(-1 * bank.getPayments(),"Amount","Payments");
        JFreeChart bankChart = ChartFactory.createBarChart("Bank Details", "Name", "Amount",
                bankData);
        // create the chart panel and add it to the frame
        ChartPanel chartPanel = new ChartPanel(bankChart);
        barChartFrame.add(chartPanel);

        // set the frame properties

        barChartFrame.setSize(500, 500);
        barChartFrame.setLocationRelativeTo(null);

        barChartFrame.setVisible(true);

    }


    // MODIFIES: this
    // EFFECTS: directs to changing inventory details
    private void manageInventory() {
        JLabel inventoryLabel = new JLabel("Inventory Menu");
        JButton addItemButton = new JButton("Add Item to Inventory");
        JButton removeItemButton = new JButton("Remove Item from Inventory");
        JButton thresholdCheckButton = new JButton("Check Inventory Threshold");
        JButton viewInventoryButton = new JButton("View All Inventory Items");
        JButton quitButton = new JButton("Quit");

        // create a new frame for the inventory management screen
        JFrame inventoryFrame = new JFrame("Inventory Management");
        inventoryFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        inventoryFrame.setSize(700, 800);
        JPanel inventoryButtonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbcHelper(gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gridBuildingHelper(inventoryLabel, addItemButton, removeItemButton, thresholdCheckButton, viewInventoryButton,
                quitButton, inventoryButtonPanel, gbc);


        // add the button panel to the frame
        inventoryFrame.getContentPane().setLayout(new BorderLayout());
        inventoryFrame.getContentPane().add(inventoryButtonPanel, BorderLayout.CENTER);



        // add an action listener to the add item button
        actionListenerHelper(addItemButton, removeItemButton, thresholdCheckButton, viewInventoryButton);


        // add an action listener to the quit button
        quitButtonHelper(quitButton, inventoryFrame);

        // add the inventory panel to the frame
        inventoryFrame.getContentPane().add(inventoryButtonPanel);

        // show the inventory frame
        inventoryFrame.setVisible(true);
    }

    private void quitButtonHelper(JButton quitButton, JFrame inventoryFrame) {
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inventoryFrame.dispose();
                frame.setVisible(true);

            }
        });
    }

    private void actionListenerHelper(JButton addItemButton, JButton removeItemButton, JButton thresholdCheckButton,
                                      JButton viewInventoryButton) {
        addItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItemsToInventory();
            }
        });


        // add an action listener to the remove item button
        removeItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeItemFromInventory();
            }
        });


        // add an action listener to the threshold check button
        thresholdCheckButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                thresholdCheck();
            }
        });

        // add an action listener to the view inventory button
        viewInventoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                allInventoryItems();
            }
        });
    }

    private void gridBuildingHelper(JLabel inventoryLabel, JButton addItemButton, JButton removeItemButton,
                                    JButton thresholdCheckButton, JButton viewInventoryButton, JButton quitButton,
                                    JPanel inventoryButtonPanel, GridBagConstraints gbc) {
        inventoryButtonPanel.add(inventoryLabel, gbc);
        gbc.gridy++;
        inventoryButtonPanel.add(addItemButton, gbc);
        gbc.gridy++;
        inventoryButtonPanel.add(removeItemButton, gbc);
        gbc.gridy++;
        inventoryButtonPanel.add(thresholdCheckButton, gbc);
        gbc.gridy++;
        inventoryButtonPanel.add(viewInventoryButton, gbc);
        gbc.gridy++;
        inventoryButtonPanel.add(quitButton, gbc);
    }



    // MODIFIES: this
    // EFFECTS: adds item to inventory
    private void addItemsToInventory() {
        // create a new frame for the add item screen
        JFrame addItemFrame = new JFrame("Add Item to Inventory");

        addItemFrame.setSize(400, 300);

        // create input fields and labels for item information
        JLabel nameLabel = new JLabel("Item Name:");
        JTextField nameField = new JTextField(20);
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(20);
        JLabel unitLabel = new JLabel("Unit:");
        JTextField unitField = new JTextField(20);
        JLabel thresholdLabel = new JLabel("Threshold:");
        JTextField thresholdField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(20);
        JButton addButton = new JButton("Add Item");

        // add input fields and labels to the frame
        JPanel addItemPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        addItemsToInventoryHelper(addItemFrame, nameLabel, nameField, quantityLabel, quantityField, unitLabel,
                unitField, thresholdLabel, thresholdField, priceLabel, priceField, addButton, addItemPanel, gbc);


        // add the add item panel to the frame
        addItemFrame.getContentPane().setLayout(new BorderLayout());
        addItemFrame.getContentPane().add(addItemPanel, BorderLayout.CENTER);

        // show the add item frame
        addItemFrame.setVisible(true);
    }

    private void addItemsToInventoryHelper(JFrame addItemFrame, JLabel nameLabel, JTextField nameField,
                                           JLabel quantityLabel, JTextField quantityField, JLabel unitLabel,
                                           JTextField unitField, JLabel thresholdLabel, JTextField thresholdField,
                                           JLabel priceLabel, JTextField priceField, JButton addButton,
                                           JPanel addItemPanel, GridBagConstraints gbc) {
        gbcHelper(gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addItemPanel.add(nameLabel, gbc);
        gbc.gridx++;
        addItemPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        creditTransactionFrame(quantityLabel, quantityField, unitLabel, unitField, thresholdLabel, thresholdField,
                priceLabel, priceField, addItemPanel, gbc);
        addItemPanel.add(addButton, gbc);

        // add an action listener to the add button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helperForAddingItems(nameField, quantityField, unitField, thresholdField, priceField, addItemFrame);
            }
        });
    }


    private void helperForAddingItems(JTextField nameField, JTextField quantityField, JTextField unitField,
                                      JTextField thresholdField, JTextField priceField, JFrame addItemFrame) {
        String name = nameField.getText().toLowerCase();
        if (helperForHelper2(addItemFrame, name)) {
            return;
        }

        int quantity = Integer.parseInt(quantityField.getText());
        String unit = unitField.getText().toLowerCase();
        int threshold = Integer.parseInt(thresholdField.getText());
        double price = 0;

        try {
            price = parseDouble(priceField.getText());
            if (price < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(addItemFrame,
                    "Invalid price. Please enter a positive numeric value.",
                    "Invalid Price",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        helperForHelperOfAddItem(addItemFrame, name, quantity, unit, threshold, price);
    }

    private boolean helperForHelper2(JFrame addItemFrame, String name) {
        if (inventory.isItemPresent(name)) {
            JOptionPane.showMessageDialog(addItemFrame,
                    "Item with name " + name + " already exists in inventory.",
                    "Item Already Exists",
                    JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    private void helperForHelperOfAddItem(JFrame addItemFrame, String name, int quantity, String unit, int threshold,
                                          double price) {
        if (price > bank.getBalance()) {
            conditionalCheckerRefactor(addItemFrame,
                    "You don't have enough balance in your account", "Insufficient Funds");
        } else {
            inventory.boughtNewItem(name, quantity, unit, threshold, price);
            JOptionPane.showMessageDialog(addItemFrame,
                    "Item added successfully",
                    "Item Added",
                    JOptionPane.INFORMATION_MESSAGE);
            addItemFrame.dispose();
        }
    }



    // MODIFIES: this
    // EFFECTS: removes item from inventory
    private void removeItemFromInventory() {
        JTextField itemNameField = new JTextField();
        Object[] message = {
                "Enter item name: ", itemNameField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Remove Item from Inventory",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = itemNameField.getText().toLowerCase();

            if (!inventory.isItemPresent(name)) {
                JOptionPane.showMessageDialog(null, "Item with name " + name
                        + " does not exist in inventory.");
                return;
            }

            int confirmationOption = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to remove " + name + " from inventory?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);
            if (confirmationOption == JOptionPane.YES_OPTION) {
                itemRemovalConfirmation(name);
            }
        }
    }

    private void itemRemovalConfirmation(String name) {
        if (inventory.removeItem(name)) {
            JOptionPane.showMessageDialog(null, "Item removed successfully");
        } else {
            JOptionPane.showMessageDialog(null, "Unable to ");
        }
    }


    private void thresholdCheck() {
        JTextField itemNameField = new JTextField(10);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter item name: "));
        inputPanel.add(itemNameField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Check Inventory Threshold",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = itemNameField.getText().toLowerCase();

            if (!inventory.isItemPresent(name)) {
                JOptionPane.showMessageDialog(null, "Item with name " + name
                        + " does not exist in inventory.");
                return;
            }

            Item item = inventory.giveItem(name);
            thresholdCheckHelper(name, item);
        }
    }

    private void thresholdCheckHelper(String name, Item item) {
        if (item != null) {
            if (item.belowThreshold()) {
                JOptionPane.showMessageDialog(null, "Quantity for " + name
                        + " is lower than the threshold " + item.getThreshold());
            } else {
                JOptionPane.showMessageDialog(null, "Quantity for " + name
                        + " is higher than the threshold " + item.getThreshold());
            }

        } else {
            JOptionPane.showMessageDialog(null,
                    "Cannot find the item. Please check your spelling.");
        }
    }

    // EFFECTS: prints list of all inventory items
    private void allInventoryItems() {
        String[] columnNames = {"Name", "Quantity", "Price", "Threshold"};
        Object[][] data = new Object[inventory.getInventory().length()][4];

        for (int i = 0; i < inventory.getInventory().length(); i++) {
            Item item = inventory.get(i);
            data[i][0] = item.getItemName();
            data[i][1] = item.getQuantity();
            data[i][2] = item.getUnit();
            data[i][3] = item.getThreshold();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Inventory Items");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }



    // MODIFIES: this
    // EFFECTS: directs to managing creditors
    public void manageCreditors() {
        JFrame creditorFrame = new JFrame("Manage Creditors");
        JLabel creditorLabel = new JLabel("Manage Creditors");
        creditorFrame.setLayout(null);
        creditorFrame.setSize(700, 800);

        JButton listCreditorsButton = new JButton("List Creditors");
        JButton addCreditorButton = new JButton("Add Creditor");
        JButton removeCreditorButton = new JButton("Remove Creditor");
        JButton recordPaymentButton = new JButton("Record Payment");
        JButton backButton = new JButton("Quit");
        manageTransactionRefactor(creditorFrame,creditorLabel,listCreditorsButton,addCreditorButton,
                removeCreditorButton,recordPaymentButton,backButton);

        creditorActionListeners(creditorFrame,
                listCreditorsButton, addCreditorButton, removeCreditorButton, recordPaymentButton, backButton);


        creditorFrame.setVisible(true);
    }

    private void creditorActionListeners(JFrame creditorFrame, JButton listCreditorsButton,
                                         JButton addCreditorButton, JButton removeCreditorButton,
                                         JButton recordPaymentButton, JButton backButton) {
        // Add action listeners to buttons
        listCreditorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listCreditors();
            }
        });

        addCreditorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCreditor();
            }
        });

        removeCreditorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCreditor();
            }
        });

        creditorsActionListerners2(creditorFrame, recordPaymentButton, backButton);
    }

    private void creditorsActionListerners2(JFrame creditorFrame, JButton recordPaymentButton, JButton backButton) {
        recordPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordPayment();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditorFrame.dispose();
                frame.setVisible(true);

            }
        });
    }

    // EFFECTS: list all creditors with their amount owed
    private void listCreditors() {
        String[] columnNames = {"Name", "Amount Owed"};
        Object[][] data = new Object[creditors.size()][2];

        for (int i = 0; i < creditors.size(); i++) {
            Creditor creditor = creditors.creditorsGet(i);
            data[i][0] = creditor.getName();
            data[i][1] = creditor.getOwed();

        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Creditors");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds new creditor to creditors
    private void addCreditor() {
        JTextField itemNameField = new JTextField();
        Object[] message = {
                "Enter Creditor name: ", itemNameField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Add Creditor",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = itemNameField.getText().toLowerCase();
            int confirmationOption = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to add " + name + " to creditors?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);
            if (confirmationOption == JOptionPane.YES_OPTION) {
                Creditor creditor = new Creditor(name);
                Creditors.addCreditors(creditor);
                JOptionPane.showMessageDialog(null,"Creditor Added successfully");

            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes creditor from creditors
    private void removeCreditor() {
        JTextField itemNameField = new JTextField();
        Object[] message = {
                "Enter Creditor name: ", itemNameField
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Remove Creditor",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String name = itemNameField.getText().toLowerCase();
            int confirmationOption = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to remove " + name + " ?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);
            if (confirmationOption == JOptionPane.YES_OPTION) {
                if (creditors.removeCreditor(name)) {
                    JOptionPane.showMessageDialog(null,"Creditor Added successfully");
                } else {
                    JOptionPane.showMessageDialog(null,"Cannot find the given Creditor");

                }

            }
        }
    }

    // MODIFIES: this
// EFFECTS: records payment by creditors
    private void recordPayment() {
        JFrame recordPayment = new JFrame("Record Payment");
        recordPayment.setLayout(null);
        recordPayment.setSize(400,500);
        JButton confirm = new JButton("Record Payment");
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel paymentAmountLabel = new JLabel("Payment Amount:");
        JTextField paymentAmountField = new JTextField();
        nameLabel.setBounds(50,100,100,30);
        nameField.setBounds(160,100,100,30);
        paymentAmountLabel.setBounds(50,150,100,30);
        paymentAmountField.setBounds(160,150,100,30);
        confirm.setBounds(125,250,150,50);
        recordPayment.add(nameField);
        recordPayment.add(nameLabel);
        recordPayment.add(paymentAmountLabel);
        recordPayment.add(paymentAmountField);
        recordPayment.add(confirm);
        recordPayment.setVisible(true);

        recordPaymentHelper(confirm, nameField, paymentAmountField);

    }

    private void recordPaymentHelper(JButton confirm, JTextField nameField, JTextField paymentAmountField) {
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Creditor creditor = creditors.getCreditor(nameField.getText());
                    double paymentAmount = Double.parseDouble(paymentAmountField.getText());
                    if (creditor == null) {
                        JOptionPane.showMessageDialog(null,
                                "There is no Creditor with this name in your list");
                        } else {
                        creditor.paymentReceived(paymentAmount);
                        JOptionPane.showMessageDialog(null,
                                "Payment of $" + paymentAmount + " received from " + creditor.getName()
                                    + " recorded successfully.");
                    }
                } catch (IllegalArgumentException e1) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid input. Please check name and payment amount.");
                }
            }
        });
    }


    // EFFECTS: manages transactions
    private void manageTransaction() {

        JFrame transactionFrame = new JFrame();
        transactionFrame.setLayout(null);
        JLabel transactionLabel = new JLabel("Perform a Transaction");
        JButton creditButton = new JButton("Credit Sale");
        JButton cashButton = new JButton("Cash Sale");
        JButton creditReturnButton = new JButton("Credit Return");
        JButton cashReturnButton = new JButton("Cash Return");
        JButton quitButton = new JButton("quit");
        manageTransactionRefactor(transactionFrame, transactionLabel, creditButton, cashButton, creditReturnButton,
                cashReturnButton, quitButton);
        addingListenersToTransactionButton(transactionFrame, creditButton, cashButton, quitButton);
        creditReturnActionListener(creditReturnButton, cashReturnButton);
        transactionFrame.setVisible(true);
    }

    private void creditReturnActionListener(JButton creditReturnButton, JButton cashReturnButton) {
        creditReturnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordCreditReturn();
            }
        });
        cashReturnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordCashReturn();
            }
        });
    }

    private void addingListenersToTransactionButton(JFrame transactionFrame, JButton creditButton, JButton cashButton,
                                                    JButton quitButton) {
        creditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordCreditTransaction();
            }
        });
        cashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recordCashTransaction();
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transactionFrame.dispose();
                frame.setVisible(true);
            }
        });
    }

    private void manageTransactionRefactor(JFrame transactionFrame, JLabel transactionLabel, JButton creditButton,
                                           JButton cashButton, JButton creditReturnButton, JButton cashReturnButton,
                                           JButton quitButton) {
        transactionFrame.add(transactionLabel);
        transactionFrame.add(creditButton);
        transactionFrame.add(cashButton);
        transactionFrame.add(creditReturnButton);
        transactionFrame.add(cashReturnButton);
        transactionFrame.add(quitButton);
        transactionFrame.setSize(700,800);
        transactionLabel.setBounds(button.getBounds());
        creditButton.setBounds(button1.getBounds());
        cashButton.setBounds(button2.getBounds());
        creditReturnButton.setBounds(button3.getBounds());
        cashReturnButton.setBounds(button4.getBounds());
        quitButton.setBounds(button5.getBounds());
    }


    // EFFECTS: records a cash transaction
    private void recordCashTransaction() {
        JFrame cashTransactionFrame = new JFrame("Perform Cash Transaction");
        cashTransactionFrame.setSize(400, 300);

        JLabel nameLabel = new JLabel("Item Name:");
        JTextField nameField = new JTextField(20);
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(20);
        JButton transactionButton = new JButton("Confirm Transaction");

        JPanel cashTransactionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbcHelper(gbc);
        cashTransactionGrid(gbc.fill, GridBagConstraints.HORIZONTAL, cashTransactionPanel,
                nameLabel, gbc, nameField, 1, quantityLabel, quantityField, priceLabel, priceField);
        refactoring(cashTransactionFrame, transactionButton, cashTransactionPanel, gbc);
        cashTransactionActionListener(cashTransactionFrame, nameField, quantityField, priceField, transactionButton);
    }

    private void cashTransactionActionListener(JFrame cashTransactionFrame, JTextField nameField,
                                               JTextField quantityField, JTextField priceField,
                                               JButton transactionButton) {
        transactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                cashTransactionHelper(name, quantity, price, cashTransactionFrame);
            }
        });
    }

    private void gbcHelper(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
    }

    private void refactoring(JFrame cashTransactionFrame, JButton transactionButton, JPanel cashTransactionPanel,
                             GridBagConstraints gbc) {
        cashTransactionPanel.add(transactionButton, gbc);

        cashTransactionFrame.add(cashTransactionPanel);
        cashTransactionFrame.setVisible(true);
    }

    private void cashTransactionHelper(String name, int quantity, double price, JFrame cashTransactionFrame) {
        try {
            if (!inventory.isItemPresent(name)) {
                conditionalCheckerRefactor(cashTransactionFrame, "Item not present in Inventory",
                        "Invalid Item");
            } else if (quantity < 0) {
                conditionalCheckerRefactor(cashTransactionFrame, "Please Enter a positive quantity", "Invalid Quanity");
            } else if (inventory.giveItem(name).getQuantity() < quantity) {
                conditionalCheckerRefactor(cashTransactionFrame,
                        "The item selected does not have the required quantity",
                        "Invalid Quanity");
            } else {
                doCashTransaction(name, quantity, price, cashTransactionFrame);


            }



        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(cashTransactionFrame,
                    "Please Enter a valid number",
                    "Invalid Number",
                    JOptionPane.ERROR_MESSAGE);


        }
    }

    private void conditionalCheckerRefactor(JFrame cashTransactionFrame, String itemNotPresentInInventory,
                                            String invalidItem) {
        JOptionPane.showMessageDialog(cashTransactionFrame,
                itemNotPresentInInventory,
                invalidItem,
                JOptionPane.ERROR_MESSAGE);
    }

    private void doCashTransaction(String name, int quantity, double price, JFrame cashTransactionFrame) {
        CashSales cashSales = new CashSales();
        cashSales.sales(name, quantity, price);
        JOptionPane.showMessageDialog(cashTransactionFrame,"Transaction confirmed");
        cashTransactionFrame.dispose();
    }

    private void cashTransactionGrid(int gbc, int horizontal, JPanel cashTransactionPanel, JLabel nameLabel,
                                     GridBagConstraints gbc1, JTextField nameField, int gridy, JLabel quantityLabel,
                                     JTextField quantityField, JLabel priceLabel, JTextField priceField) {
        gbc = horizontal;
        cashTransactionPanel.add(nameLabel, gbc1);
        gbc1.gridx = 1;
        cashTransactionPanel.add(nameField, gbc1);
        gbc1.gridx = 0;
        gbc1.gridy = gridy;
        cashTransactionPanel.add(quantityLabel, gbc1);
        gbc1.gridx = 1;
        cashTransactionPanel.add(quantityField, gbc1);
        gbc1.gridx = 0;
        gbc1.gridy = 3;
        cashTransactionPanel.add(priceLabel, gbc1);
        gbc1.gridx = 1;
        cashTransactionPanel.add(priceField, gbc1);
        gbc1.gridx = 0;
        gbc1.gridy = 4;
        gbc1.gridwidth = 2;
    }

    //EFFECTS: records credit transaction
    private void recordCreditTransaction() {
        JFrame creditTransactionFrame = new JFrame("Perform Credit Transaction");
        creditTransactionFrame.setSize(400, 300);

        JLabel nameLabel = new JLabel("Item Name:");
        JTextField nameField = new JTextField(20);
        JLabel customerLabel = new JLabel("Customer Name:");
        JTextField customerField = new JTextField(20);
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(20);
        JButton transactionButton = new JButton("Confirm Transaction");

        JPanel creditTransactionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        creditTransactionGrid(nameLabel, nameField, customerLabel, customerField, quantityLabel, quantityField,
                priceLabel, priceField, transactionButton, creditTransactionPanel, gbc);

        creditTransactionFrame.setContentPane(creditTransactionPanel);
        creditTransactionFrame.pack();
        creditTransactionFrame.setLocationRelativeTo(null);
        creditTransactionFrame.setVisible(true);

        creditTransactionActionListener(creditTransactionFrame, nameField, customerField, quantityField, priceField,
                transactionButton);
    }

    private void creditTransactionActionListener(JFrame creditTransactionFrame, JTextField nameField,
                                                 JTextField customerField, JTextField quantityField,
                                                 JTextField priceField, JButton transactionButton) {
        transactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerForCredit(nameField, customerField, quantityField, priceField, creditTransactionFrame);
            }
        });
    }

    private void creditTransactionGrid(JLabel nameLabel, JTextField nameField, JLabel customerLabel,
                                       JTextField customerField, JLabel quantityLabel, JTextField quantityField,
                                       JLabel priceLabel, JTextField priceField, JButton transactionButton,
                                       JPanel creditTransactionPanel, GridBagConstraints gbc) {
        gbc.fill = GridBagConstraints.HORIZONTAL;
        creditTransactionGridHelper(nameLabel, nameField, creditTransactionPanel, gbc);
        creditTransactionPanel.add(customerLabel, gbc);
        gbc.gridx = 1;
        creditTransactionPanel.add(customerField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        creditTransactionPanel.add(quantityLabel, gbc);
        gbc.gridx = 1;
        creditTransactionPanel.add(quantityField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        creditTransactionPanel.add(priceLabel, gbc);
        gbc.gridx = 1;
        creditTransactionPanel.add(priceField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        creditTransactionPanel.add(transactionButton, gbc);
    }

    private void creditTransactionGridHelper(JLabel nameLabel, JTextField nameField, JPanel creditTransactionPanel,
                                             GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        creditTransactionPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        creditTransactionPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
    }


    private void listenerForCredit(JTextField nameField, JTextField customerField, JTextField quantityField,
                                   JTextField priceField, JFrame creditTransactionFrame) {
        String name = nameField.getText();
        String customerName = customerField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        conditionalCheckerHelper(creditTransactionFrame, name, customerName, quantity, price);
    }

    private void conditionalCheckerHelper(JFrame creditTransactionFrame, String name, String customerName,
                                          int quantity, double price) {
        if (price < 0) {
            conditionalCheckerRefactor(creditTransactionFrame, "Enter a valid price",
                    "Invalid Price");
        } else if (quantity < 0) {
            conditionalCheckerRefactor(creditTransactionFrame, "Enter a valid quantity",
                    "Invalid Quantity");
        } else if (!inventory.isItemPresent(name)) {
            conditionalCheckerRefactor(creditTransactionFrame,
                    "The item " + name + " doesn't exist in your inventory",
                    "Invalid Item");
        } else if (inventory.giveItem(name).getQuantity() < quantity) {
            conditionalCheckerRefactor(creditTransactionFrame,
                    "The quantity of " + name + " is less than quantity being sold",
                    "Invalid Quantity");
        } else {
            CreditSales creditSales = new CreditSales();
            creditSales.sales(name, customerName, quantity, price);
            JOptionPane.showMessageDialog(creditTransactionFrame,"Transaction confirmed");
            creditTransactionFrame.dispose();
        }
    }


    private void creditTransactionFrame(JLabel nameLabel, JTextField nameField, JLabel customerLabel,
                                        JTextField customerField, JLabel quantityLabel, JTextField quantityField,
                                        JLabel priceLabel, JTextField priceField, JPanel creditTransactionPanel,
                                        GridBagConstraints gbc) {
        creditTransactionPanel.add(nameLabel, gbc);
        gbc.gridx++;
        creditTransactionPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        creditTransactionPanel.add(customerLabel, gbc);
        gbc.gridx++;
        creditTransactionPanel.add(customerField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        creditTransactionPanel.add(quantityLabel, gbc);
        gbc.gridx++;
        creditTransactionPanel.add(quantityField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        creditTransactionPanel.add(priceLabel, gbc);
        gbc.gridx++;
        creditTransactionPanel.add(priceField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
    }

    //EFFECTS: records cash return
    public void recordCashReturn() {
        JFrame cashReturnTransactionFrame = new JFrame("Perform Cash Return");
        cashReturnTransactionFrame.setSize(400, 300);

        JLabel nameLabel = new JLabel("Item Name:");
        JTextField nameField = new JTextField(20);
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(20);
        JButton transactionButton = new JButton("Confirm Cash Return");

        JPanel cashTransactionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbcHelper(gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        helperForCashReturn(nameLabel, nameField, quantityLabel, quantityField, priceLabel, priceField,
                transactionButton,
                cashTransactionPanel, gbc);

        cashReturnHelper(cashReturnTransactionFrame, nameField, quantityField, priceField, transactionButton);

        cashReturnTransactionFrame.add(cashTransactionPanel);
        cashReturnTransactionFrame.setVisible(true);
    }


    private void cashReturnHelper(JFrame cashReturnTransactionFrame, JTextField nameField, JTextField quantityField,
                                  JTextField priceField, JButton transactionButton) {
        transactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().toLowerCase();
                    int quantity = Integer.parseInt(quantityField.getText());
                    double price = Double.parseDouble(priceField.getText());


                    itemPresentHekper(name, quantity, price, cashReturnTransactionFrame);

                    cashReturnTransactionFrame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(cashReturnTransactionFrame, "Please enter a valid number",
                            "Invalid Number", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void itemPresentHekper(String name, int quantity, double price, JFrame cashReturnTransactionFrame) {
        if (!inventory.isItemPresent(name)) {
            JOptionPane.showMessageDialog(cashReturnTransactionFrame,
                    "The item is not present in Inventory",
                    "Invalid Item", JOptionPane.ERROR_MESSAGE);
        } else {
            CashSales cashSales = new CashSales();
            cashSales.cashReturn(name, quantity, price);
            JOptionPane.showMessageDialog(cashReturnTransactionFrame,
                    "Cash return successfully completed");
        }
    }

    private void helperForCashReturn(JLabel nameLabel, JTextField nameField, JLabel quantityLabel,
                                     JTextField quantityField, JLabel priceLabel, JTextField priceField,
                                     JButton transactionButton, JPanel cashTransactionPanel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        cashTransactionPanel.add(nameLabel, gbc);

        helperForCashReturn2(nameField, quantityLabel, cashTransactionPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        cashTransactionPanel.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        cashTransactionPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        cashTransactionPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        cashTransactionPanel.add(transactionButton, gbc);
    }

    private void helperForCashReturn2(JTextField nameField, JLabel quantityLabel, JPanel cashTransactionPanel,
                                      GridBagConstraints gbc) {
        gbc.gridx = 1;
        gbc.gridy = 0;
        cashTransactionPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        cashTransactionPanel.add(quantityLabel, gbc);
    }

    //EFFECTS: records credit return
    public void recordCreditReturn() {
        JFrame creditReturnTransactionFrame = new JFrame("Perform Credit Return");
        creditReturnTransactionFrame.setSize(400, 300);

        JLabel nameLabel = new JLabel("Item Name:");
        JTextField nameField = new JTextField(20);
        JLabel customerLabel = new JLabel("Customer Name:");
        JTextField customerField = new JTextField(20);
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(20);
        JButton returnButton = new JButton("Confirm Credit Return");

        JPanel creditReturnPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbcHelper(gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        creditReturnGrid(nameLabel, nameField, customerLabel, customerField, quantityLabel, quantityField,
                priceLabel, creditReturnPanel, gbc);

        creditReturnHelper2(creditReturnTransactionFrame, nameField, customerField, quantityField, priceField,
                returnButton, creditReturnPanel, gbc);

        creditReturnTransactionFrame.add(creditReturnPanel);
        creditReturnTransactionFrame.setVisible(true);
    }

    private void creditReturnHelper2(JFrame creditReturnTransactionFrame, JTextField nameField,
                                     JTextField customerField, JTextField quantityField, JTextField priceField,
                                     JButton returnButton, JPanel creditReturnPanel, GridBagConstraints gbc) {
        gbc.gridx = 1;
        gbc.gridy = 3;
        creditReturnPanel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        creditReturnPanel.add(returnButton, gbc);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditReturnActionPerformed(nameField, customerField, quantityField, priceField,
                        creditReturnTransactionFrame);
            }
        });
    }

    private void creditReturnGrid(JLabel nameLabel, JTextField nameField, JLabel customerLabel,
                                  JTextField customerField, JLabel quantityLabel, JTextField quantityField,
                                  JLabel priceLabel, JPanel creditReturnPanel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        creditReturnPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        creditReturnPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        creditReturnPanel.add(customerLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        creditReturnPanel.add(customerField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        creditReturnPanel.add(quantityLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        creditReturnPanel.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        creditReturnPanel.add(priceLabel, gbc);
    }

    private void creditReturnActionPerformed(JTextField nameField, JTextField customerField,
                                             JTextField quantityField, JTextField priceField,
                                             JFrame creditReturnTransactionFrame) {
        try {
            String name = nameField.getText();
            String customerName = customerField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());
            creditReturnHelper3(creditReturnTransactionFrame, name, customerName, quantity, price);


        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(creditReturnTransactionFrame, "Please Enter a valid number",
                    "Invalid Number",
                    JOptionPane.ERROR_MESSAGE);


        }
    }

    private void creditReturnHelper3(JFrame creditReturnTransactionFrame, String name, String customerName,
                                     int quantity, double price) {
        if (Creditors.getCreditor(customerName) == null) {
            JOptionPane.showMessageDialog(creditReturnTransactionFrame, "The creditor does not exist",
                    "Invalid Creditor",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!inventory.isItemPresent(name)) {
            JOptionPane.showMessageDialog(creditReturnTransactionFrame,
                    "The item you are trying to return doesn't exist","Invalid Item",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            CreditSales creditSales = new CreditSales();
            creditSales.recordCreditReturn(name, customerName, quantity, price);
            JOptionPane.showMessageDialog(creditReturnTransactionFrame,
                    "Return successfully completed");
            creditReturnTransactionFrame.dispose();
        }
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
        }
    }


}
