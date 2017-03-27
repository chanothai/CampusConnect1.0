package gallery.zicure.company.com.modellibrary.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 4GRYZ52 on 3/22/2017.
 */

public class ResponseUserInfo {
    @SerializedName("result")
    private ResultUserInfo result;

    public ResultUserInfo getResult() {
        return result;
    }

    public void setResult(ResultUserInfo result) {
        this.result = result;
    }

    public static class ResultUserInfo {
        @SerializedName("Success")
        private String success;
        @SerializedName("Error")
        private String error;
        @SerializedName("Data")
        private DataUserInfo data;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public DataUserInfo getData() {
            return data;
        }

        public void setData(DataUserInfo data) {
            this.data = data;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public static class DataUserInfo {
            @SerializedName("User")
            private User user;

            public User getUser() {
                return user;
            }

            public void setUser(User user) {
                this.user = user;
            }

            public static class User {
                @SerializedName("username")
                private String username;
                @SerializedName("created")
                private String created;
                @SerializedName("modified")
                private String modified;
                @SerializedName("first_name")
                private String firstName;
                @SerializedName("last_name")
                private String lastName;
                @SerializedName("screen_name")
                private String screenName;
                @SerializedName("birth_date")
                private String birthday;
                @SerializedName("citizen_id")
                private String citizenID;
                @SerializedName("phone")
                private String phone;
                @SerializedName("profile_image_path")
                private String imgPath;

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getCreated() {
                    return created;
                }

                public void setCreated(String created) {
                    this.created = created;
                }

                public String getModified() {
                    return modified;
                }

                public void setModified(String modified) {
                    this.modified = modified;
                }

                public String getFirstName() {
                    return firstName;
                }

                public void setFirstName(String firstName) {
                    this.firstName = firstName;
                }

                public String getLastName() {
                    return lastName;
                }

                public void setLastName(String lastName) {
                    this.lastName = lastName;
                }

                public String getScreenName() {
                    return screenName;
                }

                public void setScreenName(String screenName) {
                    this.screenName = screenName;
                }

                public String getBirthday() {
                    return birthday;
                }

                public void setBirthday(String birthday) {
                    this.birthday = birthday;
                }

                public String getCitizenID() {
                    return citizenID;
                }

                public void setCitizenID(String citizenID) {
                    this.citizenID = citizenID;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getImgPath() {
                    return imgPath;
                }

                public void setImgPath(String imgPath) {
                    this.imgPath = imgPath;
                }
            }
        }
    }
}
