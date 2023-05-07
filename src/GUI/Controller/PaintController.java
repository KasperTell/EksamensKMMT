package GUI.Controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaintController implements Initializable {

    @FXML private Button brush, saveCanvas, newCanvas;
    @FXML private ColorPicker colorPicker;
    @FXML private TextField brushSize, fileName;
    @FXML private Canvas canvas;
    boolean toolSelected = false;
    GraphicsContext brushTool;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        brushTool = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged( e -> {
            double size = Double.parseDouble(brushSize.getText());
            double x = e.getX() - size / 2;
            double y = e.getY() - size / 2;
            if (toolSelected && !brushSize.getText().isEmpty()){

                brushTool.setFill(colorPicker.getValue());
                brushTool.fillRoundRect(x,y, size, size, size, size);
            }
        });
    }

    public void handleSelectTool(ActionEvent actionEvent) {
        toolSelected = true;
    }
    public void handleNewCanvas(ActionEvent actionEvent) {brushTool.restore();}
    public void handleSaveCanvas(ActionEvent actionEvent) throws IOException {saveCanvas(canvas);}

    public void saveCanvas(Canvas c) throws IOException {

        int width = (int) c.getWidth();
        int height = (int) c.getHeight();
        String filepath = "SavedCanvas/" + fileName.getText() + ".PNG";

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableImage picture = c.snapshot(null, null);
        ImageIO.write(SwingFXUtils.fromFXImage(picture, null), "png", new File(filepath));
    }

    public void handleBackToMainView(ActionEvent actionEvent) {
    }
}


