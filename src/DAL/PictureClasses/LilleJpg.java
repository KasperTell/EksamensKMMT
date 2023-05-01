package DAL.PictureClasses;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LilleJpg implements IImageViewKlient {



    @Override
    public ImageView getImageView() throws FileNotFoundException {
        Image png1 = new Image(new FileInputStream("Resources/Pictures/pictureIcons/jpg-file.png"));


        //Her laves billederamme objekterne med indhold
        ImageView png= new ImageView(png1);
        png.setFitWidth(20);
        png.setFitHeight(20);

        return png;

    }
}
