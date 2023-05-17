package DAL;

import BE.ProjectFiles;

import java.sql.SQLException;
import java.util.List;

public interface iFileDataAccess {

    List<ProjectFiles> loadFilesFromAProject(int projectID) throws Exception;
    void updateUsedInDoc(Boolean usedInDoc, int id) throws Exception;
    ProjectFiles createNewFile(ProjectFiles file) throws SQLException;
    void deleteFile(ProjectFiles file) throws SQLException;

    //void updateFileOrder (int OrderFiles, int id) throws SQLException;

    //void updateFileOrders(int selectedFileId, int fileToMoveId, int selectedFileNewOrder, int fileToMoveNewOrder) throws SQLException;

    void moveFile(int projectId, ProjectFiles file, Boolean moveUp) throws Exception;
}
