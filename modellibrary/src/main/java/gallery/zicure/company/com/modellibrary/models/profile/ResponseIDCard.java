package gallery.zicure.company.com.modellibrary.models.profile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ballomo on 7/30/2017 AD.
 */

public class ResponseIDCard {
    @SerializedName("result") private ResultProfile result;

    public ResultProfile getResult() {
        return result;
    }

    public void setResult(ResultProfile result) {
        this.result = result;
    }

    public static class ResultProfile {
        @SerializedName("Success") private String success;
        @SerializedName("Error") private String error;
        @SerializedName("ProfileData") private ProfileData data;

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

        public ProfileData getData() {
            return data;
        }

        public void setData(ProfileData data) {
            this.data = data;
        }


        public static class ProfileData {
            @SerializedName("profile_id") private int profileID;
            @SerializedName("person_card_no") private String cardNo;
            @SerializedName("firstname_th") private String firstNameTH;
            @SerializedName("lastname_th") private String lastNameTH;
            @SerializedName("firstname_en") private String firstNameEN;
            @SerializedName("lastname_en") private String lastNameEN;
            @SerializedName("moblie_no") private String mobile;
            @SerializedName("phone_no") private String phone;
            @SerializedName("email") private String email;
            @SerializedName("year_level") private String yearLavel;
            @SerializedName("section") private String section;
            @SerializedName("department") private String department;
            @SerializedName("organization") private String organization;
            @SerializedName("study_start") private String studyStart;
            @SerializedName("img_path") private String imgPath;
            @SerializedName("username") private String username;
            @SerializedName("position_org") private String positionOrg;
            @SerializedName("position_edu") private String positionEdu;
            @SerializedName("citizen_id") private String citizenId;

            public int getProfileID() {
                return profileID;
            }

            public void setProfileID(int profileID) {
                this.profileID = profileID;
            }

            public String getCardNo() {
                return cardNo;
            }

            public void setCardNo(String cardNo) {
                this.cardNo = cardNo;
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

            public String getFirstNameEN() {
                return firstNameEN;
            }

            public void setFirstNameEN(String firstNameEN) {
                this.firstNameEN = firstNameEN;
            }

            public String getLastNameEN() {
                return lastNameEN;
            }

            public void setLastNameEN(String lastNameEN) {
                this.lastNameEN = lastNameEN;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getSection() {
                return section;
            }

            public void setSection(String section) {
                this.section = section;
            }

            public String getDepartment() {
                return department;
            }

            public void setDepartment(String department) {
                this.department = department;
            }

            public String getOrganization() {
                return organization;
            }

            public void setOrganization(String organization) {
                this.organization = organization;
            }

            public String getImgPath() {
                return imgPath;
            }

            public void setImgPath(String imgPath) {
                this.imgPath = imgPath;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPositionOrg() {
                return positionOrg;
            }

            public void setPositionOrg(String positionOrg) {
                this.positionOrg = positionOrg;
            }

            public String getPositionEdu() {
                return positionEdu;
            }

            public void setPositionEdu(String positionEdu) {
                this.positionEdu = positionEdu;
            }

            public String getCitizenId() {
                return citizenId;
            }

            public void setCitizenId(String citizenId) {
                this.citizenId = citizenId;
            }

            public String getYearLavel() {
                return yearLavel;
            }

            public void setYearLavel(String yearLavel) {
                this.yearLavel = yearLavel;
            }

            public String getStudyStart() {
                return studyStart;
            }

            public void setStudyStart(String studyStart) {
                this.studyStart = studyStart;
            }
        }
    }
}
