package BLL;

import BE.ProjectTechnician;
import BE.Role;
import BE.User;
import DAL.IUserDataAccess;
import DAL.UserDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserManager {

    private IUserDataAccess userDAO;

    /**
     * Constructor for the class "UserManager".
     * @throws IOException
     */
    public UserManager() throws IOException { userDAO = new UserDAO();}

    /**
     * Gets a specific user from based on a username.
     * @param username
     * @return
     * @throws Exception
     */
    public User loadUser(String username) throws Exception { return userDAO.loadUser(username);}

    /**
     * Gets a list of all users with a specific role.
     * @param role
     * @return
     * @throws Exception
     */
    public List<User> loadUserOfAKind(int role) throws Exception { return userDAO.loadUserOfAType(role);}

    /**
     * Sends a created user through BLL.
     * @param user
     * @return
     * @throws Exception
     */
    public User createNewUser(User user) throws Exception { return userDAO.createNewUser(user);}

    /**
     * Send a user marked for soft deletion trough BLL.
     * @param selectedUser
     * @throws Exception
     */
    public void deleteUser(User selectedUser) throws Exception { userDAO.deleteUser(selectedUser);}

    /**
     * Sends a username for validation through the BLL.
     * @param username
     * @return
     * @throws Exception
     */
    public boolean validateUsername(String username) throws Exception { return userDAO.validateUsername(username);}

    /**
     * Gets a list of all the technicians working on a specific project based on an ID.
     * @param projectID
     * @return
     * @throws Exception
     */
    public List<User> filterTechnicianById(int projectID) throws Exception { return userDAO.filterTechnicianById(projectID);}

    /**
     * Removes a technician from a project based on the user and project.
     * @param selectedTechnician
     * @param projectID
     * @throws Exception
     */
    public void removeTechnicianFromProject(User selectedTechnician, int projectID) throws Exception { userDAO.removeTechnicianFromProject(selectedTechnician, projectID);}

    /**
     * Adds a specific technician to a project.
     * @param technicianID
     * @param projectID
     * @return
     * @throws Exception
     */
    public ProjectTechnician moveTechnician(int technicianID, int projectID) throws Exception { return userDAO.moveTechnician(technicianID, projectID);}

    /**
     * Gets a list of all available employee roles.
     * @return
     * @throws SQLException
     */
    public List<Role> AllRoles() throws SQLException { return userDAO.allRoles();}
}