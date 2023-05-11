package DAL;

import BE.Customer;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements ICustomerDataAccess {

    private DatabaseConnector databaseConnector;

    /**
     * Constructor for the class "CustomerDAO".
     * @throws IOException
     */
    public CustomerDAO() throws IOException {
        databaseConnector = DatabaseConnector.getInstance();
    }

    /**
     * Gets a list of all customers from the database.
     * @return
     * @throws SQLException
     */
    public List<Customer> loadAllCustomers() throws SQLException{
        ArrayList<Customer> allCustomers = new ArrayList<>();
        //SQL query and getting the database connection.
        String sql = "SELECT * FROM Customers";

        try(Connection conn = databaseConnector.getConnection()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //Getting the information from the database as long as there are more rows.
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

    /**
     * Gets a specific customer from the database based on an ID.
     * @param customerID
     * @return
     * @throws Exception
     */
    public Customer loadCustomer(int customerID) throws SQLException {
        Customer customer = null;
        //SQL Query
        String sql = "SELECT * FROM Customers WHERE ID = ?";
        // getting the connection to the database.
        try (Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            //Getting the information from the database.
            while (rs.next()) {
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
            throw new SQLException("Failed to add Customer from the database", ex);
        }
    }

    /**
     * Creating a new customer in the database.
     * @param customer
     * @return
     * @throws SQLException
     */
    public Customer createNewCustomer(Customer customer) throws SQLException {
        //SQL Query
        String sql = "INSERT INTO Customers(First_Name, Last_Name, Company_Name, Address, Zip_Code, Mail, Phone_Number) VALUES (?,?,?,?,?,?,?)";
        //Getting connection to the database.
        try(Connection conn = databaseConnector.getConnection()) {
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
            return customer;
        } catch(SQLException ex){
            ex.printStackTrace();
            throw new SQLException("Could not create customer", ex);
        }
    }
}
