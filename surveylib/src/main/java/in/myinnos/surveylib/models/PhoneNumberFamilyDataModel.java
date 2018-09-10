package in.myinnos.surveylib.models;

import com.google.gson.annotations.SerializedName;

public class PhoneNumberFamilyDataModel {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("gender")
    private String gender;
    @SerializedName("village_name")
    private String village_name;
    @SerializedName("advisor_id")
    private String advisor_id;
    @SerializedName("added_at")
    private String added_at;
    @SerializedName("image")
    private String image;
    @SerializedName("is_your_customer")
    private Boolean is_your_customer;

    public PhoneNumberFamilyDataModel(int id, String name, String gender, String village_name,
                                      String advisor_id, String added_at, String image,
                                      Boolean is_your_customer) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.village_name = village_name;
        this.advisor_id = advisor_id;
        this.added_at = added_at;
        this.image = image;
        this.is_your_customer = is_your_customer;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getAdvisor_id() {
        return advisor_id;
    }

    public void setAdvisor_id(String advisor_id) {
        this.advisor_id = advisor_id;
    }

    public String getAdded_at() {
        return added_at;
    }

    public void setAdded_at(String added_at) {
        this.added_at = added_at;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getIs_your_customer() {
        return is_your_customer;
    }

    public void setIs_your_customer(Boolean is_your_customer) {
        this.is_your_customer = is_your_customer;
    }
}
