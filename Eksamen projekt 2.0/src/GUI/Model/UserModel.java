package GUI.Model;

import BE.User;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UserModel {

    private ObservableList<User> allTechnicians;
    private UserManager userManager;
    private User user;
    private User createdUser;

    public UserModel() throws Exception {
        userManager = new UserManager();
        allTechnicians = FXCollections.observableArrayList();
        allTechnicians.addAll(userManager.loadTechnicians());
        System.out.println(allTechnicians.size());
    }

    public List<User> loadUser(String name)throws Exception{return userManager.loadUser(name);}

    public ObservableList<User> getAllTechnicians() {return allTechnicians;}

    public boolean validateUsername(String username) throws Exception{return userManager.validateUsername(username);}

    public void createNewUser(String firstName, String lastName, String username, String password, int role) throws Exception {
        createdUser =  userManager.createNewUser(firstName, lastName, username, password, role);
        allTechnicians.add(createdUser);
        allTechnicians.clear();
        allTechnicians.addAll(userManager.loadTechnicians());
    }

    public User getLoggedinUser(){return user;}

    public void setLoggedinUser(User user){
        this.user = user;
    }

    public void deleteUser(User selectedKoordinator) throws Exception {
        userManager.deleteUser(selectedKoordinator);
        allTechnicians.remove(selectedKoordinator);
    }


}

