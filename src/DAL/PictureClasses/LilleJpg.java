package DAL.PictureClasses;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LilleJpg implements IImageViewKlient {

    /**
     * Overrides the GetImageView in the interface to match a .jpg file.
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public ImageView getImageView() throws FileNotFoundException {
        Image png1 = new Image(new FileInputStream("Resources/Pictures/FileTypeIcons/jpg-file.png"));

        //Her laves billederamme objekterne med indhold
        ImageView png= new ImageView(png1);
        png.setFitWidth(25);
        png.setFitHeight(25);

        return png;
    }
}