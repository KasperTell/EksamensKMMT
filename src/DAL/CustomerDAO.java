package DAL;

import BE.Customer;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements ICustomerDataAccess {

    private DatabaseConnector databaseConnector;

    public CustomerDAO() throws IOException {
        databaseConnector = DatabaseConnector.getInstance();
    }


    public List<Customer> loadAllCustomers() throws SQLException{
        ArrayList<Customer> allCustomers = new ArrayList<>();

        String sql = "SELECT * FROM Customers";

        try(Connection conn = databaseConnector.getConnection()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("ID");
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");
                String companyName = rs.getString("Company_Name");
                String address = rs.getString("Address");
                int zipcode = rs.getInt("Zip_Code");
                String mail = rs.getString("Mail");
                int phoneNumber = rs.getInt("Phone_Number");

                Customer customer = new Customer(id, firstName, lastName, companyName, address, mail, phoneNumber, zipcode);
                allCustomers.add(customer);
            }
            return allCustomers;
        } catch (SQLException ex){
            ex.printStackTrace();
            throw new SQLException("Could not get all customers");
        }
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
                String companyName = rs.getString("Company_Name");
                String address = rs.getString("Address");
                int zipcode = rs.getInt("Zip_Code");
                String mail = rs.getString("Mail");
                int phoneNumber = rs.getInt("Phone_Number");


                customer = new Customer(id, firstName, lastName, companyName, address, mail, phoneNumber, zipcode);

            }
            return customer;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failed to add Customer from the database", ex);
        }
    }

    @Override
    public Customer createNewCustomer(Customer customer) throws Exception {
        String sql = "INSERT INTO Customers(First_Name, Last_Name, Company_Name, Address, Zip_Code, Mail, Phone_Number) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //Setting the parameters and executing the query.
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getCompanyName());
            stmt.setString(4, customer.getAddress());
            stmt.setInt(5, customer.getZipCode());
            stmt.setString(6, customer.getMail());
            stmt.setInt(7, customer.getPhoneNumber());
            stmt.execute();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                customer.setId(rs.getInt(1));
            }
        }
        return customer;
    }

    @Override
    public void deleteCustomer(Customer selectedCustomer) throws  Exception {
        //SQL query
        String sql = "DELETE FROM Customer WHERE Customer_ID = ?";
        //Getting connection to the database.
        try (java.sql.Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            //Setting the parameter and executing the query.
            stmt.setInt(1, selectedCustomer.getId());
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not delete this event", ex);
        }
    }
}
