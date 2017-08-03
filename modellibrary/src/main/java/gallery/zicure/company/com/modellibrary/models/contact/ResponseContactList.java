package gallery.zicure.company.com.modellibrary.models.contact;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pakgon on 7/31/2017 AD.
 */

public class ResponseContactList {
    @SerializedName("result") private ResultContactList result;

    public ResultContactList getResult() {
        return result;
    }

    public void setResult(ResultContactList result) {
        this.result = result;
    }

    public static class ResultContactList {
        @SerializedName("Success") private String success;
        @SerializedName("Error") private String error;
        @SerializedName("Data") private List<ContactListData> data;

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

        public List<ContactListData> getData() {
            return data;
        }

        public void setData(List<ContactListData> data) {
            this.data = data;
        }

        public static class ContactListData {
            @SerializedName("UserPersonal") private UserPersonal userPersonal;

            public UserPersonal getUserPersonal() {
                return userPersonal;
            }

            public void setUserPersonal(UserPersonal userPersonal) {
                this.userPersonal = userPersonal;
            }

            public static class UserPersonal {
                @SerializedName("user_id") private int id;
                @SerializedName("firstname_th") private String firstNameTH;
                @SerializedName("lastname_th") private String lastNameTH;
                @SerializedName("firstname_en") private String firstNameEN;
                @SerializedName("lastname_en") private String lastNameEN;
                @SerializedName("moblie_no") private String mobileNo;
                @SerializedName("phone_no") private String phoneNo;
                @SerializedName("img_part") private String imgPath;
                @SerializedName("dept_name_th") private String department;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getFirstNameTH() {
                    return firstNameTH;
                }

                public void setFirstNameTH(String firstNameTH) {
                    this.firstNameTH = firstNameTH;
                }

                public String getLastNameTH() {
                    return lastNameTH;
                }

                public void setLastNameTH(String lastNameTH) {
                    this.lastNameTH = lastNameTH;
                }

                public String getLastNameEN() {
                    return lastNameEN;
                }

                public void setLastNameEN(String lastNameEN) {
                    this.lastNameEN = lastNameEN;
                }

                public String getFirstNameEN() {
                    return firstNameEN;
                }

                public void setFirstNameEN(String firstNameEN) {
                    this.firstNameEN = firstNameEN;
                }

                public String getMobileNo() {
                    return mobileNo;
                }

                public void setMobileNo(String mobileNo) {
                    this.mobileNo = mobileNo;
                }

                public String getPhoneNo() {
                    return phoneNo;
                }

                public void setPhoneNo(String phoneNo) {
                    this.phoneNo = phoneNo;
                }

                public String getImgPath() {
                    return imgPath;
                }

                public void setImgPath(String imgPath) {
                    this.imgPath = imgPath;
                }

                public String getDepartment() {
                    return department;
                }

                public void setDepartment(String department) {
                    this.department = department;
                }
            }
        }
    }
}
