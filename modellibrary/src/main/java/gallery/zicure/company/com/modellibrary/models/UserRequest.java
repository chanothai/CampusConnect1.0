package gallery.zicure.company.com.modellibrary.models;

import com.google.gson.annotations.Expose;

/**
 * Created by 4GRYZ52 on 3/10/2017.
 */

public class UserRequest {
    @Expose
    private String token;
    public void setToken(String token) {
        this.token = token;
    }
}
