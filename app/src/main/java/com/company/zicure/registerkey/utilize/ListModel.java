package com.company.zicure.registerkey.utilize;

import com.company.zicure.registerkey.models.DateModel;
import com.company.zicure.registerkey.models.LocationModel;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 10/25/2016.
 */
public class ListModel {
    @SerializedName("LocationModel")
    public LocationModel locationModel;

    @SerializedName("date")
    public DateModel dateModel;
}
