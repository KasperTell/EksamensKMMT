package DAL;

import BE.Customer;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO implements ICustomerDataAccess {

    private DatabaseConnector databaseConnector;

    public CustomerDAO() throws IOException {
        databaseConnector = DatabaseConnector.getInstance();
    }

    @Override
    public java.util.List<Customer> loadProjectManager() throws Exception {

        ArrayList<Customer> allProjectManager = new ArrayList<>();
        //SQL Query
        String sql = "SELECT * FROM Customer WHERE Role = 2";
        // getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                //Map DB to user object
                int id = rs.getInt("ID");
                String address = rs.getString("Address");
                int phonenumber = rs.getInt("Phonenumber");
                String companyname = rs.getString("CompanyName");
                int zipcode = rs.getInt("Zipcode");
                String mail = rs.getString("Mail");
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");
                int room = rs.getInt("Room");

                Customer customer = new Customer(id, address, phonenumber, companyname, zipcode, mail, firstName, lastName, room);

                allProjectManager.add(customer);
            }
            return allProjectManager;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failed to add Customer from the database", ex);
        }
    }

    @Override
    public java.util.List<Customer> loadCustomer(String companyname) throws Exception {
        ArrayList<Customer> allCustomer = new ArrayList<>();
        //SQL Query
        String sql = "SELECT * FROM Customer WHERE Companyname = ?";
        //Getting the connection to the database.
        try (java.sql.Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Setting the paramers and executing the statement.
            stmt.setString(1, companyname);
            ResultSet rs = stmt.executeQuery();

            // Loop through rows from the database result set
            while (rs.next()) {
                //Map DB row to user object
                int id = rs.getInt("ID");
                String address = rs.getString("Address");
                int phonenumber = rs.getInt("Phonenumber");
                companyname = rs.getString("CompanyName");
                int zipcode = rs.getInt("Zipcode");
                String mail = rs.getString("Mail");
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");
                int room = rs.getInt("Room");

                Customer customer = new Customer(id, address, phonenumber, companyname, zipcode, mail, firstName, lastName, room);

                allCustomer.add(customer);
            }
            return allCustomer;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not get EventKoordinator from database", ex);
        }
    }

    @Override
    public java.util.List<Customer> loadProjectManager(String companyname) throws Exception {
        return null;
    }

    @Override
    public Customer createNewCustomer(Customer customer) throws Exception {
        return null;
    }


    @Override
    public Customer createNewCustomer(String address, int phonenumber, String companyname, int zipcode, String mail, String firstName, String lastName, int room) throws Exception {
        //SQL Query
        String sql = "INSERT INTO Customer (address, phonenumber, companyname, zipcode, mail, firstName, lastName, room) VALUES(?,?,?,?,?,?,?,?)";
        //Getting the connection to the database
        try (java.sql.Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting the parameters and executing the query
            stmt.setString(1, address);
            stmt.setInt(2, phonenumber);
            stmt.setString(3, companyname);
            stmt.setInt(4, zipcode);
            stmt.setString(5, mail);
            stmt.setString(6, firstName);
            stmt.setString(7, lastName);
            stmt.setInt(8, room);
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            Customer customer = new Customer(id, address, phonenumber, companyname, zipcode, mail, firstName, lastName, room);
            return customer;
            }catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not create Customer", ex);
        }
    }
    @Override
    public void deleteCustomer(Customer selectedCustomer) throws  Exception {
        //SQL query
        String sql = "DELETE FROM Customer WHERE Customer_ID = ?";
        //Getting connection to the database.
        try(java.sql.Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameter and executing the query.
            stmt.setInt(1, selectedCustomer.getId());
            stmt.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not delete this event", ex);
        }
    }

    @Override
    public List loadProjectManagers() {
        return null;
    }


}
