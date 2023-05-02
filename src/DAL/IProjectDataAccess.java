package DAL;

import BE.Project;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IProjectDataAccess {

    ArrayList<Project> loadProjectOfAType(boolean open) throws Exception;
    void changeProjectStatus(int projectStatus, int id) throws Exception;

    Project createNewProject(Project project) throws SQLException;
}
