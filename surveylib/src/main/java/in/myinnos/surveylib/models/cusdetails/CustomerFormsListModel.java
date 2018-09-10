package in.myinnos.surveylib.models.cusdetails;

import com.google.gson.annotations.SerializedName;

public class CustomerFormsListModel {

    @SerializedName("id")
    int id;
    @SerializedName("form_name")
    String form_name;
    @SerializedName("link")
    String form_url;
    @SerializedName("status")
    Boolean from_status;
    @SerializedName("image")
    String from_image;
    @SerializedName("notes")
    String notes;
    @SerializedName("submit_url")
    String submit_url;

    public CustomerFormsListModel(int id, String form_name, String form_url, Boolean from_status,
                                  String from_image, String notes, String submit_url) {
        this.id = id;
        this.form_name = form_name;
        this.form_url = form_url;
        this.from_status = from_status;
        this.from_image = from_image;
        this.notes = notes;
        this.submit_url = submit_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForm_name() {
        return form_name;
    }

    public void setForm_name(String form_name) {
        this.form_name = form_name;
    }

    public String getForm_url() {
        return form_url;
    }

    public void setForm_url(String form_url) {
        this.form_url = form_url;
    }

    public Boolean getFrom_status() {
        return from_status;
    }

    public void setFrom_status(Boolean from_status) {
        this.from_status = from_status;
    }

    public String getFrom_image() {
        return from_image;
    }

    public void setFrom_image(String from_image) {
        this.from_image = from_image;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSubmit_url() {
        return submit_url;
    }

    public void setSubmit_url(String submit_url) {
        this.submit_url = submit_url;
    }
}
