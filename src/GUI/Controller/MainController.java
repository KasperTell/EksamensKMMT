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
import PersonsTypes.Technician;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class MainController extends BaseController {

    public Button btnAddNewProject;
    public ComboBox<Customer> cbxCustomer;
    public TextField txtfProjectName, searchBox;
    public Label customerHeader1;
    public VBox vbxCreateNewProject;
    public AnchorPane acpMainView;
    @FXML
    private TableColumn projectDateOpen, projectOpenCustomer, projectCloseDate, projectCloseCustomer, filesPictureColoum, filesFilenameColoum, filesDate, filesInReport;

    @FXML
    private Button closeProject, reOpenProject, openFile, btnSaveNewFile, saveNote, newProject, newUser, removeUser, newCustomer, addTechnician, removeTechnician;

    @FXML
    private Tab openProjects;
    @FXML
    private TableView<Project> openProjectsTable, closeProjectsTable;

    @FXML
    private TableView<ProjectFiles> fileTable;
    @FXML
    private ListView<User> lstTechniciansOnCase;

    @FXML
    private ListView<User> lstProjectManagers, lstTechnicians, lstSalesPersons;

    @FXML
    private Label email, zipCode, address, name, city, telephone, customerHeader;
    @FXML
    private TextField txtfSearchField;
    @FXML
    private TextArea NotesTextArea;
    @FXML
    private Button btnCustomerInfo;

    private Project selectedProject;
    private ProjectFiles selectedFiles;

    private File file;
    private String filePath = "Resources/Pictures/ImagesSavedFromTechnicians";
    private Path target = Paths.get(filePath);
    private UserModel userModel;
    private ProjectModel projectModel;
    private CustomerModel customerModel;
    private ProjectFilesModel projectFilesModel;
    private boolean isMenuOpen;


    PersonTypeChooser personTypeChooser = new PersonTypeChooser();


    @Override
    public void setup() throws Exception {
        userModel = getModel().getUserModel();
        customerModel = getModel().getCustomerModel();
        projectFilesModel = getModel().getProjectFilesModel();
        lstTechnicians.setItems(userModel.getAllTechnicians());
        lstProjectManagers.setItems(userModel.getallProjectManagers());
        lstSalesPersons.setItems(userModel.getallSalesmen());
        cbxCustomer.setItems(customerModel.getAllCustomers());
        turnButtonONOrOff();
        setProjectColoums();
        listenerLstAllCloseProjects();
        listenerLstAllOpenProjects();
//        listenerFilesMarkedOrNot();
    }


    @FXML
    private void listenerLstAllCloseProjects() {
        closeProjectsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedProject = closeProjectsTable.getSelectionModel().getSelectedItem();
            try {
                setUpCustomer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                setupFiles();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                setupTechniciansOnProject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void listenerLstAllOpenProjects() {
        openProjectsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {

            selectedProject = openProjectsTable.getSelectionModel().getSelectedItem();
            try {
                setUpCustomer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                setupFiles();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                setupTechniciansOnProject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    @FXML
    private void setupFiles() throws Exception {
        if (selectedProject != null) {
            int projectNumber = selectedProject.getId();
            filesPictureColoum.setCellValueFactory(new PropertyValueFactory<>("picture"));

            filesFilenameColoum.setCellValueFactory(new PropertyValueFactory<>("name"));
            filesDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            filesInReport.setCellValueFactory(new PropertyValueFactory<>("usedBox"));


            fileTable.setItems(projectFilesModel.getAllFilesFromProject(projectNumber));


            projectFilesModel.observer();
        }
    }


    private void setProjectColoums() throws Exception {

        projectModel = new ProjectModel();

        projectDateOpen.setCellValueFactory(new PropertyValueFactory<>("date"));
        projectOpenCustomer.setCellValueFactory(new PropertyValueFactory<>("title"));
        projectCloseDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        projectCloseCustomer.setCellValueFactory(new PropertyValueFactory<>("title"));

        openProjectsTable.setItems(projectModel.getAllProjectsOpen());
        closeProjectsTable.setItems(projectModel.getAllProjectsClose());


    }

    public void handleOpenCustomerDoc(ActionEvent actionEvent) {
    }

    public void closeProjectAction(ActionEvent actionEvent) throws Exception {
        int closeProject = 1;
        int id = selectedProject.getId();
        selectedProject = null;
        projectModel.changeProjectStatus(closeProject, id);
    }

    public void reopenProjectAction(ActionEvent actionEvent) throws Exception {
        int reOpenProject = 0;
        int id = selectedProject.getId();
        selectedProject = null;
        projectModel.changeProjectStatus(reOpenProject, id);
    }

    public void openFileAction(ActionEvent actionEvent) {
    }

    public void saveNoteAction(ActionEvent actionEvent) {
    }

    public void newUserAction(ActionEvent actionEvent) {
    }

    public void removeUserAction(ActionEvent actionEvent) {
    }

    public void addTechnicianAction(ActionEvent actionEvent) throws Exception {
        int projectID = selectedProject.getId();
        int technicanID = lstTechnicians.getSelectionModel().getSelectedItem().getId();

        System.out.println(projectID);
        System.out.println(technicanID);

        userModel.moveTechnician(technicanID, projectID);
    }


    public void removeTechnicianAction(ActionEvent actionEvent) {

    }

    public void newProjectAction() {
        TranslateTransition transition = new TranslateTransition();
        vbxCreateNewProject.toFront();
        transition.setNode(vbxCreateNewProject);
        transition.setDuration(Duration.millis(150));

        if (!isMenuOpen) {
            isMenuOpen = true;
            transition.setToX(0);
            acpMainView.setOpacity(0.2);
            EventHandler<MouseEvent> menuHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    newProjectAction();
                    acpMainView.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);

                }
            };
            acpMainView.addEventHandler(MouseEvent.MOUSE_CLICKED, menuHandler);
        } else {
            isMenuOpen = false;
            transition.setToX(-400);
            acpMainView.setOpacity(1);
        }
        transition.play();
    }

    public void newCustomerAction(ActionEvent actionEvent) {
    }

    @FXML
    private void setUpCustomer() throws Exception {

        if (selectedProject != null) {
            int customerID = selectedProject.getCustomernumber();
            Customer customer = customerModel.loadCustomer(customerID);

            name.setText(customer.getFirstName());
            address.setText(customer.getAddress());
            zipCode.setText(String.valueOf(customer.getZipCode()));
            email.setText(customer.getMail());
            telephone.setText(String.valueOf(customer.getPhoneNumber()));
        }
    }

    private void setupTechniciansOnProject() throws Exception {
        if (selectedProject != null) {
            int projectId = selectedProject.getId();
            lstTechniciansOnCase.setItems(userModel.getAllTechniciansOnProject(projectId));
        }
    }

    @FXML
    private void handleSaveNewFile(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File explore");

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                Files.copy(file.toPath(), target.resolve(file.toPath().getFileName()), REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void turnButtonONOrOff() {

        Boolean[] turnButtonOnOrOff = personTypeChooser.turnButtonOnOrOff();

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


    public void handleAddNewProject(ActionEvent actionEvent) throws SQLException {
        int id = 1;
        String title = txtfProjectName.getText();
        int customerID = cbxCustomer.getSelectionModel().getSelectedItem().getId();
        LocalDate date = LocalDate.now();
        boolean isOpen = true;
        Project project = new Project(id, title, customerID, date, isOpen);
        projectModel.createNewProject(project);

        newProjectAction();
    }

    public void searchProjectsByStringQuery(KeyEvent keyEvent) throws Exception {
        String query = searchBox.getText();
        //testListView.setItems(projectModel.searchByQuery(query));
        openProjectsTable.getItems().clear();
        openProjectsTable.setItems(projectModel.searchByQuery(query));
    }
}
