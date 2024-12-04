package controller;

import model.Assortment;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TableController{
    private static final String[] COLUMN_NAMES = {"ID", "Назва", "Кількість в наявності", "Ціна"};
    private static final String[] ORDER_COLUMN_NAMES = {"ID", "Назва", "Обрана кількість", "Ціна"};
    /**
     * Creates a DefaultTableModel with predefined column identifiers.
     * This is a utility method used to standardize the creation of table models across methods.
     *
     * @param type Selected type. 1 - for the table of available products 2 - for selected products.
     * @return A DefaultTableModel with columns set up for displaying product information.
     */
    private static DefaultTableModel createTableModel(int type) {
        DefaultTableModel tableModel = new DefaultTableModel();
        if(type == 1) {
            tableModel.setColumnIdentifiers(COLUMN_NAMES);
        } else if(type == 2) {
            tableModel.setColumnIdentifiers(ORDER_COLUMN_NAMES);
        } else throw new IllegalArgumentException();

        return tableModel;
    }


    /**
     * Adds a single product's data as a row to the specified table model.
     * This method abstracts the details of how a product's data is added to a table model, making it reusable.
     *
     * @param tableModel The table model to which the product data will be added.
     * @param product The product whose data is to be added.
     * @param amount The quantity of the product available in stock.
     */
    private static void addRowToTableModel(DefaultTableModel tableModel, Product product, Integer amount) {
        tableModel.addRow(new Object[]{
                product.getId(),
                product.getName(),
                amount,
                product.getPrice()
        });
    }


    /**
     * Loads all products and their quantities into a table model from a given assortment.
     * This method directly reflects all products in the assortment without any filtering.
     *
     * @param assortment The assortment from which products and quantities are to be loaded.
     * @param type Selected type. 1 - for the table of available products 2 - for selected products.
     * @return A DefaultTableModel populated with all products and their respective quantities.
     * @throws IllegalArgumentException if the provided assortment is null.
     */
    public static DefaultTableModel loadAssortment(Assortment assortment, int type) {
        if (assortment == null) {
            throw new IllegalArgumentException("Assortment must not be null.");
        }

        DefaultTableModel tableModel = createTableModel(type);
        assortment.getAssortment().forEach((product, amount) -> addRowToTableModel(tableModel, product, amount));

        return tableModel;

    }

    /**
     * Filters and loads products into a table model based on a search text.
     * Only products whose names contain the specified search text (case-insensitive) are included.
     *
     * @param searchText The text used to filter product names.
     * @param assortment The assortment from which to filter products.
     * @return A DefaultTableModel populated only with products that match the search criteria.
     * @throws IllegalArgumentException if the provided assortment is null.
     */
    public static DefaultTableModel filterTable(String searchText, Assortment assortment) {
        if (assortment == null) {
            throw new IllegalArgumentException("Assortment must not be null.");
        }

        DefaultTableModel tableModel = createTableModel(1);
        List<Product> filteredProducts = assortment.getAssortment().keySet().stream()
                .filter(product -> product.getName().toLowerCase().contains(searchText.toLowerCase()))
                .toList();

        filteredProducts.forEach(product -> addRowToTableModel(tableModel, product, assortment.getAssortment().get(product)));

        return tableModel;
    }

    /**
     * Deletes the selected row from the JTable and removes the corresponding product from the selected assortment.
     *
     * This method first checks that the provided JTable and selected assortment are not null.
     * It then retrieves the index of the selected row in the table and removes the product
     * corresponding to the value in the first column of that row from the selected assortment.
     * If the product ID in the first column is not a valid integer, a NumberFormatException is caught
     * and printed. Finally, the selected row is removed from the table model.
     *
     * @param table the JTable from which to delete the selected row
     * @param selectedAssortment the assortment from which to remove the selected product
     * @throws IllegalArgumentException if the provided table or selected assortment is null
     */
    public static void deleteSelectedRow(JTable table, Assortment selectedAssortment) {
        if (table == null) throw new IllegalArgumentException("Table cannot be null");
        if (selectedAssortment == null) throw new IllegalArgumentException("SelectedAssortment cannot be null");

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        int selectedRowIndex = table.getSelectedRow();

        if (selectedRowIndex != -1) {
            try {
               selectedAssortment.removeProductByProductID(Integer.parseInt(table.
                      getValueAt(selectedRowIndex, 0).toString()));
            } catch (NumberFormatException e) {
              System.out.println(e);
            }
            model.removeRow(selectedRowIndex);
        }
    }
}
