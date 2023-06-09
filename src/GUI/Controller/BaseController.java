package GUI.Controller;

import GUI.Model.FacadeModel;
import javafx.scene.control.Alert;

public abstract class BaseController {

    private FacadeModel facadeModel;

    public void setModel(FacadeModel facadeModel){this.facadeModel = facadeModel;}

    public FacadeModel getModel(){return facadeModel;}

    /**
     * Global error message.
     * @param t
     */
    public void displayError(Throwable t){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public abstract void setup() throws Exception;
}
