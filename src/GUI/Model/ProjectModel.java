package GUI.Model;

import BE.Project;
import BLL.ProjectManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjectModel {

    private ObservableList<Project> projectsOpen;
    private ObservableList<Project> projectClose;

    private ProjectManager projectManager;

public ProjectModel() throws Exception {
    projectManager = new ProjectManager();
    projectsOpen = FXCollections.observableArrayList();
    projectClose = FXCollections.observableArrayList();
    projectsOpen.addAll(projectManager.loadProjectOfAType(true));
    projectClose.addAll(projectManager.loadProjectOfAType(false));

}

    public ObservableList<Project> getAllProjectsOpen() {return projectsOpen;}

    public ObservableList<Project> getAllProjectsClose() {return projectClose;}












}
