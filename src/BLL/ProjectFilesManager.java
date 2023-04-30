package BLL;

import BE.Project;
import BE.ProjectFiles;
import DAL.FilesDAO;
import DAL.ProjectDAO;

import java.io.IOException;
import java.util.ArrayList;

public class ProjectFilesManager {

    FilesDAO filesDAO;



    public ProjectFilesManager() throws IOException {
        filesDAO = new FilesDAO();
    }

    public ArrayList<ProjectFiles> loadFilesFromAProject(int projectID) throws Exception{return (ArrayList<ProjectFiles>) filesDAO.loadFilesFromAProject(projectID);}



}
