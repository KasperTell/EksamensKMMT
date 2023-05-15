
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
    public CustomerManager() throws IOException {customerDAO = new CustomerDAO();}

    /**
     * Gets a list of all available customers.
     * @return
     * @throws SQLException
     */
    public List<Customer> loadAllCustomers() throws SQLException {return customerDAO.loadAllCustomers();}

    /**
     * Gets a specific customer based on an ID.
     * @param customerID
     * @return
     * @throws Exception
     */
    public List<Customer> loadCustomer(int customerID) throws Exception{return customerDAO.loadCustomer(customerID);}

    /**
     * Sends a created user through the BLL.
     * @param customer
     * @return
     * @throws Exception
     */
    public Customer createNewCustomer(Customer customer) throws Exception {return customerDAO.createNewCustomer(customer);}
}
