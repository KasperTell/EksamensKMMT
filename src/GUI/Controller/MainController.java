package GUI.Controller;

import BE.Project;
import BE.User;
import GUI.Model.ProjectModel;
import GUI.Model.UserModel;
import PersonsTypes.PersonTypeChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class MainController extends BaseController {
    @FXML
    private TableColumn projectDateOpen,projectOpenCustomer,projectCloseDate,projectCloseCustomer;

    @FXML
    private Button closeProject,reOpenProject,openFile,btnSaveNewFile,saveNote,newProject,newUser,removeUser,newCustomer,addTechnician,removeTechnician;

    @FXML
    private Tab openProjects;
    @FXML
    private TableView<Project> openProjectsTable,closeProjectsTable;

    @FXML
    private TableView fileTable;
    @FXML
    private ListView lstTechniciansOnCase;
    @FXML
    private ListView<User> lstTechnicians;

    @FXML
    private ListView<User> lstProjectManagers;
    @FXML
    private ListView<User> lstSalesPersons;
    @FXML
    private Label email,zipCode,address,name,city,telephone,customerHeader;
    @FXML
    private TextField txtfSearchField;
    @FXML
    private TextArea NotesTextArea;
    @FXML
    private Button btnCustomerInfo;

    private File file;
    private String filePath = "Resources/Pictures/ImagesSavedFromTechnicians";
    private Path target = Paths.get(filePath);
    private UserModel userModel;
    private ProjectModel projectModel;

    PersonTypeChooser personTypeChooser=new PersonTypeChooser();


    @Override
    public void setup() throws Exception {
        userModel = getModel().getUserModel();
        lstTechnicians.setItems(userModel.getAllTechnicians());
        lstProjectManagers.setItems(userModel.getallProjectManagers());
        lstSalesPersons.setItems(userModel.getallSalesmen());
        turnButtonONOrOff();
        setProjectColoums();
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
}
