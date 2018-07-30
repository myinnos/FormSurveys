package in.myinnos.surveylib.models.village;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myinnos on 12/03/18.
 */

public class VillageListDetailsModel {

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("district_name")
    String district_name;

    public VillageListDetailsModel(int id, String name, String district_name) {
        this.id = id;
        this.name = name;
        this.district_name = district_name;
    }

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

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }
}
