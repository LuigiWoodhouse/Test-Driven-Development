import lombok.extern.slf4j.Slf4j;
import org.example.model.Catalog;
import org.example.model.Item;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class CatalogTest {

    @Test
    public void testSelectItemFromCatalog() {
        log.trace("Enter Method testSelectItemFromCatalog");

        Catalog catalog = new Catalog();

        catalog.addItem("Item 1", 21.15, 1);
        catalog.addItem("Item 2", 22.30, 1);
        catalog.addItem("Item 3", 23.45, 1);

        // Select an item from the catalog
        String selectedItem = catalog.selectItem(0);

        // Check that the selected item matches the expected item
        Assert.assertEquals("Item 1", selectedItem);

        log.info("Return Method testSelectItemFromCatalog:{}" , selectedItem);
    }

    @Test
    public void testAddToCart() {
        log.trace("Enter Method testAddToCart");

        Catalog catalog = new Catalog();

        Item item = new Item("Item 1", 22.30, 1);

        // Add the item to the cart
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());
        log.info("Enter Method testAddToCart: itemName={} itemPrice={}" , item.getName(),item.getPrice());

        // Check that the item was added to the cart
        Assert.assertTrue(catalog.containItems(item));
        log.info("Return Method testAddToCart: itemName={} result={}" , item.getName(),catalog.containItems(item));
    }

    @Test
    public void testIncreaseItemQuantity() {

        log.trace("Enter Method testIncreaseItemQuantity");
        Catalog catalog = new Catalog();

        //Instantiate a sample item
        Item item = new Item("Item 3", 23.45, 1);

        // Add the item to the cart
        catalog.addItem(item.getName(), item.getPrice(), item.getQty());
        log.info("Item is added to cart:{}", item.getName());

        catalog.increaseItemQuantity(item.getQty());
        log.info("Increase the qty of the item:{}", item.getQty());

        // Check that the item quantity has been increased
        Assert.assertEquals(2, catalog.getItemQuantity(item.getQty()));
        log.info("Return Method testIncreaseItemQuantity {}", catalog.getItemQuantity(item.getQty()));
    }

}