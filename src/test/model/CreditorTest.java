package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditorTest {
    private Creditor creditor;

    @BeforeEach
    void setUp() {
        creditor = new Creditor("Safi");
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
    void testPaymentReceived() {
        creditor.addOwed(100);
        creditor.paymentReceived(50);
        assertEquals(50, creditor.getOwed());
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

}

