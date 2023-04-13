package ui;

import model.Creditor;
import model.Creditors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditorsUI {

    private JButton button;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private Creditors creditors;
    private JFrame frame;
// This class is Creditors UI class which used to design the manage creditors menu in the application

    public CreditorsUI(JButton button, JButton button1, JButton button2, JButton button3, JButton button4,
                       JButton button5,Creditors creditors,JFrame frame) {

        this.button = button;
        this.button1 = button1;
        this.button2 = button2;
        this.button3 = button3;
        this.button4 = button4;
        this.button5 = button5;
        this.creditors = creditors;
        this.frame = frame;
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

    //effects: makes the transaction frame
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

    //effects:add actions listeners to the button
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

    //effects: add action listeners to the button
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

    //effects: list all the creditors
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

    // modifies:this
    //effects: adds creditor
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
                    JOptionPane.showMessageDialog(null,"Creditor removed successfully");
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

    //effects: adds action listener to record payment button
    private void recordPaymentHelper(JButton confirm, JTextField nameField, JTextField paymentAmountField) {
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Creditor creditor = Creditors.getCreditor(nameField.getText());
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


}
