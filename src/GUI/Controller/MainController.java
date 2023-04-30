package GUI.Controller;

import BE.Customer;
import BE.Project;
import BE.ProjectFiles;
import BE.User;
import GUI.Model.ProjectFilesModel;
import GUI.Model.ProjectModel;
import GUI.Model.UserModel;
import GUI.Model.CustomerModel;
import PersonsTypes.PersonTypeChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class MainController extends BaseController {

    @FXML
    private TableColumn projectDateOpen,projectOpenCustomer,projectCloseDate,projectCloseCustomer,filesPictureColoum,filesFilenameColoum,filesDate,filesInReport;

    @FXML
    private Button closeProject,reOpenProject,openFile,btnSaveNewFile,saveNote,newProject,newUser,removeUser,newCustomer,addTechnician,removeTechnician;

    @FXML
    private Tab openProjects;
    @FXML
    private TableView<Project> openProjectsTable,closeProjectsTable;

    @FXML
    private TableView<ProjectFiles> fileTable;
    @FXML
    private ListView lstTechniciansOnCase;

    @FXML
    private ListView<User> lstProjectManagers,lstTechnicians,lstSalesPersons;

    @FXML
    private Label email,zipCode,address,name,city,telephone,customerHeader;
    @FXML
    private TextField txtfSearchField;
    @FXML
    private TextArea NotesTextArea;
    @FXML
    private Button btnCustomerInfo;

    private Project selectedProject;


    private File file;
    private String filePath = "Resources/Pictures/ImagesSavedFromTechnicians";
    private Path target = Paths.get(filePath);
    private UserModel userModel;
    private ProjectModel projectModel;

    private ProjectFilesModel projectFilesModel;


    PersonTypeChooser personTypeChooser=new PersonTypeChooser();


    @Override
    public void setup() throws Exception {
        userModel = getModel().getUserModel();
        lstTechnicians.setItems(userModel.getAllTechnicians());
        lstProjectManagers.setItems(userModel.getallProjectManagers());
        lstSalesPersons.setItems(userModel.getallSalesmen());
        turnButtonONOrOff();
        setProjectColoums();
        listenerLstAllCloseProjects();
        listenerLstAllOpenProjects();
    }


    @FXML
    private void listenerLstAllCloseProjects() {
        closeProjectsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedProject  = closeProjectsTable.getSelectionModel().getSelectedItem();
            try {
                setupFiles();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void listenerLstAllOpenProjects() {
        openProjectsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {

            selectedProject = openProjectsTable.getSelectionModel().getSelectedItem();
            try {
                setupFiles();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

    }

    @FXML
    private void setupFiles() throws Exception {
        projectFilesModel=new ProjectFilesModel();
        int projectNumber=selectedProject.getId();
        //filesPictureColoum.setCellValueFactory(new PropertyValueFactory<>("date"));
        filesFilenameColoum.setCellValueFactory(new PropertyValueFactory<>("name"));
        filesDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        //filesInReport.setCellValueFactory(new PropertyValueFactory<>("date"));

        fileTable.setItems(projectFilesModel.getAllFilesFromProject(projectNumber));
    }













    private void setProjectColoums() throws Exception {

        projectModel=new ProjectModel();

        projectDateOpen.setCellValueFactory(new PropertyValueFactory<>("date"));
        projectOpenCustomer.setCellValueFactory(new PropertyValueFactory<>("title"));
        projectCloseDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        projectCloseCustomer.setCellValueFactory(new PropertyValueFactory<>("title"));

        openProjectsTable.setItems(projectModel.getAllProjectsOpen());
        closeProjectsTable.setItems(projectModel.getAllProjectsClose());


    }





    public void handleOpenCustomerDoc(ActionEvent actionEvent) {
    }

    public void closeProjectAction(ActionEvent actionEvent) {
    }

    public void reopenProjectAction(ActionEvent actionEvent) {
    }

    public void openFileAction(ActionEvent actionEvent) {
    }

    public void saveNoteAction(ActionEvent actionEvent) {
    }

    public void newUserAction(ActionEvent actionEvent) {
    }

    public void removeUserAction(ActionEvent actionEvent) {
    }

    public void addTechnicianAction(ActionEvent actionEvent) {
    }

    public void removeTechnicianAction(ActionEvent actionEvent) {
    }

    public void newProjectAction(ActionEvent actionEvent) {
    }

    public void newCustomerAction(ActionEvent actionEvent) {
        List<Customer> customer = new ArrayList<>();
        // Create a new instance of the customer class
        Customer newCustomer = new Customer();

        //set the properties of the new Customer
        newCustomer.setFirstName(name.getText());
        newCustomer.setAddress(address.getText());
        newCustomer.setZipCode(Integer.parseInt(zipCode.getText()));
        newCustomer.setCity(city.getText());
        newCustomer.setMail(email.getText());
        newCustomer.setPhoneNumber(Integer.parseInt(telephone.getText()));

        // Add the new customer to your systems list of customer
        customer.add(newCustomer);

        // Update the UI to display the new customer's information
        projectOpenCustomer.setText(newCustomer.getFirstName());
        name.setText(newCustomer.getFirstName());
        address.setText(newCustomer.getAddress());
        zipCode.setText(String.valueOf(newCustomer.getZipCode()));
        city.setText(newCustomer.getCity());
        email.setText(newCustomer.getMail());
        telephone.setText(String.valueOf(newCustomer.getPhoneNumber()));
    }
    @FXML
    private void handleSaveNewFile(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File explore");

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            try{
                Files.copy(file.toPath(), target.resolve(file.toPath().getFileName()), REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void turnButtonONOrOff() {

        Boolean[] turnButtonOnOrOff=personTypeChooser.turnButtonOnOrOff();

        closeProject.setDisable(turnButtonOnOrOff[0]);
        reOpenProject.setDisable(turnButtonOnOrOff[1]);
        openFile.setDisable(turnButtonOnOrOff[2]);
        btnSaveNewFile.setDisable(turnButtonOnOrOff[3]);
        saveNote.setDisable(turnButtonOnOrOff[4]);
        newProject.setDisable(turnButtonOnOrOff[5]);
        newUser.setDisable(turnButtonOnOrOff[6]);
        removeUser.setDisable(turnButtonOnOrOff[7]);
        newCustomer.setDisable(turnButtonOnOrOff[8]);
        addTechnician.setDisable(turnButtonOnOrOff[9]);
        removeTechnician.setDisable(turnButtonOnOrOff[10]);

    }


}
