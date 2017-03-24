package com.company.zicure.registerkey.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 3/23/2017.
 */

public class ResponseUserCode {
    @Expose
    private ResultUserCode result;

    public ResultUserCode getResult() {
        return result;
    }

    public void setResult(ResultUserCode result) {
        this.result = result;
    }

    public static class ResultUserCode{
        @SerializedName("Success")
        private String success;
        @SerializedName("DeviceToken")
        private DeviceToken deviceToken;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public DeviceToken getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(DeviceToken deviceToken) {
            this.deviceToken = deviceToken;
        }

        public static class DeviceToken{
            @SerializedName("device_code")
            private String deviceCode;
            @SerializedName("user_code")
            private String userCode;
            @SerializedName("user_code_expiry_date")
            private String userCodeExpire;
            @SerializedName("verification_url")
            private String urlVerify;

            public String getDeviceCode() {
                return deviceCode;
            }

            public void setDeviceCode(String deviceCode) {
                this.deviceCode = deviceCode;
            }

            public String getUserCode() {
                return userCode;
            }

            public void setUserCode(String userCode) {
                this.userCode = userCode;
            }

            public String getUserCodeExpire() {
                return userCodeExpire;
            }

            public void setUserCodeExpire(String userCodeExpire) {
                this.userCodeExpire = userCodeExpire;
            }

            public String getUrlVerify() {
                return urlVerify;
            }

            public void setUrlVerify(String urlVerify) {
                this.urlVerify = urlVerify;
            }
        }
    }
}
