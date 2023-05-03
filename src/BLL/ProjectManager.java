package BLL;

import BE.Project;
import DAL.IProjectDataAccess;
import DAL.ProjectDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProjectManager {

    private IProjectDataAccess projectDAO;


    public ProjectManager() throws IOException {projectDAO = new ProjectDAO();}

    public ArrayList<Project> loadProjectOfAType(Boolean open) throws Exception{return projectDAO.loadProjectOfAType(open);}

    public Project createNewProject(Project project) throws SQLException{
        return projectDAO.createNewProject(project);
    }

    public void changeProjectStatus(int projectStatus, int id) throws Exception{
        projectDAO.changeProjectStatus(projectStatus, id);
    }

    public ArrayList<Project> searchByQuery(String query) throws Exception {
        return projectDAO.searchByQuery(query);
    }


}
