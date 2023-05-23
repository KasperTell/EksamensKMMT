package BLL;

import BE.Customer;
import DAL.CustomerDAO;
import DAL.ICustomerDataAccess;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CustomerManager {

    private ICustomerDataAccess customerDAO;

    /**
     * Constructor for the class "CustomerManager".
     * @throws IOException
     */
    public CustomerManager() throws IOException { customerDAO = new CustomerDAO();}

    /**
     * Gets a list of all available customers.
     * @return
     * @throws SQLException
     */
    public List<Customer> loadAllCustomers() throws SQLException { return customerDAO.loadAllCustomers();}

    /**
     * Sends a specific customer through the BLL for creation in the database.
     * @param customer
     * @return
     * @throws SQLException
     */
    public Customer createNewCustomer(Customer customer) throws SQLException { return customerDAO.createNewCustomer(customer);}

    /**
     * Gets a specific customer based on an ID.
     * @param customerID
     * @return
     * @throws SQLException
     */
    public Customer loadCustomer(int customerID) throws SQLException { return customerDAO.loadCustomer(customerID);}

    /**
     * Gets a specific city based on the zipcode.
     * @param zipCode
     * @return
     * @throws SQLException
     */
    public String TownToZipCode(int zipCode) throws SQLException { return customerDAO.TownToZipCode(zipCode);}
}