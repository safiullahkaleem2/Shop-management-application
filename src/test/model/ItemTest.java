package model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

    public class ItemTest {
        private Item item;
        private Item item2;

        public ItemTest(){
            item = new Item("Book", 5, "piece", 2);
            item2 = new Item("Apples", 5, "kgs", 10);
        }


        @Test
         void testGetItemName() {
            assertEquals("Book", item.getItemName());
        }

        @Test
        void testGetQuantity() {
            assertEquals(5, item.getQuantity());
        }

        @Test
        void testGetUnit() {
            assertEquals("piece", item.getUnit());
        }


        @Test
        void testSetItemName() {
            item.setItemName("Pen");
            assertEquals("Pen", item.getItemName());
        }

        @Test
         void testSetQuantity() {
            item.setQuantity(10);
            assertEquals(10, item.getQuantity());
        }


        @Test
        void testGetThreshold() {
            assertEquals(2, item.getThreshold());
        }

        @Test
        void testSetThreshold() {
            item.setThreshold(3);
            assertEquals(3, item.getThreshold());
        }

        @Test
        void testBelowThreshold() {
            assertFalse(item.belowThreshold());
        }

        @Test
        void testIncreaseQuantity() {
            item.increaseQuantity(2);
            assertEquals(7, item.getQuantity());
        }

        @Test
        void testDecreaseQuantity() {
            item.decreaseQuantity(2);
            assertEquals(3, item.getQuantity());
        }


    }


