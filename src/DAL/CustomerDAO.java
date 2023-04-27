package DAL;

import BE.Customer;
import com.sun.jdi.connect.spi.Connection;

import java.awt.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CustomerDAO implements ICustomerDataAccess{

    private DatabaseConnector databaseConnector;

    public CustomerDAO() throws IOException {databaseConnector = DatabaseConnector.getInstance();}

    @Override
    public List<Customer> loadProjectManager() throws Exception {

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

                Customer customer = new Customer(id,address,phonenumber,companyname,zipcode,mail,firstName,lastName,room);

                allProjectManager.add(customer);
            }
            return allProjectManager;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failed to add Customer from the database", ex);
        }
    }

    @Override
    public List<Customer> loadCustomer(String customer) throws Exception {
        ArrayList<Customer> allCustomer = new ArrayList<>();
        //SQL Query
        String sql = "SELECT * FROM Customer WHERE Companyname = ?";
        //Getting the connection to the database.
        try(java.sql.Connection conn = databaseConnector.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            //Setting the paramers and executing the statement.
            stmt.setString(1,customer);
            ResultSet rs = stmt.executeQuery();

            // Loop through rows from the database result set
            while (rs.next()) {
                //Map DB row to user object
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

                allCustomer.add(customer);
            }
            return allCustomer;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Could not get EventKoordinator from database", ex);

    }





}
