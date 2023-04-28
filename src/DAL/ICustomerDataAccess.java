package DAL;

import BE.Customer;

import java.awt.*;

public interface ICustomerDataAccess {
    java.util.List<Customer> loadProjectManager() throws Exception;

    java.util.List<Customer> loadCustomer(String companyname) throws Exception;
    java.util.List<Customer> loadProjectManager(String companyname) throws Exception;
    
    Customer createNewCustomer(Customer customer) throws Exception;

    Customer createNewCustomer(String address, int phonenumber, String companyname, int zipcode, String mail, String firstName, String lastName, int room) throws Exception;

    void deleteCustomer(Customer selectedCustomer) throws Exception;

    List loadProjectManagers();
}
