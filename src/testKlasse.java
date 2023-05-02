import BLL.CustomerManager;
import DAL.CustomerDAO;
import DAL.FilesDAO;
import DAL.ProjectDAO;
import GUI.Model.CustomerModel;

import java.io.IOException;

public class testKlasse {



    public static void main(String[] args) throws Exception {


        FilesDAO filesDAO=new FilesDAO();
        filesDAO.updateUsedInDoc(true,1);




    }
}
