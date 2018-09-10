package in.myinnos.surveylib.models.cusdetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerDetailsModel {

    @SerializedName("name")
    String name;
    @SerializedName("dob")
    String dob;
    @SerializedName("phone_number")
    String phone_number;
    @SerializedName("gender")
    String gender;
    @SerializedName("email")
    String email;
    @SerializedName("image")
    String image;
    @SerializedName("status")
    Boolean status;
    @SerializedName("form_status")
    List<CustomerFormsListModel> customerFormsListModels;

    @SerializedName("village_name")
    String village;
    @SerializedName("preferred_language")
    String preferred_language;

    public CustomerDetailsModel(String name, String dob, String phone_number, String gender,
                                String email, String image, Boolean status,
                                List<CustomerFormsListModel> customerFormsListModels, String village,
                                String preferred_language) {
        this.name = name;
        this.dob = dob;
        this.phone_number = phone_number;
        this.gender = gender;
        this.email = email;
        this.image = image;
        this.status = status;
        this.customerFormsListModels = customerFormsListModels;
        this.village = village;
        this.preferred_language = preferred_language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<CustomerFormsListModel> getCustomerFormsListModels() {
        return customerFormsListModels;
    }

    public void setCustomerFormsListModels(List<CustomerFormsListModel> customerFormsListModels) {
        this.customerFormsListModels = customerFormsListModels;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPreferred_language() {
        return preferred_language;
    }

    public void setPreferred_language(String preferred_language) {
        this.preferred_language = preferred_language;
    }
}
