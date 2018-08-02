package in.myinnos.surveylib.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import in.myinnos.surveylib.Answers;
import in.myinnos.surveylib.ApiInterface.SurveysApiClient;
import in.myinnos.surveylib.ApiInterface.SurveysApiInterface;
import in.myinnos.surveylib.R;
import in.myinnos.surveylib.SurveyActivity;
import in.myinnos.surveylib.models.ChoicesListModel;
import in.myinnos.surveylib.models.Question;
import in.myinnos.surveylib.models.village.VillageListModel;
import in.myinnos.surveylib.widgets.AppSurveyConstants;
import in.myinnos.surveylib.widgets.SurveyHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentRadioboxes extends Fragment {

    private Question q_data;
    private FragmentActivity mContext;
    private Button button_continue;
    private TextView textview_q_title;
    private RadioGroup radioGroup;
    private final ArrayList<RadioButton> allRb = new ArrayList<>();
    private boolean at_leaset_one_checked = false;
    private boolean is_village_check = false;
    private String questionId, questionVariableType;
    private LinearLayout liProgress;
    private String base_url = "URL";
    private String registeredBy, designation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_radioboxes, container, false);

        liProgress = (LinearLayout) rootView.findViewById(R.id.liProgress);
        liProgress.setVisibility(View.GONE);
        button_continue = (Button) rootView.findViewById(R.id.button_continue);
        textview_q_title = (TextView) rootView.findViewById(R.id.textview_q_title);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SurveyActivity) mContext).go_to_next();
            }
        });

        return rootView;
    }

    private void collect_data() {

        //----- collection & validation for is_required
        String the_choice = "";
        String the_choice_answers = "";
        at_leaset_one_checked = false;
        for (RadioButton rb : allRb) {
            if (rb.isChecked()) {
                at_leaset_one_checked = true;
                the_choice = rb.getTag().toString();
                the_choice_answers = rb.getText().toString();
            }
        }

        if (the_choice.length() > 0) {
            //Answers.getInstance().put_answer(questionId, the_choice);

            SurveyHelper.putAnswer(textview_q_title.getText().toString().trim(), the_choice_answers,
                    questionVariableType, questionId, the_choice);
        }


        if (q_data.getRequired()) {
            if (at_leaset_one_checked) {
                button_continue.setVisibility(View.VISIBLE);
            } else {
                button_continue.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();
        q_data = (Question) getArguments().getSerializable("data");
        base_url = getArguments().getString(AppSurveyConstants.BASE_URL);
        registeredBy = getArguments().getString(AppSurveyConstants.SUR_REGISTERED_BY);
        designation = getArguments().getString(AppSurveyConstants.SUR_REGISTERED_DESIGNATION);

        questionId = q_data.getQuestionId();
        questionVariableType = q_data.getQuestion_v_type();
        textview_q_title.setText(Html.fromHtml(q_data.getQuestionTitle()));
        is_village_check = q_data.getIs_village_check();

        final List<String> qq_data = new ArrayList<String>();
        final List<Object> qq_data_tag = new ArrayList<Object>();

        if (is_village_check) {

            liProgress.setVisibility(View.VISIBLE);

            SurveysApiInterface apiService =
                    SurveysApiClient.getClient(base_url).create(SurveysApiInterface.class);

            Call<VillageListModel> villageListModelCall = null;

            if (designation.equals("1BA")) {
                villageListModelCall = apiService.villageListRBA("Android", registeredBy);
            } else {
                villageListModelCall = apiService.villageListMBA("Android", registeredBy);
            }

            villageListModelCall.enqueue(new Callback<VillageListModel>() {
                @Override
                public void onResponse(Call<VillageListModel> call, Response<VillageListModel> response) {

                    for (int i = 0; i < response.body().getVillageListDetailsModels().size(); i++) {
                        Log.d("SDcsd", response.body().getVillageListDetailsModels().get(i).getName());
                        qq_data.add(response.body().getVillageListDetailsModels().get(i).getName());
                        qq_data_tag.add(response.body().getVillageListDetailsModels().get(i).getId());
                    }

                    for (int i = 0; i < qq_data.size(); i++) {
                        RadioButton rb = new RadioButton(mContext);
                        rb.setText(Html.fromHtml(qq_data.get(i)));
                        rb.setTag(qq_data_tag.get(i));
                        rb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        rb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        radioGroup.addView(rb);
                        allRb.add(rb);

                        rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                collect_data();
                            }
                        });
                    }

                    liProgress.setVisibility(View.GONE);
                }

                @SuppressLint("ShowToast")
                @Override
                public void onFailure(Call<VillageListModel> call, Throwable t) {
                    liProgress.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Please check your internet and try again!", Toast.LENGTH_LONG);
                }
            });


        } else {

            for (int i = 0; i < q_data.getChoicesListModelList().size(); i++) {
                qq_data.add(q_data.getChoicesListModelList().get(i).getName());
                qq_data_tag.add(q_data.getChoicesListModelList().get(i).getValue());
            }

            for (int i = 0; i < qq_data.size(); i++) {
                RadioButton rb = new RadioButton(mContext);
                rb.setText(Html.fromHtml(qq_data.get(i)));
                rb.setTag(qq_data_tag.get(i));
                rb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                rb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                radioGroup.addView(rb);
                allRb.add(rb);

                rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        collect_data();
                    }
                });
            }
        }
        /*if (q_data.getRandomChoices()) {
            Collections.shuffle(qq_data);
        }*/


        if (q_data.getRequired()) {
            if (at_leaset_one_checked) {
                button_continue.setVisibility(View.VISIBLE);
            } else {
                button_continue.setVisibility(View.GONE);
            }
        }


    }


}