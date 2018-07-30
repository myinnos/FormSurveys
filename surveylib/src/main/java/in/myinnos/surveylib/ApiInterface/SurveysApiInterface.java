package in.myinnos.surveylib.ApiInterface;

/**
 * Created by myinnos on 04/01/2018.
 */

import in.myinnos.surveylib.models.ImageUploadModel;
import in.myinnos.surveylib.models.PhoneNumberModel;
import in.myinnos.surveylib.models.village.VillageListModel;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface SurveysApiInterface {

    ////////////////////////////////////////////////////////////////////////////////////

    // get base view details
    @GET("customer/check-registration/")
    Call<PhoneNumberModel> phoneNumberVerification(
            @Query("mobile_number") String mobile_number);

    ////////////////////////////////////////////////////////////////////////////////////

    // get list of reg customers
    @GET("master/villages-list/")
    Call<VillageListModel> villageListRBA(@Query("source") String source,
                                          @Query("oneba__advisor_id") String user_id);

    @GET("master/villages-list/")
    Call<VillageListModel> villageListMBA(@Query("source") String source,
                                          @Query("master1ba__advisor_id") String user_id);


    // post image upload
    @Multipart
    @POST("assessment/kyc-images/")
    Call<ImageUploadModel> uploadImage
    (@Part("file\"; filename=\"image.jpg\"") RequestBody file);

}