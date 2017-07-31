package gallery.zicure.company.com.modellibrary.models.contact;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 7/31/2017 AD.
 */

public class ResponseAddContact {
    @SerializedName("result") private ResultAddContact result;

    public ResultAddContact getResult() {
        return result;
    }

    public void setResult(ResultAddContact result) {
        this.result = result;
    }

    public static class ResultAddContact {
        @SerializedName("Success") private String success;
        @SerializedName("Error") private String error;
        @SerializedName("Message") private String message;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
