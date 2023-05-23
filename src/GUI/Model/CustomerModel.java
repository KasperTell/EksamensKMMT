package GUI.Model;

import BE.Customer;
import BLL.CustomerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class CustomerModel {

    private ObservableList<Customer> allCustomers;
    private ObservableList<Customer> customerInfo;
    private CustomerManager customerManager;
    private Customer createdCustomer;

    /**
     * Constructor for the class "CustomerModel".
     * @throws Exception
     */
    public CustomerModel() throws Exception {
        customerManager = new CustomerManager();
        allCustomers = FXCollections.observableArrayList();
        allCustomers.addAll(customerManager.loadAllCustomers());
        customerInfo = FXCollections.observableArrayList();
    }

    /**
     * Getter for the list of all customers.
     * @return
     */
    public ObservableList<Customer> getAllCustomers(){return allCustomers;}

    /**
     * Sends the customer based on a specific ID through the model.
     * @param customerID
     * @return
     * @throws Exception
     */
    public ObservableList<Customer> loadCustomerList(int customerID) throws Exception {
        customerInfo.clear();
        customerInfo.addAll(loadCustomer(customerID));
        return customerInfo;}


    /**
     * Sends the customer based on a specific ID through the model.
     * @param customerID
     * @return
     * @throws Exception
     */
    public Customer loadCustomer(int customerID) throws Exception { return customerManager.loadCustomer(customerID);}


    /**
     * Sends the customer through the model and adds it to the list of all customers.
     * @param customer
     * @throws Exception
     */
    public void createNewCustomer(Customer customer) throws Exception {
        createdCustomer = customerManager.createNewCustomer(customer);
        allCustomers.add(createdCustomer);
    }


    public String TownToZipCode(int zipCode) throws SQLException
    {
        return customerManager.TownToZipCode(zipCode);
    }




}
