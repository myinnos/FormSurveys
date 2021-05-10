package in.myinnos.surveylib.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.myinnos.surveylib.Answers;
import in.myinnos.surveylib.R;
import in.myinnos.surveylib.SurveyActivity;
import in.myinnos.surveylib.adapters.RealmDataListAdapter;
import in.myinnos.surveylib.function.RealmObjectFlow;
import in.myinnos.surveylib.models.RealmQuestionAnswersModel;
import in.myinnos.surveylib.models.SurveyProperties;
import in.myinnos.surveylib.widgets.bottomview.BottomDialog;
import io.realm.RealmResults;


public class FragmentEnd extends Fragment {

    private FragmentActivity mContext;
    private TextView textView_end;

    private List<RealmQuestionAnswersModel> realmQuestionAnswersModelArrayList = new ArrayList<>();
    private RecyclerView listView;
    public static RealmDataListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_end, container, false);


        Button button_finish = (Button) rootView.findViewById(R.id.button_finish);
        textView_end = (TextView) rootView.findViewById(R.id.textView_end);

        listView = (RecyclerView) rootView.findViewById(R.id.listView);
        adapter = new RealmDataListAdapter(getActivity(), realmQuestionAnswersModelArrayList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(layoutManager);
        listView.setHasFixedSize(true);
        listView.setAdapter(adapter);

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
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {

            RealmResults<RealmQuestionAnswersModel> r = RealmObjectFlow.realm.where(RealmQuestionAnswersModel.class)
                    .findAll();

            RealmQuestionAnswersModel realmQuestionAnswersModel = null;
            realmQuestionAnswersModelArrayList.clear();
            for (int i = 0; i < r.size(); i++) {
                realmQuestionAnswersModel = new RealmQuestionAnswersModel();
                //Log.d("dscds", r.get(i).getQuestion() + " : " + r.get(i).getAnswer());
                if (!r.get(i).getQuestion().equals("latitude") && !r.get(i).getQuestion().equals("longitude") &&
                        !r.get(i).getQuestion().equals("advisor_id") && !r.get(i).getQuestion().equals("customer")
                        && !r.get(i).getQuestion().equals("image")  && !r.get(i).getQuestion().equals("survey_task_id")
                        && !r.get(i).getQuestion().equals("station_id")
                        && !r.get(i).getQuestion().equals("village_id")) {

                    Log.d("dscds", r.get(i).getQuestion() + " : " + r.get(i).getAnswer());

                    if(!r.get(i).getAnswer().equals("") && r.get(i).getAnswer() != null
                            && !r.get(i).getAnswer().equals("Choose Date")) {
                        realmQuestionAnswersModel.setQuestion(r.get(i).getQuestion());
                        realmQuestionAnswersModel.setAnswer(r.get(i).getAnswer());
                        realmQuestionAnswersModelArrayList.add(realmQuestionAnswersModel);
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
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