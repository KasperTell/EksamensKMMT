package GUI.Model;

import BE.Project;
import BE.User;
import BLL.ProjectManager;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

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














}
