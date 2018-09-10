package in.myinnos.surveylib.models.cusdetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by myinnos on 09/02/18.
 */

public class CustListModel {

    @SerializedName("next")
    int nextId;
    @SerializedName("results")
    List<CustListDetailsModel> custListDetailModels;


    public CustListModel(int nextId, List<CustListDetailsModel> custListDetailModels) {
        this.nextId = nextId;
        this.custListDetailModels = custListDetailModels;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public List<CustListDetailsModel> getCustListDetailModels() {
        return custListDetailModels;
    }

    public void setCustListDetailModels(List<CustListDetailsModel> custListDetailModels) {
        this.custListDetailModels = custListDetailModels;
    }
}
