package GUI.Controller;

import GUI.Model.FacadeModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public abstract class BaseController {

    private FacadeModel facadeModel;

    public void setModel(FacadeModel facadeModel){this.facadeModel = facadeModel;}

    public FacadeModel getModel(){return facadeModel;}


    public void closeWindow(Button btn){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }


    public void displayError(Throwable t){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public abstract void setup() throws Exception;
}
