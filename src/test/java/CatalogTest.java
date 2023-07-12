import lombok.extern.slf4j.Slf4j;
import org.example.impl.EmailServiceMock;
import org.example.model.Catalog;
import org.example.model.Customer;
import org.example.model.Item;
import org.example.model.PaymentGateway;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

@Slf4j
public class CatalogTest {

    private PaymentGateway paymentGateway;

    private EmailServiceMock emailServiceMock;

    @BeforeEach
    public void setUp() {
        // Create a new instance of PaymentPrompt and EmailServiceMock before each test
        paymentGateway = new PaymentGateway();
        emailServiceMock = new EmailServiceMock();
        paymentGateway.setEmailService(emailServiceMock);
    }

    @Test
    public void select_Item_From_Catalog_Return_Success() {
        Catalog catalog = new Catalog();

        catalog.addItem("Item 1", BigDecimal.valueOf(21.15), 1);
        catalog.addItem("Item 2", BigDecimal.valueOf(22.30), 1);
        catalog.addItem("Item 3", BigDecimal.valueOf(23.45), 1);

        // Select an item from the catalog
        String selectedItem = catalog.selectItem(0);

        // Check that the selected item matches the expected item
        assertEquals("Item 1", selectedItem);

        // Print expected and actual values
        System.out.println("Expected: Item 1");
        System.out.println("Actual  : " + selectedItem);
    }
    @Test
    public void select_Item_From_Catalog_Return_Not_Found() {
        Catalog catalog = new Catalog();

        catalog.addItem("Item 1", BigDecimal.valueOf(21.15), 1);
        catalog.addItem("Item 2", BigDecimal.valueOf(22.30), 1);
        catalog.addItem("Item 3", BigDecimal.valueOf(23.45), 1);

        String expected = "null";

        // Select an item from the catalog using an invalid index
        String invalidItem = catalog.selectItem(3);

        // Check that the returned item is null
        assertEquals(null, invalidItem);

        // Print expected and actual values
        System.out.println("Expected: " + expected);
        System.out.println("Actual  : " + invalidItem);
    }

    @Test
    public void add_To_Cart_Return_Success() {
        Catalog catalog = new Catalog();

        Item item = new Item("Item 1", 22.30, 1);

        // Add the item to the cart
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());


        // Check that the item was added to the cart
        assertTrue(catalog.containItems(item));

