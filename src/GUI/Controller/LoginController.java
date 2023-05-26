package GUI.Controller;

import BE.User;
import GUI.Model.UserModel;
import PersonsTypes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController extends BaseController {
    @FXML
    private AnchorPane loginAnchorPane;
    @FXML
    private ImageView imageViewLogin, loginIcon;
    @FXML
    private PasswordField passwordBox;
    @FXML
    private TextField usernameBox;
    @FXML
    private Button signIn;
    private PersonTypeChooser personTypeChooser;
    private UserModel userModel;
    private User user;

    /**
     * Handle what happens when the sign-in button in the clicked.
     * If the login information is correct, open a new FXML file.
     * @param actionEvent
     * @throws Exception
     */
    public void handleSignIn(ActionEvent actionEvent)  {
        //Setting the local variables.
        String username = usernameBox.getText();
        String password = passwordBox.getText();
        
        //Check if the username matches a name from the database, else show error.
        boolean flag = false;
        try {
            flag = userModel.validateUsername(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(!flag) {
            loginFailedAlert();
        } else {
            //Set the user based on the correct username and get the matching password.

            try {
                user = userModel.loadUser(username);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //Check if the database password matches the user password, else show error.
            if(BCrypt.checkpw(password, user.getPassword())) {
                userModel.setLoggedinUser(user);
                chooseUserType();
                openMainWindow();
            }
            else{
                loginFailedAlert();
            }
        }
    }

    private void chooseUserType() {
        personTypeChooser=new PersonTypeChooser();

        int personType = user.getRole();
        switch (personType)
        {

            case 2:
                personTypeChooser.chooseType(PersonType.ProjectManager);
                break;

            case 3:
                personTypeChooser.chooseType(PersonType.Salesman);
                break;

            case 4:
                personTypeChooser.chooseType(PersonType.Technician);
                break;

        }
    }

    /**
     * Alert for failing to login.
     */
    private void loginFailedAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Wrong username or password");
        alert.showAndWait();
    }

    /**
     * Opens the "main view" window for the specific user role.
     * @throws Exception
     */
    private void openMainWindow()  {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/View/MainWindow.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        pane.getStylesheets().add(personTypeChooser.getCSS());
        loginAnchorPane.getChildren().setAll(pane);

        MainController controller = loader.getController();
        controller.setModel(super.getModel());
        controller.setup();

    }

    @Override
    public void setup() {
        userModel = getModel().getUserModel();
        imageViewLogin.setImage(new Image("Pictures/LoginBackgroundBlur.jpg"));
        loginIcon.setImage(new Image("Pictures/ButtonImages/LoginLogo.png"));

        imageViewLogin.setOpacity(0.40);
    }
}
