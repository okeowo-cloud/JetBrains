package carsharing;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static final MenuService menuService = new MenuService();

    public static void main(String[] args) {

        try {
            String dbName = "carsharing";
            if (args.length > 1 && args[1].equals("-databaseFileName")) {
                dbName = args[2];
            }
            Connection conn = InitDB.connect(dbName);
            conn.setAutoCommit(true);
            Statement stmt = conn.createStatement();
            InitDB.createTableCompany(stmt, "company");
            InitDB.createTableCar(stmt, "CAR");
            InitDB.createTableCustomer(stmt, "CUSTOMER");
            menuService.mainMenu();
            stmt.close();
            conn.close();
        } catch(SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}