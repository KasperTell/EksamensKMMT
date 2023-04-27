package GUI.Model;

import BE.User;
import BLL.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UserModel {

    private ObservableList<User> allTechnicians;
    private ObservableList<User> allSalesmen;
    private ObservableList<User> allProjectManager;
    private UserManager userManager;
    private User user;
    private User createdUser;

    public UserModel() throws Exception {
        userManager = new UserManager();
        allTechnicians = FXCollections.observableArrayList();
        allSalesmen = FXCollections.observableArrayList();
        allProjectManager = FXCollections.observableArrayList();
        allTechnicians.addAll(userManager.loadUserOfAKind(4));
        allSalesmen.addAll(userManager.loadUserOfAKind(3));
        allProjectManager.addAll(userManager.loadUserOfAKind(2));

        System.out.println(allTechnicians.size());
    }

    public List<User> loadUser(String name)throws Exception{return userManager.loadUser(name);}

    public ObservableList<User> getAllTechnicians() {return allTechnicians;}

    public ObservableList<User> getallSalesmen() {return allSalesmen;}


    public ObservableList<User> getallProjectManagers() {return allProjectManager;}

    public boolean validateUsername(String username) throws Exception{return userManager.validateUsername(username);}

    public void createNewUser(String firstName, String lastName, String username, String password, int role) throws Exception {
        createdUser =  userManager.createNewUser(firstName, lastName, username, password, role);
        allTechnicians.add(createdUser);
        allTechnicians.clear();
        allTechnicians.addAll(userManager.loadUserOfAKind(role));
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

