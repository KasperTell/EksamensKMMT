package BE;

import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

import java.time.LocalDate;

public class ProjectFiles {

private int id, projectID;
private String filePath,name;
private LocalDate date;
private ImageView picture;
private CheckBox usedBox;
private int OrderFiles;

    /**
     * Constructor for the class "ProjectFiles".
     * @param id
     * @param projectID
     * @param name
     * @param filePath
     * @param date
     * @param picture
     * @param usedBox
     */
    public ProjectFiles(int id, int projectID, String name, String filePath, LocalDate date,  ImageView picture, CheckBox usedBox, int OrderFiles) {
        this.id = id;
        this.projectID = projectID;
        this.name=name;
        this.filePath = filePath;
        this.date = date;
        this.picture = picture;
        this.usedBox = usedBox;
        this.OrderFiles = OrderFiles;
    }

    /**
     * Getters and setters for the entire class.
     * @return
     */
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

    public int getOrderFiles() {
        return OrderFiles;
    }

    public void setOrder(int orderfiles) {
        OrderFiles = orderfiles;
    }

    /**
     * Overriding the default toString method.
     * @return
     */
    @Override
    public String toString() {

        boolean newBox=true;
        if (usedBox.isSelected())
            newBox=true;
            else
                newBox=false;


        return newBox+"";
    }
}
