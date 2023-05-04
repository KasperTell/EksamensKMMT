package BLL;

import BE.Project;
import DAL.IProjectDataAccess;
import DAL.ProjectDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProjectManager {

    private IProjectDataAccess projectDAO;

    /**
     * Constructor for the class "ProjectManager".
     * @throws IOException
     */
    public ProjectManager() throws IOException {projectDAO = new ProjectDAO();}

    /**
     * Gets a list of all open projects.
     * @param open
     * @return
     * @throws Exception
     */
    public List<Project> loadProjectOfAType(Boolean open) throws Exception{return projectDAO.loadProjectOfAType(open);}

    /**
     * Send a created project through BLL.
     * @param project
     * @return
     * @throws SQLException
     */
    public Project createNewProject(Project project) throws SQLException{return projectDAO.createNewProject(project);}

    /**
     * Sends information for changing the project from open to closed through BLL.
     * @param projectStatus
     * @param id
     * @throws Exception
     */
    public void changeProjectStatus(int projectStatus, int id) throws Exception{projectDAO.changeProjectStatus(projectStatus, id);}

    /**
     * Gets a list based on a search query.
     * @param query
     * @return
     * @throws Exception
     */
    public List<Project> searchByQuery(String query) throws Exception {return projectDAO.searchByQuery(query);}
}
