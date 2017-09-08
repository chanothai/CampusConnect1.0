package gallery.zicure.company.com.modellibrary.models.contact;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 9/8/2017 AD.
 */

public class RequestDeleteProfile {
    @SerializedName("Contact") private ContactDelete contact;

    public void setContact(ContactDelete contact) {
        this.contact = contact;
    }

    public class ContactDelete {
        @SerializedName("token") private String token;
        @SerializedName("contact_id") private String contactID;

        public void setToken(String token) {
            this.token = token;
        }

        public void setContactID(String contactID) {
            this.contactID = contactID;
        }
    }
}
