package UTIL;

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


}
