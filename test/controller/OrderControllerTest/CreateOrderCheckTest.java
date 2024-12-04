package controller.OrderControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import controller.OrderController;
import model.Assortment;
import model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class CreateOrderCheckTest {

    @TempDir
    Path tempDir;

    @Test
    void testCreateOrderCheck_ValidInput() throws IOException {
        // Arrange
        Assortment assortment = new Assortment();
        Product product1 = new Product(1, "T-shirt", 15.99);
        Product product2 = new Product(2, "Jeans", 45.00);

        assortment.addProduct(product1, 3); // 3 units of T-shirts
        assortment.addProduct(product2, 2); // 2 units of Jeans

        String orderNumber = "20241127";
        String expectedFilePath = tempDir.resolve(orderNumber + ".txt").toString();

        // Act
        OrderController.createOrderCheck(assortment, orderNumber);

        // Assert
        File createdFile = new File("checks/" + orderNumber + ".txt");
        assertTrue(createdFile.exists(), "Order check file should be created");

        String fileContent = Files.readString(createdFile.toPath());
        assertTrue(fileContent.contains("T-shirt"), "File should contain product details");
        assertTrue(fileContent.contains("Jeans"), "File should contain product details");
        assertTrue(fileContent.contains("Всього: 137,97"), "File should contain the total price");

        // Cleanup
        Files.deleteIfExists(createdFile.toPath());
    }

    @Test
    void testCreateOrderCheck_NullAssortment() {
        // Arrange
        Assortment nullAssortment = null;
        String orderNumber = "20241127";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            OrderController.createOrderCheck(nullAssortment, orderNumber);
        }, "Null assortment should throw IllegalArgumentException");
    }

    @Test
    void testCreateOrderCheck_EmptyAssortment() {
        // Arrange
        Assortment emptyAssortment = new Assortment();
        String orderNumber = "20241127";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            OrderController.createOrderCheck(emptyAssortment, orderNumber);
        }, "Empty assortment should throw IllegalArgumentException");
    }

    @Test
    void testCreateOrderCheck_InvalidOrderNumber() {
        // Arrange
        Assortment assortment = new Assortment();
        Product product = new Product(1, "T-shirt", 15.99);
        assortment.addProduct(product, 3);

        String invalidOrderNumber = "!@#$%";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            OrderController.createOrderCheck(assortment, invalidOrderNumber);
        }, "Invalid order number should throw IllegalArgumentException");
    }
}
