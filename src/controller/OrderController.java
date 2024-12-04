package controller;

import model.Assortment;
import model.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class OrderController {
    /**
     * Calculates the total price of the selected assortment.
     *
     * This method iterates through the products in the selected assortment, calculates the total price
     * based on the quantity and price of each product, and returns the formatted total price as a string.
     *
     * @param selectedAssortment the assortment of selected products
     * @return the formatted total price of the selected assortment
     */
    public static String getOrderPrice(Assortment selectedAssortment){
        ArrayList<Product> productArrayList = new ArrayList<>(selectedAssortment.getAssortment().keySet());

        double totalPrice = 0;

        for(Product product: productArrayList){
            totalPrice += product.getPrice() * selectedAssortment.getAssortment().get(product);
        }
        String formattedSummary = String.format("%.2f", totalPrice);

        return formattedSummary;
    }

    /**
     * Generates a unique order number based on the current date and time.
     *
     * This method retrieves the current date and time, formats it as a string in the pattern "yyyyMMddHHmmss",
     * and returns the formatted string as the order number.
     *
     * @return a unique order number based on the current date and time
     */
    public static String getOrderNumber() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        return formatter.format(now);
    }

    /**
     * Creates an order check file for the selected assortment.
     *
     * This method validates the order number and selected assortment, generates a file path based on the order number,
     * writes the order details to a file, and handles any IO exceptions that may occur.
     *
     * @param selectedAssortment the assortment of selected products
     * @param orderNumber the order number to use for the file name
     * @throws IllegalArgumentException if the selected assortment is null or the order number is invalid
     */
    public static void createOrderCheck(Assortment selectedAssortment, String orderNumber) {
        validateOrderNumber(orderNumber);
        if (selectedAssortment.isEmpty()) throw new IllegalArgumentException("Selected assortment is empty");
        if (selectedAssortment == null) throw new IllegalArgumentException("Assortment cannot be null");

        String numberOnly = orderNumber.replaceAll("[^0-9]", "");
        if (numberOnly.isEmpty()) throw new IllegalArgumentException("Order number contains no digits");

        String filePath = "checks/"+ numberOnly + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writeOrderDetails(selectedAssortment, writer, orderNumber);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Validates the order number.
     *
     * This method checks if the order number is null or empty and throws an IllegalArgumentException if it is.
     *
     * @param orderNumber the order number to validate
     * @throws IllegalArgumentException if the order number is null or empty
     */
    private static void validateOrderNumber(String orderNumber) {
        if (orderNumber == null || orderNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Order number cannot be null or empty");
        }
    }

    /**
     * Writes the order details to the provided BufferedWriter.
     *
     * This method writes the order number, product details, and total price of the selected assortment to the provided writer.
     *
     * @param selectedAssortment the assortment of selected products
     * @param writer the BufferedWriter to write the order details to
     * @param orderNumber the order number to include in the order details
     * @throws IOException if an I/O error occurs
     */
    private static void writeOrderDetails(Assortment selectedAssortment, BufferedWriter writer, String orderNumber) throws IOException {
        writer.write(orderNumber + "\n");
        writer.newLine();

        for (Map.Entry<Product, Integer> entry : selectedAssortment.getAssortment().entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            writer.write(product.getName() + "\n" + quantity + " x " + product.getPrice() + "\n----------------------\n");
        }

        String total = getOrderPrice(selectedAssortment);
        writer.write("Всього: " + total + "\n");
        System.out.println("Чек: " + orderNumber + " записано в файл");
    }

}
