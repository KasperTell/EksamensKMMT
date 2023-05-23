package DAL.PictureClasses;

import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

public interface IImageViewKlient {
    ImageView getImageView() throws FileNotFoundException;
}