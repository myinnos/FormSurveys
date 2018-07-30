package in.myinnos.surveylib.models.village;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by myinnos on 09/02/18.
 */

public class VillageListModel {

    @SerializedName("results")
    List<VillageListDetailsModel> villageListDetailsModels;


    public VillageListModel(List<VillageListDetailsModel> villageListDetailsModels) {
        this.villageListDetailsModels = villageListDetailsModels;
    }

    public List<VillageListDetailsModel> getVillageListDetailsModels() {
        return villageListDetailsModels;
    }

    public void setVillageListDetailsModels(List<VillageListDetailsModel> villageListDetailsModels) {
        this.villageListDetailsModels = villageListDetailsModels;
    }
}
