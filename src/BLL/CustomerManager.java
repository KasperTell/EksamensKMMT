
package BLL;

import BE.Customer;
import DAL.CustomerDAO;
import DAL.ICustomerDataAccess;

import java.awt.*;
import java.io.IOException;

public class CustomerManager {

    private ICustomerDataAccess customerDAO;

    public CustomerManager() throws IOException {customerDAO = new CustomerDAO();}

    public List<Customer> loadCustomer(Customer) throws Exception{return  customerDAO.loadCustomer(customer);}

    public List<Customer> loadProjectManagers() throws  Exception{return customerDAO.loadProjectManagers();}

    public Customer createNewCustomer(Customer customer) throws Exception {return customerDAO.createNewCustomer(customer);}

    public void deleteCustomer(Customer selectedCustomer) throws Exception {customerDAO.deleteCustomer(selectedCustomer);}


}
