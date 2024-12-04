package model;

import java.util.HashMap;
import java.util.Map;

public class Assortment {
    private Map<Product, Integer> productList;

    /**
     * Constructs a new Assortment instance with an empty product list.
     */
    public Assortment() {
        productList = new HashMap<Product, Integer>();
    }

    /**
     * Adds a specified amount of a product to the assortment.
     * If the product already exists in the assortment, the specified amount
     * is added to the existing amount. If the product does not exist,
     * it is added with the specified amount.
     *
     * @param product The product to be added or updated in the assortment.
     * @param amount  The amount of the product to be added.
     * @throws NullPointerException if the provided product is null.
     * @throws IllegalArgumentException if the provided amount is negative.
     */
    public void addProduct(Product product, int amount) {
        if (product == null) throw new IllegalArgumentException("Product is null");
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");

        if (productList.containsKey(product)) {
            productList.put(product, productList.get(product) + amount);
            return;
        }

        productList.put(product, amount);
    }

    /**
     * Checks if the assortment is empty.
     *
     * @return true if there are no products in the assortment, false otherwise.
     */
    public boolean isEmpty() {
        return productList.isEmpty();
    }

    /**
     * Returns a string representation of the assortment, listing all products
     * and their quantities.
     *
     * @return A string representation of the assortment.
     */
    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder("Assortment:\n");

        for (Map.Entry<Product, Integer> entry : productList.entrySet()) {
            Product product = entry.getKey();
            Integer amount = entry.getValue();
            resultString.append(product.toString()).append("amount: ").append(amount).append("\n-----------\n");
        }
        return resultString.toString();
    }

    /**
     * Retrieves the current assortment of products and their quantities.
     * This method returns a map where each key is a {@link Product} object and each value is an integer
     * representing the quantity of that product in the assortment.
     *
     * @return A {@link Map} of {@link Product} to {@link Integer} mapping each product to its quantity.
     */
    public Map<Product, Integer> getAssortment() {
        return productList;
    }

    /**
     * Removes the product with the specified ID from the assortment.
     *
     * @param productID the ID of the product to be removed
     */
    public void removeProductByProductID(int productID) {
        for (Product product : productList.keySet()) {
            if (product.getId() == productID) {
                productList.remove(product);
                System.out.println("Product with ID " + productID + " has been removed from the selected assortment.");
                return;
            }
        }
        System.out.println("Product with ID " + productID + " not found in the assortment.");
    }
}
