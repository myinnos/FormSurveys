package in.myinnos.surveylib.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import in.myinnos.surveylib.Answers;
import in.myinnos.surveylib.R;
import in.myinnos.surveylib.SurveyActivity;
import in.myinnos.surveylib.function.RealmObjectFlow;
import in.myinnos.surveylib.models.RealmQuestionAnswersModel;
import in.myinnos.surveylib.models.SurveyProperties;
import in.myinnos.surveylib.widgets.bottomview.BottomDialog;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class FragmentEnd extends Fragment {

    private FragmentActivity mContext;
    private TextView textView_end;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_end, container, false);


        Button button_finish = (Button) rootView.findViewById(R.id.button_finish);
        textView_end = (TextView) rootView.findViewById(R.id.textView_end);


        RealmResults<RealmQuestionAnswersModel> r = RealmObjectFlow.realm.where(RealmQuestionAnswersModel.class)
                .findAll();

        for (int i = 0; i < r.size(); i++) {

            Log.d("dscds",r.get(i).getQuestion() + " : " + r.get(i).getAnswer());
        }


        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new BottomDialog.Builder(getActivity())
                        .setTitle(R.string.finish_popup_title)
                        .setContent(R.string.finish_popup_message)
                        .setPositiveText(R.string.finish_popup_ok)
                        .setNegativeText(R.string.finish_popup_no)
                        .setPositiveBackgroundColorResource(R.color.colorPrimary)
                        //.setPositiveBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary)
                        .setPositiveTextColorResource(android.R.color.white)
                        .setNegativeTextColorResource(R.color.colorAccent)
                        //.setPositiveTextColor(ContextCompat.getColor(this, android.R.color.colorPrimary)
                        .onPositive(new BottomDialog.ButtonCallback() {
                            @Override
                            public void onClick(BottomDialog dialog) {
                                ((SurveyActivity) mContext).event_survey_completed(Answers.getInstance());
                            }
                        }).show();

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity();
        SurveyProperties survery_properties = (SurveyProperties) getArguments().getSerializable("survery_properties");

        assert survery_properties != null;
        textView_end.setText(Html.fromHtml(survery_properties.getEndMessage()));

    }
}