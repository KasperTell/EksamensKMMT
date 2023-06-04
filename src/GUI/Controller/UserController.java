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

import java.io.IOException;
import java.sql.SQLException;

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

    @FXML
    private Label errorTextNewUser;
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
        boolean save=true;
        String firstName=null,lastName=null,username=null,password=null;
        int role=0;

        TextField[] textFields={firstNameTextField,lastNameTextField,usernameTextField,passwordTextField};
        String[] field={firstName,lastName,username,password};
        String[] errorText={"Error in first Name","Error in Last Name","Error in user name","Error in password"};


        for (int i = 0; i < 4; i++) {
            if (!textFields[i].getText().equals(""))
                field[i] = textFields[i].getText();

            else
            {
                save=false;
                String error = textFields[i].getText();
                errorTextNewUser.setText(errorText[i]);
            }
        }

        if (rolesComboBox.getSelectionModel().getSelectedItem()!=null)
            role = rolesComboBox.getSelectionModel().getSelectedItem().getId();
        else
        {
            save=false;
            errorTextNewUser.setText("Error in combobox");
        }

        if (save)
        {
            String salt = BCrypt.gensalt(12);

            field[3] = BCrypt.hashpw(field[3], salt);   //password = BCrypt.hashpw(password, salt); Rettet


            User user = new User(id, field[0], field[1], field[2], field[3], role);

            try {
                if(userModel.validateUsername(username)){
                    usernameAlert();
                } else{
                    userModel.createNewUser(user);
                }
            } catch (SQLException e) {
                displayError(e);
            }

            for (int i = 0; i < 4; i++) {
                textFields[i].clear();
            }
            errorTextNewUser.setText("");
            handleOpenNewEmployeeWindow();
        }

    }


    public void handleRemoveEmployee(ActionEvent actionEvent) {
        if (selectedUser!=null)
        {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete User");
           
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    if (TechsListView.getSelectionModel().getSelectedItem() != null) {
                        selectedUser = TechsListView.getSelectionModel().getSelectedItem();
                    } else if (managersListView.getSelectionModel().getSelectedItem() != null) {
                        selectedUser = managersListView.getSelectionModel().getSelectedItem();
                    } else if (salesListView.getSelectionModel().getSelectedItem() != null) {
                        selectedUser = salesListView.getSelectionModel().getSelectedItem();
                    }
                    try {
                        userModel.deleteUser(selectedUser);
                    } catch (SQLException e) {
                        displayError(e);
                    }
                }
            });



        }

    }

    public void handleOpenMainWindow(ActionEvent actionEvent)  {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/MainWindow.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            displayError(e);
        }
        pane.getStylesheets().add("/GUI/View/MainWindow.css");
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

    private void usernameAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Username already exists. Please enter another username");
        alert.showAndWait();
    }
}
