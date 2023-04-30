package GUI.Model;

import BE.Project;
import BE.ProjectFiles;
import BLL.ProjectFilesManager;
import BLL.ProjectManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectFilesModel {

    private ObservableList<ProjectFiles> projectFiles;

    private ProjectFilesManager projectFilesManager;


    public ProjectFilesModel() throws Exception {
        projectFilesManager = new ProjectFilesManager();
        projectFiles = FXCollections.observableArrayList();
//        projectFiles.addAll(projectFilesManager.loadFilesFromAProject(0));

    }


    public ObservableList<ProjectFiles> getAllFilesFromProject(int projectID) throws Exception {

        projectFiles.addAll(projectFilesManager.loadFilesFromAProject(projectID));
         return projectFiles;
    }





}
