package controller;

import database.DatabaseUtils;
import model.Assortment;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

import static controller.TableController.filterTable;
import static controller.TableController.loadAssortment;

public class GoodListController {
    /**
     * Updates the available assortment table with the latest data from the database.
     *
     * This method retrieves the current available assortment from the database using the provided
     * connection and updates the specified JTable with the retrieved data. If a SQL exception occurs,
     * the error message is printed to the console.
     *
     * @param table the JTable to be updated with the available assortment data
     * @param connection the database connection used to retrieve the available assortment
     */
    static public void updateAvaibleAssortmentTable(JTable table, Connection connection) {
        try {
            Assortment availableAssortment = DatabaseUtils.loadAllAssortment(connection);
            table.setModel(loadAssortment(availableAssortment, 1));
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    /**
     * Displays a filtered assortment table based on the provided product name.
     *
     * This method retrieves the current available assortment from the database using the provided
     * connection and filters the assortment based on the provided product name. The filtered data
     * is then used to update the specified JTable. If a SQL exception occurs, the error message is
     * printed to the console.
     *
     * @param table the JTable to be updated with the filtered assortment data
     * @param productName the name of the product to filter the assortment by
     * @param connection the database connection used to retrieve the available assortment
     */
    static public void showFilteredAssortmentTable(JTable table, String productName, Connection connection) {
        try {
            Assortment availableAssortment = DatabaseUtils.loadAllAssortment(connection);
            table.setModel(filterTable(productName, availableAssortment));
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

}
