package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import java.util.List;
import anhpha.clientfirst.crm.R;

/**
 * Created by mc975 on 2/6/17.
 */
public class ChooseClientStatusAdapter extends RecyclerView.Adapter<ChooseClientStatusAdapter.MyViewHolder>{
    private List<MStatus> activityItemList;
    public Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public CheckBox checkBox;
        public MyViewHolder(View view) {
            super(view);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        }

    }

    public ChooseClientStatusAdapter(Context context, List<MStatus> activityItemList) {
        this.activityItemList = activityItemList;
        this.mContext = context;
    }

    public void setActivityItemList(List<MStatus> activityItemList) {
        this.activityItemList = activityItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_check_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)  {
        final MStatus activityItem = activityItemList.get(position);
        holder.checkBox.setText(activityItem.getName());

        holder.checkBox.setChecked(activityItem.is_select());

    }
    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
