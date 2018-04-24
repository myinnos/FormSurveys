package in.myinnos.surveylib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myinnos on 18/01/18.
 */

public class ImageUploadModel {

    @SerializedName("id")
    private int id;

    public ImageUploadModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
