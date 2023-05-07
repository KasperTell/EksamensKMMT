package GUI.Model;

import BE.User;
import BLL.UserManager;
import PersonsTypes.Technician;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UserModel {

    private ObservableList<User> allTechnicians;
    private ObservableList<User> allSalesmen;
    private ObservableList<User> allProjectManager;
    private ObservableList<User> allTechniciansOnProject;
    private UserManager userManager;
    private User user;

    /**
     * Constructor for the class "UserModel".
     * @throws Exception
     */
    public UserModel() throws Exception {
        userManager = new UserManager();
        allTechnicians = FXCollections.observableArrayList();
        allSalesmen = FXCollections.observableArrayList();
        allProjectManager = FXCollections.observableArrayList();
        allTechnicians.addAll(userManager.loadUserOfAKind(4));
        allSalesmen.addAll(userManager.loadUserOfAKind(3));
        allProjectManager.addAll(userManager.loadUserOfAKind(2));
        allTechniciansOnProject = FXCollections.observableArrayList();
    }

    /**
     * Send the loaded username though the model.
     * @param name
     * @return
     * @throws Exception
     */
    public User loadUser(String name)throws Exception{return userManager.loadUser(name);}

    /**
     * Getters for the lists.
     * @return
     */
    public ObservableList<User> getAllTechnicians() {return allTechnicians;}

    public ObservableList<User> getallSalesmen() {return allSalesmen;}
    public ObservableList<User> getAllTechniciansOnProject(int projectID) throws Exception {
        allTechniciansOnProject.clear();
        allTechniciansOnProject.addAll(userManager.filterTechnicianById(projectID));
        return allTechniciansOnProject;
    }

    public ObservableList<User> getallProjectManagers() {return allProjectManager;}

    /**
     * Sending the validating through the model.
     * @param username
     * @return
     * @throws Exception
     */
    public boolean validateUsername(String username) throws Exception{return userManager.validateUsername(username);}

    /**
     *
     * @param createdUser
     * @throws Exception
     */
    public void createNewUser(User createdUser) throws Exception {
        User newUser =  userManager.createNewUser(createdUser);
        allTechnicians.add(newUser);
        allTechnicians.clear();
        allTechnicians.addAll(userManager.loadUserOfAKind(createdUser.getRole()));
    }

    /**
     * Getter and setter for the logged-in user.
     * @return
     */
    public User getLoggedinUser(){return user;}

    public void setLoggedinUser(User user){
        this.user = user;
    }

    /**
     * Send the selected user for soft deletion.
     * @param selectedUser
     * @throws Exception
     */
    public void deleteUser(User selectedUser) throws Exception {
        userManager.deleteUser(selectedUser);
        allTechnicians.remove(selectedUser);
    }

    /**
     * Send the selected technician and ID for the selected project through the model.
     * @param selectedTechnician
     * @param projectID
     * @throws Exception
     */
    public void removeTechnicianFromProject(User selectedTechnician, int projectID) throws Exception {
        allTechniciansOnProject.remove(selectedTechnician);
        userManager.removeTechnicianFromProject(selectedTechnician, projectID);
    }

    /**
     * Send the selected technician and ID for the selected project through the model.
     * @param technicanID
     * @param projectID
     * @throws Exception
     */
    public void moveTechnician(int technicanID, int projectID) throws Exception {
        userManager.moveTechnician(technicanID, projectID);
        allTechniciansOnProject.clear();
        allTechniciansOnProject.addAll(userManager.filterTechnicianById(projectID));
    }
}

