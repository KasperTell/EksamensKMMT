package DAL;

import BE.ProjectFiles;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

public interface iFileDataAccess {
    List<ProjectFiles> loadFilesFromAProject(int projectID) throws SQLException, FileNotFoundException;
    void updateUsedInDoc(Boolean usedInDoc, int id) throws SQLException;
    ProjectFiles createNewFile(ProjectFiles file) throws SQLException;
    void deleteFile(ProjectFiles file) throws SQLException;
    boolean doesFileExist(String filepath) throws SQLException;
}