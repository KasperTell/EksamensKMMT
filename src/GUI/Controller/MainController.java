package GUI.Controller;

import BE.User;
import GUI.Model.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController extends BaseController {
    @FXML
    private TableView<User> tblvTechnicians;
    @FXML
    private TableColumn<User, String> clmFirstName, clmLastName;
    @FXML
    private Button btnCreateNewUser, btnDeleteUser;

    private UserModel userModel;


    @Override
    public void setup() throws Exception {
        userModel = getModel().getUserModel();

        /*
        tblvTechnicians.setItems(userModel.getAllTechnicians());
        clmFirstName.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getFirstName()));
        clmLastName.setCellValueFactory(c -> new SimpleObjectProperty<>(c.getValue().getLastName()));





    }

*/

    /*
    public void handleCreateNewUser(ActionEvent actionEvent) {
        try {
            //Binding all the data to the variables.
            //String firstName = txtfFirstName.getText();
            //String lastName = txtfLastName.getText();
            //String username = txtfUsername.getText();
            //String password = txtfPassword.getText();
            String firstName = "Kasper";
            String lastName = "Tell";
            String username = "2";
            String password = "2";
            String salt = BCrypt.gensalt(12);
            password = BCrypt.hashpw(password, salt);
            int role = 1;

            //Sending the variables to the model.
            userModel.createNewUser(firstName, lastName, username, password, role);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void handleDeleteUser(ActionEvent actionEvent) throws Exception {
        main.java.BE.User selectedUser = tblvTechnicians.getSelectionModel().getSelectedItem();
        userModel.deleteUser(selectedUser);
    }

*/

    }
}
