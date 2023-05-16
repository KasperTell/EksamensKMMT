package UTIL;

import javafx.scene.control.Alert;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ShowFile {

    public void showFile(String filePath) {
        boolean filesExits = Files.exists(Path.of(filePath)); //check om filen eksisterer
        File file = new File(filePath);
        try {
            if (filesExits) {
                Desktop desktop = Desktop.getDesktop();
                if (file.exists()) desktop.open(file); //Her åbnes filen i computerens standard program til filtypen.
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

    private void alertUser(String errorMessage,String title){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(errorMessage);
        alert.showAndWait();
    }


}
