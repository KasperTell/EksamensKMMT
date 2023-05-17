package GUI.Controller;

import BE.*;
import GUI.Model.*;
import PersonsTypes.PersonTypeChooser;
import UTIL.ShowFile;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

public class NyController extends BaseController {

    public Button addNewProjectButton;
    public Button btnAddNewCustomer;
    @FXML
    private Tab openProjectsTab, closedProjectsTab;
    @FXML //Main view anchor pane
    private AnchorPane mainViewAnchorPane;

    @FXML //Anchor pane for the slide in menus. Enables opacity play.
    private AnchorPane vBoxAnchorPane;

    @FXML //Tableviews for open and closed projects
    private TableView<Project> openProjectsTable,closedProjectsTable;

    @FXML //All columns for the projects table
    private TableColumn projectNameClosed, projectDateClosed, customerNameClosed, projectNameOpen,customerProjectOpen, projectDateOpen;
    @FXML//Tableview for customers
    public TableView<Customer> customerTable;

    @FXML //All columns for the customer table
    private TableColumn customerMailClm, customerPhoneClm, customerZipClm, customerAddressClm, customerNameClm;

    @FXML //All buttons for the main view
    private Button newProjectButton, reOpenProjectButton, closeProjectButton, newCustomerButton, openProjectWindowButton, openUserWindowButton;

    @FXML //Slide in windows for creating a project or customer
    private VBox vbxCreateNewProject, vbxCreateNewCustomer;

    @FXML //Combo box for selecting a customer, when adding a new project
    private ComboBox<Customer> customerComboBox;

    @FXML //Text-Area containing project notes
    private TextArea NotesTextArea;

    @FXML //All text fields
    private TextField projectNameTextField, customerPhoneNumberTextField, customerEmailTextField, customerZipCodeTextField, customerAddressTextField, companyNameTextField, customerLastNameTextField, customerFirstNameTextField, searchBoxTextField;
    private Project selectedProject;
    private ProjectModel projectModel;
    private CustomerModel customerModel;
    private UserModel userModel;
    private ProjectController controller;
    private boolean isMenuOpen;
    private int projectNumber;
    private boolean onOpenProjectList;

    ShowFile showFile=new ShowFile();

    PersonTypeChooser personTypeChooser = new PersonTypeChooser();

    /**
     * Set up the view when the view is getting shown.
     */
    @Override
    public void setup() {
        //Initializing all our models.
        customerModel = getModel().getCustomerModel();
        projectModel = getModel().getProjectModel();
        userModel = getModel().getUserModel();
        //Setting the information of the listviews and combobox.
        customerComboBox.setItems(customerModel.getAllCustomers());
        projectDateOpen.setCellValueFactory(new PropertyValueFactory<>("Date"));
        projectNameOpen.setCellValueFactory(new PropertyValueFactory<>("Title"));
        customerProjectOpen.setCellValueFactory(new PropertyValueFactory<>("companyName"));

        projectDateClosed.setCellValueFactory(new PropertyValueFactory<>("Date"));
        projectNameClosed.setCellValueFactory(new PropertyValueFactory<>("Title"));
        customerNameClosed.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        try {
            openProjectsTable.setItems(projectModel.getAllProjectsOpen(userModel.getLoggedinUser().getId()));
        } catch (Exception e){
            e.printStackTrace();
            displayError(e);
        }
        closedProjectsTable.setItems(projectModel.getAllProjectsClose());
        turnButtonONOrOff();
        listenerLstAllCloseProjects();
        listenerLstAllOpenProjects();
        disableButtons();
        pictureToButton();
        NotesTextArea.setWrapText(true);
        NotesTextArea.setEditable(false);
        listenerMouseClickOpenProject();
        listenerMouseClickCloseProject();
    }

