package in.myinnos.surveylib.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;

import in.myinnos.surveylib.ApiInterface.SurveysApiClient;
import in.myinnos.surveylib.ApiInterface.SurveysApiInterface;
import in.myinnos.surveylib.R;
import in.myinnos.surveylib.SurveyActivity;
import in.myinnos.surveylib.adapters.CustomersListAdapter;
import in.myinnos.surveylib.models.PhoneNumberFamilyDataModel;
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
    private String questionId, questionVariableType, registeredBy;
    private int max_length = 1000, min_length = 0;
    private Boolean is_phone_number = false;
    private Boolean is_phone_number_check = true;
    private String base_url = "URL";
    private LinearLayout liProgress;
    private Boolean CUST_VIEW_VISI = false;

    private RelativeLayout liCustomerLayout;
    private Button button_continue_rv;
    private TextView textview_q_title_rv;
    private List<PhoneNumberFamilyDataModel> custListDetailsModels = new ArrayList<>();
    private RecyclerView listView;
    public static CustomersListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_number, container, false);

        liCustomerLayout = (RelativeLayout) rootView.findViewById(R.id.liCustomerLayout);
        liCustomerLayout.setVisibility(View.GONE);
        button_continue_rv = (Button) rootView.findViewById(R.id.button_continue_rv);
        textview_q_title_rv = (TextView) rootView.findViewById(R.id.textview_q_title_rv);
        liProgress = (LinearLayout) rootView.findViewById(R.id.liProgress);
        liProgress.setVisibility(View.GONE);
        button_continue = (Button) rootView.findViewById(R.id.button_continue);
        textview_q_title = (TextView) rootView.findViewById(R.id.textview_q_title);
        editText_answer = (EditText) rootView.findViewById(R.id.editText_answer);
        editText_answer.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        listView = (RecyclerView) rootView.findViewById(R.id.listView);
        adapter = new CustomersListAdapter(getActivity(), custListDetailsModels);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);
        listView.setAdapter(adapter);

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

                                SharedPreferences prefs = mContext.getSharedPreferences("CUSTOMER_DETAILS", Context.MODE_PRIVATE);
                                String restoredText = prefs.getString("gender", "male");
                                //Log.d("gender_test", restoredText);

                                Call<PhoneNumberModel> call =
                                        apiService.phoneNumberVerification(editText_answer.getText().toString().trim(),
                                                restoredText.toLowerCase(), registeredBy);

                                call.enqueue(new Callback<PhoneNumberModel>() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onResponse(Call<PhoneNumberModel> call, Response<PhoneNumberModel> response) {
                                        liProgress.setVisibility(View.GONE);

                                        if (!response.body().getPhoneNumberDataModel().getIs_new()) {
                                            /*Toast.makeText(getActivity().getApplicationContext(),
                                                 response.body().getPhoneNumberDataModel().getMsg(), Toast.LENGTH_LONG).show();*/

                                            /*Alerter.create(getActivity())
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
                                                    .show();*/
                                            liCustomerLayout.setVisibility(View.VISIBLE);
                                            custListDetailsModels.clear();


                                           /* CustListDetailsModel custListDetailsModel = new CustListDetailsModel(1, "Jhon BA", true,
                                                    "+919550233669", "https://img00.deviantart.net/a676/i/2008/326/9/5/gary__superhero_accountant_by_superheronation.jpg",
                                                    "Male", "", "HSR Layout", true, "BAA0280");*/

                                            custListDetailsModels.addAll(response.body().getPhoneNumberDataModel().getPhoneNumberFamilyDataModelList());
                                            adapter.notifyDataSetChanged();

                                            textview_q_title_rv.setText("People who are all already registered with " + editText_answer.getText().toString().trim());

                                            if (response.body().getPhoneNumberDataModel().getCan_add()) {
                                                button_continue_rv.setClickable(true);
                                                button_continue_rv.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                                button_continue_rv.setText(response.body().getPhoneNumberDataModel().getCan_add_message());
                                                button_continue_rv.setTextColor(getResources().getColor(R.color.white));
                                                button_continue_rv.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        liCustomerLayout.setVisibility(View.GONE);
                                                        SurveyHelper.putAnswer(textview_q_title.getText().toString().trim(), editText_answer.getText().toString().trim(),
                                                                questionVariableType, questionId, editText_answer.getText().toString().trim());
                                                        ((SurveyActivity) mContext).go_to_next();
                                                    }
                                                });
                                            } else {
                                                button_continue_rv.setClickable(false);
                                                button_continue_rv.setBackgroundColor(getResources().getColor(R.color.white));
                                                button_continue_rv.setText(response.body().getPhoneNumberDataModel().getCan_add_message());
                                                button_continue_rv.setTextColor(getResources().getColor(R.color.red));
                                            }


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
        CUST_VIEW_VISI = getArguments().getBoolean(AppSurveyConstants.CUSTOMER_VIEW_VISIBILITY);

        if (!CUST_VIEW_VISI) {
            liCustomerLayout.setVisibility(View.GONE);
        }

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
        registeredBy = getArguments().getString(AppSurveyConstants.SUR_REGISTERED_BY);

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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        if(isVisibleToUser){ //if listener is called before fragment is attached will throw NPE
            //Toast.makeText(getContext(), "sdsd", Toast.LENGTH_SHORT).show();
            liCustomerLayout.setVisibility(View.GONE);
        }
    }
}