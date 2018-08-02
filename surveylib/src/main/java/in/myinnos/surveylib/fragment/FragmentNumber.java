package in.myinnos.surveylib.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;

import in.myinnos.surveylib.Answers;
import in.myinnos.surveylib.ApiInterface.SurveysApiClient;
import in.myinnos.surveylib.ApiInterface.SurveysApiInterface;
import in.myinnos.surveylib.R;
import in.myinnos.surveylib.SurveyActivity;
import in.myinnos.surveylib.activity.CropActivity;
import in.myinnos.surveylib.models.PhoneNumberModel;
import in.myinnos.surveylib.models.Question;
import in.myinnos.surveylib.widgets.AppSurveyConstants;
import in.myinnos.surveylib.widgets.SurveyHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNumber extends Fragment {

    private FragmentActivity mContext;
    private Button button_continue;
    private TextView textview_q_title;
    private EditText editText_answer;
    private String questionId, questionVariableType;
    private int max_length = 1000, min_length = 0;
    private Boolean is_phone_number = false;
    private Boolean is_phone_number_check = true;
    private String base_url = "URL";
    private LinearLayout liProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_number, container, false);

        liProgress = (LinearLayout) rootView.findViewById(R.id.liProgress);
        liProgress.setVisibility(View.GONE);
        button_continue = (Button) rootView.findViewById(R.id.button_continue);
        textview_q_title = (TextView) rootView.findViewById(R.id.textview_q_title);
        editText_answer = (EditText) rootView.findViewById(R.id.editText_answer);
        editText_answer.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Answers.getInstance().put_answer(questionId, editText_answer.getText().toString().trim());

                if (!is_phone_number) {

                    if (editText_answer.getText().toString().trim().contains(".")) {
                        invalidNumber();
                        return;
                    }

                    if (editText_answer.getText().toString().trim().length() != 0) {
                        SurveyHelper.putAnswer(textview_q_title.getText().toString().trim(), editText_answer.getText().toString().trim(),
                                questionVariableType, questionId, editText_answer.getText().toString().trim());
                        ((SurveyActivity) mContext).go_to_next();
                    } else {
                        SurveyHelper.putAnswer(textview_q_title.getText().toString().trim(), editText_answer.getText().toString().trim(),
                                questionVariableType, questionId, null);
                        ((SurveyActivity) mContext).go_to_next();
                    }

                } else {

                    if (editText_answer.getText().toString().trim().contains(".")) {
                        invalidNumber();
                        return;
                    }

                    if (editText_answer.getText().toString().trim().length() != 0) {

                        String first = String.valueOf(editText_answer.getText().toString().charAt(0));
                        if (first.equals("6") || first.equals("7") || first.equals("8") || first.equals("9")) {

                            if (is_phone_number_check) {
                                liProgress.setVisibility(View.VISIBLE);

                                SurveysApiInterface apiService =
                                        SurveysApiClient.getClient(base_url).create(SurveysApiInterface.class);

                                Call<PhoneNumberModel> call =
                                        apiService.phoneNumberVerification(editText_answer.getText().toString().trim());

                                call.enqueue(new Callback<PhoneNumberModel>() {
                                    @Override
                                    public void onResponse(Call<PhoneNumberModel> call, Response<PhoneNumberModel> response) {
                                        liProgress.setVisibility(View.GONE);

                                        if (response.body().getPhoneNumberDataModel().getIs_registered()) {
                                    /*Toast.makeText(getActivity().getApplicationContext(),
                                            response.body().getPhoneNumberDataModel().getMsg(), Toast.LENGTH_LONG).show();*/

                                            Alerter.create(getActivity())
                                                    .setTitle(response.body().getPhoneNumberDataModel().getMsg())
                                                    //.setText("Message Cannot be empty!")
                                                    .setDuration(4000)
                                                    .setIcon(R.drawable.alerter_ic_face)
                                                    .setIconColorFilter(getResources().getColor(R.color.white))
                                                    .enableProgress(true)
                                                    .enableSwipeToDismiss()
                                                    .setProgressColorRes(R.color.colorPrimaryDark)
                                                    .setBackgroundColorRes(R.color.red)
                                                    .setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            Alerter.hide();
                                                        }
                                                    })
                                                    .show();

                                        } else {

                                            SurveyHelper.putAnswer(textview_q_title.getText().toString().trim(), editText_answer.getText().toString().trim(),
                                                    questionVariableType, questionId, editText_answer.getText().toString().trim());
                                            ((SurveyActivity) mContext).go_to_next();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<PhoneNumberModel> call, Throwable t) {
                                        liProgress.setVisibility(View.GONE);
                                    }
                                });
                            } else {
                                SurveyHelper.putAnswer(textview_q_title.getText().toString().trim(), editText_answer.getText().toString().trim(),
                                        questionVariableType, questionId, editText_answer.getText().toString().trim());
                                ((SurveyActivity) mContext).go_to_next();
                            }

                        } else {
                            //Toast.makeText(getActivity().getApplicationContext(), "Invalid Phone Number!", Toast.LENGTH_LONG).show();

                            invalidNumber();
                        }
                    } else {
                        SurveyHelper.putAnswer(textview_q_title.getText().toString().trim(), editText_answer.getText().toString().trim(),
                                questionVariableType, questionId, null);
                        ((SurveyActivity) mContext).go_to_next();
                    }
                }
            }
        });

        return rootView;
    }

    private void invalidNumber() {

        Alerter.create(getActivity())
                .setTitle("Invalid Phone Number!")
                //.setText("Message Cannot be empty!")
                .setDuration(4000)
                .setIcon(R.drawable.alerter_ic_face)
                .setIconColorFilter(getResources().getColor(R.color.white))
                .enableProgress(true)
                .enableSwipeToDismiss()
                .setProgressColorRes(R.color.colorPrimaryDark)
                .setBackgroundColorRes(R.color.red)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Alerter.hide();
                    }
                })
                .show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();
        Question q_data = (Question) getArguments().getSerializable("data");
        base_url = getArguments().getString(AppSurveyConstants.BASE_URL);


        if (q_data.getRequired()) {
            button_continue.setVisibility(View.GONE);
            editText_answer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() >= min_length) {
                        button_continue.setVisibility(View.VISIBLE);
                    } else {
                        button_continue.setVisibility(View.GONE);
                    }
                }
            });
        }


        questionId = q_data.getQuestionId();
        questionVariableType = q_data.getQuestion_v_type();
        is_phone_number = q_data.getIs_phone_number();
        is_phone_number_check = q_data.getIs_phone_number_check();

        if (q_data.getMax_char_length() != null) {
            max_length = Integer.parseInt(q_data.getMax_char_length());
            editText_answer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max_length)});
        }
        if (q_data.getMin_char_length() != null) {
            min_length = Integer.parseInt(q_data.getMin_char_length());
        }

        textview_q_title.setText(Html.fromHtml(q_data.getQuestionTitle()));
        editText_answer.requestFocus();
        /*InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText_answer, 0);*/


    }
}