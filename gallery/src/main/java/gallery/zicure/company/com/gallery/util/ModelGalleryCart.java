package gallery.zicure.company.com.gallery.util;

import java.io.File;

import gallery.zicure.company.com.gallery.model.ModelFile;

/**
 * Created by 4GRYZ52 on 3/14/2017.
 */

public class ModelGalleryCart {
    private static ModelGalleryCart me = null;
    private ModelFile modelFile = null;

    public ModelGalleryCart(){
        modelFile = new ModelFile();
    }

    public static ModelGalleryCart getInstatnce(){
        if (me == null){
            me = new ModelGalleryCart();
        }
        return me;
    }


    public ModelFile getModelFile(){
        return modelFile;
    }
}
