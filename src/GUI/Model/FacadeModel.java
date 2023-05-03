package GUI.Model;

import BLL.ProjectFilesManager;

public class FacadeModel {

    private UserModel userModel;

    private ProjectFilesModel projectFilesModel;
    private ProjectModel projectModel;
    private CustomerModel customerModel;

    private ProjectModel projectModel;


    public FacadeModel() throws Exception{
        projectFilesModel = new ProjectFilesModel();
        projectModel = new ProjectModel();
        customerModel = new CustomerModel();
        userModel = new UserModel();
        projectModel = new ProjectModel();

    }

    public ProjectFilesModel getProjectFilesModel(){return projectFilesModel;}
    public void setProjectFilesModel(ProjectFilesModel projectFilesModel){this.projectFilesModel = projectFilesModel;}

    public ProjectModel getProjectModel(){return projectModel;}
    public void setProjectModel(ProjectModel projectModel){this.projectModel = projectModel;}

    public CustomerModel getCustomerModel(){return customerModel;}
    public void setCustomerModel(CustomerModel customerModel){this.customerModel = customerModel;}
    public UserModel getUserModel(){return userModel;}
    public ProjectModel getProjectModel(){return projectModel;}
    public void setUserModel(UserModel userModel){this.userModel = userModel;}
    public void setProjectModel(ProjectModel projectModel){this.projectModel = projectModel;}
}
