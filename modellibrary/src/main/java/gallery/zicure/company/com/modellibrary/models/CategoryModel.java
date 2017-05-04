package gallery.zicure.company.com.modellibrary.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pakgon on 4/28/2017 AD.
 */

public class CategoryModel {
    //create model here
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

        @SerializedName("Data")
        private List<Data> data;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }


        public static class Data {
            @SerializedName("BlocCategory")
            private BlocCategory blogCategories;
            @SerializedName("Bloc")
            private List<Bloc> bloc;

            public BlocCategory getBlogCategories() {
                return blogCategories;
            }

            public void setBlogCategories(BlocCategory blogCategories) {
                this.blogCategories = blogCategories;
            }

            public List<Bloc> getBloc() {
                return bloc;
            }

            public void setBloc(List<Bloc> bloc) {
                this.bloc = bloc;
            }

            public static class BlocCategory {
                @SerializedName("id")
                private int id;
                @SerializedName("bloc_category_name")
                private String blocCategoryName;
                @SerializedName("sort_order")
                private int sortOrder;
                @SerializedName("created")
                private String created;
                @SerializedName("modified")
                private String modified;
                @SerializedName("bloc_category_image_path")
                private String blocCategoryImagePath;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getBlocCategoryName() {
                    return blocCategoryName;
                }

                public void setBlocCategoryName(String blocCategoryName) {
                    this.blocCategoryName = blocCategoryName;
                }

                public int getSortOrder() {
                    return sortOrder;
                }

                public void setSortOrder(int sortOrder) {
                    this.sortOrder = sortOrder;
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

                public String getBlocCategoryImagePath() {
                    return blocCategoryImagePath;
                }

                public void setBlocCategoryImagePath(String blocCategoryImagePath) {
                    this.blocCategoryImagePath = blocCategoryImagePath;
                }
            }

            public static class Bloc {
                @SerializedName("id")
                private int id;
                @SerializedName("bloc_name")
                private String blocName;
                @SerializedName("bloc_description")
                private String blocDescription;
                @SerializedName("bloc_owner_id")
                private int blocOwnerId;
                @SerializedName("bloc_url")
                private String blocUrl;
                @SerializedName("bloc_icon_path")
                private String blocIconPath;
                @SerializedName("bloc_image1_path")
                private String blocImage1Path;
                @SerializedName("bloc_image2_path")
                private String blocImage2Path;
                @SerializedName("bloc_image3_path")
                private String blocImage3Path;
                @SerializedName("bloc_category_id")
                private int blocCategoryId;
                @SerializedName("created")
                private String created;
                @SerializedName("modified")
                private String modified;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getBlocName() {
                    return blocName;
                }

                public void setBlocName(String blocName) {
                    this.blocName = blocName;
                }

                public String getBlocDescription() {
                    return blocDescription;
                }

                public void setBlocDescription(String blocDescription) {
                    this.blocDescription = blocDescription;
                }

                public int getBlocOwnerId() {
                    return blocOwnerId;
                }

                public void setBlocOwnerId(int blocOwnerId) {
                    this.blocOwnerId = blocOwnerId;
                }

                public String getBlocUrl() {
                    return blocUrl;
                }

                public void setBlocUrl(String blocUrl) {
                    this.blocUrl = blocUrl;
                }

                public String getBlocIconPath() {
                    return blocIconPath;
                }

                public void setBlocIconPath(String blocIconPath) {
                    this.blocIconPath = blocIconPath;
                }

                public String getBlocImage1Path() {
                    return blocImage1Path;
                }

                public void setBlocImage1Path(String blocImage1Path) {
                    this.blocImage1Path = blocImage1Path;
                }

                public String getBlocImage2Path() {
                    return blocImage2Path;
                }

                public void setBlocImage2Path(String blocImage2Path) {
                    this.blocImage2Path = blocImage2Path;
                }

                public String getBlocImage3Path() {
                    return blocImage3Path;
                }

                public void setBlocImage3Path(String blocImage3Path) {
                    this.blocImage3Path = blocImage3Path;
                }

                public int getBlocCategoryId() {
                    return blocCategoryId;
                }

                public void setBlocCategoryId(int blocCategoryId) {
                    this.blocCategoryId = blocCategoryId;
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
            }
        }
    }
}
