package gallery.zicure.company.com.modellibrary.models.bloc;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 4GRYZ52 on 3/22/2017.
 */

public class ResponseBlocUser {
    @SerializedName("result")
    private ResultBlocUser result;

    public ResultBlocUser getResult() {
        return result;
    }

    public void setResult(ResultBlocUser result) {
        this.result = result;
    }

    public static class ResultBlocUser {
        @SerializedName("Success")
        private String success;
        @SerializedName("Error")
        private String error;
        @SerializedName("Data")
        private DataBloc data;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public DataBloc getData() {
            return data;
        }

        public void setData(DataBloc data) {
            this.data = data;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public static class DataBloc {
            @SerializedName("UserAccessControl") private List<UserAccessControl> bloc;
            @SerializedName("UserInfo") private UserInfo userInfo;

            public List<UserAccessControl> getBloc() {
                return bloc;
            }

            public void setBloc(List<UserAccessControl> bloc) {
                this.bloc = bloc;
            }

            public UserInfo getUserInfo() {
                return userInfo;
            }

            public void setUserInfo(UserInfo userInfo) {
                this.userInfo = userInfo;
            }

            public static class UserInfo {
                @SerializedName("firstname_th") private String firstNameTH;
                @SerializedName("lastname_th") private String lastNameTH;
                @SerializedName("firstname_en") private String firstNameEN;
                @SerializedName("lastname_en") private String lastNameEN;
                @SerializedName("point") private int point;
                @SerializedName("img_path") private String imgPath;

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

                public int getPoint() {
                    return point;
                }

                public void setPoint(int point) {
                    this.point = point;
                }

                public String getImgPath() {
                    return imgPath;
                }

                public void setImgPath(String imgPath) {
                    this.imgPath = imgPath;
                }
            }

            public static class UserAccessControl {
                @SerializedName("Category") private CategoryBloc category;
                @SerializedName("App") private List<BlocUser> blocs;

                public CategoryBloc getCategory() {
                    return category;
                }

                public void setCategory(CategoryBloc category) {
                    this.category = category;
                }

                public List<BlocUser> getBlocs() {
                    return blocs;
                }

                public void setBlocs(List<BlocUser> blocs) {
                    this.blocs = blocs;
                }

                public static class CategoryBloc {
                    @SerializedName("id") private int id;
                    @SerializedName("cat_name_th") private String categoryNameTH;
                    @SerializedName("cat_name_en") private String categoryNameEN;
                    @SerializedName("order_seq") private int orderSequence;
                    @SerializedName("cat_img_path") private String imagePath;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getCategoryNameTH() {
                        return categoryNameTH;
                    }

                    public void setCategoryNameTH(String categoryNameTH) {
                        this.categoryNameTH = categoryNameTH;
                    }

                    public String getCategoryNameEN() {
                        return categoryNameEN;
                    }

                    public void setCategoryNameEN(String categoryNameEN) {
                        this.categoryNameEN = categoryNameEN;
                    }

                    public int getOrderSequence() {
                        return orderSequence;
                    }

                    public void setOrderSequence(int orderSequence) {
                        this.orderSequence = orderSequence;
                    }

                    public String getImagePath() {
                        return imagePath;
                    }

                    public void setImagePath(String imagePath) {
                        this.imagePath = imagePath;
                    }
                }

                public static class BlocUser {
                    @SerializedName("id") private int id;
                    @SerializedName("app_name_th") private String blocNameTH;
                    @SerializedName("app_name_en") private String blocNameEN;
                    @SerializedName("order_seq") private int orderSequence;
                    @SerializedName("app_img_path") private String imagePath;
                    @SerializedName("app_url_path") private String blocURL;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getBlocNameTH() {
                        return blocNameTH;
                    }

                    public void setBlocNameTH(String blocNameTH) {
                        this.blocNameTH = blocNameTH;
                    }

                    public String getBlocNameEN() {
                        return blocNameEN;
                    }

                    public void setBlocNameEN(String blocNameEN) {
                        this.blocNameEN = blocNameEN;
                    }

                    public int getOrderSequence() {
                        return orderSequence;
                    }

                    public void setOrderSequence(int orderSequence) {
                        this.orderSequence = orderSequence;
                    }

                    public String getImagePath() {
                        return imagePath;
                    }

                    public void setImagePath(String imagePath) {
                        this.imagePath = imagePath;
                    }

                    public String getBlocURL() {
                        return blocURL;
                    }

                    public void setBlocURL(String blocURL) {
                        this.blocURL = blocURL;
                    }
                }
            }
        }
    }
}
