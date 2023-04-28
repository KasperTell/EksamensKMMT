package GUI.Controller;

import BE.User;
import GUI.Model.UserModel;
import PersonsTypes.PersonTypeChooser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController extends BaseController {
    @FXML
    private Button closeProject,reOpenProject,openFile,saveFile,saveNote,newProject,newUser,removeUser,newCustomer,addTechnician,removeTechnician;

    @FXML
    private Tab openProjects;
    @FXML
    private TableView OpenProjectsTable;
    @FXML
    private TableView closeProjectsTable;
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

    private UserModel userModel;

    PersonTypeChooser personTypeChooser=new PersonTypeChooser();


    @Override
    public void setup() throws Exception {
        userModel = getModel().getUserModel();
        lstTechnicians.setItems(userModel.getAllTechnicians());
        lstProjectManagers.setItems(userModel.getallProjectManagers());
        lstSalesPersons.setItems(userModel.getallSalesmen());
        turnButtonONOrOff();
    }

    private void turnButtonONOrOff() {

        Boolean[] turnButtonOnOrOff=personTypeChooser.turnButtonOnOrOff();

       closeProject.setDisable(turnButtonOnOrOff[0]);
       reOpenProject.setDisable(turnButtonOnOrOff[1]);
       openFile.setDisable(turnButtonOnOrOff[2]);
       saveFile.setDisable(turnButtonOnOrOff[3]);
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

    public void saveFileAction(ActionEvent actionEvent) {
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
}
