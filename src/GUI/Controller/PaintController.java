package GUI.Controller;

import GUI.Model.ProjectModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PaintController extends BaseController {

    @FXML private Slider sizeSlider;
    @FXML private Button brush, saveCanvas, newCanvas, speaker, screen, erase, projector;
    @FXML private ColorPicker colorPicker;
    @FXML private Canvas canvas;
    boolean brushSelected = false;
    boolean speakerSelected = false;
    boolean screenSelected = false;
    boolean eraseSelected = false;
    boolean projectorSelected = false;
    GraphicsContext brushTool;
    private  ProjectModel projectModel;


    public void handleBrush(ActionEvent actionEvent) {brushSelected = true; speakerSelected = false; screenSelected = false; eraseSelected = false; projectorSelected = false;}
    public void handleSpeaker(ActionEvent actionEvent) {speakerSelected = true; screenSelected = false; brushSelected = false; eraseSelected = false; projectorSelected = false;}
    public void handleScreen(ActionEvent actionEvent) {screenSelected = true; brushSelected = false; speakerSelected = false; eraseSelected = false; projectorSelected = false; }
    public void handleErase(ActionEvent actionEvent) {eraseSelected = true; brushSelected = false; screenSelected = false; speakerSelected = false; projectorSelected = false;}
    public void handleProjector(ActionEvent actionEvent) {projectorSelected = true; speakerSelected = false; eraseSelected = false; screenSelected = false; brushSelected = false;}
    public void handleNewCanvas(ActionEvent actionEvent) {brushTool.restore();}
    public void handleSaveCanvas(ActionEvent actionEvent) throws IOException {saveCanvas(canvas);}


    public void saveCanvas(Canvas c) throws IOException {

        String name = projectModel.getProjectTitle();

        int width = (int) c.getWidth();
        int height = (int) c.getHeight();


        String filepath =  "Resources/SavedProjectCanvas/" + "Prototype " +  name + ".PNG";

        Path path = Paths.get(filepath);
        //Files.createDirectories(path);

        System.out.println("Directory is created!");

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableImage picture = c.snapshot(null, null);
        ImageIO.write(SwingFXUtils.fromFXImage(picture, null), "png", new File(path.toUri()));
    }

    public void handleBackToMainView(ActionEvent actionEvent) {
        Stage stage = (Stage) erase.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setup() throws Exception {

        projectModel = getModel().getProjectModel();
        Image projecter = new Image("/Paint/Projector.png");
        Image speaker = new Image("/Paint/Speaker.png");
        Image screen = new Image("/Paint/Screen.png");

        brushTool = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged( e -> {
            double size = Double.parseDouble(String.valueOf(sizeSlider.getValue()));
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            if (brushSelected){
                brushTool.setLineWidth(10);
                brushTool.setFill(colorPicker.getValue());
                brushTool.strokeLine(x,y,x,y);
            }

            else if (projectorSelected){
                brushTool.drawImage(projecter, x - projecter.getWidth()  / 2, y - projecter.getHeight() / 2);
            }

            else if (screenSelected){
                brushTool.drawImage(screen, x - screen.getWidth() / 2, y - screen.getHeight() / 2);
            }

            else if (speakerSelected){
                brushTool.drawImage(speaker, x - speaker.getWidth() / 2, y - speaker.getHeight() / 2);
            }

            else if (eraseSelected){
                brushTool.clearRect(x,y,size,size);
            }
        });

    }
}


