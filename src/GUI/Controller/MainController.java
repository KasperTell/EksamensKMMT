package GUI.Controller;

import BE.*;
import GUI.Model.*;
import PersonsTypes.ButtonType;
import PersonsTypes.PersonType;
import PersonsTypes.PersonTypeChooser;
import UTIL.ShowFile;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController extends BaseController {

    @FXML
    private TabPane projectTabs;
    @FXML
    private Label newCustomerErrorText,errorMessageNewProject;
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
    private TableView<Customer> customerTable;

    @FXML //All columns for the customer table
    private TableColumn customerMailClm, customerPhoneClm, customerZipClm, customerAddressClm, customerNameClm, customerZipToTown;

    @FXML //All buttons for the main view
    private Button newProjectButton, reOpenProjectButton, closeProjectButton, newCustomerButton, openProjectWindowButton, openUserWindowButton, btnAddNewCustomer, addNewProjectButton, openPDFButton;

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
    private boolean isMenuOpen,save=true;
    private int phoneNumber,customerZipCode;

    private ShowFile showFile=new ShowFile();

    private PersonTypeChooser personTypeChooser = new PersonTypeChooser();

    String firstName,lastName,customerAddress;

    Customer customer;

    /**
     * Set up the view when the view is getting shown.
     */
    @Override

    public void setup()  {

        //Initializing all our models.
        customerModel = getModel().getCustomerModel();
        projectModel = getModel().getProjectModel();
        userModel = getModel().getUserModel();
        //Setting the information of the listviews and combobox.
        customerComboBox.setItems(customerModel.getAllCustomers());
       setupTableForProject();
        turnButtonONOrOff();
        listenerLstAllCloseProjects();
        listenerLstAllOpenProjects();
        disableButtons();
        pictureToButton();
        NotesTextArea.setWrapText(true);
        NotesTextArea.setEditable(false);
        listenerMouseClickOpenProject();
        listenerMouseClickCloseProject();
        listenerTabs();
    }

    private void listenerTabs() {

        projectTabs.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Tab>() {
                        @Override
                        public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                          removeSelection();
                        }
                    }
            );}


        private void removeSelection()
        {

            try {
                customerTable.setItems(customerModel.loadCustomerList(0));
            } catch (SQLException e) {
                displayError(e);
            }

            NotesTextArea.setText("");
            openProjectsTable.getSelectionModel().clearSelection();
            closedProjectsTable.getSelectionModel().clearSelection();
            openProjectWindowButton.setDisable(true);
            openPDFButton.setDisable(true);

        }


    private void setupTableForProject() {
        projectDateOpen.setCellValueFactory(new PropertyValueFactory<>("Date"));
        projectNameOpen.setCellValueFactory(new PropertyValueFactory<>("Title"));
        customerProjectOpen.setCellValueFactory(new PropertyValueFactory<>("companyName"));

        projectDateClosed.setCellValueFactory(new PropertyValueFactory<>("Date"));
        projectNameClosed.setCellValueFactory(new PropertyValueFactory<>("Title"));
        customerNameClosed.setCellValueFactory(new PropertyValueFactory<>("companyName"));

        try {
            openProjectsTable.setItems(projectModel.getAllProjectsOpen(userModel.getLoggedinUser().getId()));
        } catch (SQLException e) {
            displayError(e);
        }
        closedProjectsTable.setItems(projectModel.getAllProjectsClose());
    }

    private void pictureToButton() {
        String[] listOfFiles = new String[]{"Pictures/ButtonImages/AddProject.png", "Pictures/ButtonImages/AddCustomer.png", "Pictures/ButtonImages/CloseProject.png", "Pictures/ButtonImages/ReOpenProject.png", "Pictures/ButtonImages/OpenUserWindow.png",
        "Pictures/ButtonImages/EditProject.png", "Pictures/ButtonImages/OpenPDF.png"};
        String[] listOfToolTips = new String[]{"Add a new project", "Add a new customer", "Close a project", "Re-Open a project", "Opens up window for User editing", "Opens up window for project editing", "Open up PDF file"};
        Button[] listOfButtons = new Button[]{newProjectButton, newCustomerButton, closeProjectButton, reOpenProjectButton, openUserWindowButton, openProjectWindowButton, openPDFButton};

        for(int i = 0; i < listOfFiles.length; ++i) {
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

        HashMap<ButtonType, Boolean> turnButtonOnOrOff = personTypeChooser.turnButtonOnOrOff();

        closeProjectButton.setDisable(turnButtonOnOrOff.get(ButtonType.CloseProjectButton));
        reOpenProjectButton.setDisable(turnButtonOnOrOff.get(ButtonType.ReOpenProjectButton));
        newProjectButton.setDisable(turnButtonOnOrOff.get(ButtonType.NewProjectButton));
        newCustomerButton.setDisable(turnButtonOnOrOff.get(ButtonType.NewCustomerButton));
        openProjectWindowButton.setDisable(turnButtonOnOrOff.get(ButtonType.OpenProjectWindowButton));
        openUserWindowButton.setDisable(turnButtonOnOrOff.get(ButtonType.OpenUserWindowButton));
        closedProjectsTable.setDisable(turnButtonOnOrOff.get(ButtonType.ClosedProjectsTable));
        searchBoxTextField.setDisable(turnButtonOnOrOff.get(ButtonType.SearchBoxTextField));


    }
    /**
     * Listener for the tableview containing files for opening files.
     */
    public void listenerMouseClickOpenProject() {
        openProjectsTable.setOnMouseClicked(event -> {
            selectedProject = openProjectsTable.getSelectionModel().getSelectedItem();

            if (event.getClickCount() == 2) {
                getCustomerPDFAction();
            }
        });
    }

    public void listenerMouseClickCloseProject() {
        closedProjectsTable.setOnMouseClicked(event -> {
            selectedProject = closedProjectsTable.getSelectionModel().getSelectedItem();

            if (event.getClickCount() == 2) {
                getCustomerPDFAction();

            }
        });
    }

    @FXML
    private void listenerLstAllCloseProjects() {
        closedProjectsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedProject = closedProjectsTable.getSelectionModel().getSelectedItem();

            if (selectedProject != null)
            {
                setProjectColumns();

                    HashMap<ButtonType, Boolean> turnButtonOnOrOff = personTypeChooser.closeProjectButtonOnOrOff();

                    closeProjectButton.setDisable(turnButtonOnOrOff.get(ButtonType.CloseProjectButton));
                    reOpenProjectButton.setDisable(turnButtonOnOrOff.get(ButtonType.ReOpenProjectButton));
                    openProjectWindowButton.setDisable(turnButtonOnOrOff.get(ButtonType.OpenProjectWindowButton));

                    openPDFButton.setDisable(false);
                    NotesTextArea.setText(selectedProject.getNote());

                }

        });
    }

    @FXML
    private void listenerLstAllOpenProjects() {
        openProjectsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedProject = openProjectsTable.getSelectionModel().getSelectedItem();

            if (selectedProject!=null)
            {

                setProjectColumns();

                HashMap<ButtonType, Boolean> turnButtonOnOrOff = personTypeChooser.openProjectButtonOnOrOff();

                closeProjectButton.setDisable(turnButtonOnOrOff.get(ButtonType.CloseProjectButton));
                reOpenProjectButton.setDisable(turnButtonOnOrOff.get(ButtonType.ReOpenProjectButton));
                openProjectWindowButton.setDisable(turnButtonOnOrOff.get(ButtonType.OpenProjectWindowButton));

                openPDFButton.setDisable(false);
                NotesTextArea.setText(selectedProject.getNote());


            }

        });

    }

    /**
     * Set the information in the project column in the tableview.
     */
    private void setProjectColumns()  {


        customerNameClm.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        customerAddressClm.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerZipClm.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        customerMailClm.setCellValueFactory(new PropertyValueFactory<>("Mail"));
        customerPhoneClm.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerZipToTown.setCellValueFactory(new PropertyValueFactory<>("town"));


        try {
            customerTable.setItems(customerModel.loadCustomerList(selectedProject.getCustomerID()));
        } catch (SQLException e) {
            displayError(e);;
        }
    }


    /**
     *Handle what happens when the "close project" button is clicked.
     *Closing a project by changing the bit in the database from 0 to 1 for a specific project.
     * @param actionEvent
     */
    @FXML
    private void closeProjectAction(ActionEvent actionEvent) {
        //Setting the data for the variables and calls the method from the model.

        if (selectedProject!=null)
        {
            int closeProject = 1;
            int id = selectedProject.getId();
            selectedProject = null;

            try {
                projectModel.changeProjectStatus(closeProject, id);
            } catch (SQLException e) {
                displayError(e);
            }

            removeSelection();
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

        if (selectedProject!=null)
        {
            int reOpenProject = 0;
            int id = selectedProject.getId();

            try {
                projectModel.changeProjectStatus(reOpenProject, id);
            } catch (SQLException e) {
                displayError(e);
            }

            removeSelection();


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
        String title="";
        int customerID=0;
        save=true;


        if (!projectNameTextField.getText().equals("") )
            title = projectNameTextField.getText();

        else
        {
            save=false;
            errorMessageNewProject.setText("Error insert project name");
        }


       if (customerComboBox.getSelectionModel().getSelectedItem()!=null)
           customerID = customerComboBox.getSelectionModel().getSelectedItem().getId();
       else
       {
           save=false;
           errorMessageNewProject.setText("Error select from selection box");
       }

            LocalDate date = LocalDate.now();
            boolean isOpen = true;
            String note="";
            String company="";


        if (save)
        {

        Project project = new Project(id, title, customerID, date, isOpen, note,company);

            try {
                projectModel.createNewProject(project);
            } catch (SQLException e) {
                displayError(e);
            }

            newProjectAction();

            projectNameTextField.setText("");
            customerComboBox.getSelectionModel().clearSelection();

            removeSelection();

            //Close the vbox.

            newProjectAction();
            newProjectAction();

        }


    }

    /**
     * Handle what happens when the "Add new customer" button is clicked.
     * Initializing a new customer to be inserted in the database.
     * @param actionEvent

     */


    public void handleAddNewCustomer(ActionEvent actionEvent) {
        //Setting the data in the variables.

        save=true;
        String[] field=tjekNameAndAddressFields();
        tjekPhoneNumber();
        tjekZipCode();

            String companyName = companyNameTextField.getText();
            String mail = customerEmailTextField.getText();

            saveCustomer(field, companyName, mail);
            }

    public String[] tjekNameAndAddressFields() {
        TextField[] textFields={customerFirstNameTextField,customerLastNameTextField,customerAddressTextField};
        String[] field={firstName,lastName,customerAddress};
        String[] errorText={"Error in first Name","Error in Last Name","Error in Address"};


        for (int i = 0; i < 3; i++) {
            if (!textFields[i].getText().equals(""))
                field[i] = textFields[i].getText();

            else
            {
                save=false;
                String error = textFields[i].getText();
                newCustomerErrorText.setText(errorText[i] + "     -> " + error + " <-");
            }
        }
        return field;
        }


    private void tjekPhoneNumber() {
        if (!customerPhoneNumberTextField.getText().equals("") && customerPhoneNumberTextField.getText().chars().allMatch( Character::isDigit ))
            phoneNumber=Integer.parseInt(customerPhoneNumberTextField.getText());

        else
        {
            save=false;
            String error = customerPhoneNumberTextField.getText();
            newCustomerErrorText.setText("Error in Phone number" + "     -> " + error + " <-");
        }

    }

    private void tjekZipCode() {
        String town = null;
        String error = customerZipCodeTextField.getText();

        if (!customerZipCodeTextField.getText().equals("") && customerZipCodeTextField.getText().chars().allMatch( Character::isDigit ))
        {

            customerZipCode=Integer.parseInt(customerZipCodeTextField.getText());
            try {
                town=customerModel.TownToZipCode(customerZipCode);
            } catch (SQLException e) {
                displayError(e);

            }

            if (town==null)
            {
                save=false;
                newCustomerErrorText.setText("Error in zip code" + "     -> " + error + " <-");
            }

        }
        else
        {
            save=false;
            newCustomerErrorText.setText("Error in zip code" + "     -> " + error + " <-");
        }
    }

    public void handleClearCustomerTextFields(ActionEvent actionEvent) {
        newCustomerErrorText.setText("");
        customerPhoneNumberTextField.clear();
        customerAddressTextField.clear();
        customerFirstNameTextField.clear();
        customerLastNameTextField.clear();
        customerEmailTextField.clear();
        companyNameTextField.clear();
        customerZipCodeTextField.clear();

    }


    private void saveCustomer(String[] field, String companyName,String mail) {

        if (save) {
            int id = 1;
            customer = new Customer(id, field[0], field[1], companyName, field[2], mail, phoneNumber, customerZipCode, "");

            //Sending the customer to the database.

            try {
                customerModel.createNewCustomer(customer);
            } catch (SQLException e) {
                displayError(e);
            }


            newCustomerAction(); //Close the vbox.

            TextField[] textFields={customerFirstNameTextField,customerLastNameTextField,customerAddressTextField,
                    customerPhoneNumberTextField,customerZipCodeTextField,companyNameTextField,customerEmailTextField};


            for (int i = 0; i <7 ; i++)
                textFields[i].clear();

            newCustomerErrorText.setText("");
        }
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
        } catch (SQLException e) {
            displayError(e);
        }
    }


    /**
     * This will open a new window for editing the project regarding files, PDF and technician.
    */

    public void handleOpenProjectWindow()  {

        if (selectedProject!=null)
        {
            projectModel.setSelectedProject(selectedProject);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/View/ProjectWindow.fxml"));
            AnchorPane pane = null;
            try {
                pane = loader.load();
            } catch (IOException e) {
                displayError(e);
            }
            pane.getStylesheets().add("/GUI/View/MainWindow.css");
            mainViewAnchorPane.getChildren().setAll(pane);

            controller = loader.getController();
            controller.setModel(super.getModel());
            controller.setup();
        }


    }


    /**
     * This will open a menu that can add new users.
     */


    public void handleOpenUserWindow(ActionEvent actionEvent)  {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/UserWindow.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            displayError(e);
        }
        pane.getStylesheets().add("/GUI/View/MainWindow.css");
        mainViewAnchorPane.getChildren().setAll(pane);

        UserController controller = loader.getController();
        controller.setModel(super.getModel());
        controller.setup();
    }


    /**
     * This will open a PDF file that is attached to the given Project.
     */

    public void getCustomerPDFAction()  {


        if (selectedProject!=null)
        {

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


}
