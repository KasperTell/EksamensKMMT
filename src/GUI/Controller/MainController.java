package GUI.Controller;

import BE.Customer;
import BE.Project;
import BE.ProjectFiles;
import BE.User;
import GUI.Model.*;
import PersonsTypes.PersonTypeChooser;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

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
    private TextArea NotesTextArea;

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

    /**
     * Set up the view when the view is getting shown.
     */
    @Override
    public void setup() {
        //Initializing all our models.
        userModel = getModel().getUserModel();
        customerModel = getModel().getCustomerModel();
        projectFilesModel = getModel().getProjectFilesModel();
        projectModel = getModel().getProjectModel();
        //Setting the information of the listviews and combobox.
        lstTechnicians.setItems(userModel.getAllTechnicians());
        lstProjectManagers.setItems(userModel.getallProjectManagers());
        lstSalesPersons.setItems(userModel.getallSalesmen());
        cbxCustomer.setItems(customerModel.getAllCustomers());
        turnButtonONOrOff();
        setProjectColumns();
        listenerLstAllCloseProjects();
        listenerLstAllOpenProjects();
        listenerMouseClickPicture();

    }

    /**
     * Listener for the listview containing closed projects.
     */
    @FXML
    private void listenerLstAllCloseProjects() {
        closeProjectsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedProject = closeProjectsTable.getSelectionModel().getSelectedItem();
            try {
                setUpCustomer();
                setupFiles();
                setupTechniciansOnProject();
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });
    }

    /**
     * Listener for the listview containing open projects.
     */
    @FXML
    private void listenerLstAllOpenProjects() {
        openProjectsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedProject = openProjectsTable.getSelectionModel().getSelectedItem();
            try {
                setUpCustomer();
                setupFiles();
                setupTechniciansOnProject();
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });

    }

    /**
     * Listener for the tableview containing files for opening files.
     */
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

    /**
     * Open a file with the standard desktop program.
     */
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
                displayError(e);
                e.printStackTrace();
            }
    }

    /**
     * Set up the files information column in the tableview.
     */
    @FXML
    private void setupFiles() {
        if (selectedProject != null) {
            int projectNumber = selectedProject.getId();
            filesPictureColoum.setCellValueFactory(new PropertyValueFactory<>("picture"));
            filesFilenameColoum.setCellValueFactory(new PropertyValueFactory<>("name"));
            filesDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            filesInReport.setCellValueFactory(new PropertyValueFactory<>("usedBox"));

            try {
                fileTable.setItems(projectFilesModel.getAllFilesFromProject(projectNumber));
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
            projectFilesModel.isRunningFalse(); //Stopper tidligere løkker i projectFiles
            projectFilesModel.observer();
        }
    }

    /**
     * Set the information in the project column in the tableview.
     */
    private void setProjectColumns() {

        projectDateOpen.setCellValueFactory(new PropertyValueFactory<>("date"));
        projectOpenCustomer.setCellValueFactory(new PropertyValueFactory<>("title"));
        projectCloseDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        projectCloseCustomer.setCellValueFactory(new PropertyValueFactory<>("title"));

        openProjectsTable.setItems(projectModel.getAllProjectsOpen());
        closeProjectsTable.setItems(projectModel.getAllProjectsClose());


    }

    public void handleOpenCustomerDoc(ActionEvent actionEvent) {
    }

    /**
     *Handle what happens when the "close project" button is clicked.
     *Closing a project by changing the bit in the database from 0 to 1 for a specific project.
     * @param actionEvent
     */
    @FXML
    private void closeProjectAction(ActionEvent actionEvent) {
        //Setting the data for the variables and calls the method from the model.
        int closeProject = 1;
        int id = selectedProject.getId();
        selectedProject = null;
        try {
            projectModel.changeProjectStatus(closeProject, id);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * Handle what happens when the "re-open project" button is clicked.
     * Re-opening a project by changing the bit in the database from 1 to 0 for a specific project.
     * @param actionEvent
     */
    @FXML
    private void reopenProjectAction(ActionEvent actionEvent) {
        //Setting the data for the variables and calls the method from the model.
        int reOpenProject = 0;
        int id = selectedProject.getId();
        selectedProject = null;
        try {
            projectModel.changeProjectStatus(reOpenProject, id);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    public void openFileAction(ActionEvent actionEvent) {
    }

    public void saveNoteAction(ActionEvent actionEvent) {
    }

    public void newUserAction(ActionEvent actionEvent) {
    }

    public void removeUserAction(ActionEvent actionEvent) {

    }

    /**
     * Handle what happens when the "Add technician" button is clicked.
     * Adds a selected user/employee to a selected project.
     * @param actionEvent
     */
    @FXML
    private void addTechnicianAction(ActionEvent actionEvent) {
        //Setting the data for the variables and calls the method from the model.
        int projectID = selectedProject.getId();
        int technicanID = lstTechnicians.getSelectionModel().getSelectedItem().getId();
        try {
            userModel.moveTechnician(technicanID, projectID);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * Handle what happens when the "Remove technician" button is clicked.
     * Removes a selected user/employee from a selected project.
     * @param actionEvent
     */
    @FXML
    private void removeTechnicianAction(ActionEvent actionEvent) {
        //Setting the data for the variables and calls the method from the model.
        User selectedTechnician = lstTechniciansOnCase.getSelectionModel().getSelectedItem();
        int projectID = selectedProject.getId();
        try {
            userModel.removeTechnicianFromProject(selectedTechnician, projectID);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * Handle what happens when the "New project" button is clicked.
     * Opens a Vbox containing the text-fields, combobox and button for adding a new project.
     */
    @FXML
    private void newProjectAction() {
        //Initializing a new transition.
        TranslateTransition transition = new TranslateTransition();
        vbxCreateNewProject.toFront();
        transition.setNode(vbxCreateNewProject);
        transition.setDuration(Duration.millis(150));

        //If the Vbox is not shown, show it and set the background out of focus.
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
            //else remove the vbox and set the main view back in focus.
        } else {
            isMenuOpen = false;
            transition.setToX(-400);
            acpMainView.setOpacity(1);
        }
        transition.play();
    }

    /**
     * Handle what happens when the "new customer" button is clicked.
     * Opens a vbox containing the text-fields and button needed for making a new customer.
     */
    @FXML
    private void newCustomerAction() {
        //Initializing a new transition.
        TranslateTransition transition = new TranslateTransition();
        vbxCreateNewCustomer.toFront();
        transition.setNode(vbxCreateNewCustomer);
        transition.setDuration(Duration.millis(150));
        //If the Vbox is not shown, show it and set the background out of focus.
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
            //Else remove the vbox from the view and set the main view back in focus.
        } else {
            isMenuOpen = false;
            transition.setToX(-400);
            acpMainView.setOpacity(1);
        }
        transition.play();
    }

    /**
     * Set up the information about the customer in the main view when a project is selected.
     */
    @FXML
    private void setUpCustomer() {
        if (selectedProject != null) {
            //Setting the information in the labels.
            int customerID = selectedProject.getCustomerID();
            Customer customer = null;
            try {
                customer = customerModel.loadCustomer(customerID);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }

            name.setText(customer.getFirstName());
            address.setText(customer.getAddress());
            zipCode.setText(String.valueOf(customer.getZipCode()));
            email.setText(customer.getMail());
            telephone.setText(String.valueOf(customer.getPhoneNumber()));
        }
    }

    /**
     * Set up the information in the technician column in the table view, based on a selected project.
     */
    private void setupTechniciansOnProject() {
        if (selectedProject != null) {
            int projectId = selectedProject.getId();
            try {
                lstTechniciansOnCase.setItems(userModel.getAllTechniciansOnProject(projectId));
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }
    }

    /**
     * Save a new file from the users pc.
     * @param actionEvent
     */
    @FXML
    private void handleSaveNewFile(ActionEvent actionEvent) {
        //Opens the default file explore
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File explore");
        //Filter what type of files should be shown in the file explore.
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*jpeg");
        fileChooser.getExtensionFilters().add(extensionFilter);
        file = fileChooser.showOpenDialog(stage);
        //If a file is selected. Copy it to the resource folder.
        if (file != null) {
            try {
                Files.copy(file.toPath(), target.resolve(file.toPath().getFileName()), REPLACE_EXISTING);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
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

    /**
     * Handle what happens when the "Add new project" button is clicked.
     * Initializing a project for inserting in the database.
     * @param actionEvent
     */
    @FXML
    private void handleAddNewProject(ActionEvent actionEvent) {
        //Setting the data in the variables.
        int id = 1;
        String title = txtfProjectName.getText();
        int customerID = cbxCustomer.getSelectionModel().getSelectedItem().getId();
        LocalDate date = LocalDate.now();
        boolean isOpen = true;
        //Initializing the project.
        Project project = new Project(id, title, customerID, date, isOpen);
        try {
            //Send the project to the database.
            projectModel.createNewProject(project);
        } catch (SQLException e) {
            displayError(e);
            e.printStackTrace();
        }
        //Close the vbox.
        newProjectAction();
    }

    /**
     * When a key is pressed in the search text-field, update the listview showing projects.
     * @param keyEvent
     */
    @FXML
    private void searchProjectsByStringQuery(KeyEvent keyEvent) {
        String query = searchBox.getText();
        openProjectsTable.getItems().clear();
        try {
            openProjectsTable.setItems(projectModel.searchByQuery(query));
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * Handle what happens when the "Add new customer" button is clicked.
     * Initializing a new customer to be inserted in the database.
     * @param actionEvent
     */
    public void handleAddNewCustomer(ActionEvent actionEvent) {
        //Setting the data in the variables.
        int id = 1;
        String firstName = txtfCustomerFirstName.getText();
        String lastName = txtfCustomerLastName.getText();
        String companyName = txtfcompanyName.getText();
        String customerAddress = txtfAddress.getText();
        String mail = txtfEmail.getText();
        int phoneNumber = Integer.parseInt(txtfPhoneNumber.getText());
        int customerZipCode = Integer.parseInt(txtfZipCode.getText());
        //Initializing the customer.
        Customer customer = new Customer(id, firstName, lastName, companyName, customerAddress, mail, phoneNumber, customerZipCode);
        try {
            //Sending the customer to the database.
            customerModel.createNewCustomer(customer);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
        //Close the vbox.
        newCustomerAction();
    }


    public void handleDraw(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/Paint/PaintView.fxml"));
        Parent root = loader.load();

        PaintController controller = loader.getController();

        Stage stage = new Stage();

        stage.setScene(new Scene(root));
        stage.show();
    }
}
