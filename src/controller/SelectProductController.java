package controller;

import model.Assortment;
import model.Product;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;


public class SelectProductController {

    /**
     * Fills the provided combo box with the names of products from the given assortment.
     *
     * This method retrieves the list of products from the provided assortment and populates
     * the combo box with the names of these products. It throws an exception if the assortment
     * or combo box is null.
     *
     * @param comboBox the combo box to be filled with product names
     * @param allAssortment the assortment containing the products
     * @throws IllegalArgumentException if the assortment or combo box is null
     */
    public static void fillComboBoxWithAssortment(JComboBox<Object> comboBox, Assortment allAssortment) {
        if (allAssortment == null) throw new IllegalArgumentException("Assortment is null");
        if (comboBox == null) throw new IllegalArgumentException("ComboBox is null");

        ArrayList<Product> productList = new ArrayList<>(allAssortment.getAssortment().keySet());

        for (Product product : productList) {
            comboBox.addItem(product.getName());
        }
    }

    /**
     * Fills the selected assortment with the specified amount of the selected product from the combo box.
     *
     * This method validates the input parameters, retrieves the selected product from the combo box, and
     * updates the selected assortment with the specified amount of the product. If the amount exceeds the
     * available quantity, an exception is thrown.
     *
     * @param comboBox the combo box containing the product names
     * @param amount the amount of the product to add to the selected assortment
     * @param allAssortment the assortment of all available products
     * @param selectedAssortment the assortment to be updated with the selected product
     * @return the updated selected assortment
     * @throws IllegalArgumentException if any input is invalid or the amount exceeds the available quantity
     */
    public static Assortment fillAssortmentWithSelectedProduct(JComboBox<Object> comboBox, int amount,
                                                               Assortment allAssortment, Assortment selectedAssortment) {
        if (comboBox == null || allAssortment == null || selectedAssortment == null || comboBox.getSelectedItem() == null) {
            throw new IllegalArgumentException("Invalid input");
        }
        if (amount < 1) throw new IllegalArgumentException("Некоректна кіл-сть");

        String selectedProductName = comboBox.getSelectedItem().toString();
        Map<Product, Integer> allProducts = allAssortment.getAssortment();
        Map<Product, Integer> selectedProducts = selectedAssortment.getAssortment();

        for (Map.Entry<Product, Integer> entry : allProducts.entrySet()) {
            Product product = entry.getKey();
            int availableAmount = entry.getValue();

            if (product.getName().equals(selectedProductName)) {
                if (availableAmount < amount) {
                    throw new IllegalArgumentException("Кількість товару у доступі: " + availableAmount);
                }

                int currentSelectedAmount = selectedProducts.getOrDefault(product, 0) + amount;
                if (availableAmount < currentSelectedAmount) {
                    throw new IllegalArgumentException("Загальна кількість товару у кошику більше доступної (" + availableAmount + ")");
                }
                selectedProducts.put(product, currentSelectedAmount);
                return selectedAssortment;
            }
        }
        throw new IllegalArgumentException("Ви не обрали товар");
    }
}
