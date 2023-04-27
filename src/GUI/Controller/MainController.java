package GUI.Controller;

import BE.User;
import GUI.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController extends BaseController {
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


    @Override
    public void setup() throws Exception {
        userModel = getModel().getUserModel();
        lstTechnicians.setItems(userModel.getAllTechnicians());
        lstProjectManagers.setItems(userModel.getallProjectManagers());
        lstSalesPersons.setItems(userModel.getallSalesmen());
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
}
