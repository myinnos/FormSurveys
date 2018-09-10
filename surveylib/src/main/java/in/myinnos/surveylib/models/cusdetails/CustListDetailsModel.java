package in.myinnos.surveylib.models.cusdetails;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myinnos on 12/03/18.
 */

public class CustListDetailsModel {

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("status")
    Boolean status;
    @SerializedName("phone_number")
    String phone_number;
    @SerializedName("image")
    String image;
    @SerializedName("gender")
    String gender;
    @SerializedName("notes")
    String verified;

    @SerializedName("village_name")
    String village_name;

    @SerializedName("is_your_customer")
    Boolean is_your_customer;
    @SerializedName("advisor_id")
    String advisor_id;

    public CustListDetailsModel(int id, String name, Boolean status, String phone_number,
                                String image, String gender, String verified, String village_name,
                                Boolean is_your_customer, String advisor_id) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.phone_number = phone_number;
        this.image = image;
        this.gender = gender;
        this.verified = verified;
        this.village_name = village_name;
        this.is_your_customer = is_your_customer;
        this.advisor_id = advisor_id;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public Boolean getIs_your_customer() {
        return is_your_customer;
    }

    public void setIs_your_customer(Boolean is_your_customer) {
        this.is_your_customer = is_your_customer;
    }

    public String getAdvisor_id() {
        return advisor_id;
    }

    public void setAdvisor_id(String advisor_id) {
        this.advisor_id = advisor_id;
    }
}
