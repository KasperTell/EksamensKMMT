package GUI.Controller;

import BE.Project;
import BE.ProjectFiles;
import DAL.FilesDAO;
import GUI.Model.ProjectFilesModel;
import GUI.Model.ProjectModel;
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
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;

public class PaintController extends BaseController {

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
    public void setup() throws Exception {
        projectModel = getModel().getProjectModel();
        projectFilesModel = getModel().getProjectFilesModel();
        clickToolSelect();
        dragToolSelect();
    }

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

    public void setSpeakerTool(){
        Image speaker = new Image("/Paint/Speaker.png");
        tool.drawImage(speaker, x - speaker.getWidth() / 2, y - speaker.getHeight() / 2);
    }

    public void setProjectorTool(){
        Image projector = new Image("/Paint/Projector.png");
        tool.drawImage(projector, x - projector.getWidth() / 2, y - projector.getHeight() / 2);
    }

    public void setScreenTool(){
        Image screen = new Image("/Paint/Screen.png");
        tool.drawImage(screen, x - screen.getWidth() / 2, y - screen.getHeight() / 2);
    }

    public void setCableTool() {
        tool.setFill(Color.BLACK);
        tool.fillRoundRect(x,y,10,10,10,10);
    }

    private void setTechBoxTool() {
        Image techBox = new Image("/Paint/TechBox.PNG");
        tool.drawImage(techBox, x - techBox.getWidth() / 2, y - techBox.getHeight() / 2);
    }

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

    public void saveCanvas(Canvas c) throws Exception {

        int id = projectModel.getSelectedProject().getId();

        String name = projectModel.getSelectedProject().getTitle();

        LocalDate date = LocalDate.now();

        String filepath =  "Resources/SavedProjectCanvas/" + "Prototype " +  name + ".PNG";
        Path path = Paths.get(filepath);

        WritableImage picture = c.snapshot(null, null);
        ImageIO.write(SwingFXUtils.fromFXImage(picture, null), "png", new File(path.toUri()));

        FilesDAO filesDAO=new FilesDAO();

        ProjectFiles fileToSave = new ProjectFiles(1, id, name, filepath, date, null, null,filesDAO.getFileAmount()+1);

        projectFilesModel.createNewFile(fileToSave);

    }

    public void handleBackToMainView(ActionEvent actionEvent) {
        Stage stage = (Stage) saveCanvas.getScene().getWindow();
        stage.close();
    }
}

