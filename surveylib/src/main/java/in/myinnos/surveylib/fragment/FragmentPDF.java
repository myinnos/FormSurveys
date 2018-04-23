package in.myinnos.surveylib.fragment;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import in.myinnos.surveylib.R;
import in.myinnos.surveylib.SurveyActivity;
import in.myinnos.surveylib.models.Question;
import in.myinnos.surveylib.widgets.SurveyHelper;

public class FragmentPDF extends Fragment {

    private FragmentActivity mContext;
    private Button button_continue, button_view_pdf;
    private TextView textview_q_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_pdf, container, false);

        button_continue = (Button) rootView.findViewById(R.id.button_continue);
        textview_q_title = (TextView) rootView.findViewById(R.id.textview_q_title);
        button_view_pdf = (Button) rootView.findViewById(R.id.button_view_pdf);
        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Answers.getInstance().put_answer(questionId, editText_answer.getText().toString().trim());

                //SurveyHelper.putAnswer(questionVariableType, questionId, editText_answer.getText().toString().trim());

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

        textview_q_title.setText(Html.fromHtml(q_data.getQuestionTitle()));

        button_view_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(q_data.getPdf_file_link()));
                startActivity(browserIntent);
            }
        });

    }
}