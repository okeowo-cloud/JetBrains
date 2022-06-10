package carsharing;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QueryService {

    public boolean createACar(int id, Scanner scanner) {
        boolean bool = false;
        try {
            Car car = new Car();
            car.setName(scanner.nextLine());
            Connection conn = InitDB.connect(InitDB.DBConfiguration.dbName);
            conn.setAutoCommit(true);
            String query = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?,?);";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, id);
            bool = preparedStatement.executeUpdate() > 0;
            conn.close();
            preparedStatement.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return bool;
    }

    public boolean createACompany(Scanner scanner) {
        Company company = new Company();
        boolean bool = false;
        company.setName(scanner.nextLine());
        try {
            Connection conn = InitDB.connect(InitDB.DBConfiguration.dbName);
            conn.setAutoCommit(true);
            if (viewAllCompanies().isEmpty()) {
                Statement statement = conn.createStatement();
                statement.execute("ALTER TABLE company ALTER COLUMN id RESTART WITH 1;");
            }
            String query = "INSERT INTO company (NAME) VALUES (?);";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, company.getName());
            bool = preparedStatement.executeUpdate() > 0;
            conn.close();
            preparedStatement.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return bool;
    }

    public boolean CreateACustomer(Scanner scanner){
        boolean bool = false;
        try {
            Customer customer = new Customer();
            customer.setName(scanner.nextLine());
            Connection conn = InitDB.connect(InitDB.DBConfiguration.dbName);
            conn.setAutoCommit(true);
            String query = "INSERT INTO CUSTOMER (NAME) VALUES (?);";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, customer.getName());
            bool = preparedStatement.executeUpdate() > 0;
            conn.close();
            preparedStatement.close();
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return bool;
    }

    public boolean rentACar(int car_id, int customer_id) {
        boolean bool = false;
        try {
            Connection conn = InitDB.connect(InitDB.DBConfiguration.dbName);
            conn.setAutoCommit(true);
            String query = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, car_id);
            preparedStatement.setInt(2, customer_id);
            bool = preparedStatement.executeUpdate() > 0;
            conn.close();
            preparedStatement.close();
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return bool;
    }

    public boolean returnACar(int customer_id, int car_id) {
        boolean bool = false;
        try {
            Connection conn = InitDB.connect(InitDB.DBConfiguration.dbName);
            conn.setAutoCommit(true);
            String query = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE RENTED_CAR_ID = ? and ID = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, car_id);
            preparedStatement.setInt(2, customer_id);
            bool = preparedStatement.executeUpdate() > 0;
            conn.close();
            preparedStatement.close();
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return bool;
    }

    public String selectACompany(int id) {
        final Company company = new Company();
        try {
            Connection conn = InitDB.connect(InitDB.DBConfiguration.dbName);
            conn.setAutoCommit(true);
            String query = "SELECT * FROM company WHERE ID = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                company.setId(rs.getInt(1));
                company.setName(rs.getString(2));
            }
            conn.close();
            preparedStatement.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return company.getName();
    }

    public List<Car> viewAllCars(int id) {
        List<Car> carList = new ArrayList<>();
        try {
            Connection conn = InitDB.connect(InitDB.DBConfiguration.dbName);
            conn.setAutoCommit(true);
            String query = "SELECT ID, NAME, COMPANY_ID FROM CAR WHERE COMPANY_ID = ? ORDER BY ID;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt(1));
                car.setName(rs.getString(2));
                car.setCompany_id(rs.getInt(3));
                carList.add(car);
            }
            conn.close();
            preparedStatement.close();
        } catch(SQLException se) {
            System.err.println(se.getMessage());
        }
        return carList;
    }

    public List<Company> viewAllCompanies() {
        List<Company> companyList = new ArrayList<>();
        try {
            Connection conn = InitDB.connect(InitDB.DBConfiguration.dbName);
            conn.setAutoCommit(true);
            String query = "SELECT ID, NAME FROM company ORDER BY ID;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Company company = new Company();
                company.setId(rs.getInt(1));
                company.setName(rs.getString(2));
                companyList.add(company);
            }
            conn.close();
            preparedStatement.close();
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return companyList;
    }

    public List<Customer> loginInAsCustomer() {
        List<Customer> customerList = new ArrayList<>();
        try {
            Connection conn = InitDB.connect(InitDB.DBConfiguration.dbName);
            conn.setAutoCommit(true);
            String query = "SELECT ID, NAME FROM CUSTOMER ORDER BY ID;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt(1));
                customer.setName(rs.getString(2));
                customerList.add(customer);
            }
            conn.close();
            preparedStatement.close();
        } catch (SQLException se) {
            System.out.println(se.getMessage());
        }
        return customerList;
    }

    public List<Car> getAvailableCar(int company_id) {
        List<Car> carList = new ArrayList<>();
        try {
            Connection conn = InitDB.connect(InitDB.DBConfiguration.dbName);
            conn.setAutoCommit(true);
            String query =
                    "SELECT CAR.id as id, CAR.name, CAR.company_id \n" +
                    "FROM CAR LEFT JOIN customer \n" +
                    "ON CAR.id = customer.rented_car_id \n" +
                    "WHERE customer.name IS NULL AND CAR.company_id = ? ORDER BY id;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, company_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getInt(1));
                car.setName(resultSet.getString(2));
                car.setCompany_id(resultSet.getInt(3));
                carList.add(car);
            }
        } catch(SQLException se) {
            System.err.println(se.getMessage());
        }
        return carList;
    }

    public int rentedCarByCustomer (int customer_id) {
        int result = 0;
        try {
            Connection conn = InitDB.connect(InitDB.DBConfiguration.dbName);
            conn.setAutoCommit(true);
            String query =
                   "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE ID = ? AND RENTED_CAR_ID IS NOT NULL;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, customer_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return result;
    }

    public Car selectACarById(int car_id) {
        Car car = new Car();
        try {
            Connection conn = InitDB.connect(InitDB.DBConfiguration.dbName);
            conn.setAutoCommit(true);
            String query = "SELECT ID, NAME, COMPANY_ID FROM CAR WHERE ID = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, car_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                car.setId(rs.getInt(1));
                car.setName(rs.getString(2));
                car.setCompany_id(rs.getInt(3));
            }
        } catch (SQLException se) {
            System.err.println(se.getMessage());
        }
        return car;
    }
}