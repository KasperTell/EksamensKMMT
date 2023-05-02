
package BLL;

import BE.Customer;
import DAL.CustomerDAO;
import DAL.ICustomerDataAccess;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerManager {

    private ICustomerDataAccess customerDAO;

    public CustomerManager() throws IOException {customerDAO = new CustomerDAO();}

    public List<Customer> loadAllCustomers() throws SQLException {
        return customerDAO.loadAllCustomers();
    }
    public Customer loadCustomer(int customerID) throws Exception{return customerDAO.loadCustomer(customerID);}

    public List<Customer> loadProjectManagers() throws  Exception{return (List<Customer>) customerDAO.loadProjectManagers();}

    public Customer createNewCustomer(Customer customer) throws Exception {return customerDAO.createNewCustomer(customer);}

    public void deleteCustomer(Customer selectedCustomer) throws Exception {customerDAO.deleteCustomer(selectedCustomer);}


}
