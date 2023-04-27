import GUI.Controller.LoginController;
import GUI.Model.FacadeModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GUI/View/LoginView.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.setModel(new FacadeModel());
        controller.setup();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
