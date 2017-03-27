package gallery.zicure.company.com.modellibrary.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 2/15/2017.
 */

public class BaseResponse {
    @SerializedName("result")
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        @SerializedName("Success")
        private String success;
        @SerializedName("Error")
        private String error;
        @SerializedName("EResult")
        private String eResult;
        @SerializedName("Data")
        private Data data;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String geteResult() {
            return eResult;
        }

        public void seteResult(String eResult) {
            this.eResult = eResult;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public static class Data {
            private String version;

            public String getVersion() {
                return version;
            }
            public void setVersion(String version) {
                this.version = version;
            }
        }
    }
}
