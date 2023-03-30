package ui;

import model.Bank;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
// This class is Bank UI class

public class BankUI {
    private Bank bank;

    public BankUI() {

        bank = Bank.getBank();
    }

    // MODIFIES: this
    // EFFECTS: directs to checking bank details

    public void checkBank() {
        if (Bank.getBank() == null) {
            Bank.getBank(0);
            bank = Bank.getBank();
        }
        JFrame barChartFrame = new JFrame("Bank Details");

        DefaultCategoryDataset bankData = new DefaultCategoryDataset();
        bankData.setValue(bank.getBalance(),"Amount","Balance");
        bankData.setValue(bank.getReceipts(),"Amount","Receipts");
        bankData.setValue(-1 * bank.getPayments(),"Amount","Payments");
        JFreeChart bankChart = ChartFactory.createBarChart("Bank Details", "Name",
                "Amount",
                bankData);
        // create the chart panel and add it to the frame
        ChartPanel chartPanel = new ChartPanel(bankChart);
        barChartFrame.add(chartPanel);

        // set the frame properties

        barChartFrame.setSize(500, 500);
        barChartFrame.setLocationRelativeTo(null);

        barChartFrame.setVisible(true);

    }



}
