package GUI.Controller;

import BE.Role;
import BE.User;
import GUI.Model.UserModel;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;

public class UserController extends BaseController{

    @FXML //Main pane for this window
    private AnchorPane mainViewAnchorPane;

    @FXML //Tab pane containing lists of employees
    private TabPane employeeTabPane;

    @FXML //List-Views containing all employees
    private ListView<User> TechsListView, managersListView, salesListView;

    @FXML //All buttons within this view
    private Button addEmployeeButton, removeEmployeeButton, openMainWindowButton, openNewEmployeeWindow;

    @FXML //V-Box for adding a new employee
    private VBox newUserVbox;

    @FXML //Text-Fields for adding a new employee
    private TextField firstNameTextField, lastNameTextField, usernameTextField, passwordTextField;

    @FXML //Combo-Box for picking new employee role.
    private ComboBox<Role> rolesComboBox;
    private UserModel userModel;
    private boolean isMenuOpen;

    private User selectedUser;

    @Override
    public void setup()  {
        //Initializing all our models.
        userModel = getModel().getUserModel();
        //Setting the information of the listviews and combobox.
        TechsListView.setItems(userModel.getAllTechnicians());
        managersListView.setItems(userModel.getallProjectManagers());
        salesListView.setItems(userModel.getallSalesmen());
        rolesComboBox.setItems(userModel.getAllRoles());
        listenerSalesList();
        listenerManagersList();
        listenerTechsList();
        pictureToButton();
    }

    private void pictureToButton() {
        String[] listOfFiles = {"Pictures/ButtonImages/GoBack.png", "Pictures/ButtonImages/AddProject.png", "Pictures/ButtonImages/DeleteUser.png"};

        String[] listOfToolTips = {"Go back to previous window", "Add a new User", "Delete selected User"};

        Button[] listOfButtons ={openMainWindowButton, openNewEmployeeWindow, removeEmployeeButton};

        for (int i = 0; i < listOfFiles.length; i++) {

            Image img = new Image(listOfFiles[i]);
            ImageView view = new ImageView(img);
            Tooltip tip = new Tooltip(listOfToolTips[i]);

            listOfButtons[i].setGraphic(view);
            listOfButtons[i].setTooltip(tip);
        }
    }


    @FXML
    private void listenerTechsList() {
        TechsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedUser=TechsListView.getSelectionModel().getSelectedItem();

        });

    }

    @FXML
    private void listenerSalesList() {
        salesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedUser=salesListView.getSelectionModel().getSelectedItem();

        });

    }


    @FXML
    private void listenerManagersList() {
        managersListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            selectedUser=managersListView.getSelectionModel().getSelectedItem();


        });

    }






    public void handleAddEmployee(ActionEvent actionEvent) {
        int id = 1;
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String salt = BCrypt.gensalt(12);
        password = BCrypt.hashpw(password, salt);
        int role = rolesComboBox.getSelectionModel().getSelectedItem().getId();

        User user = new User(id, firstName, lastName, username, password, role);
        try{
            userModel.createNewUser(user);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    public void handleRemoveEmployee(ActionEvent actionEvent) {


        if (selectedUser!=null)
        {

            if(TechsListView.getSelectionModel().getSelectedItem() != null){
                selectedUser = TechsListView.getSelectionModel().getSelectedItem();}
            else if(managersListView.getSelectionModel().getSelectedItem() != null){
                selectedUser = managersListView.getSelectionModel().getSelectedItem();}
            else if(salesListView.getSelectionModel().getSelectedItem() != null){
                selectedUser = salesListView.getSelectionModel().getSelectedItem();}
            try {
                userModel.deleteUser(selectedUser);
            } catch (Exception e){
                displayError(e);
                e.printStackTrace();
            }
        }

    }




    public void handleOpenMainWindow(ActionEvent actionEvent) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/ProjectManager/MainWindow.fxml"));
        AnchorPane pane = loader.load();
        pane.getStylesheets().add("/GUI/View/ProjectManager/MainWindow.css");
        mainViewAnchorPane.getChildren().setAll(pane);

        MainController controller = loader.getController();
        controller.setModel(super.getModel());
        controller.setup();
    }

    public void handleOpenNewEmployeeWindow() {
        //Initializing a new transition.
        TranslateTransition transition = new TranslateTransition();
        newUserVbox.toFront();
        transition.setNode(newUserVbox);
        transition.setDuration(Duration.millis(150));

        //If the Vbox is not shown, show it and set the background out of focus.
        if (!isMenuOpen) {
            isMenuOpen = true;
            transition.setToX(0);
            mainViewAnchorPane.setOpacity(0.2);
            EventHandler<MouseEvent> menuHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    handleOpenNewEmployeeWindow();
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

}
