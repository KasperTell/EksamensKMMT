package DAL;

import BE.ProjectTechnician;
import BE.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDataAccess {

    List<User> loadUser(String username) throws Exception;
    boolean validateUsername(String username) throws Exception;
    List<User> loadUserOfAType(int role) throws Exception;
    User createNewUser(String firstName, String lastName, String username, String password, int role) throws Exception;
    void deleteUser(User selectedUser) throws Exception;
    List<User> filterTechnicianById(int projectID) throws SQLException;
    ProjectTechnician moveTechnician(int technicianID, int projectID) throws Exception;

    void removeTechnicianFromProject(int technicianID, int projectID) throws Exception;
}
