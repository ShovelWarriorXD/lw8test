package controller.OrderControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import controller.OrderController;
import model.Assortment;
import model.Product;
import org.junit.jupiter.api.Test;

class GetOrderPriceTest {

    @Test
    void testGetOrderPrice_ValidAssortment() {
        // Arrange
        Product product1 = new Product(1, "T-shirt", 15.99); // 15.99 per T-shirt
        Product product2 = new Product(2, "Jeans", 45.00);   // 45.00 per Jeans

        Assortment assortment = new Assortment();
        assortment.addProduct(product1, 3); // 3 T-shirts
        assortment.addProduct(product2, 2); // 2 pairs of Jeans

        // Act
        String result = OrderController.getOrderPrice(assortment);

        // Assert
        assertEquals("147,97", result, "Total price should be correctly calculated and formatted");
    }

    @Test
    void testGetOrderPrice_EmptyAssortment() {
        // Arrange
        Assortment emptyAssortment = new Assortment();

        // Act
        String result = OrderController.getOrderPrice(emptyAssortment);

        // Assert
        assertEquals("0,00", result, "Total price for an empty assortment should be 0.00");
    }

    @Test
    void testGetOrderPrice_NullAssortment() {
        // Arrange
        Assortment nullAssortment = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            OrderController.getOrderPrice(nullAssortment);
        }, "Null assortment should throw a NullPointerException");
    }
}
