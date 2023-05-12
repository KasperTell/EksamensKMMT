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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;

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

    @Override
    public void setup() throws Exception {
        //Initializing all our models.
        userModel = getModel().getUserModel();
        //Setting the information of the listviews and combobox.
        TechsListView.setItems(userModel.getAllTechnicians());
        managersListView.setItems(userModel.getallProjectManagers());
        salesListView.setItems(userModel.getallSalesmen());
        rolesComboBox.setItems(userModel.getAllRoles());
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

        User selectedUser = null;
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





    public void handleOpenMainWindow(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/ProjectManager/NytVindue1.fxml"));
        AnchorPane pane = loader.load();
        pane.getStylesheets().add("/GUI/View/ProjectManager/managerView.css");
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
