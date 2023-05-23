package DAL;

import BE.Project;

import java.sql.SQLException;
import java.util.List;

public interface IProjectDataAccess {
    List<Project> loadProjectOfAType(boolean open) throws SQLException;
    void changeProjectStatus(int projectStatus, int id) throws SQLException;
    Project createNewProject(Project project) throws SQLException;
    List<Project> searchByQuery(String query) throws SQLException;
    void changeNote(String note, int id) throws SQLException;
    List<Project> allProjectsForTechnician(int technicianID) throws SQLException;
}