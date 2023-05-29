package GUI.Controller;

import BE.ProjectFiles;
import GUI.Model.ProjectFilesModel;
import GUI.Model.ProjectModel;
import UTIL.ShowFile;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;

public class DrawController extends BaseController {

    GraphicsContext tool;
    private ProjectModel projectModel;
    private ProjectFilesModel projectFilesModel;

    @FXML private Button saveCanvas;
    @FXML private Canvas canvas;
    boolean speakerSelected = false;
    boolean screenSelected = false;
    boolean projectorSelected = false;
    boolean cableSelected = false;
    boolean eraserSelected = false;
    boolean techBoxSelected = false;
    private double x;
    private double y;

    @Override
    public void setup()  {
        projectModel = getModel().getProjectModel();
        projectFilesModel = getModel().getProjectFilesModel();
        clickToolSelect();
        dragToolSelect();
    }

    /**
     * applies the selected image via a switch to a SetOnMouseClicked listener.
     */
    public void clickToolSelect(){

        tool= canvas.getGraphicsContext2D();
        canvas.setOnMouseClicked(e -> {

             x = e.getX();
             y = e.getY();

            switch (speakerSelected + "-" + screenSelected + "-" + projectorSelected + "-" + cableSelected + "-" + techBoxSelected){

                case "true-false-false-false-false":
                    setSpeakerTool();
                    break;

                case "false-true-false-false-false":
                    setScreenTool();
                    break;

                case "false-false-true-false-false":
                    setProjectorTool();
                    break;

                case "false-false-false-true-false":
                    setCableTool();
                    break;

                case "false-false-false-false-true":
                    setTechBoxTool();
                    break;
            }
        });
    }

    /**
     * Applies the eraser to a setOnMouseDragged listener.
     */
    public void dragToolSelect(){

        tool = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(e -> {

            x = e.getX();
            y = e.getY();

            if (eraserSelected){
                setEraserTool();
            }
        });
    }

    /**
     * Methods to apply different images to GraphicsContent tool.
     */
    public void setSpeakerTool(){
        Image speaker = new Image("/Pictures/PaintIcons/Speaker.png");
        tool.drawImage(speaker, x - speaker.getWidth() / 2, y - speaker.getHeight() / 2);
    }

    public void setProjectorTool(){
        Image projector = new Image("/Pictures/PaintIcons/Projector.png");
        tool.drawImage(projector, x - projector.getWidth() / 2, y - projector.getHeight() / 2);
    }

    public void setScreenTool(){
        Image screen = new Image("/Pictures/PaintIcons/Screen.png");
        tool.drawImage(screen, x - screen.getWidth() / 2, y - screen.getHeight() / 2);
    }

    public void setCableTool() {
        tool.setFill(Color.BLACK);
        tool.fillRoundRect(x,y,10,10,10,10);
    }

    private void setTechBoxTool() {
        Image techBox = new Image("/Pictures/PaintIcons/TechBox.png");
        tool.drawImage(techBox, x - techBox.getWidth() / 2, y - techBox.getHeight() / 2);
    }

    /**
     * Methods to apply an eraser to the GraphicsContent tool.
     */
    public void setEraserTool(){
        tool.clearRect(x - 25,y - 25,50,50);
    }

    public void handleCable(ActionEvent actionEvent) {cableSelected = true; speakerSelected = false; screenSelected = false; projectorSelected = false; eraserSelected = false; techBoxSelected = false;}
    public void handleSpeaker(ActionEvent actionEvent) {cableSelected = false; speakerSelected = true; screenSelected = false; projectorSelected = false; eraserSelected = false; techBoxSelected = false;}
    public void handleScreen(ActionEvent actionEvent) {cableSelected = false; speakerSelected = false; screenSelected = true; projectorSelected = false; eraserSelected = false; techBoxSelected = false;}
    public void handleProjector(ActionEvent actionEvent) {cableSelected = false; speakerSelected = false; screenSelected = false; projectorSelected = true; eraserSelected = false; techBoxSelected = false;}
    public void handleEraser(ActionEvent actionEvent) {cableSelected = false; speakerSelected = false; screenSelected = false; projectorSelected = false; eraserSelected = true; techBoxSelected = false;}
    public void handleTechBox(ActionEvent actionEvent) {cableSelected = false; speakerSelected = false; screenSelected = false; projectorSelected = false; eraserSelected = false; techBoxSelected = true;}
    public void handleClear(ActionEvent actionEvent) {tool.clearRect(0,0,canvas.getWidth(), canvas.getHeight());}
    public void handleSaveCanvas(ActionEvent actionEvent) throws Exception {saveCanvas(canvas);}

    /**
     * Saves graphical content applied to a canvas to a readable .png file.
     * @param c
     * @throws Exception
     */
    public void saveCanvas(Canvas c)  {

        //Gets selectedProject ID
        int id = projectModel.getSelectedProject().getId();

        //Gets selectedProject Title
        String name = projectModel.getSelectedProject().getTitle();

        LocalDate date = LocalDate.now();

        //Converts the String to a filepath
        String filepath =  "Resources/Pictures/SavedProjectCanvas/" + "Prototype " +  name + ".png";
        Path path = Paths.get(filepath);

        //If a file of the same does not exist a file is written to the path.
        try {
            if (!projectFilesModel.doesFileExist(filepath)) {

                WritableImage picture = c.snapshot(null, null);
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(picture, null), "png", new File(path.toUri()));
                } catch (IOException e) {
                    displayError(e);
                }

                //Saves the file properties to the database.
                ProjectFiles fileToSave = new ProjectFiles(1, id, name, filepath, date, null, null);
                projectFilesModel.createNewFile(fileToSave);
            }
            else {
                ShowFile showFile = new ShowFile();
                showFile.showErrorBox("Something went wrong", "Delete previous drawings before adding a new drawing.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Closes the Draw window
     * @param actionEvent
     */
    public void handleBackToMainView(ActionEvent actionEvent) {
        Stage stage = (Stage) saveCanvas.getScene().getWindow();
        stage.close();
    }
}

