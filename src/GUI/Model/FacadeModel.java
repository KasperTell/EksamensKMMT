package GUI.Model;

public class FacadeModel {

    private UserModel userModel;

    public FacadeModel() throws Exception{

        userModel = new UserModel();
    }


    public UserModel getUserModel(){return userModel;}
    public void setUserModel(UserModel userModel){this.userModel = userModel;}

}
