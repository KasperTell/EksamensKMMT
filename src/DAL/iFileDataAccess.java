package DAL;

import BE.ProjectFiles;

import java.sql.SQLException;
import java.util.List;

public interface iFileDataAccess {

    List<ProjectFiles> loadFilesFromAProject(int projectID) throws Exception;
    void updateUsedInDoc(Boolean usedInDoc, int id) throws Exception;
    ProjectFiles createNewFile(ProjectFiles file) throws SQLException;
    void deleteFile(ProjectFiles file) throws SQLException;

    void updateFileOrder (int OrderBy) throws SQLException;
}
