package gallery.zicure.company.com.modellibrary.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 11/26/2016.
 */

public class DataModel {
    @SerializedName("Data")
    private String data;
    @SerializedName("User")
    private User user;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class User {
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
