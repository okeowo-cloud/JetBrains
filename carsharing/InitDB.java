package carsharing;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDB {

    private static class Queries {

        final private static String CREATE_COMPANY =
                "CREATE TABLE IF NOT EXISTS %s (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(%d) NOT NULL, " +
                "UNIQUE(NAME)" +
                ");";

        final private static String CREATE_CAR =
                "CREATE TABLE IF NOT EXISTS %s (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(50) NOT NULL UNIQUE, " +
                "COMPANY_ID INT NOT NULL, " +
                "CONSTRAINT fk_company_id FOREIGN KEY (COMPANY_ID) " +
                "REFERENCES company (ID) ON DELETE CASCADE ON UPDATE SET NULL);";

        final private static String CREATE_CUSTOMER =
                "CREATE TABLE IF NOT EXISTS %s (" +
                "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                "NAME VARCHAR(50) NOT NULL UNIQUE, " +
                "RENTED_CAR_ID INT, " +
                "CONSTRAINT fk_Rented_Car_Id FOREIGN KEY (RENTED_CAR_ID) " +
                "REFERENCES CAR (ID) ON DELETE CASCADE);";

        static String createTableCustomer(String tableName) {
            return String.format(CREATE_CUSTOMER, tableName);
        }

        static String createTableCompany(String tableName) {
        return String.format(CREATE_COMPANY, tableName, DBConfiguration.companyNameMaxLength);
        }

        static String createTableCar(String tableName) {
            return String.format(CREATE_CAR, tableName);
        }
    }

    public static class DBConfiguration {

        final static String dbLocation = "./src/carsharing/db";
        final static String dbUrl = "jdbc:h2:" + dbLocation + "/%s";
        final static String dbName = "carsharing";
        final static int companyNameMaxLength = 62;

    }

   public static void createTableCompany(Statement stmt, String tableName) throws SQLException {
        stmt.execute(Queries.createTableCompany(tableName));
    }

    public static void createTableCar(Statement stmt, String tableName) throws Exception {
        stmt.execute(Queries.createTableCar(tableName));
    }

    public static void createTableCustomer(Statement stmt, String tableName) throws SQLException {
        stmt.execute(Queries.createTableCustomer(tableName));
    }

    public static Connection connect(String databaseName) throws SQLException {
        File dir = new File(DBConfiguration.dbLocation);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return DriverManager.getConnection(
                String.format(DBConfiguration.dbUrl,
                        !databaseName.isBlank() ?
                                databaseName :
                                DBConfiguration.dbName)
        );
    }
}