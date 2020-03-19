package in.myinnos.surveylib.models.numbeDuplicationCheck;

import com.google.gson.annotations.SerializedName;

public class NumberDuplicationCheckListModel {

    @SerializedName("advisor_id")
    private String advisor_id;

    public NumberDuplicationCheckListModel(String advisor_id) {
        this.advisor_id = advisor_id;
    }

    public String getAdvisor_id() {
        return advisor_id;
    }

    public void setAdvisor_id(String advisor_id) {
        this.advisor_id = advisor_id;
    }
}