    private void pictureToButton() {
        String[] listOfFiles = {"Pictures/Add Project Button.png", "Pictures/Add Customer Button.png",
                "Pictures/Close Project Button.png","Pictures/Open PDF Button.png", "Pictures/Add Employee Button.PNG", "Pictures/Close Project Button.PNG"};

        String[] listOfToolTips = {"Add a new project", "Add a new customer",
                "Close a project", "Re-Open a project", "Opens up window for User editing", "Opens up window for project editing"};

        Button[] listOfButtons ={newProjectButton,newCustomerButton, closeProjectButton, reOpenProjectButton, openUserWindowButton, openProjectWindowButton};

        for (int i = 0; i < listOfFiles.length; i++) {

            Image img = new Image(listOfFiles[i]);
            ImageView view = new ImageView(img);
            Tooltip tip = new Tooltip(listOfToolTips[i]);

            listOfButtons[i].setGraphic(view);
            listOfButtons[i].setTooltip(tip);
        }
    }

    private void disableButtons() {
        reOpenProjectButton.setDisable(true);
        closeProjectButton.setDisable(true);
    }

    private void turnButtonONOrOff() {

        HashMap<String, Boolean> turnButtonOnOrOff = personTypeChooser.turnButtonOnOrOff();


        closeProjectButton.setDisable(turnButtonOnOrOff.get("closeProjectButton"));
        reOpenProjectButton.setDisable(turnButtonOnOrOff.get("reOpenProjectButton"));
        newProjectButton.setDisable(turnButtonOnOrOff.get("newProjectButton"));
        newCustomerButton.setDisable(turnButtonOnOrOff.get("newCustomerButton"));
        openProjectWindowButton.setDisable(turnButtonOnOrOff.get("openProjectWindowButton"));
        openUserWindowButton.setDisable(turnButtonOnOrOff.get("openUserWindowButton"));
        closedProjectsTable.setDisable(turnButtonOnOrOff.get("closedProjectsTable"));
       
    }
    /**
     * Listener for the tableview containing files for opening files.
     */
    public void listenerMouseClickOpenProject() {
        openProjectsTable.setOnMouseClicked(event -> {
            selectedProject = openProjectsTable.getSelectionModel().getSelectedItem();

            if (event.getClickCount() == 2) {
                try {
                    getCustomerPDFAction();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public void listenerMouseClickCloseProject() {
        closedProjectsTable.setOnMouseClicked(event -> {
            selectedProject = closedProjectsTable.getSelectionModel().getSelectedItem();

            if (event.getClickCount() == 2) {
                try {
                    getCustomerPDFAction();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }







    @FXML
    private void listenerLstAllCloseProjects() {
        closedProjectsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedProject = closedProjectsTable.getSelectionModel().getSelectedItem();
            projectNumber=closedProjectsTable.getSelectionModel().getSelectedIndex();

            if (selectedProject != null)
            {
                try {
                    setProjectColumns();
                } catch (Exception e) {
                    displayError(e);
                    e.printStackTrace();
                }
                reOpenProjectButton.setDisable(false);
                closeProjectButton.setDisable(true);
                NotesTextArea.setText(selectedProject.getNote());
            }
        });
    }

    @FXML
    private void listenerLstAllOpenProjects() {
        openProjectsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedProject = openProjectsTable.getSelectionModel().getSelectedItem();
            projectNumber=openProjectsTable.getSelectionModel().getFocusedIndex();

            if (selectedProject!=null)
            {
                try {
                    setProjectColumns();
                } catch (Exception e) {
                    displayError(e);
                    e.printStackTrace();
                }
                reOpenProjectButton.setDisable(true);
                closeProjectButton.setDisable(false);
                NotesTextArea.setText(selectedProject.getNote());
            }
        });

    }

    /**
     * Set the information in the project column in the tableview.
     */
    private void setProjectColumns() throws Exception {


        customerNameClm.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        customerAddressClm.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerZipClm.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        customerMailClm.setCellValueFactory(new PropertyValueFactory<>("Mail"));
        customerPhoneClm.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

      /**  openProjectsTable.setItems(projectModel.getAllProjectsOpen());
        closedProjectsTable.setItems(projectModel.getAllProjectsClose()); */
        customerTable.setItems(customerModel.loadCustomerList(selectedProject.getCustomerID()));
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

        try {
            projectModel.changeProjectStatus(reOpenProject, id);
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
            mainViewAnchorPane.setOpacity(0.2);
            EventHandler<MouseEvent> menuHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    newProjectAction();
                    mainViewAnchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);

                }
            };
            mainViewAnchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, menuHandler);
            //else remove the vbox and set the main view back in focus.
        } else {
            isMenuOpen = false;
            transition.setToX(-345);
            mainViewAnchorPane.setOpacity(1);
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
            transition.setToX(855);
            mainViewAnchorPane.setOpacity(0.2);
            EventHandler<MouseEvent> menuHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    newCustomerAction();
                    mainViewAnchorPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);

                }
            };
            mainViewAnchorPane.addEventHandler(MouseEvent.MOUSE_CLICKED, menuHandler);
            //Else remove the vbox from the view and set the main view back in focus.
        } else {
            isMenuOpen = false;
            transition.setToX(1200);
            mainViewAnchorPane.setOpacity(1);
        }
        transition.play();
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
        String title = projectNameTextField.getText();
        int customerID = customerComboBox.getSelectionModel().getSelectedItem().getId();
        LocalDate date = LocalDate.now();
        boolean isOpen = true;
        String note="";
        String company="";
        //Initializing the project.
        Project project = new Project(id, title, customerID, date, isOpen, note,company);
        try {
            //Send the project to the database.
            projectModel.createNewProject(project);
            newProjectAction();
        } catch (SQLException e) {
            displayError(e);
            e.printStackTrace();
        }
        //Close the vbox.
        newProjectAction();
    }

    /**
     * Handle what happens when the "Add new customer" button is clicked.
     * Initializing a new customer to be inserted in the database.
     * @param actionEvent
     */
    public void handleAddNewCustomer(ActionEvent actionEvent) {
        //Setting the data in the variables.
        int id = 1;
        String firstName = customerFirstNameTextField.getText();
        String lastName = customerLastNameTextField.getText();
        String companyName = companyNameTextField.getText();
        String customerAddress = customerAddressTextField.getText();
        String mail = customerEmailTextField.getText();
        int phoneNumber = Integer.parseInt(customerPhoneNumberTextField.getText());
        int customerZipCode = Integer.parseInt(customerZipCodeTextField.getText());
        //Initializing the customer.
        Customer customer = new Customer(id, firstName, lastName, companyName, customerAddress, mail, phoneNumber, customerZipCode);
        try {
            //Sending the customer to the database.
            customerModel.createNewCustomer(customer);
            newCustomerAction();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
        //Close the vbox.
        newCustomerAction();
    }

    /**
     * When a key is pressed in the search text-field, update the listview showing projects.
     * @param keyEvent
     */
    @FXML
    private void searchProjectsByStringQuery(KeyEvent keyEvent) {
        String query = searchBoxTextField.getText();
        TableView<Project> table;
        if (openProjectsTab.isSelected()) {
            // Search for open projects
            table = openProjectsTable;
            table.getItems().clear();
        } else {
            // Search for closed projects
            table = closedProjectsTable;
            table.getItems().clear();
        }
        table.getItems().clear();
        try {
            table.setItems(projectModel.searchByQuery(query));
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    public void handleOpenProjectWindow() throws Exception {

        if (selectedProject!=null)
        {
            projectModel.setSelectedProject(openProjectsTable.getSelectionModel().getSelectedItem());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/View/ProjectManager/NytVindue2.fxml"));
            AnchorPane pane = loader.load();
            pane.getStylesheets().add("/GUI/View/ProjectManager/managerView.css");
            mainViewAnchorPane.getChildren().setAll(pane);

            controller = loader.getController();
            controller.setModel(super.getModel());
            controller.setup();
        }


    }

    public void handleOpenUserWindow(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/ProjectManager/NytVindueUser.fxml"));
        AnchorPane pane = loader.load();
        pane.getStylesheets().add("/GUI/View/ProjectManager/managerView.css");
        mainViewAnchorPane.getChildren().setAll(pane);

        UserController controller = loader.getController();
        controller.setModel(super.getModel());
        controller.setup();
    }

    public void getCustomerPDFAction() throws MalformedURLException, FileNotFoundException {


        String path = "Resources/PDF/"+selectedProject.getCompanyName()+ " "+selectedProject.getTitle()+" installations dokumentation.pdf";

        if (Files.exists(Path.of(path)))
        {

            showFile.showFile(path);
        }
        else
        {

            showFile.showErrorBox("PDF does not exit", "File message");
        }




    }
}