        // Print expected and actual values
        System.out.println("Expected: Item 1");
        System.out.println("Actual  : " + item.getName());
    }
    @Test
    public void add_To_Cart_Return_Bad_Request() {
        Catalog catalog = new Catalog();

        Item item = new Item("Item 1", 22.30, 1);

        // Add the item to the cart
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());

        // Check that the item was added to the cart
        assertTrue(catalog.containItems(item));

        // Simulate a bad request
        catalog.addItem(null, BigDecimal.valueOf(-10.0), 0);

        // Ensure that the invalid item was not added to the cart
        assertFalse(catalog.containItems(null));

        // Print expected and actual values
        System.out.println("Expected: Item 1");
        System.out.println("Actual  : " + item.getName());
    }
    @Test
    public void increase_Item_Quantity_Return_Success() {
        Catalog catalog = new Catalog();

        //Instantiate a sample item
        Item item = new Item("Item 3", 23.45, 1);

        // Add the item to the cart
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());

        catalog.increaseItemQuantity(item.getQty());

        // Check that the item quantity has been increased
        assertEquals(2, catalog.getItemQuantity(item.getQty()));

        // Print expected and actual values
        System.out.println("Expected:" +  2);
        System.out.println("Actual:" + catalog.getItemQuantity(item.getQty()));
    }
    @Test
    public void increase_Item_Quantity_Return_Not_Found() {
        Catalog catalog = new Catalog();

        Item item = new Item("Item 3", 23.45, 1);

        // Add the item to the catalog
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());

        boolean quantityIncreased = catalog.increaseItemQuantity(2);

        // Verify that the quantity was not increased because the item was not found
        assertFalse(quantityIncreased);

        // Print expected and actual values
        System.out.println("Expected: false");
        System.out.println("Actual: " + quantityIncreased);
    }
    @Test
    public void testDecreaseItemQuantity() {
        Catalog catalog = new Catalog();

        // Instantiate a sample item
        Item item = new Item("Item 3", 23.45, 2);

        // Add the item to the catalog
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());

        // Decrease the item quantity
        catalog.decreaseItemQuantity(item.getQty());

        // Get the updated item quantity from the catalog
        int updatedQuantity = catalog.getItemQuantity(item.getQty());

        // Check that the item quantity has been decreased to 1
        assertEquals(1, updatedQuantity);

        // Print expected and actual values
        System.out.println("Expected: 1");
        System.out.println("Actual  : " + updatedQuantity);
    }

    @Test
    public void testIncreaseItemQuantityWithSameItem() {
        Catalog catalog = new Catalog();

        catalog.addItem("Item 1", BigDecimal.valueOf(21.15), 2);
        catalog.addItem("Item 1", BigDecimal.valueOf(21.15), 2);

        // Assert that the item quantity has been increased in the cart
        assertEquals(4, catalog.getItemQuantityByName("Item 1"));

        // Print expected and actual values
        System.out.println("Expected: 4");
        System.out.println("Actual  : " + catalog.getItemQuantityByName("Item 1"));
    }

    @Test
    public void costPerItem() {

        Catalog catalog = new Catalog();

        Item item = new Item("Item 1", 22.30, 3);

        // Add the item to the cart
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());


       // double expected = 22.30 * 3;
        BigDecimal expected = catalog.calculateExpectedCost(item.getPrice(), item.getQty());
        BigDecimal actual = catalog.getItemTotal(item.getName());


        //double delta = 0.000001;
        assertEquals(expected, actual);

        System.out.println("Expected: " + expected);
        System.out.println("Actual  : " + catalog.getItemTotal("Item 1"));
    }

    @Test
    public void testTotalCostInSubtotal() {
        Catalog catalog = new Catalog();

        catalog.addItem("Item 1", new BigDecimal("30"), 2);
        catalog.addItem("Item 2", new BigDecimal("15"), 3);

        // Calculate the total cost in subtotal for Item 1
        BigDecimal subtotalItem1 = catalog.getItemTotal("Item 1");

        // Calculate the total cost in subtotal for Item 2
        BigDecimal subtotalItem2 = catalog.getItemTotal("Item 2");

        // Calculate the overall cost
        BigDecimal totalCost = catalog.calculateOverallCost(subtotalItem1, subtotalItem2);

        BigDecimal expected = new BigDecimal("105");

        assertEquals(0, expected.compareTo(totalCost));

        System.out.println("Expected: " + expected);
        System.out.println("Actual  : " + totalCost);
    }

    @Test
    public void testUniqueCartForEachCustomer() {
        // Create a list of customers
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Captain Jekka"));
        customers.add(new Customer("Captain Bubba"));
        customers.add(new Customer("Captain Iron Dog"));
        customers.add(new Customer("Captain Jeppo"));

        // Verify each customer has a unique cart
        boolean result = Customer.checkUniqueCarts(customers);
        System.out.println("Test result: " + result);
        assertTrue(result);
    }
    @Test
    public void testPromptMessage() {

        PaymentGateway paymentGateway = new PaymentGateway();

        String expectedPromptMessage = "Please proceed with the payment.";

        String actualPromptMessage = paymentGateway.getPaymentPromptMessage();

        assertEquals(expectedPromptMessage, actualPromptMessage);
    }
    @Test
    public void testIsPaymentSuccessful() {

        PaymentGateway paymentGateway = new PaymentGateway();

        boolean expectedPaymentSuccess = true;

        //email sent after payment is processed
        boolean actualPaymentSuccess = paymentGateway.processPayment();

        assertEquals(expectedPaymentSuccess, actualPaymentSuccess);
    }
}
