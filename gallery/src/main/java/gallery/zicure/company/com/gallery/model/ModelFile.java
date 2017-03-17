package gallery.zicure.company.com.gallery.model;

import org.parceler.Parcel;
import org.parceler.ParcelClass;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by 4GRYZ52 on 3/13/2017.
 */
@Parcel
public class ModelFile {
    public ModelFile(){

    }
    private ArrayList<Detail> arrFile;
    private Detail detail;


    public ArrayList<Detail> getArrFile() {
        return arrFile;
    }

    public void setArrFile(ArrayList<Detail> arrFile) {
        this.arrFile = arrFile;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    @Parcel
    public static class Detail{
        File file;
        String imgName;

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public String getImgName() {
            return imgName;
        }

        public void setImgName(String imgName) {
            this.imgName = imgName;
        }
    }
}
