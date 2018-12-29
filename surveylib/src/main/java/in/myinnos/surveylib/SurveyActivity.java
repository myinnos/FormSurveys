package in.myinnos.surveylib;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import in.myinnos.surveylib.ApiInterface.SurveysApiClient;
import in.myinnos.surveylib.ApiInterface.SurveysApiInterface;
import in.myinnos.surveylib.adapters.AdapterFragmentQ;
import in.myinnos.surveylib.fragment.FragmentCheckboxes;
import in.myinnos.surveylib.fragment.FragmentDate;
import in.myinnos.surveylib.fragment.FragmentEnd;
import in.myinnos.surveylib.fragment.FragmentImage;
import in.myinnos.surveylib.fragment.FragmentMultiline;
import in.myinnos.surveylib.fragment.FragmentNumber;
import in.myinnos.surveylib.fragment.FragmentPDF;
import in.myinnos.surveylib.fragment.FragmentRadioboxes;
import in.myinnos.surveylib.fragment.FragmentStart;
import in.myinnos.surveylib.fragment.FragmentTextSimple;
import in.myinnos.surveylib.libCamera.bean.PickResult;
import in.myinnos.surveylib.libCamera.listeners.IPickResult;
import in.myinnos.surveylib.models.ImageUploadModel;
import in.myinnos.surveylib.models.Question;
import in.myinnos.surveylib.models.RealmQuestionAnswersModel;
import in.myinnos.surveylib.models.SurveyPojo;
import in.myinnos.surveylib.widgets.AppSurveyConstants;
import in.myinnos.surveylib.widgets.SurveySharedFlows;
import in.myinnos.surveylib.widgets.bottomview.BottomDialog;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyActivity extends AppCompatActivity implements IPickResult {

    private SurveyPojo mSurveyPojo;
    private ViewPager mPager;
    private String style_string = null;
    private String registered_by;
    private String customer_id;
    private String registered_designation;
    private String latitude, longitude;
    private String source_extra, customer_phone_extra;
    private String base_url;
    private String form_name;
    File photoFile;
    private LinearLayout liProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_survey);

        // Initialize Realm
        Realm.init(this);


        photoFile = new File(getExternalFilesDir("img"), "survey_scan.jpg");
        liProgress = (LinearLayout) findViewById(R.id.liProgress);
        liProgress.setVisibility(View.GONE);


        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mSurveyPojo = new Gson().fromJson(bundle.getString("json_survey"), SurveyPojo.class);
            /// registered_by user
            registered_by = bundle.getString(AppSurveyConstants.SUR_REGISTERED_BY);
            registered_designation = bundle.getString(AppSurveyConstants.SUR_REGISTERED_DESIGNATION);
            customer_id = bundle.getString(AppSurveyConstants.SUR_CUSTOMER_ID);
            base_url = bundle.getString(AppSurveyConstants.BASE_URL);
            form_name = bundle.getString(AppSurveyConstants.FORM_NAME);

            latitude = bundle.getString(AppSurveyConstants.SUR_LATITUDE);
            longitude = bundle.getString(AppSurveyConstants.SUR_LONGITUDE);

            source_extra = bundle.getString(AppSurveyConstants.SOURCE_EXTRA);
            customer_phone_extra = bundle.getString(AppSurveyConstants.CUSTOMER_PHONE_EXTRA);

            SharedPreferences.Editor editor = getSharedPreferences(AppSurveyConstants.PREFERENCES_SURVEYS, MODE_PRIVATE).edit();
            editor.putString(AppSurveyConstants.FORM_NAME, form_name);
            editor.apply();

            //
            if (bundle.containsKey("style")) {
                style_string = bundle.getString("style");
            }
        }


        Log.i("json Object = ", String.valueOf(mSurveyPojo.getQuestions()));

        final ArrayList<Fragment> arraylist_fragments = new ArrayList<>();

        //- START -
        if (!mSurveyPojo.getSurveyProperties().getSkipIntro()) {
            FragmentStart frag_start = new FragmentStart();
            Bundle sBundle = new Bundle();
            sBundle.putSerializable("survery_properties", mSurveyPojo.getSurveyProperties());
            sBundle.putString("style", style_string);
            sBundle.putString(AppSurveyConstants.SUR_REGISTERED_BY, registered_by);
            sBundle.putString(AppSurveyConstants.SUR_REGISTERED_DESIGNATION, registered_designation);
            sBundle.putString(AppSurveyConstants.SUR_CUSTOMER_ID, customer_id);
            sBundle.putString(AppSurveyConstants.SUR_LATITUDE, latitude);
            sBundle.putString(AppSurveyConstants.SUR_LONGITUDE, longitude);
            sBundle.putString(AppSurveyConstants.SOURCE_EXTRA, source_extra);
            sBundle.putString(AppSurveyConstants.CUSTOMER_PHONE_EXTRA, customer_phone_extra);
            frag_start.setArguments(sBundle);
            arraylist_fragments.add(frag_start);
        }

        //- FILL -
        for (Question mQuestion : mSurveyPojo.getQuestions()) {

            if (mQuestion.getQuestionType().equals("Date")) {
                FragmentDate frag = new FragmentDate();
                Bundle xBundle = new Bundle();
                xBundle.putSerializable("data", mQuestion);
                xBundle.putString("style", style_string);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_BY, registered_by);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_DESIGNATION, registered_designation);
                xBundle.putString(AppSurveyConstants.SUR_CUSTOMER_ID, customer_id);
                xBundle.putString(AppSurveyConstants.BASE_URL, base_url);
                frag.setArguments(xBundle);
                arraylist_fragments.add(frag);
            }

            if (mQuestion.getQuestionType().equals("String")) {
                FragmentTextSimple frag = new FragmentTextSimple();
                Bundle xBundle = new Bundle();
                xBundle.putSerializable("data", mQuestion);
                xBundle.putString("style", style_string);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_BY, registered_by);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_DESIGNATION, registered_designation);
                xBundle.putString(AppSurveyConstants.SUR_CUSTOMER_ID, customer_id);
                xBundle.putString(AppSurveyConstants.BASE_URL, base_url);
                frag.setArguments(xBundle);
                arraylist_fragments.add(frag);
            }

            if (mQuestion.getQuestionType().equals("Checkboxes")) {
                FragmentCheckboxes frag = new FragmentCheckboxes();
                Bundle xBundle = new Bundle();
                xBundle.putSerializable("data", mQuestion);
                xBundle.putString("style", style_string);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_BY, registered_by);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_DESIGNATION, registered_designation);
                xBundle.putString(AppSurveyConstants.SUR_CUSTOMER_ID, customer_id);
                xBundle.putString(AppSurveyConstants.BASE_URL, base_url);
                frag.setArguments(xBundle);
                arraylist_fragments.add(frag);
            }

            if (mQuestion.getQuestionType().equals("Radioboxes")) {
                FragmentRadioboxes frag = new FragmentRadioboxes();
                Bundle xBundle = new Bundle();
                xBundle.putSerializable("data", mQuestion);
                xBundle.putString("style", style_string);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_BY, registered_by);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_DESIGNATION, registered_designation);
                xBundle.putString(AppSurveyConstants.SUR_CUSTOMER_ID, customer_id);
                xBundle.putString(AppSurveyConstants.BASE_URL, base_url);
                frag.setArguments(xBundle);
                arraylist_fragments.add(frag);
            }

            if (mQuestion.getQuestionType().equals("Number")) {
                FragmentNumber frag = new FragmentNumber();
                Bundle xBundle = new Bundle();
                xBundle.putSerializable("data", mQuestion);
                xBundle.putString("style", style_string);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_BY, registered_by);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_DESIGNATION, registered_designation);
                xBundle.putString(AppSurveyConstants.SUR_CUSTOMER_ID, customer_id);
                xBundle.putString(AppSurveyConstants.BASE_URL, base_url);
                xBundle.putBoolean(AppSurveyConstants.CUSTOMER_VIEW_VISIBILITY, false);
                frag.setArguments(xBundle);
                arraylist_fragments.add(frag);
            }

            if (mQuestion.getQuestionType().equals("StringMultiline")) {
                FragmentMultiline frag = new FragmentMultiline();
                Bundle xBundle = new Bundle();
                xBundle.putSerializable("data", mQuestion);
                xBundle.putString("style", style_string);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_BY, registered_by);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_DESIGNATION, registered_designation);
                xBundle.putString(AppSurveyConstants.SUR_CUSTOMER_ID, customer_id);
                xBundle.putString(AppSurveyConstants.BASE_URL, base_url);
                frag.setArguments(xBundle);
                arraylist_fragments.add(frag);
            }

            if (mQuestion.getQuestionType().equals("Image")) {
                FragmentImage frag = new FragmentImage();
                Bundle xBundle = new Bundle();
                xBundle.putSerializable("data", mQuestion);
                xBundle.putString("style", style_string);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_BY, registered_by);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_DESIGNATION, registered_designation);
                xBundle.putString(AppSurveyConstants.SUR_CUSTOMER_ID, customer_id);
                xBundle.putString(AppSurveyConstants.BASE_URL, base_url);
                frag.setArguments(xBundle);
                arraylist_fragments.add(frag);
            }

            if (mQuestion.getQuestionType().equals("PDF")) {
                FragmentPDF frag = new FragmentPDF();
                Bundle xBundle = new Bundle();
                xBundle.putSerializable("data", mQuestion);
                xBundle.putString("style", style_string);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_BY, registered_by);
                xBundle.putString(AppSurveyConstants.SUR_REGISTERED_DESIGNATION, registered_designation);
                xBundle.putString(AppSurveyConstants.SUR_CUSTOMER_ID, customer_id);
                xBundle.putString(AppSurveyConstants.BASE_URL, base_url);
                frag.setArguments(xBundle);
                arraylist_fragments.add(frag);
            }

        }

        //- END -
        FragmentEnd frag_end = new FragmentEnd();
        Bundle eBundle = new Bundle();
        eBundle.putSerializable("survery_properties", mSurveyPojo.getSurveyProperties());
        eBundle.putString("style", style_string);
        eBundle.putString(AppSurveyConstants.SUR_REGISTERED_BY, registered_by);
        eBundle.putString(AppSurveyConstants.SUR_REGISTERED_DESIGNATION, registered_designation);
        eBundle.putString(AppSurveyConstants.SUR_CUSTOMER_ID, customer_id);
        eBundle.putString(AppSurveyConstants.BASE_URL, base_url);
        frag_end.setArguments(eBundle);
        arraylist_fragments.add(frag_end);


        mPager = (ViewPager) findViewById(R.id.pager);
        AdapterFragmentQ mPagerAdapter = new AdapterFragmentQ(getSupportFragmentManager(), arraylist_fragments);
        mPager.setAdapter(mPagerAdapter);

    }

    public void go_to_next() {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            //super.onBackPressed();

            new BottomDialog.Builder(this)
                    .setTitle(R.string.close_popup_title)
                    .setContent(R.string.close_popup_message)
                    .setPositiveText(R.string.close_popup_ok)
                    .setNegativeText(R.string.close_popup_no)
                    .setPositiveBackgroundColorResource(R.color.colorPrimary)
                    //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                    .setPositiveTextColorResource(android.R.color.white)
                    .setNegativeTextColorResource(R.color.colorAccent)
                    //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                    .onPositive(new BottomDialog.ButtonCallback() {
                        @Override
                        public void onClick(BottomDialog dialog) {
                            SurveyActivity.this.finish();
                        }
                    }).show();

        } else {

            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void event_survey_completed(Answers instance) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("answers", instance.get_json_object());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onPickResult(final PickResult r) {
        if (r.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
            //getImageView().setImageBitmap(r.getBitmap());

            //Log.d("photoFile_Path",r.getUri());

            //Uri imageUri = Uri.fromFile(new File(String.valueOf(photoFile.getPath())));
            Log.d("asda", String.valueOf(r.getUri()));
            //sendImageToServer(imageUri);

            liProgress.setVisibility(View.VISIBLE);

            SurveysApiInterface apiService =
                    SurveysApiClient.getClient(base_url).create(SurveysApiInterface.class);

            File file = new File(r.getPath());
            Log.d("asda", String.valueOf(file));
            // create RequestBody instance from file

            String mimeType = "multipart/form-data";
            //URLConnection.guessContentTypeFromName(file.getName());

            //Log.d("asda", String.valueOf(MediaType.parse("multipart/form-data")));
            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(mimeType),
                            file
                    );

            Log.d("asdafg", String.valueOf(requestFile));
            Call<ImageUploadModel> call = null;

            SharedPreferences prefs = getSharedPreferences(AppSurveyConstants.PREFERENCES_SURVEYS, MODE_PRIVATE);
            String formText = prefs.getString(AppSurveyConstants.FORM_NAME, "");


            RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);

            if (formText.equals("surveys/abhis-create")) {
                call = apiService.uploadImageAbhi(fbody);
            } else {
                call = apiService.uploadImage(fbody);
            }

            call.enqueue(new Callback<ImageUploadModel>() {
                @Override
                public void onResponse(Call<ImageUploadModel> call, Response<ImageUploadModel> response) {
                    liProgress.setVisibility(View.GONE);
                    //CustomToast.ToastCenter(PickUpActivity.this, "Successfully Updated!");
                    //Helper.restartApp(DeliveryActivity.this);

                    //Log.d("sdcsc", response.body().getFile());
                    Log.d("sdcsc", String.valueOf(response.body().getId()));

                    FragmentImage frag = new FragmentImage();
                    frag.moveNext(String.valueOf(response.body().getId()),
                            SurveySharedFlows.getQuestionID(SurveyActivity.this),
                            SurveySharedFlows.getQuestionType(SurveyActivity.this),
                            SurveyActivity.this);
                }

                @Override
                public void onFailure(Call<ImageUploadModel> call, Throwable t) {
                    liProgress.setVisibility(View.GONE);
                    //CustomToast.ToastCenter(PickUpActivity.this, "Successfully Updated!");
                    //Helper.restartApp(DeliveryActivity.this);
                    Log.d("sdcsc", String.valueOf(t.getMessage()));
                }
            });


            //r.getPath();
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == 100 && photoFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath());
            //ivShow.setImageBitmap(bitmap);

        }
    }
}
