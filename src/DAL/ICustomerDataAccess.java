package DAL;

import BE.Customer;

import java.sql.SQLException;
import java.util.List;

public interface ICustomerDataAccess {

    List<Customer> loadAllCustomers() throws SQLException;

    List<Customer> loadCustomer(int customerID) throws SQLException;
    
    Customer createNewCustomer(Customer customer) throws SQLException;
}
