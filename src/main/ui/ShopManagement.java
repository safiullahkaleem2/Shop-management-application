package ui;

import model.*;
import model.exceptions.InsufficientBalanceException;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
// Shop Management application

public class ShopManagement {
    private static final String JSON_STORE = "./data/inventory.json";
    private static final String JSON_STORE2 = "./data/creditors.json";
    private static final String JSON_STORE3 = "./data/bank.json";
    private Creditors creditors;
    private Creditor creditor;
    private CashSales cashSales;
    private CreditSales creditSales;
    private Bank bank;
    private Inventory inventory;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner input;


    // EFFECTS: runs the teller application
    public ShopManagement() throws FileNotFoundException {
        runShopManagement();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runShopManagement() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
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
        System.out.println("Do you want to load a previously saved file ");
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
        System.out.println("Select from:");
        System.out.println("\tb -> Check balance");
        System.out.println("\tr -> Check receipts");
        System.out.println("\tp -> Check payments");

        String selection = input.next();
        selection = selection.toLowerCase();

        switch (selection) {
            case "b":
                System.out.println("Balance: " + bank.getBalance());
                break;
            case "r":
                System.out.println("Receipts: " + bank.getReceipts());
                break;
            case "p":
                System.out.println("Payments: " + bank.getPayments());
                break;
            default:
                System.out.println("Selection not valid...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: directs to changing inventory details
    private void manageInventory() {
        String command;
        while (true) {
            inventoryMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                return;
            } else if (command.equals("1")) {
                addItemsToInventory();
            } else if (command.equals("2")) {
                removeItemFromInventory();
            } else if (command.equals("3")) {
                thresholdCheck();
            } else if (command.equals("4")) {
                allInventoryItems();
            } else {
                System.out.println("Selection not valid...");
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: adds item to inventory
    private void addItemsToInventory() {
        System.out.print("Enter item name: ");
        String name = input.next();
        name = name.toLowerCase();

        if (inventory.isItemPresent(name)) {
            System.out.println("Item with name " + name + " already exists in inventory.");
            return;
        }
        System.out.print("Enter quantity: ");
        int quantity = input.nextInt();

        System.out.print("Enter unit: ");
        String unit = input.next();
        unit = unit.toLowerCase();

        System.out.print("Enter threshold: ");
        int threshold = input.nextInt();

        System.out.print("Enter price: ");
        double price = input.nextDouble();

        try {
            if (inventory.boughtNewItem(name, quantity, unit, threshold, price)) {
                System.out.println("Item added successfully");
            }
        } catch (InsufficientBalanceException e) {
            System.out.println("Unable to add item to inventory. Insufficient balance.");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes item from inventory
    private void removeItemFromInventory() {
        System.out.print("Enter item name: ");
        String name = input.next();
        name = name.toLowerCase();

        if (!inventory.isItemPresent(name)) {
            System.out.println("Item with name " + name + " does not exist in inventory.");
            return;
        }


        if (inventory.removeItem(name)) {
            System.out.println("Item removed successfully");
        } else {
            System.out.println("Unable to remove item from inventory");


        }
    }

    // MODIFIES:
    // EFFECTS: checks threshold
    private void thresholdCheck() {
        System.out.print("Enter item name: ");
        String name = input.next();
        name = name.toLowerCase();


        if (!inventory.isItemPresent(name)) {
            System.out.println("Item with name " + name + " does not exist in inventory.");
            return;
        }
        Item item = inventory.giveItem(name);
        if (item != null) {
            if (item.getQuantity() < item.getThreshold()) {
                System.out.println("Quantity for " + name + " is lower than the threshold" + item.getThreshold());
            } else {
                System.out.println("Quantity for " + name + " is higher than the threshold " + item.getThreshold());
            }

        } else {
            System.out.println("Cannot find the item. Please check your spelling.");
        }

    }

    // EFFECTS: prints list of all inventory items
    private void allInventoryItems() {
        System.out.println("Inventory:");
        for (int n = 0;n < Inventory.getInventory().length(); n++) {
            Item item = inventory.get(n);
            System.out.println(item.getItemName() + ": " + item.getQuantity() + item.getUnit());
        }
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
            jsonWriter = new JsonWriter("./data/inventory" + name + ".json","./data/creditors" + name + ".json","./data/bank" + name + ".json");
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
            jsonReader = new JsonReader("./data/inventory" + name + ".json","./data/creditors" + name + ".json","./data/bank" + name + ".json");
            inventory = jsonReader.read();
            creditors = jsonReader.readC();
            bank = jsonReader.readB();
            System.out.println("Loaded Inventory and Creditors from " + name);
        } catch (IOException e) {
            System.out.println("Unable to read from file of " + name);
        }
    }


}
