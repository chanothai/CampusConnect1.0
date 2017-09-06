package gallery.zicure.company.com.modellibrary.models.register;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pakgon on 8/18/2017 AD.
 */

public class ResponseUniversities {
    @SerializedName("result") private ResultOrg result;

    public ResultOrg getResult() {
        return result;
    }

    public void setResult(ResultOrg result) {
        this.result = result;
    }

    public class ResultOrg {
        @SerializedName("Success") private String success;
        @SerializedName("Error") private String error;
        @SerializedName("Data") private DataOrg data;

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

        public DataOrg getData() {
            return data;
        }

        public void setData(DataOrg data) {
            this.data = data;
        }


        public class DataOrg {
            @SerializedName("UserType") private List<UserType> userTypes;
            @SerializedName("Org_name") private List<OrgName> orgNames;

            public List<UserType> getUserTypes() {
                return userTypes;
            }

            public void setUserTypes(List<UserType> userTypes) {
                this.userTypes = userTypes;
            }

            public List<OrgName> getOrgNames() {
                return orgNames;
            }

            public void setOrgNames(List<OrgName> orgNames) {
                this.orgNames = orgNames;
            }


            public class UserType {
                @SerializedName("user_type_id") private int userTypeId;
                @SerializedName("user_type_name_th") private String userTypeNameTH;
                @SerializedName("user_type_name_en") private String userTypeNameEN;

                public int getUserTypeId() {
                    return userTypeId;
                }

                public void setUserTypeId(int userTypeId) {
                    this.userTypeId = userTypeId;
                }

                public String getUserTypeNameTH() {
                    return userTypeNameTH;
                }

                public void setUserTypeNameTH(String userTypeNameTH) {
                    this.userTypeNameTH = userTypeNameTH;
                }

                public String getUserTypeNameEN() {
                    return userTypeNameEN;
                }

                public void setUserTypeNameEN(String userTypeNameEN) {
                    this.userTypeNameEN = userTypeNameEN;
                }
            }

            public class OrgName {
                @SerializedName("org_id") private int orgId;
                @SerializedName("org_name_th") private String orgNameTH;
                @SerializedName("org_name_en") private String orgNameEN;

                public int getOrgId() {
                    return orgId;
                }

                public void setOrgId(int orgId) {
                    this.orgId = orgId;
                }

                public String getOrgNameTH() {
                    return orgNameTH;
                }

                public void setOrgNameTH(String orgNameTH) {
                    this.orgNameTH = orgNameTH;
                }

                public String getOrgNameEN() {
                    return orgNameEN;
                }

                public void setOrgNameEN(String orgNameEN) {
                    this.orgNameEN = orgNameEN;
                }
            }
        }
    }
}
