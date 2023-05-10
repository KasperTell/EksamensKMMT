package GUI.Controller;

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

public class PaintController extends BaseController {

    GraphicsContext brushTool;
    private ProjectModel projectModel;

    @FXML private Button saveCanvas;
    @FXML private Canvas canvas;
    boolean speakerSelected = false;
    boolean screenSelected = false;
    boolean projectorSelected = false;
    boolean cableSelected = false;
    private double x;
    private double y;

    @Override
    public void setup() throws Exception {
        projectModel = getModel().getProjectModel();
        toolSelect();
    }

    public void toolSelect(){

        brushTool = canvas.getGraphicsContext2D();
        canvas.setOnMouseClicked(e -> {

             x = e.getX();
             y = e.getY();

            switch (speakerSelected + "-" + screenSelected + "-" + projectorSelected + "-" + cableSelected){

                case "true-false-false-false":
                    setSpeakerTool();
                    break;

                case "false-true-false-false":
                    setScreenTool();
                    break;

                case "false-false-true-false":
                    setProjectorTool();
                    break;

                case "false-false-false-true":
                    setCableTool();
                    break;

            }
        });
    }

    public void setSpeakerTool(){
        Image speaker = new Image("/Paint/Speaker.png");
        brushTool.drawImage(speaker, x - speaker.getWidth() / 2, y - speaker.getHeight() / 2);
    }

    public void setProjectorTool(){
        Image projector = new Image("/Paint/Projector.png");
        brushTool.drawImage(projector, x - projector.getWidth() / 2, y - projector.getHeight() / 2);
    }

    public void setScreenTool(){
        Image screen = new Image("/Paint/Screen.png");
        brushTool.drawImage(screen, x - screen.getWidth() / 2, y - screen.getHeight() / 2);
    }

    public void setCableTool() {
        brushTool.setFill(Color.BLACK);
        brushTool.fillRoundRect(x,y,10,10,10,10);
        }


    public void handleCable(ActionEvent actionEvent) {cableSelected = true; speakerSelected = false; screenSelected = false; projectorSelected = false;}
    public void handleSpeaker(ActionEvent actionEvent) {cableSelected = false; speakerSelected = true; screenSelected = false; projectorSelected = false;}
    public void handleScreen(ActionEvent actionEvent) {cableSelected = false; speakerSelected = false; screenSelected = true; projectorSelected = false;}
    public void handleProjector(ActionEvent actionEvent) {cableSelected = false; speakerSelected = false; screenSelected = false; projectorSelected = true;}
    public void handleSaveCanvas(ActionEvent actionEvent) throws IOException {saveCanvas(canvas);}

    public void saveCanvas(Canvas c) throws IOException {

        String name = projectModel.getProjectTitle();
        String filepath =  "Resources/SavedProjectCanvas/" + "Prototype " +  name + ".PNG";
        Path path = Paths.get(filepath);

        WritableImage picture = c.snapshot(null, null);
        ImageIO.write(SwingFXUtils.fromFXImage(picture, null), "png", new File(path.toUri()));
    }

    public void handleBackToMainView(ActionEvent actionEvent) {
        Stage stage = (Stage) saveCanvas.getScene().getWindow();
        stage.close();
    }

}

