package in.myinnos.surveylib.fragment;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import in.myinnos.surveylib.Answers;
import in.myinnos.surveylib.R;
import in.myinnos.surveylib.SurveyActivity;
import in.myinnos.surveylib.models.Question;
import in.myinnos.surveylib.widgets.AppSurveyConstants;
import in.myinnos.surveylib.widgets.SurveyHelper;

import static android.content.Context.MODE_PRIVATE;

public class FragmentTextSimple extends Fragment {

    private FragmentActivity mContext;
    private Button button_continue;
    private TextView textview_q_title, txCount;
    private EditText editText_answer;
    private String questionId, questionVariableType;
    private int max_length = 1000, min_length = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_text_simple, container, false);

        button_continue = (Button) rootView.findViewById(R.id.button_continue);
        textview_q_title = (TextView) rootView.findViewById(R.id.textview_q_title);
        txCount = (TextView) rootView.findViewById(R.id.txCount);
        editText_answer = (EditText) rootView.findViewById(R.id.editText_answer);
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Answers.getInstance().put_answer(questionId, editText_answer.getText().toString().trim());


                SurveyHelper.putAnswer(textview_q_title.getText().toString().trim(), editText_answer.getText().toString().trim(),
                        questionVariableType, questionId, editText_answer.getText().toString().trim());

                ((SurveyActivity) mContext).go_to_next();
            }
        });

        return rootView;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();
        Question q_data = (Question) getArguments().getSerializable("data");

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
                    if (s.length() > min_length) {
                        button_continue.setVisibility(View.VISIBLE);
                    } else {
                        button_continue.setVisibility(View.GONE);
                    }
                }
            });
        }

        questionId = q_data.getQuestionId();
        questionVariableType = q_data.getQuestion_v_type();


        SharedPreferences prefs = mContext.getSharedPreferences(AppSurveyConstants.PREFERENCES_SURVEYS, MODE_PRIVATE);
        String formText = prefs.getString(AppSurveyConstants.QUESTION_TOTAL_COUNT, "");
        txCount.setText(getArguments().getString(AppSurveyConstants.QUESTION_COUNT_ID) + "/" + formText);

        if (q_data.getMax_char_length() != null) {
            max_length = Integer.parseInt(q_data.getMax_char_length());
            editText_answer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max_length)});
        }
        if (q_data.getMin_char_length() != null) {
            min_length = Integer.parseInt(q_data.getMin_char_length());
        }

        textview_q_title.setText(Html.fromHtml(q_data.getQuestionTitle()));
        editText_answer.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText_answer, 0);


    }
}