package controller.SelectProductControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import model.Assortment;
import model.Product;
import org.junit.jupiter.api.Test;

class FillAssortmentWithSelectedProductTest {

    @Test
    void testFillAssortmentWithValidProduct() {
        // Arrange
        Assortment assortment = new Assortment();
        Product product = new Product(1, "T-shirt", 15.99);

        // Act
        assortment.addProduct(product, 10); // Adding 10 T-shirts

        // Assert
        assertEquals(10, assortment.getAssortment().get(product), "Product quantity should be updated correctly");
    }

    @Test
    void testFillAssortmentWithZeroQuantity() {
        // Arrange
        Assortment assortment = new Assortment();
        Product product = new Product(2, "Jeans", 45.00);

        // Act
        assortment.addProduct(product, 0); // Adding 0 pairs of Jeans

        // Assert
        assertEquals(0, assortment.getAssortment().get(product), "Product quantity should be 0 when added as 0");
    }

    @Test
    void testFillAssortmentWithNegativeQuantity() {
        // Arrange
        Assortment assortment = new Assortment();
        Product product = new Product(3, "Jacket", 99.99);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            assortment.addProduct(product, -3);
        }, "Adding negative quantity should throw IllegalArgumentException");
    }

    @Test
    void testFillAssortmentWithDuplicateProduct() {
        // Arrange
        Assortment assortment = new Assortment();
        Product product = new Product(4, "Sweater", 25.50);

        assortment.addProduct(product, 5); // Adding 5 Sweaters initially

        // Act
        assortment.addProduct(product, 3); // Adding 3 more Sweaters

        // Assert
        assertEquals(8, assortment.getAssortment().get(product), "Product quantity should be the sum of both additions");
    }

    @Test
    void testFillAssortmentWithNullProduct() {
        // Arrange
        Assortment assortment = new Assortment();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            assortment.addProduct(null, 5);
        }, "Adding null product should throw IllegalArgumentException");
    }
}
