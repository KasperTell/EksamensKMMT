import DAL.ProjectDAO;

import java.io.IOException;

public class testKlasse {



    public static void main(String[] args) throws Exception {


        ProjectDAO projectDAO=new ProjectDAO();
        System.out.println(projectDAO.loadProjectOfAType(false));



    }
}
