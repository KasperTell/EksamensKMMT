package DAL.PictureClasses;

import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

public class ImageViewKlient {

    private IImageViewKlient iImageViewKlient;

        public ImageViewKlient(IImageViewKlient iMedalje)
        {
        this.iImageViewKlient = iMedalje;
    }

    public void setiMedalje(IImageViewKlient iMedalje) {
        this.iImageViewKlient = iMedalje;
    }

 public ImageView getImageView() throws FileNotFoundException {
    return iImageViewKlient.getImageView();
}

}
