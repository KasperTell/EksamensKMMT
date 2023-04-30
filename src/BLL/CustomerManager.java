
package BLL;

import BE.Customer;
import DAL.CustomerDAO;
import DAL.ICustomerDataAccess;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerManager {

    List<Customer> customer = new ArrayList<>();

    private ICustomerDataAccess customerDAO;

    public CustomerManager() throws IOException {customerDAO = new CustomerDAO();}

    public List<Customer> loadCustomer(String companyname) throws Exception{return (List<Customer>) customerDAO.loadCustomer(companyname);}

    public List<Customer> loadProjectManagers() throws  Exception{return (List<Customer>) customerDAO.loadProjectManagers();}

    public Customer createNewCustomer(Customer customer) throws Exception {return customerDAO.createNewCustomer(customer);}

    public void deleteCustomer(Customer selectedCustomer) throws Exception {customerDAO.deleteCustomer(selectedCustomer);}


}
