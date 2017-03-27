package gallery.zicure.company.com.modellibrary.utilize;


import com.google.gson.annotations.SerializedName;

import gallery.zicure.company.com.modellibrary.models.DateModel;
import gallery.zicure.company.com.modellibrary.models.LocationModel;

/**
 * Created by 4GRYZ52 on 10/25/2016.
 */
public class ListModel {
    @SerializedName("LocationModel")
    public LocationModel locationModel;

    @SerializedName("date")
    public DateModel dateModel;
}
