package in.myinnos.surveylib.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhoneNumberDataModel {

    @SerializedName("family_members")
    private List<PhoneNumberFamilyDataModel> phoneNumberFamilyDataModelList;
    @SerializedName("is_new")
    private Boolean is_new;
    @SerializedName("can_add_message")
    private String can_add_message;
    @SerializedName("can_add")
    private Boolean can_add;

    public PhoneNumberDataModel(List<PhoneNumberFamilyDataModel> phoneNumberFamilyDataModelList,
                                Boolean is_new, String can_add_message, Boolean can_add) {
        this.phoneNumberFamilyDataModelList = phoneNumberFamilyDataModelList;
        this.is_new = is_new;
        this.can_add_message = can_add_message;
        this.can_add = can_add;
    }

    public List<PhoneNumberFamilyDataModel> getPhoneNumberFamilyDataModelList() {
        return phoneNumberFamilyDataModelList;
    }

    public void setPhoneNumberFamilyDataModelList(List<PhoneNumberFamilyDataModel> phoneNumberFamilyDataModelList) {
        this.phoneNumberFamilyDataModelList = phoneNumberFamilyDataModelList;
    }

    public Boolean getIs_new() {
        return is_new;
    }

    public void setIs_new(Boolean is_new) {
        this.is_new = is_new;
    }

    public String getCan_add_message() {
        return can_add_message;
    }

    public void setCan_add_message(String can_add_message) {
        this.can_add_message = can_add_message;
    }

    public Boolean getCan_add() {
        return can_add;
    }

    public void setCan_add(Boolean can_add) {
        this.can_add = can_add;
    }
}
