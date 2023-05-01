import BLL.CustomerManager;
import DAL.CustomerDAO;
import DAL.ProjectDAO;
import GUI.Model.CustomerModel;

import java.io.IOException;

public class testKlasse {



    public static void main(String[] args) throws Exception {


        //CustomerManager customerManager = new CustomerManager();
        CustomerModel customerModel = new CustomerModel();

        System.out.println(customerModel.loadCustomer(13));




    }
}
