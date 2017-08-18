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
            @SerializedName("orgs_list") private List<ORGList> orgLists;

            public List<ORGList> getOrgLists() {
                return orgLists;
            }

            public void setOrgLists(List<ORGList> orgLists) {
                this.orgLists = orgLists;
            }

            public class ORGList {
                @SerializedName("org_id") private int id;
                @SerializedName("org_name") private String name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
