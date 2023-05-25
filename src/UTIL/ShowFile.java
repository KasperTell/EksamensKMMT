package UTIL;

import javafx.scene.control.Alert;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ShowFile {

    public void showFile(String filePath) {
        boolean filesExits = Files.exists(Path.of(filePath)); //check if the file exists
        File file = new File(filePath);
        try {
            if (filesExits) {
                Desktop desktop = Desktop.getDesktop();
                if (file.exists()) desktop.open(file); //Here, the file is opened in the computer's standard program for the file type.
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showErrorBox(String errorMessage, String title)
    {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle(title);
        info.setHeaderText(errorMessage);
        info.showAndWait();

    }


}
