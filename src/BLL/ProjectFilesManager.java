package BLL;

import BE.ProjectFiles;
import DAL.FilesDAO;
import DAL.iFileDataAccess;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ProjectFilesManager {

    private iFileDataAccess filesDAO;

    /**
     * Constructor for the class "ProjectFilesManager".
     * @throws IOException
     */
    public ProjectFilesManager() throws IOException {filesDAO = new FilesDAO();}

    /**
     * Gets a list of files based on an ID.
     * @param projectID
     * @return
     * @throws Exception
     */
    public List<ProjectFiles> loadFilesFromAProject(int projectID) throws Exception{return filesDAO.loadFilesFromAProject(projectID);}

    /**
     * Sends an update on a documents status through the BLL.
     * @param usedInDoc
     * @param id
     * @throws Exception
     */
    public void updateUsedInDoc(Boolean usedInDoc, int id) throws Exception {filesDAO.updateUsedInDoc(usedInDoc,id);}

    /**
     * Sends a new file through the BLL.
     * @param file
     * @return
     * @throws SQLException
     */
    public ProjectFiles createNewFile(ProjectFiles file) throws SQLException{return filesDAO.createNewFile(file);}

    /**
     * Sends a file marked for deletion trough the BLL.
     * @param file
     * @throws SQLException
     */
    public void deleteFile(ProjectFiles file) throws SQLException{filesDAO.deleteFile(file);}
}
