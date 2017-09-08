package gallery.zicure.company.com.modellibrary.models.contact;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 9/8/2017 AD.
 */

public class ResponseDeleteProfile {
    @SerializedName("result") private ResultDelete result;

    public ResultDelete getResult() {
        return result;
    }

    public void setResult(ResultDelete result) {
        this.result = result;
    }

    public class ResultDelete {
        @SerializedName("Success") private String success;
        @SerializedName("Error") private String error;

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
    }
}
