package DAL;

import BE.Customer;

import java.sql.SQLException;
import java.util.List;

public interface ICustomerDataAccess {

    List<Customer> loadAllCustomers() throws SQLException;
    Customer createNewCustomer(Customer customer) throws SQLException;
    Customer loadCustomer(int customerID) throws Exception;


    public String TownToZipCode(int zipCode) throws SQLException;

}
