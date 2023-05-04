package DAL;

import BE.ProjectTechnician;
import BE.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDataAccess {

    User loadUser(String username) throws SQLException;
    boolean validateUsername(String username) throws SQLException;
    List<User> loadUserOfAType(int role) throws SQLException;
    User createNewUser(User user) throws SQLException;
    void deleteUser(User selectedUser) throws SQLException;
    List<User> filterTechnicianById(int projectID) throws SQLException;
    ProjectTechnician moveTechnician(int technicianID, int projectID) throws SQLException;
    void removeTechnicianFromProject(User selectedTechnician, int projectID) throws SQLException;
}
