package com.company.zicure.registerkey.models.version;

import com.company.zicure.registerkey.models.version.AndroidVersion;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 11/25/2016.
 */

public class DataVersion {
    @SerializedName("AndroidVersion")
    @Expose
    private AndroidVersion androidVersion;

    /**
     *
     * @return
     * The androidVersion
     */
    public AndroidVersion getAndroidVersion() {
        return androidVersion;
    }

    /**
     *
     * @param androidVersion
     * The AndroidVersion
     */
    public void setAndroidVersion(AndroidVersion androidVersion) {
        this.androidVersion = androidVersion;
    }
}
