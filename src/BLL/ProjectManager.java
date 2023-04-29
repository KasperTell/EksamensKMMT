package BLL;

import BE.Project;
import DAL.ProjectDAO;

import java.io.IOException;
import java.util.ArrayList;

public class ProjectManager {

    ProjectDAO projectDAO;


    public ProjectManager() throws IOException {
        projectDAO = new ProjectDAO();
    }

    public ArrayList<Project> loadProjectOfAType(Boolean open) throws Exception{return projectDAO.loadProjectOfAType(open);}


}
