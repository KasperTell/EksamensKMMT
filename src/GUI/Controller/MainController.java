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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class MainController extends BaseController {

    @FXML
    private ComboBox<Customer> cbxCustomer;
    @FXML
    private VBox vbxCreateNewProject, vbxCreateNewCustomer;
    @FXML
    private AnchorPane acpMainView;

    @FXML
    private TableColumn projectDateOpen, projectOpenCustomer, projectCloseDate, projectCloseCustomer, filesPictureColoum, filesFilenameColoum, filesDate, filesInReport;

    @FXML
    private Button closeProject,reOpenProject,openFile,btnSaveNewFile,saveNote,newProject,newUser,removeUser,newCustomer,addTechnician,removeTechnician, btnAddNewProject, btnCustomerInfo, btnAddNewCustomer;
    @FXML
    private Tab openProjects;
    @FXML
    private TableView<Project> openProjectsTable,closeProjectsTable;
    @FXML
    private TableView<ProjectFiles> fileTable;
    @FXML
    private ListView<User> lstProjectManagers,lstTechnicians,lstSalesPersons, lstTechniciansOnCase;

    @FXML
    private Label email, zipCode, address, name, city, telephone, customerHeader;
    @FXML
    private TextField txtfSearchField, txtfProjectName, txtfPhoneNumber, txtfEmail, txtfZipCode, txtfAddress, txtfcompanyName, txtfCustomerLastName, txtfCustomerFirstName, searchBox;
    @FXML
    private TextArea txtaNote;

    private Project selectedProject;
    private ProjectFiles selectedfile;

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
        listenerMouseClickPicture();

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
            System.out.println(selectedProject.getId());
            System.out.println(selectedProject.getCustomernumber());
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


    public void listenerMouseClickPicture()
    {
        fileTable.setOnMouseClicked(event -> {

             selectedfile = fileTable.getSelectionModel().getSelectedItem();


            if (event.getClickCount() == 2) { //Ved dobbeltklik kan man starte musikken
                try {
                   showFile();
                } catch (Exception e) {
                    displayError(e);
                }
            }
        });
    }

    private void showFile() {



            boolean filesExits = Files.exists(Path.of(selectedfile.getFilePath())); //check om filen eksisterer
            File file = new File(selectedfile.getFilePath());

            try {

                if (filesExits) {

                    Desktop desktop = Desktop.getDesktop();

                    if (file.exists()) desktop.open(file);
                } //else
                else
                    System.out.println("Virker ikke");
                // informationUser("File do not exist!");
                // Her kaldes en metode, der viser et vindue med besked om, at filen ikke findes.
                // Text file, should be opening in default text editor

            } catch (Exception e) {
                throw new RuntimeException(e);
            }


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

    public void saveNoteAction(ActionEvent actionEvent) throws Exception {
        String note = txtaNote.getText();
        int projectId = selectedProject.getId();
        projectModel.saveNote(note, projectId);
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



    public void removeTechnicianAction(ActionEvent actionEvent) throws Exception{
        User selectedTechnician = lstTechniciansOnCase.getSelectionModel().getSelectedItem();
        int projectID = selectedProject.getId();
        userModel.removeTechnicianFromProject(selectedTechnician, projectID);


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

    public void newCustomerAction() {
        TranslateTransition transition = new TranslateTransition();
        vbxCreateNewCustomer.toFront();
        transition.setNode(vbxCreateNewCustomer);
        transition.setDuration(Duration.millis(150));

        if(!isMenuOpen){
            isMenuOpen = true;
            transition.setToX(0);
            acpMainView.setOpacity(0.2);
            EventHandler<MouseEvent> menuHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    newCustomerAction();
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
        String note = txtaNote.getText();
        Project project = new Project(id, title, customerID, date, isOpen, note);
        projectModel.createNewProject(project);

        newProjectAction();
    }


    public void searchProjectsByStringQuery(KeyEvent keyEvent) throws Exception {
        String query = searchBox.getText();
        //testListView.setItems(projectModel.searchByQuery(query));
        openProjectsTable.getItems().clear();
        openProjectsTable.setItems(projectModel.searchByQuery(query));
    }
    public void handleAddNewCustomer(ActionEvent actionEvent) throws Exception {
        int id = 1;
        String firstName = txtfCustomerFirstName.getText();
        String lastName = txtfCustomerLastName.getText();
        String companyName = txtfcompanyName.getText();
        String customerAddress = txtfAddress.getText();
        String mail = txtfEmail.getText();
        int phoneNumber = Integer.parseInt(txtfPhoneNumber.getText());
        int customerZipCode = Integer.parseInt(txtfZipCode.getText());
        Customer customer = new Customer(id, firstName, lastName, companyName, customerAddress, mail, phoneNumber, customerZipCode);
        customerModel.createNewCustomer(customer);

        newCustomerAction();
    }
}
