package GUI.Model;

import BE.Project;
import BLL.ProjectManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class ProjectModel {

    private ObservableList<Project> projectsOpen;
    private ObservableList<Project> projectClose;

    private ProjectManager projectManager;
    private ObservableList<Project> searchedProjects;

public ProjectModel() throws Exception {
    projectManager = new ProjectManager();
    projectsOpen = FXCollections.observableArrayList();
    projectClose = FXCollections.observableArrayList();
    projectsOpen.addAll(projectManager.loadProjectOfAType(true));
    projectClose.addAll(projectManager.loadProjectOfAType(false));
    searchedProjects = FXCollections.observableArrayList();

}

    public ObservableList<Project> getAllProjectsOpen() {return projectsOpen;}

    public ObservableList<Project> getAllProjectsClose() {return projectClose;}

    public void createNewProject(Project project) throws SQLException{
    projectManager.createNewProject(project);
    projectsOpen.add(project);
    }

    public void changeProjectStatus(int projectStatus, int id) throws Exception {
    projectManager.changeProjectStatus(projectStatus, id);
    projectsOpen.clear();
    projectClose.clear();
    projectClose.addAll(projectManager.loadProjectOfAType(false));
    projectsOpen.addAll(projectManager.loadProjectOfAType(true));
}
    public ObservableList<Project> searchByQuery(String searchQuery) throws Exception {
        System.out.println(searchedProjects.size() + "Search");
        searchedProjects.addAll(projectManager.searchByQuery(searchQuery));
        return searchedProjects; }

}
