import lombok.extern.slf4j.Slf4j;
import org.example.impl.EmailServiceMock;
import org.example.model.Catalog;
import org.example.model.Customer;
import org.example.model.Item;
import org.example.model.PaymentGateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;

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
    public void testSelectItemFromCatalog() {
        Catalog catalog = new Catalog();

        catalog.addItem("Item 1", 21.15, 1);
        catalog.addItem("Item 2", 22.30, 1);
        catalog.addItem("Item 3", 23.45, 1);

        // Select an item from the catalog
        String selectedItem = catalog.selectItem(0);

        // Check that the selected item matches the expected item
        Assert.assertEquals("Item 1", selectedItem);

        // Print expected and actual values
        System.out.println("Expected: Item 1");
        System.out.println("Actual  : " + selectedItem);
    }


    @Test
    public void testAddToCart() {
        Catalog catalog = new Catalog();

        Item item = new Item("Item 1", 22.30, 1);

        // Add the item to the cart
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());


        // Check that the item was added to the cart
        Assert.assertTrue(catalog.containItems(item));

        // Print expected and actual values
        System.out.println("Expected: Item 1");
        System.out.println("Actual  : " + item.getName());
    }

    @Test
    public void testIncreaseItemQuantity() {
        Catalog catalog = new Catalog();

        //Instantiate a sample item
        Item item = new Item("Item 3", 23.45, 1);

        // Add the item to the cart
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());

        catalog.increaseItemQuantity(item.getQty());

        // Check that the item quantity has been increased
        Assert.assertEquals(2, catalog.getItemQuantity(item.getQty()));

        // Print expected and actual values
        System.out.println("Expected:" +  2);
        System.out.println("Actual:" + catalog.getItemQuantity(item.getQty()));
    }

    @Test
    public void testDecreaseItemQuantity() {
        Catalog catalog = new Catalog();

        // Instantiate a sample item
        Item item = new Item("Item 3", 23.45, 3);

        // Add the item to the catalog
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());

        // Decrease the item quantity
        catalog.decreaseItemQuantity(item.getQty());

        // Get the updated item quantity from the catalog
        int updatedQuantity = catalog.getItemQuantity(item.getQty());

        // Check that the item quantity has been decreased to 2
        Assert.assertEquals(2, updatedQuantity);

        // Print expected and actual values
        System.out.println("Expected: 2");
        System.out.println("Actual  : " + updatedQuantity);
    }






    @Test
    public void testIncreaseItemQuantityWithSameItem() {
        Catalog catalog = new Catalog();

        // Create a sample item
        Item item = new Item("Item 2", 22.30, 1);

        // Add the item to the cart
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());

        // Add the same item to the cart again
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());

        // Assert that the item quantity has been increased in the cart
        Assert.assertEquals(2, catalog.getItemQuantityByName("Item 2"));

    }

    @Test
    public void costPerItem() {

        Catalog catalog = new Catalog();

        Item item = new Item("Item 1", 22.30, 1);

        // Add the item to the cart
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());


        // Add the item to the cart
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());


        // Use assertEquals(expected, actual, delta) to compare floating-point numbers
        double expected = 22.30 * 2;
        double actual = catalog.getItemTotal("Item 1");
        double delta = 0.000001;
        Assert.assertEquals(expected, actual, delta);
    }

    @Test
    public void testTotalCostInSubtotal() {

        Catalog catalog = new Catalog();

        Item item1 = new Item("Item 1", 22.30, 1);

        Item item2 = new Item("Item 2", 22.45, 1);

        // Add the item to the cart
        catalog.addItem(item1.getName(), item1.getPrice(), item1.getQty());


        // Add the item to the cart
        catalog.addItem(item1.getName(), item1.getPrice(), item1.getQty());


        // Add the item to the cart
        catalog.addItem(item2.getName(), item2.getPrice(), item2.getQty());


        // Calculate the total cost in subtotal
        double subtotal = 0.0;
        for (Item item : catalog.getItems()) {
            subtotal += item.getPrice();
        }

        // Check that the total cost in subtotal is correct
        double expected = 22.30 * 2 + 22.45;
        double actual = subtotal;
        double delta = 0.000001;
        Assert.assertEquals(expected, actual, delta);

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
        Set<Catalog> uniqueCarts = new HashSet<>();
        for (Customer customer : customers) {
            Catalog catalog = customer.getCatalog();
            // Check if the cart is already in the set of unique carts
            assertFalse(uniqueCarts.contains(customer));
            uniqueCarts.add(catalog);
        }

    }


    @Test
    public void testPromptMessage() {

        PaymentGateway paymentGateway = new PaymentGateway();

        String expectedPromptMessage = "Please proceed with the payment.";

        String actualPromptMessage = paymentGateway.getPaymentPromptMessage();

        Assert.assertEquals(expectedPromptMessage, actualPromptMessage);
    }

    @Test
    public void testIsPaymentSuccessful() {

        PaymentGateway paymentGateway = new PaymentGateway();

        boolean expectedPaymentSuccess = true;

        //email sent after payment is processed
        boolean actualPaymentSuccess = paymentGateway.processPayment();

        Assert.assertEquals(expectedPaymentSuccess, actualPaymentSuccess);
    }
}