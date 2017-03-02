package com.company.zicure.registerkey.models.version;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 11/25/2016.
 */

public class AndroidVersion {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("version")
    @Expose
    private String version;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The version
     */
    public String getVersion() {
        return version;
    }

    /**
     *
     * @param version
     * The version
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
