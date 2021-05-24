package in.myinnos.formsurveys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;

import in.myinnos.formsurveys.ApiInterface.ApiInterfaceSurveys;
import in.myinnos.surveylib.SurveyActivity;
import in.myinnos.surveylib.widgets.AppSurveyConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int SURVEY_REQUEST = 1337;
    private Button btSurveyForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSurveyForm = (Button) findViewById(R.id.btSurveyForm);

        btSurveyForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://staging-associate.1bridge.in:8888/media/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiInterfaceSurveys apiInterfaceSurveys = retrofit.create(ApiInterfaceSurveys.class);

                Call<JsonObject> jsonCall = apiInterfaceSurveys.cusRegForm();

                Log.d("wedweqdwe", String.valueOf(jsonCall.request().url()));
                jsonCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        Log.d("wedweqdwe", String.valueOf(response.body().toString()));
                        openSurvey(response.body().toString(), "RB7788", "http://staging-associate.1bridge.in:8888/api/v1/");

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("wedweqdwe", String.valueOf(t.getMessage()));
                    }
                });
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SURVEY_REQUEST) {
            if (resultCode == RESULT_OK) {

                String answers_json = data.getExtras().getString("answers");
                Log.d("****", "****************** WE HAVE ANSWERS ******************");
                Log.v("ANSWERS JSON", answers_json);
                Log.d("****", "*****************************************************");

                //do whatever you want with them...
            }
        }
    }

    //json stored in the assets folder. but you can get it from wherever you like.
    private String loadSurveyJson(String filename) {
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void openSurvey(String json, String registered_by, String base_url) {
        Intent i_survey = new Intent(MainActivity.this, SurveyActivity.class);
        //i_survey.putExtra("json_survey", loadSurveyJson("customer_survey.json"));
        i_survey.putExtra("json_survey", json);
        i_survey.putExtra(AppSurveyConstants.SUR_REGISTERED_BY, registered_by);
        i_survey.putExtra(AppSurveyConstants.SUR_REGISTERED_DESIGNATION, "1BA");
        i_survey.putExtra(AppSurveyConstants.BASE_URL, base_url);
        i_survey.putExtra(AppSurveyConstants.SUR_CUSTOMER_ID, "1");
        i_survey.putExtra(AppSurveyConstants.FORM_NAME, "surveys/abhis-create/");
        i_survey.putExtra(AppSurveyConstants.SOURCE_EXTRA, "0");
        i_survey.putExtra(AppSurveyConstants.CUSTOMER_PHONE_EXTRA, "0");
        i_survey.putExtra(AppSurveyConstants.STATION_ID, "0");
        i_survey.putExtra(AppSurveyConstants.SURVEY_TASK_ID, "0");
        i_survey.putExtra(AppSurveyConstants.SUR_ONBOARD_REGISTRATION, "M1BA");
        startActivityForResult(i_survey, SURVEY_REQUEST);
    }
}
