package in.myinnos.surveylib.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
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
import java.util.TimeZone;

import in.myinnos.surveylib.Answers;
import in.myinnos.surveylib.R;
import in.myinnos.surveylib.SurveyActivity;
import in.myinnos.surveylib.models.Question;
import in.myinnos.surveylib.widgets.SurveyHelper;

public class FragmentDate extends Fragment {

    private FragmentActivity mContext;
    private Button button_continue;
    private TextView textview_q_title;
    private TextView editText_answer;
    private String questionId, questionVariableType;
    private int max_length = 1000, min_length = 3;
    private int date_max = 18;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_date, container, false);

        button_continue = (Button) rootView.findViewById(R.id.button_continue);
        textview_q_title = (TextView) rootView.findViewById(R.id.textview_q_title);
        editText_answer = (TextView) rootView.findViewById(R.id.editText_answer);
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Answers.getInstance().put_answer(questionId, editText_answer.getText().toString().trim());

                String date_string = editText_answer.getText().toString().trim();
                if (date_string.equals("Choose Date")) {
                    date_string = "";
                }

                SurveyHelper.putAnswer(textview_q_title.getText().toString().trim(), editText_answer.getText().toString().trim(),
                        questionVariableType, questionId, date_string);

                ((SurveyActivity) mContext).go_to_next();
            }
        });


        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();
        final Question q_data = (Question) getArguments().getSerializable("data");

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
        date_max = q_data.getDate_condition();

        if (q_data.getMax_char_length() != null) {
            max_length = Integer.parseInt(q_data.getMax_char_length());
            editText_answer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max_length)});
        }
        if (q_data.getMin_char_length() != null) {
            min_length = Integer.parseInt(q_data.getMin_char_length());
        }

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

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                int minYear = year - date_max;
                int minMonth = month;
                int minDay = day;

                myCalendar.set(minYear, minMonth, minDay);
                long minDateInMilliSeconds = myCalendar.getTimeInMillis();

                datePickerDialog.getDatePicker().setMaxDate(minDateInMilliSeconds);
                datePickerDialog.show();
            }
        });

    }

    private void updateLabel(TextView edittext, Calendar myCalendar) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
}