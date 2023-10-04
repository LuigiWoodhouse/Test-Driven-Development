package org.example.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.ItemNotFoundException;
import org.example.model.Customer;
import org.example.model.Item;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@Slf4j
public class CatalogServiceImplTest {
    CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();
    @Test
    public void select_Item_From_Catalog_Return_Success() {


        catalogServiceImpl.addItem("Item 1", BigDecimal.valueOf(21.15), 1);
        catalogServiceImpl.addItem("Item 2", BigDecimal.valueOf(22.30), 1);
        catalogServiceImpl.addItem("Item 3", BigDecimal.valueOf(23.45), 1);

        // Select an item from the catalog
        String selectedItem = catalogServiceImpl.selectItem(0);

        // Check that the selected item matches the expected item
        assertEquals("Item 1", selectedItem);

        // Print expected and actual values
        System.out.println("Expected: Item 1");
        System.out.println("Actual  : " + selectedItem);
    }
    @Test
    public void select_Item_From_Catalog_Return_Not_Found() {
        CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();

        catalogServiceImpl.addItem("Item 1", BigDecimal.valueOf(21.15), 1);
        catalogServiceImpl.addItem("Item 2", BigDecimal.valueOf(22.30), 1);
        catalogServiceImpl.addItem("Item 3", BigDecimal.valueOf(23.45), 1);

        String expected = "null";

        // Select an item from the catalog using an invalid index
        String invalidItem = catalogServiceImpl.selectItem(3);

        // Check that the returned item is null
        assertEquals(null, invalidItem);

        // Print expected and actual values
        System.out.println("Expected: " + expected);
        System.out.println("Actual  : " + invalidItem);
    }

    @Test
    public void add_To_Cart_Return_Success() {
        CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();

        Item item = new Item("Item 1", 22.30, 1);

        // Add the item to the cart
        catalogServiceImpl.addItem(item.getName(), item.getPrice(), item.getQty());


        // Check that the item was added to the cart
        assertTrue(catalogServiceImpl.containItems(item));

        // Print expected and actual values
        System.out.println("Expected: Item 1");
        System.out.println("Actual  : " + item.getName());
    }
    @Test
    public void add_To_Cart_Return_Bad_Request() {
        CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();

        Item item = new Item("Item 1", 22.30, 1);

        // Add the item to the cart
        catalogServiceImpl.addItem(item.getName(), item.getPrice(), item.getQty());

        // Check that the item was added to the cart
        assertTrue(catalogServiceImpl.containItems(item));

        // Simulate a bad request
        catalogServiceImpl.addItem(null, BigDecimal.valueOf(-10.0), 0);

        // Ensure that the invalid item was not added to the cart
        assertFalse(catalogServiceImpl.containItems(null));

        // Print expected and actual values
        System.out.println("Expected: Item 1");
        System.out.println("Actual  : " + item.getName());
    }
    @Test
    public void increase_Item_Quantity_Return_Success() {
        CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();

        //Instantiate a sample item
        Item item = new Item("Item 3", 23.45, 1);

        // Add the item to the cart
        catalogServiceImpl.addItem(item.getName(), item.getPrice(), item.getQty());

        catalogServiceImpl.increaseItemQuantity(item.getQty());

        // Check that the item quantity has been increased
        assertEquals(2, catalogServiceImpl.getItemQuantity(item.getQty()));

        // Print expected and actual values
        System.out.println("Expected:" +  2);
        System.out.println("Actual:" + catalogServiceImpl.getItemQuantity(item.getQty()));
    }
    @Test
    public void increase_Item_Quantity_Return_Not_Found() {
        CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();

        Item item = new Item("Item 3", 23.45, 1);

        // Add the item to the catalog
        catalogServiceImpl.addItem(item.getName(), item.getPrice(), item.getQty());

        boolean quantityIncreased = catalogServiceImpl.increaseItemQuantity(2);

        // Verify that the quantity was not increased because the item was not found
        assertFalse(quantityIncreased);

        // Print expected and actual values
        System.out.println("Expected: false");
        System.out.println("Actual: " + quantityIncreased);
    }
    @Test
    public void decrease_Item_Quantity_Return_Success() {
        CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();

        // Instantiate a sample item
        Item item = new Item("Item 3", 23.45, 2);

        // Add the item to the catalog
        catalogServiceImpl.addItem(item.getName(), item.getPrice(), item.getQty());

        // Decrease the item quantity
        catalogServiceImpl.decreaseItemQuantity(1);

        // Get the updated item quantity from the catalog
        //int updatedQuantity = catalog.getItemQuantity(item.getQty());

        // Check that the item quantity has been decreased to 1
        assertEquals(1,  catalogServiceImpl.getItemQuantity(item.getQty()));

        // Print expected and actual values
        System.out.println("Expected: 1");
        System.out.println("Actual  : " +  catalogServiceImpl.getItemQuantity(item.getQty()));
    }

