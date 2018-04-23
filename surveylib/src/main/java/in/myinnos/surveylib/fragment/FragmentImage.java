package in.myinnos.surveylib.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import in.myinnos.surveylib.R;
import in.myinnos.surveylib.SurveyActivity;
import in.myinnos.surveylib.activity.CropActivity;
import in.myinnos.surveylib.models.Question;
import in.myinnos.surveylib.widgets.SurveyHelper;
import in.myinnos.surveylib.widgets.SurveySharedFlows;

public class FragmentImage extends Fragment {

    private FragmentActivity mContext;
    private Button button_continue;
    private TextView textview_q_title;
    private TextView editText_answer;
    //private String questionId, questionVariableType = "string";
    //private int max_length = 1000, min_length = 3;
    private File photoFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_image, container, false);

        photoFile = new File(getActivity().getExternalFilesDir("img"), "survey_scan.jpg");

        button_continue = (Button) rootView.findViewById(R.id.button_continue);
        textview_q_title = (TextView) rootView.findViewById(R.id.textview_q_title);
        editText_answer = (TextView) rootView.findViewById(R.id.editText_answer);
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

    public void moveNext(String imageId, String questionId,
                         String questionType, Activity activity) {

        Log.d("in_fragment_done", imageId);
        Log.d("in_fragment_done", questionId);
        Log.d("in_fragment_done", questionType);

        SurveyHelper.putAnswer(questionType, questionId, imageId);
        ((SurveyActivity) activity).go_to_next();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();
        Question q_data = (Question) getArguments().getSerializable("data");

       /* if (q_data.getRequired()) {
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
        }*/

        //questionId = q_data.getQuestionId();
        //questionVariableType = q_data.getQuestion_v_type();

        if (q_data.getIs_photo_required()) {
            button_continue.setVisibility(View.GONE);
        } else {
            button_continue.setVisibility(View.VISIBLE);
        }

        textview_q_title.setText(Html.fromHtml(q_data.getQuestionTitle()));

        // save preferences
        SurveySharedFlows.saveIDPreferences(getActivity(), q_data.getQuestionId(),
                q_data.getQuestion_v_type());

        //editText_answer.requestFocus();
        /*InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText_answer, 0);*/

        editText_answer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new AlertDialog.Builder(getActivity())
                        .setTitle("Choose Image")
                        //.setMessage("Are you sure want to confirm? ")
                        .setPositiveButton("CAMERA", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                getActivity().startActivityForResult(CropActivity.getJumpIntent(getContext(),
                                        false, photoFile), 100);
                            }
                        })
                        .setNegativeButton("GALLERY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().startActivityForResult(CropActivity.getJumpIntent(getActivity(),
                                        true, photoFile), 100);
                            }
                        }).show();
            }
        });

    }
}