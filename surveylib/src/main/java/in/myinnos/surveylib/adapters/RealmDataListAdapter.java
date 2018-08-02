package in.myinnos.surveylib.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.myinnos.surveylib.R;
import in.myinnos.surveylib.models.RealmQuestionAnswersModel;

/**
 * Created by MyInnos on 20-Jul-16.
 */
public class RealmDataListAdapter extends
        RecyclerView.Adapter<RealmDataListAdapter.MyViewHolder> {

    private Activity activity;
    private List<RealmQuestionAnswersModel> districtListDetailsModelList;
    RealmQuestionAnswersModel m;

    List<RealmQuestionAnswersModel> filterList;

    public RealmDataListAdapter(Activity activity,
                                List<RealmQuestionAnswersModel> districtListDetailsModelList) {
        this.activity = activity;
        this.districtListDetailsModelList = districtListDetailsModelList;
        this.filterList = districtListDetailsModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txName, txState;

        public MyViewHolder(View view) {
            super(view);
            txState = (TextView) view.findViewById(R.id.txState);
            txName = (TextView) view.findViewById(R.id.txName);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_relam_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RealmDataListAdapter.MyViewHolder holder, final int position) {

        // getting movie data for the row
        m = districtListDetailsModelList.get(position);
        holder.txName.setText(m.getQuestion());
        holder.txState.setText(m.getAnswer());
    }

    @Override
    public int getItemCount() {
        return districtListDetailsModelList.size();
    }

}
