package in.myinnos.surveylib.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import in.myinnos.surveylib.Answers;
import in.myinnos.surveylib.R;
import in.myinnos.surveylib.SurveyActivity;
import in.myinnos.surveylib.models.Question;
import in.myinnos.surveylib.widgets.SurveyHelper;

public class FragmentImage extends Fragment {

    private FragmentActivity mContext;
    private Button button_continue;
    private TextView textview_q_title;
    private EditText editText_answer;
    private String questionId, questionVariableType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_date, container, false);

        button_continue = (Button) rootView.findViewById(R.id.button_continue);
        textview_q_title = (TextView) rootView.findViewById(R.id.textview_q_title);
        editText_answer = (EditText) rootView.findViewById(R.id.editText_answer);
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Answers.getInstance().put_answer(questionId, editText_answer.getText().toString().trim());

                SurveyHelper.putAnswer(questionVariableType, questionId, editText_answer.getText().toString().trim());

                ((SurveyActivity) mContext).go_to_next();
            }
        });


        return rootView;
    }


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
                    if (s.length() > 3) {
                        button_continue.setVisibility(View.VISIBLE);
                    } else {
                        button_continue.setVisibility(View.GONE);
                    }
                }
            });
        }

        questionId = q_data.getQuestionId();
        questionVariableType = q_data.getQuestion_v_type();
        textview_q_title.setText(Html.fromHtml(q_data.getQuestionTitle()));
        //editText_answer.requestFocus();
        /*InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText_answer, 0);*/

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText_answer, myCalendar);
            }

        };

        editText_answer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel(EditText edittext, Calendar myCalendar) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
}