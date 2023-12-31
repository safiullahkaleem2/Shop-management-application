package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionUI {
    private JButton button;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JFrame frame;
    private Inventory inventory;

    public TransactionUI(JButton button, JButton button1, JButton button2, JButton button3, JButton button4,
                         JButton button5, JFrame frame) {
        this.button = button;

        this.button1 = button1;
        this.button2 = button2;
        this.button3 = button3;
        this.button4 = button4;
        this.button5 = button5;
        this.frame = frame;
        inventory = Inventory.getInventory();
    }


    // EFFECTS: manages transactions
    public void manageTransaction() {

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

    // REQUIRES: Valid JButton objects for creditReturnButton and cashReturnButton
// MODIFIES: creditReturnButton, cashReturnButton
// EFFECTS: Adds ActionListener to creditReturnButton and cashReturnButton for handling credit and cash returns
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

    // REQUIRES: Valid instances of JFrame, JLabel, and JButton components
    // MODIFIES: transactionFrame
    // EFFECTS: Adds the specified components to the transactionFrame and sets their bounds according to the specified
    // button bounds
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

    // REQUIRES: Valid JButton instances for creditButton, cashButton, and quitButton
    // MODIFIES: creditButton, cashButton, quitButton
    // EFFECTS: Adds action listeners to creditButton, cashButton, and quitButton for recording transactions and closing
    // the transaction frame
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


    // REQUIRES: Valid input for item name, quantity, and price
    // MODIFIES: cashReturnTransactionFrame
    // EFFECTS: Displays a new window for recording a cash return transaction, including input fields for item name,
    // quantity, and price
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

    // REQUIRES: Valid input for item name, customer name, quantity, and price
    // MODIFIES: creditReturnTransactionFrame
    //EFFECTS: Displays a new window for recording a credit return transaction, including input fields for item name,
    // customer name, quantity, and price
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

    // REQUIRES: Valid instances of JFrame, JTextField, JButton, JPanel, and GridBagConstraints components
    // MODIFIES: creditReturnPanel, returnButton
    // EFFECTS: Adds components to the creditReturnPanel and sets up an action listener for the returnButton to handle
    // credit return actions
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

    // REQUIRES: Valid instances of JLabel, JTextField, JPanel, and GridBagConstraints components
    // MODIFIES: creditReturnPanel
    // EFFECTS: Adds the specified components to the creditReturnPanel using the provided GridBagConstraints
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

    // REQUIRES: Valid instances of JTextField for nameField, customerField, quantityField, and priceField,
    // and a JFrame for creditReturnTransactionFrame
    // MODIFIES: None
    // EFFECTS: Parses input from the JTextFields and calls creditReturnHelper3 to handle the credit return transaction;
    // shows error message for invalid input
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

    // REQUIRES: Valid creditReturnTransactionFrame, name, customerName, quantity, and price
    // MODIFIES: creditReturnTransactionFrame
    // EFFECTS: Validates the input and records the credit return transaction if successful, or shows an error message
    // if there is a problem with the input

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

    // REQUIRES: A valid GridBagConstraints instance (gbc)
    // MODIFIES: gbc
    // EFFECTS: Sets the initial properties of the GridBagConstraints instance for proper layout

    private void gbcHelper(GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
    }

    // REQUIRES: Valid instances of JFrame, JTextField, and JButton components
    // MODIFIES: transactionButton
    // EFFECTS: Adds an action listener to the transactionButton for handling cash return transactions
    private void cashReturnHelper(JFrame cashReturnTransactionFrame, JTextField nameField, JTextField quantityField,
                                  JTextField priceField, JButton transactionButton) {
        transactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().toLowerCase();
                    int quantity = Integer.parseInt(quantityField.getText());
                    double price = Double.parseDouble(priceField.getText());


                    itemPresentHelper(name, quantity, price, cashReturnTransactionFrame);

                    cashReturnTransactionFrame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(cashReturnTransactionFrame, "Please enter a valid number",
                            "Invalid Number", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // REQUIRES: Valid name, quantity, price, and cashReturnTransactionFrame
    // MODIFIES: cashReturnTransactionFrame
    // EFFECTS: Validates the input and records the cash return transaction if successful, or shows an error message if
    // there is a problem with the input
    private void itemPresentHelper(String name, int quantity, double price, JFrame cashReturnTransactionFrame) {
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

    // REQUIRES: Valid instances of JLabel, JTextField, JButton, JPanel, and GridBagConstraints components
    // MODIFIES: cashTransactionPanel
    // EFFECTS: Adds components to cashTransactionPanel using the specified GridBagConstraints to create a cash return
    // form
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

    // REQUIRES: Valid instances of JTextField, JLabel, JPanel, and GridBagConstraints components
    // MODIFIES: cashTransactionPanel
    // EFFECTS: Adds nameField and quantityLabel to cashTransactionPanel using the specified GridBagConstraints
    private void helperForCashReturn2(JTextField nameField, JLabel quantityLabel, JPanel cashTransactionPanel,
                                      GridBagConstraints gbc) {
        gbc.gridx = 1;
        gbc.gridy = 0;
        cashTransactionPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        cashTransactionPanel.add(quantityLabel, gbc);
    }


    // REQUIRES: None
    // MODIFIES: cashTransactionFrame
    // EFFECTS: Creates a new JFrame for recording cash transactions, including input fields for item name,
    // quantity, and price
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

    // REQUIRES: Valid instances of JFrame, JTextField, and JButton components
    // MODIFIES: transactionButton
    // EFFECTS: adds actionlisteners to buttons
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

    //EFFECTS: checks the conditions for cash transaction
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

    //modifies: inventory, bank
    //effect: performs a cash transaction
    private void doCashTransaction(String name, int quantity, double price, JFrame cashTransactionFrame) {
        CashSales cashSales = new CashSales();
        cashSales.sales(name, quantity, price);
        JOptionPane.showMessageDialog(cashTransactionFrame,"Transaction confirmed");
        cashTransactionFrame.dispose();
    }

    //effects: gives item not present error
    private void conditionalCheckerRefactor(JFrame cashTransactionFrame, String itemNotPresentInInventory,
                                            String invalidItem) {
        JOptionPane.showMessageDialog(cashTransactionFrame,
                itemNotPresentInInventory,
                invalidItem,
                JOptionPane.ERROR_MESSAGE);
    }

    //modifies:frame
    //effects: adds buttons to frame
    private void refactoring(JFrame cashTransactionFrame, JButton transactionButton, JPanel cashTransactionPanel,
                             GridBagConstraints gbc) {
        cashTransactionPanel.add(transactionButton, gbc);

        cashTransactionFrame.add(cashTransactionPanel);
        cashTransactionFrame.setVisible(true);
    }

    //effects: makes the cash transaction grid
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

    //modifies: inventory, creditors
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

    //effects: add action listener to credit transaction button
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

    //effects: creates the credit transaction grid
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

    //modifies:grid
    //effects: sets alignments of the grid
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

    //effects:helps in performing credit transaction
    private void listenerForCredit(JTextField nameField, JTextField customerField, JTextField quantityField,
                                   JTextField priceField, JFrame creditTransactionFrame) {
        String name = nameField.getText();
        String customerName = customerField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        conditionalCheckerHelper(creditTransactionFrame, name, customerName, quantity, price);
    }

    //effects: checks for conditions and gives appropriate error messages
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
            if (null == Creditors.getCreditor(customerName)) {
                Creditor creditor = new Creditor(customerName);
                Creditors.addCreditors(creditor);
            }
            performCreditSales(creditTransactionFrame, name, customerName, quantity, price, creditSales);
        }
    }

    //effects: performs the credit sales
    private void performCreditSales(JFrame creditTransactionFrame, String name, String customerName, int quantity,
                                    double price, CreditSales creditSales) {
        creditSales.sales(name, customerName, quantity, price);
        JOptionPane.showMessageDialog(creditTransactionFrame,"Transaction confirmed");
        creditTransactionFrame.dispose();
    }


}
