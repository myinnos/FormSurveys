package in.myinnos.formsurveys.ApiInterface;

/**
 * Created by myinnos on 13/03/2018.
 */

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaceSurveys {

    // get list
    @GET("/bins/xbhco.json")
    Call<JsonObject> cusRegForm();

}