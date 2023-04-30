package BE;

import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

import java.time.LocalDate;

public class ProjectFiles {

private int id, projectID;
private String filePath,name;

private LocalDate date;

private byte used;

private ImageView picture;

private CheckBox usedBox;




    public ProjectFiles(int id, int projectID, String name, String filePath, LocalDate date, byte used, ImageView picture,CheckBox usedBox) {
        this.id = id;
        this.projectID = projectID;
        this.filePath = filePath;
                this.date = date;
        this.used = used;
        this.name=name;
        this.picture = picture;
        this.usedBox = usedBox;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte getUsed() {
        return used;
    }

    public void setUsed(byte used) {
        this.used = used;
    }

    public ImageView getPicture() {
        return picture;
    }

    public void setPicture(ImageView picture) {
        this.picture = picture;
    }

    public CheckBox getUsedBox() {
        return usedBox;
    }

    public void setUsedBox(CheckBox usedBox) {
        this.usedBox = usedBox;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
