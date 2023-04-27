package BLL;

import BE.User;
import DAL.IUserDataAccess;
import DAL.UserDAO;

import java.io.IOException;
import java.util.List;

public class UserManager {

    private IUserDataAccess userDAO;

    public UserManager() throws IOException {userDAO = new UserDAO();}

    public List<User> loadUser(String name) throws Exception{return userDAO.loadUser(name);}

    public List<User> loadTechnicians() throws Exception{return userDAO.loadTechnicians();}

    public User createNewUser(String firstName, String lastName, String username, String password, int role) throws Exception {return userDAO.createNewUser(firstName, lastName, username, password, role);}

    public void deleteUser(User selectedUser) throws Exception {userDAO.deleteUser(selectedUser);}

    public boolean validateUsername(String username) throws Exception{return userDAO.validateUsername(username);}





}