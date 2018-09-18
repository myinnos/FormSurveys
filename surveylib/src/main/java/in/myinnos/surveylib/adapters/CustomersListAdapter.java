package in.myinnos.surveylib.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.myinnos.surveylib.R;
import in.myinnos.surveylib.function.CircleTransform;
import in.myinnos.surveylib.models.PhoneNumberFamilyDataModel;

/**
 * Created by MyInnos on 20-Jul-16.
 */
public class CustomersListAdapter extends RecyclerView.Adapter<CustomersListAdapter.MyViewHolder> implements Filterable {

    private Activity activity;
    private LayoutInflater inflater;
    private List<PhoneNumberFamilyDataModel> custListDetailsModels;
    PhoneNumberFamilyDataModel m;

    List<PhoneNumberFamilyDataModel> filterList;
    CustomFilter filter;

    String BA_ID = "";

    public CustomersListAdapter(Activity activity, List<PhoneNumberFamilyDataModel> custListDetailsModels,
                                String BA_ID) {
        this.activity = activity;

        this.custListDetailsModels = custListDetailsModels;
        this.filterList = custListDetailsModels;

        this.BA_ID = BA_ID;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txName, txMessage, txId, txVillage, txRegisterBy, txGender;
        ImageView imgBg;

        public MyViewHolder(View view) {
            super(view);
            txId = (TextView) view.findViewById(R.id.txId);
            txName = (TextView) view.findViewById(R.id.txName);
            imgBg = (ImageView) view.findViewById(R.id.imgBG);
            txMessage = (TextView) view.findViewById(R.id.txMessage);
            txVillage = (TextView) view.findViewById(R.id.txVillage);
            txRegisterBy = (TextView) view.findViewById(R.id.txRegisterBy);
            txGender = (TextView) view.findViewById(R.id.txGender);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_form_customers, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        // getting movie data for the row
        m = custListDetailsModels.get(position);

        holder.txId.setText(String.valueOf(m.getId()));
        holder.txName.setText(m.getName());
        holder.txGender.setText(m.getGender());
        holder.txVillage.setText(m.getVillage_name());
        //holder.txPhone.setAutoLinkMask(Linkify.PHONE_NUMBERS);
        //ImageLoaderGlideListView.imageForPKApp(imageUrl, holder.imgBg, activity.getApplicationContext());

       /* if (m.getStatus()) {
            holder.txMessage.setVisibility(View.VISIBLE);
            holder.txMessage.setText(m.getVerified());
            holder.txMessage.setTextColor(activity.getResources().getColor(R.color.green));
        } else {
            holder.txMessage.setVisibility(View.VISIBLE);
            holder.txMessage.setText(m.getVerified());
            holder.txMessage.setTextColor(activity.getResources().getColor(R.color.red));
        }*/

        if (BA_ID.equals(m.getAdvisor_id())) {
            holder.txRegisterBy.setText("Register by you");
        } else {
            //holder.txRegisterBy.setText("Register by " + m.getAdvisor_id());
            holder.txRegisterBy.setText("Not registered by you");
        }

        Picasso.get()
                .load(m.getImage())
                .transform(new CircleTransform())
                .into(holder.imgBg);
    }

    @Override
    public int getItemCount() {
        return custListDetailsModels.size();
    }

    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    //INNER CLASS
    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                //CONSTARINT TO UPPER
                constraint = constraint.toString().toUpperCase();
                ArrayList<PhoneNumberFamilyDataModel> filters = new ArrayList<PhoneNumberFamilyDataModel>();

                //get specific items
                for (int i = 0; i < filterList.size(); i++) {

                    PhoneNumberFamilyDataModel p;

                    if (filterList.get(i).getName().toUpperCase().contains(constraint)) {

                        p = new PhoneNumberFamilyDataModel(
                                filterList.get(i).getId(), filterList.get(i).getName(),
                                filterList.get(i).getGender(), filterList.get(i).getVillage_name(),
                                filterList.get(i).getAdvisor_id(), filterList.get(i).getAdded_at(),
                                filterList.get(i).getImage(),
                                filterList.get(i).getIs_your_customer());

                        filters.add(p);
                    } else if (filterList.get(i).getVillage_name().toUpperCase().contains(constraint)) {

                        p = new PhoneNumberFamilyDataModel(
                                filterList.get(i).getId(), filterList.get(i).getName(),
                                filterList.get(i).getGender(), filterList.get(i).getVillage_name(),
                                filterList.get(i).getAdvisor_id(), filterList.get(i).getAdded_at(),
                                filterList.get(i).getImage(),
                                filterList.get(i).getIs_your_customer());

                        filters.add(p);
                    }
                }
                results.count = filters.size();
                results.values = filters;

            } else {
                results.count = filterList.size();
                results.values = filterList;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub
            custListDetailsModels = (ArrayList<PhoneNumberFamilyDataModel>) results.values;
            notifyDataSetChanged();
        }

    }

}
