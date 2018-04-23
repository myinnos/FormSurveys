package in.myinnos.surveylib.ApiInterface;

/**
 * Created by 10 on 10-02-2017.
 */


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SurveysApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String BASE_URL) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}