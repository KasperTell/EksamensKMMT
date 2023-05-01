package GUI.Controller;

import BE.User;
import GUI.Model.FacadeModel;
import GUI.Model.UserModel;
import PersonsTypes.PersonType;
import PersonsTypes.PersonTypeChooser;
import PersonsTypes.PersonTypesKlient;
import PersonsTypes.Technician;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Collection;

public class LoginController extends BaseController {
    @FXML
    private AnchorPane acpBackgroundLogin;
    @FXML
    private ImageView ImgViewLogin;
    @FXML
    private PasswordField paswPassword;
    @FXML
    private TextField txtfUsername;
    @FXML
    private Button btnSignIn;
    private PersonTypeChooser personTypeChooser;
    private UserModel userModel;
    private User user;


    public void handleSignIn(ActionEvent actionEvent) throws Exception {

        String username = txtfUsername.getText();
        String password = paswPassword.getText();
        //Check if the username matches a name from the database, else show error.
        boolean flag = userModel.validateUsername(username);
        if(!flag) {
            loginFailedAlert();
        } else {
            //Set the user based on the correct username and get the matching password.
            user = userModel.loadUser(username).get(0);
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

        personTypeChooser.chooseType(PersonType.ProjectManager);

    }

    private void loginFailedAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Wrong username or password");
        alert.showAndWait();
    }

    private void openMainWindow() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(personTypeChooser.getViewString()));
        AnchorPane pane = loader.load();
        acpBackgroundLogin.getChildren().setAll(pane);

        MainController controller = loader.getController();
        controller.setModel(new FacadeModel());
        controller.setup();

    }



    public void setup() {
        userModel = getModel().getUserModel();
        ImgViewLogin.setImage(new Image("Pictures/LoginBackground.jpg"));
        ImgViewLogin.setOpacity(0.25);
    }
}
