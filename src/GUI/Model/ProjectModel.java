package GUI.Model;

import BE.Project;
import BLL.ProjectManager;
import PersonsTypes.PersonTypeChooser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Objects;

public class ProjectModel {

    private ObservableList<Project> projectsOpen;
    private ObservableList<Project> projectClose;
    private ObservableList<Project> allTechniciansProject;
    private ProjectManager projectManager;
    private ObservableList<Project> searchedProjects;
    private String projectTitle;
    private Project selectedProject;
    private PersonTypeChooser personTypeChooser = new PersonTypeChooser();
    /**
     * Constructor for the class "ProjectModel".
     * @throws Exception
     */
    public ProjectModel() throws Exception {
        projectManager = new ProjectManager();
        projectsOpen = FXCollections.observableArrayList();
        projectClose = FXCollections.observableArrayList();
        projectsOpen.addAll(projectManager.loadProjectOfAType(true));
        projectClose.addAll(projectManager.loadProjectOfAType(false));
        searchedProjects = FXCollections.observableArrayList();
        allTechniciansProject = FXCollections.observableArrayList();
    }

    /**
     * Getters for the Lists.
     * @return
     */
    public ObservableList<Project> getAllProjectsOpen(int technicianID) throws SQLException {
        if(personTypeChooser.setListInfo()){
            getAllTechniciansProject(technicianID);
        }
        return projectsOpen;}

    public ObservableList<Project> getAllProjectsClose() {return projectClose;}

    /**
     * Sends the created project through the model and adds it to the listview.
     * @param project
     * @throws SQLException
     */
    public void createNewProject(Project project) throws SQLException{
    projectManager.createNewProject(project);
    projectsOpen.add(project);
    }

    /**
     * Sending the changed project status through the model and updates the listviews
     * @param projectStatus
     * @param id
     * @throws Exception
     */
    public void changeProjectStatus(int projectStatus, int id) throws Exception {
        projectManager.changeProjectStatus(projectStatus, id);
        clearLists();
    }

    /**
     * Sends the search query through the model.
     * @param searchQuery
     * @return
     * @throws Exception
     */
    public ObservableList<Project> searchByQuery(String searchQuery) throws Exception {
        if(Objects.equals(searchQuery, "")){
            searchedProjects.addAll(projectManager.loadProjectOfAType(true));
        } else {
            searchedProjects.clear();
            searchedProjects.addAll(projectManager.searchByQuery(searchQuery));
        }
        return searchedProjects;
    }


    public Project getSelectedProject(){return selectedProject;}
    public void setSelectedProject(Project project){selectedProject = project;}




    public void changeNote(String note, int id) throws Exception {
        projectManager.changeNote(note, id);
        clearLists();

    }

public void clearLists() throws Exception {
    projectsOpen.clear();
    projectClose.clear();
    projectClose.addAll(projectManager.loadProjectOfAType(false));
    projectsOpen.addAll(projectManager.loadProjectOfAType(true));
}

    public ObservableList<Project> getAllTechniciansProject(int technicianID) throws SQLException {
        projectsOpen.clear();
        projectsOpen.addAll(projectManager.allProjectsForTechnician(technicianID));
        return projectsOpen;
    }
}
