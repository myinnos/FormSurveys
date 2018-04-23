package in.myinnos.surveylib.models;

import com.google.gson.annotations.SerializedName;

public class PhoneNumberModel {

    @SerializedName("data")
    private PhoneNumberDataModel phoneNumberDataModel;

    public PhoneNumberModel(PhoneNumberDataModel phoneNumberDataModel) {
        this.phoneNumberDataModel = phoneNumberDataModel;
    }

    public PhoneNumberDataModel getPhoneNumberDataModel() {
        return phoneNumberDataModel;
    }

    public void setPhoneNumberDataModel(PhoneNumberDataModel phoneNumberDataModel) {
        this.phoneNumberDataModel = phoneNumberDataModel;
    }
}
