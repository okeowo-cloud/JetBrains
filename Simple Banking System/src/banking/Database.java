package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    public void createNewDatabase(String fileName) {
        try (Connection conn = this.connect(fileName)) {
            if (conn.isValid(5)) {
                //System.out.println("Connection with database was established");
                //System.out.println("A new database has been created");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection connect(String fileName) {
        String url = "jdbc:sqlite:" + fileName;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewTable(String file) {
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	number TEXT,\n"
                + "	pin TEXT,\n"
                + " balance INTEGER DEFAULT 0 \n"
                + ");";
        try (Connection conn = this.connect(file)){
            Statement statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertIntoTable(String fileName, String number, String pin, int balance) {
        String sql = "INSERT INTO card (id, number, pin, balance) VALUES (?,?,?,?)";
        try (Connection conn = this.connect(fileName)) {
            if(conn.isValid(5)){
                PreparedStatement prpstmt = conn.prepareStatement(sql);
                prpstmt.setString(2, number);
                prpstmt.setString(3, pin);
                prpstmt.setInt(4, balance);
                prpstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