    @Test
    public void decrease_Item_Quantity_Return_Not_Found() {
        CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();

        // Instantiate a sample item
        Item item = new Item("Item 3", 23.45, 2);

        // Add the item to the catalog
        catalogServiceImpl.addItem(item.getName(), item.getPrice(), item.getQty());


        boolean quantityDecreased = catalogServiceImpl.decreaseItemQuantity(1);

        // Get the updated item quantity from the catalog
        //int updatedQuantity = catalog.getItemQuantity(item.getQty());

        // Check that the item quantity has been decreased to 1
        assertFalse(quantityDecreased);

        // Print expected and actual values
        System.out.println("Expected: false");
        System.out.println("Actual  : " +  quantityDecreased);
    }

    @Test
    public void testIncreaseItemQuantityWithSameItem() {
        CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();

        catalogServiceImpl.addItem("Item 1", BigDecimal.valueOf(21.15), 2);
        catalogServiceImpl.addItem("Item 1", BigDecimal.valueOf(21.15), 2);

        // Assert that the item quantity has been increased in the cart
        assertEquals(4, catalogServiceImpl.getItemQuantityByName("Item 1"));

        // Print expected and actual values
        System.out.println("Expected: 4");
        System.out.println("Actual  : " + catalogServiceImpl.getItemQuantityByName("Item 1"));
    }

    @Test
    public void cost_Per_Item_Return_Success() throws ItemNotFoundException {

        CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();

        Item item = new Item("Item 1", 22.30, 3);

        // Add the item to the cart
        catalogServiceImpl.addItem(item.getName(), item.getPrice(), item.getQty());


       // double expected = 22.30 * 3;
        BigDecimal expected = catalogServiceImpl.calculateExpectedCost(item.getPrice(), item.getQty());
        BigDecimal actual = catalogServiceImpl.getItemTotal(item.getName());


        //double delta = 0.000001;
        assertEquals(expected, actual);

        System.out.println("Expected: " + expected);
        System.out.println("Actual  : " + catalogServiceImpl.getItemTotal("Item 1"));
    }

    @Test
    public void cost_Per_Item_Return_Not_Found() {
        CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();
        Item item = new Item("Item 1", 22.30, 3);

        // Add a different item to the cart
        catalogServiceImpl.addItem("Item 2", BigDecimal.valueOf(10.50), 2);

        // Test for nonexistent item
        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> {
            catalogServiceImpl.getItemTotal(item.getName());
        });

        assertEquals(404, exception.getCode());

        System.out.println("Expected=404");
        System.out.println("Actual =" + exception.getCode());
    }


    @Test
    public void testTotalCostInSubtotal() throws ItemNotFoundException {
        CatalogServiceImpl catalogServiceImpl = new CatalogServiceImpl();

        catalogServiceImpl.addItem("Item 1", new BigDecimal("30"), 2);
        catalogServiceImpl.addItem("Item 2", new BigDecimal("15"), 3);

        // Calculate the total cost in subtotal for Item 1
        BigDecimal subtotalItem1 = catalogServiceImpl.getItemTotal("Item 1");

        // Calculate the total cost in subtotal for Item 2
        BigDecimal subtotalItem2 = catalogServiceImpl.getItemTotal("Item 2");

        // Calculate the overall cost
        BigDecimal totalCost = catalogServiceImpl.calculateOverallCost(subtotalItem1, subtotalItem2);

        BigDecimal expected = new BigDecimal("105");

        assertEquals(0, expected.compareTo(totalCost));

        System.out.println("Expected: " + expected);
        System.out.println("Actual  : " + totalCost);
    }
    @Test
    public void testUniqueCartForEachCustomer() {
        // Create a list of customers with unique carts
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("cart1"));  // Ensure unique cart identifiers for each customer
        customers.add(new Customer("cart2"));
        customers.add(new Customer("cart3"));
        customers.add(new Customer("cart4"));

        // Verify each customer has a unique cart
        boolean result = CatalogServiceImpl.checkUniqueCarts(customers);
        System.out.println("Test result: " + result);
        assertTrue(result);
    }

}