package DAL;

import BE.Customer;

import java.awt.*;
import java.io.IOException;
import java.sql.*;

public class CustomerDAO implements ICustomerDataAccess {

    private DatabaseConnector databaseConnector;

    public CustomerDAO() throws IOException {
        databaseConnector = DatabaseConnector.getInstance();
    }

   @Override
    public Customer loadCustomer(int customerID) throws Exception {

        //SQL Query
        String sql = "SELECT * FROM Customers WHERE ID = ?";
        // getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();

            Customer customer = null;
            while (rs.next()) {
                //Map DB to user object
                int id = rs.getInt("ID");
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");
                String companyname = rs.getString("Company_Name");
                String address = rs.getString("Address");
                int zipcode = rs.getInt("Zip_Code");
                String mail = rs.getString("Mail");
                int phonenumber = rs.getInt("Phone_Number");


                customer = new Customer(id, address, phonenumber, companyname, zipcode, mail, firstName, lastName);

            }
            return customer;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failed to add Customer from the database", ex);
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
        return null;
    }



    public Customer createNewCustomer(String address, int phonenumber, String companyname, int zipcode, String mail, String firstName, String lastName, int room, String city) throws Exception {
        //SQL Query
        String sql = "INSERT INTO Customer (address, phonenumber, companyname, zipcode, mail, firstName, lastName, city) VALUES(?,?,?,?,?,?,?,?)";
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
            stmt.setString(8,city);
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            Customer customer = new Customer(id, address, phonenumber, companyname, zipcode, mail, firstName,  lastName);
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
