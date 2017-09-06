package gallery.zicure.company.com.modellibrary.models.updatepassword;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 9/5/2017 AD.
 */

public class ResponseForgotPassword {
    @SerializedName("result") private ResultUpdate resultUpdate;

    public ResultUpdate getResultUpdate() {
        return resultUpdate;
    }

    public void setResultUpdate(ResultUpdate resultUpdate) {
        this.resultUpdate = resultUpdate;
    }

    public static class ResultUpdate {
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
