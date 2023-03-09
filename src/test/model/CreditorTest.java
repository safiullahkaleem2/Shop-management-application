package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreditorTest {
    private Creditor creditor;
    private Creditor creditor2;
    private Bank bank;

    @BeforeEach
    void setUp() {
        creditor = new Creditor("Safi");
        Bank.getBank(2000);
        bank = Bank.getBank();
    }

    @Test
    void testAddOwed() {
        creditor.addOwed(100);
        assertEquals(100, creditor.getOwed());
    }

    @Test
    void testSubtractOwed() {
        creditor.addOwed(100);
        creditor.subtractOwed(50);
        assertEquals(50, creditor.getOwed());
    }

    @Test
    void testGetOwed() {
        creditor.addOwed(100);
        assertEquals(100, creditor.getOwed());
    }



    @Test
    void testGetName() {
        assertEquals("Safi", creditor.getName());
    }



    @Test
    void testSetName() {
        creditor.setName("Safiullah");
        assertEquals("Safiullah", creditor.getName());
    }
    @Test
    void testPaymentReceived() {
        creditor2 = new Creditor("Safiu");
        creditor2.addOwed(100);
        creditor2.paymentReceived(50);
        assertEquals(50, creditor2.getOwed());
    }
}

