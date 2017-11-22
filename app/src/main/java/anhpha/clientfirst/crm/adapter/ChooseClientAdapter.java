package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MClient;

/**
 * Created by mc975 on 2/6/17.
 */
public class ChooseClientAdapter extends RecyclerView.Adapter<ChooseClientAdapter.MyViewHolder>{
    private List<MClient> activityItemList;
    public Context mContext;

    public MClient get(int position) {
        return activityItemList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView address, client_name;
        private ImageButton imageButton;
        public MyViewHolder(View view) {
            super(view);
            client_name = (TextView) view.findViewById(R.id.client_name);
            address= (TextView) view.findViewById(R.id.address);
            imageButton= (ImageButton) view.findViewById(R.id.imageButton);

        }

    }

    public ChooseClientAdapter(Context context, List<MClient> activityItemList) {
        this.activityItemList = activityItemList;
        this.mContext = context;
    }

    public void setActivityItemList(List<MClient> activityItemList) {
        this.activityItemList = activityItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choose_client_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)  {
        final MClient activityItem = activityItemList.get(position);
        holder.address.setText(activityItem.getAddress());
        holder.client_name.setText(activityItem.getClient_name());
        if(activityItem.getClient_structure_id() ==2)
        holder.imageButton.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_40));
        else
            holder.imageButton.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_26));
    }
    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
