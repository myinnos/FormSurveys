package in.myinnos.surveylib.models.numbeDuplicationCheck;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NumberDuplicationCheckModel {

    @SerializedName("results")
    private List<NumberDuplicationCheckListModel> results;

    public NumberDuplicationCheckModel(List<NumberDuplicationCheckListModel> results) {
        this.results = results;
    }

    public List<NumberDuplicationCheckListModel> getResults() {
        return results;
    }

    public void setResults(List<NumberDuplicationCheckListModel> results) {
        this.results = results;
    }
}
