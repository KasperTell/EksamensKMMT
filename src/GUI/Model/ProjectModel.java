package GUI.Model;

import BE.Project;
import BLL.ProjectManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class ProjectModel {

    private ObservableList<Project> projectsOpen;
    private ObservableList<Project> projectClose;
    private ObservableList<Project> searchedProjects;
    private String searchQuery;

    private ProjectManager projectManager;

public ProjectModel() throws Exception {
    projectManager = new ProjectManager();
    projectsOpen = FXCollections.observableArrayList();
    projectClose = FXCollections.observableArrayList();
    searchedProjects = FXCollections.observableArrayList();
    //searchedProjects.addAll(projectManager.searchByQuery(searchQuery));
    projectsOpen.addAll(projectManager.loadProjectOfAType(true));
    projectClose.addAll(projectManager.loadProjectOfAType(false));

}

    public ObservableList<Project> getAllProjectsOpen() {return projectsOpen;}

    public ObservableList<Project> getAllProjectsClose() {return projectClose;}


    public void createNewProject(Project project) throws SQLException{
    projectManager.createNewProject(project);
    projectsOpen.add(project);
    }

    public ObservableList<Project> searchByQuery(String searchQuery) throws Exception {
        System.out.println(searchedProjects.size() + "Search");
        searchedProjects.addAll(projectManager.searchByQuery(searchQuery));
    return searchedProjects; }


    public void changeProjectStatus(int projectStatus, int id) throws Exception {
    projectManager.changeProjectStatus(projectStatus, id);
    projectsOpen.clear();
    projectClose.clear();
    projectClose.addAll(projectManager.loadProjectOfAType(false));
    projectsOpen.addAll(projectManager.loadProjectOfAType(true));
}
}
