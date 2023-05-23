package DAL.PictureClasses;

import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

public class ImageViewKlient {

    private IImageViewKlient iImageViewKlient;

    /**
     * Constructor for the class "ImageViewKlient"
     * @param iMedalje
     */
    public ImageViewKlient(IImageViewKlient iMedalje)
        {
        this.iImageViewKlient = iMedalje;
    }

    /**
     * Gets the overwritten imageview from one of the other classes.
     * @return
     * @throws FileNotFoundException
     */
    public ImageView getImageView() throws FileNotFoundException { return iImageViewKlient.getImageView();}
}