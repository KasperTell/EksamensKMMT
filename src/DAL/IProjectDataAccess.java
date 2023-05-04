package DAL;

import BE.Project;

import java.sql.SQLException;
import java.util.List;

public interface IProjectDataAccess {

    List<Project> loadProjectOfAType(boolean open) throws Exception;
    void changeProjectStatus(int projectStatus, int id) throws SQLException;

    Project createNewProject(Project project) throws SQLException;
    List<Project> searchByQuery(String query) throws SQLException;
}
