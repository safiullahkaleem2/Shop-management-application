package ui;

import model.Bank;
import model.Inventory;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Double.parseDouble;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
// This class is Inventory UI class which used to design the manage inventory menu in the application

public class InventoryUI {
    private JFrame frame;
    private Inventory inventory;
    private Bank bank;

    public InventoryUI(JFrame frame1) {
        this.frame = frame1;

        inventory = Inventory.getInventory();
        bank = Bank.getBank();
    }

    //Effects:- manages Inventory
    public void manageInventory() {
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

    //effects: sets the grid
    private void gbcHelper(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
    }

    //effects: helps in building the grid
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

    //effects: adds action listener to the button
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

    //effects: creates a quit button
    private void quitButtonHelper(JButton quitButton, JFrame inventoryFrame) {
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inventoryFrame.dispose();
                frame.setVisible(true);

            }
        });
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

    //effects: creates frame for adding items
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


    //modifies:this
    //effects: adds item after checking all the conditionals
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

    //effects: creates an error message
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

    //effects:creates suitable prompts
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

    // modifies:this
    // effects: removes item from inventory
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

    //effects: gives item removal prompt
    private void itemRemovalConfirmation(String name) {
        if (inventory.removeItem(name)) {
            JOptionPane.showMessageDialog(null, "Item removed successfully");
        } else {
            JOptionPane.showMessageDialog(null, "Unable to ");
        }
    }


    //effects:creates a general frame
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

    //effects: gives prompt for item not Present
    private void conditionalCheckerRefactor(JFrame cashTransactionFrame, String itemNotPresentInInventory,
                                            String invalidItem) {
        JOptionPane.showMessageDialog(cashTransactionFrame,
                itemNotPresentInInventory,
                invalidItem,
                JOptionPane.ERROR_MESSAGE);
    }

    //effects: displays all inventory items
    private void allInventoryItems() {
        String[] columnNames = {"Name", "Quantity", "Price", "Threshold"};
        Object[][] data = new Object[Inventory.getInventory().length()][4];

        for (int i = 0; i < Inventory.getInventory().length(); i++) {
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

    //effects: checks threshold
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

    //effects: check the conditionals of the threshold
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



}
