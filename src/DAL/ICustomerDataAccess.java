package DAL;

import BE.Customer;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public interface ICustomerDataAccess {

    List<Customer> loadAllCustomers() throws SQLException;

    Customer loadCustomer(int customerID) throws Exception;
    
    Customer createNewCustomer(Customer customer) throws Exception;

    void deleteCustomer(Customer selectedCustomer) throws Exception;
}
