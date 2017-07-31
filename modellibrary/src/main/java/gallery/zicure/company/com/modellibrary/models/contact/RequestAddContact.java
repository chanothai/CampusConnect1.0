package gallery.zicure.company.com.modellibrary.models.contact;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 7/31/2017 AD.
 */

public class RequestAddContact {
    @SerializedName("Contact") private Contact contact;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public static class Contact {
        @SerializedName("token") private String token;
        @SerializedName("user_contact") private String userContact;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUserContact() {
            return userContact;
        }

        public void setUserContact(String userContact) {
            this.userContact = userContact;
        }
    }
}
