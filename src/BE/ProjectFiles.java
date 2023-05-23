package BE;

import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import java.time.LocalDate;

public class ProjectFiles {

private int id, projectID,rank;
private String filePath,name;
private LocalDate date;
private ImageView picture;
private CheckBox usedBox;

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
    public ProjectFiles(int id, int projectID, String name, String filePath, LocalDate date,  ImageView picture, CheckBox usedBox, int rank) {
        this.id = id;
        this.projectID = projectID;
        this.name=name;
        this.filePath = filePath;
        this.date = date;
        this.picture = picture;
        this.usedBox = usedBox;
        this.rank=rank;
    }

    /**
     * Getters and setters for the entire class.
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

    public LocalDate getDate() {
        return date;
    }

    public ImageView getPicture() {
        return picture;
    }

    public CheckBox getUsedBox() {
        return usedBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
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
