package GUI.Model;

import BE.Customer;
import BE.User;
import BLL.CustomerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CustomerModel {

    private ObservableList<Customer> allProjectmanager;
    private ObservableList<Customer> allCustomers;

    private CustomerManager customerManager;

    private Customer customer;

    private Customer createdCustomer;
    private User user;

    public CustomerModel() throws Exception {
        customerManager = new CustomerManager();
        allCustomers = FXCollections.observableArrayList();
        allCustomers.addAll(customerManager.loadAllCustomers());
    }

    public ObservableList<Customer> getAllCustomers(){return allCustomers;}
    public Customer loadCustomer(int customerID) throws Exception {return customerManager.loadCustomer(customerID);}

    public void createNewCustomer(Customer customer) throws Exception {
        createdCustomer = customerManager.createNewCustomer(customer);
        allProjectmanager.add(createdCustomer);
        allProjectmanager.clear();
        allProjectmanager.addAll(customerManager.loadProjectManagers());
    }

    public User getLoggedinUser(){return user;}

    public void setLoggedinUser (User user) {this.user = user;}

    public void deleteCustomer (Customer selectedCustomer) throws Exception {
        customerManager.deleteCustomer(selectedCustomer);
        allProjectmanager.remove(selectedCustomer);
    }





}
