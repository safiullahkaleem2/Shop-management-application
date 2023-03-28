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
import java.util.InputMismatchException;
import java.util.Scanner;

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
    private Creditor creditor;
    private CashSales cashSales;
    private CreditSales creditSales;
    private Bank bank;
    private Inventory inventory;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner input;
    private JFrame frame;
    private JButton button;

    @SuppressWarnings("methodlength")
    // EFFECTS: runs the teller application
    public ShopManagement() throws FileNotFoundException {
        frame = new JFrame("Shop Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 800);

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
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // add the buttons to the panel
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

        // add the button panel to the frame
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);

        initializeNewShop();
        // add an action listener to the buttons
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBank();
            }
        });
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                manageInventory();
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manageCreditors();
            }
        });
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manageTransaction();
            }
        });
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        // show the frame
        frame.setVisible(true);

    }

    @SuppressWarnings("methodlength")
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
                    JOptionPane.showMessageDialog(null,
                            "Invalid balance. Please enter a positive value.",
                            "Invalid Balance", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Invalid input. Please enter a valid number.", "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        Bank.getBank(balance);
        bank = Bank.getBank();
        inventory = Inventory.getInventory();
        creditors = new Creditors();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runShopManagement() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            if (bank == null) {
                init();
            } else {
                displayMenu();
                command = input.next();
                command = command.toLowerCase();

                if (command.equals("q")) {
                    keepGoing = false;
                } else {
                    processCommand(command);
                }
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "b":
                checkBank();
                break;
            case "i":
                manageInventory();
                break;
            case "c":
                manageCreditors();
                break;
            case "t":
                manageTransaction();
                break;
            case "s":
                System.out.println("Please write the name of the shop owner ");
                System.out.println("(Warning no duplicate names allowed)");
                String selection2 = input.next();
                save(selection2);
                break;

            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes inventory, creditors and bank
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.println("Welcome Bank!");
        System.out.println("Do you want to load a previously saved file?");
        System.out.println("\nSelect from:");
        System.out.println("\ty -> yes");
        System.out.println("\tn -> no");
        String selection = input.next();
        selection = selection.toLowerCase();

        switch (selection) {
            case "y":
                System.out.println("please write the name of the shopkeeper");
                String selection2 = input.next();
                load(selection2);
                break;
            case "n":
                init2();
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }

    }

    private void init2() {
        input = new Scanner(System.in);
        double balance = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Enter initial bank balance: ");
                balance = input.nextDouble();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number.");
                input.next();
            }
        }

        Bank.getBank(balance);
        bank = Bank.getBank();
        inventory = Inventory.getInventory();
        creditors = new Creditors();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> Check bank details");
        System.out.println("\ti -> Manage inventory");
        System.out.println("\tc -> Manage creditors");
        System.out.println("\tt -> Perform a transaction");
        System.out.println("\ts -> save Inventory and creditors to file");
        System.out.println("\tq -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: directs to checking bank details
    private void checkBank() {
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

    @SuppressWarnings("methodlength")
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
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gridBuildingHelper(inventoryLabel, addItemButton, removeItemButton, thresholdCheckButton, viewInventoryButton,
                quitButton, inventoryButtonPanel, gbc);


        // add the button panel to the frame
        inventoryFrame.getContentPane().setLayout(new BorderLayout());
        inventoryFrame.getContentPane().add(inventoryButtonPanel, BorderLayout.CENTER);



        // add an action listener to the add item button
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


        // add an action listener to the quit button
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inventoryFrame.dispose();
                frame.setVisible(true);

            }
        });

        // add the inventory panel to the frame
        inventoryFrame.getContentPane().add(inventoryButtonPanel);

        // show the inventory frame
        inventoryFrame.setVisible(true);
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


    @SuppressWarnings("methodlength")
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
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addItemPanel.add(nameLabel, gbc);
        gbc.gridx++;
        addItemPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addItemPanel.add(quantityLabel, gbc);
        gbc.gridx++;
        addItemPanel.add(quantityField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addItemPanel.add(unitLabel, gbc);
        gbc.gridx++;
        addItemPanel.add(unitField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addItemPanel.add(thresholdLabel, gbc);
        gbc.gridx++;
        addItemPanel.add(thresholdField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addItemPanel.add(priceLabel, gbc);
        gbc.gridx++;
        addItemPanel.add(priceField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        addItemPanel.add(addButton, gbc);

        // add an action listener to the add button
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helperForAddingItems(nameField, quantityField, unitField, thresholdField,priceField, addItemFrame);
            }
        });


        // add the add item panel to the frame
        addItemFrame.getContentPane().setLayout(new BorderLayout());
        addItemFrame.getContentPane().add(addItemPanel, BorderLayout.CENTER);

        // show the add item frame
        addItemFrame.setVisible(true);
    }

    @SuppressWarnings("methodlength")
    private void helperForAddingItems(JTextField nameField, JTextField quantityField, JTextField unitField,
                                      JTextField thresholdField, JTextField priceField, JFrame addItemFrame) {
        String name = nameField.getText().toLowerCase();
        if (inventory.isItemPresent(name)) {
            JOptionPane.showMessageDialog(addItemFrame,
                    "Item with name " + name + " already exists in inventory.",
                    "Item Already Exists",
                    JOptionPane.ERROR_MESSAGE);
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

        if (price > bank.getBalance()) {
            JOptionPane.showMessageDialog(addItemFrame,
                    "You don't have enough balance in your account",
                    "Insufficient Funds",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            inventory.boughtNewItem(name, quantity, unit, threshold, price);
            JOptionPane.showMessageDialog(addItemFrame,
                    "Item added successfully",
                    "Item Added",
                    JOptionPane.INFORMATION_MESSAGE);
            addItemFrame.dispose();
        }
    }



    @SuppressWarnings("methodlength")
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
                if (inventory.removeItem(name)) {
                    JOptionPane.showMessageDialog(null, "Item removed successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Unable to remove item from inventory");
                }
            }
        }
    }

    @SuppressWarnings("methodlength")
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

    //EFFECTS: shows inventory menu
    private void inventoryMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> Add item");
        System.out.println("\t2 -> Remove item");
        System.out.println("\t3 -> Check item threshold");
        System.out.println("\t4 -> List of all Items in Inventory");
        System.out.println("\tq -> Quit inventory management");

    }

    // MODIFIES: this
    // EFFECTS: directs to managing creditors
    private void manageCreditors() {
        String command;
        while (true) {
            creditorsMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                return;
            } else if (command.equals("1")) {
                listCreditors();
            } else if (command.equals("2")) {
                addCreditor();
            } else if (command.equals("3")) {
                removeCreditor();
            } else if (command.equals("4")) {
                recordPayment();
            } else {
                System.out.println("Selection not valid...");
            }
        }
    }

    // EFFECTS: shows creditor menu
    private void creditorsMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> List creditors");
        System.out.println("\t2 -> Add creditor");
        System.out.println("\t3 -> Remove creditor");
        System.out.println("\t4 -> Record payment by creditor");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: list all creditors with their amount owed
    private void listCreditors() {
        System.out.println("\nList of creditors:");
        for (int n = 0; n < creditors.creditorsLength(); n++) {
            Creditor creditor = creditors.creditorsGet(n);
            double amountOwed = creditor.getOwed();

            System.out.println("Name: " + creditor.getName()
                    + " Amount Owed: " + amountOwed);
        }

        System.out.printf("Total amount owed: %.2f%n", creditors.getTotalAmountOwed());
    }

    // MODIFIES: this
    // EFFECTS: adds new creditor to creditors
    private void addCreditor() {
        System.out.print("Enter creditor name: ");
        String name = input.next();
        name = name.toLowerCase();
        creditor = new Creditor(name);
        Creditors.addCreditors(this.creditor);
        System.out.println("Creditor added successfully");
    }

    // MODIFIES: this
    // EFFECTS: removes creditor from creditors
    private void removeCreditor() {
        System.out.print("Enter creditor name: ");
        String name = input.next();
        name = name.toLowerCase();

        creditors.removeCreditor(name);
    }

    // MODIFIES: this
    // EFFECTS: records payment by creditors
    private void recordPayment() {
        System.out.print("Enter creditor name: ");
        String name = input.next();
        name = name.toLowerCase();
        this.creditor = Creditors.getCreditor(name);

        System.out.print("Enter payment amount: ");
        double paymentAmount = input.nextDouble();

        try {
            creditor.paymentReceived(paymentAmount);
            System.out.println("Payment recorded successfully");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    // EFFECTS: manages transactions
    private void manageTransaction() {
        String command;

        while (true) {
            transactionMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                return;
            } else if (command.equals("1")) {
                cashSales = new CashSales();
                recordCashTransaction();
            } else if (command.equals("2")) {
                creditSales = new CreditSales();
                recordCreditTransaction();
            } else if (command.equals("3")) {
                cashSales = new CashSales();
                recordCashReturn();
            } else if (command.equals("4")) {
                creditSales = new CreditSales();
                recordCreditReturn();
            } else {
                System.out.println("Selection not valid...");
            }
        }
    }

    // EFFECTS: displays menu of transaction options to user
    private void transactionMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> Record a cash transaction");
        System.out.println("\t2 -> Record a credit transaction");
        System.out.println("\t3 -> Record a cash return");
        System.out.println("\t4 -> Record a credit return");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: records a cash transaction
    private void recordCashTransaction() {
        System.out.print("Enter item name: ");
        String name = input.next();
        name = name.toLowerCase();
        System.out.print("Enter quantity: ");
        int quantity = input.nextInt();
        System.out.print("Enter total price: ");
        double price = input.nextDouble();
        cashSales.sales(name,quantity,price);
    }

    //EFFECTS: records credit transaction
    private void recordCreditTransaction() {
        System.out.print("Enter item name: ");
        String name = input.next();
        name = name.toLowerCase();

        System.out.print("Enter customer name: ");
        String customerName = input.next();
        customerName = customerName.toLowerCase();

        System.out.print("Enter quantity: ");
        int quantity = input.nextInt();

        System.out.print("Enter total price: ");
        double price = input.nextDouble();

        creditSales.sales(name,customerName,quantity,price);



    }

    //EFFECTS: records cash return
    public void recordCashReturn() {
        System.out.print("Enter item name: ");
        String name = input.next();
        name = name.toLowerCase();
        System.out.print("Enter quantity: ");
        int quantity = input.nextInt();
        System.out.print("Enter total price: ");
        double price = input.nextDouble();

        cashSales.cashReturn(name,quantity,price);
    }

    //EFFECTS: records credit return
    public void recordCreditReturn() {
        System.out.print("Enter item name: ");
        String name = input.next();
        name = name.toLowerCase();

        System.out.print("Enter customer name: ");
        String customerName = input.next();
        customerName = customerName.toLowerCase();

        System.out.print("Enter quantity: ");
        int quantity = input.nextInt();

        System.out.print("Enter total price: ");
        double price = input.nextDouble();

        creditSales.recordCreditReturn(name,customerName,quantity,price);

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
            System.out.println("Saved Shop Status file of " + name);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file of " + name);
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
            System.out.println("Loaded Inventory and Creditors from " + name);
        } catch (IOException e) {
            System.out.println("Unable to read from file of " + name);
        }
    }


}
