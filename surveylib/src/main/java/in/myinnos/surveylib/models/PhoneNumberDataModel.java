package in.myinnos.surveylib.models;

import com.google.gson.annotations.SerializedName;

public class PhoneNumberDataModel {

    @SerializedName("cust_id")
    private int cust_id;
    @SerializedName("is_registered")
    private Boolean is_registered;
    @SerializedName("msg")
    private String msg;

    public PhoneNumberDataModel(int cust_id, Boolean is_registered, String msg) {
        this.cust_id = cust_id;
        this.is_registered = is_registered;
        this.msg = msg;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public Boolean getIs_registered() {
        return is_registered;
    }

    public void setIs_registered(Boolean is_registered) {
        this.is_registered = is_registered;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
