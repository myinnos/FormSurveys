package in.myinnos.surveylib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myinnos on 18/01/18.
 */

public class ImageUploadModel {

    @SerializedName("id")
    private String id;
    @SerializedName("file")
    private String file;
    @SerializedName("file_type")
    private String file_type;

    public ImageUploadModel(String id, String file, String file_type) {
        this.id = id;
        this.file = file;
        this.file_type = file_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }
}
