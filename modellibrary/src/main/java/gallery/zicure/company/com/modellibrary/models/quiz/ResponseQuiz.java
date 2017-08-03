package gallery.zicure.company.com.modellibrary.models.quiz;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pakgon on 7/31/2017 AD.
 */

public class ResponseQuiz {
    @SerializedName("result") private ResultQuiz result;

    public ResultQuiz getResult() {
        return result;
    }

    public void setResult(ResultQuiz result) {
        this.result = result;
    }

    public static class ResultQuiz {
        @SerializedName("Success") private String success;
        @SerializedName("Error") private String error;
        @SerializedName("Data") private DataQuiz data;

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

        public DataQuiz getData() {
            return data;
        }

        public void setData(DataQuiz data) {
            this.data = data;
        }

        public static class DataQuiz {
            @SerializedName("PersonalQuiz") private PersonalQuiz personalQuiz;

            public PersonalQuiz getPersonalQuiz() {
                return personalQuiz;
            }

            public void setPersonalQuiz(PersonalQuiz personalQuiz) {
                this.personalQuiz = personalQuiz;
            }

            public static class PersonalQuiz {
                @SerializedName("url") private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
