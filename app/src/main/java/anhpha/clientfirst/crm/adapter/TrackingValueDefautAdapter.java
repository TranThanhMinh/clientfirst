package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.Tracking_value_defaults;

/**
 * Created by Administrator on 8/8/2017.
 */

public class TrackingValueDefautAdapter extends RecyclerView.Adapter<TrackingValueDefautAdapter.MyViewHolder> {
    private Context context;
    private LayoutInflater lay;
    private List<Tracking_value_defaults> list;

    public TrackingValueDefautAdapter(Context context, List<Tracking_value_defaults> list) {
        this.context = context;
        this.list = list;
        lay = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = lay.inflate(R.layout.item_tracking_value_default, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Tracking_value_defaults tracking = list.get(position);
          holder.tvNameTracking.setText(tracking.getTracking_value_default_content());
        if(tracking.isTracking()){
            holder.cbTracking.setChecked(true);
        }
        else holder.cbTracking.setChecked(false);

        holder.cbTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tracking.isTracking()){
                    tracking.setTracking(true);
                }
                else tracking.setTracking(false);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbTracking;
        private TextView tvNameTracking;
        public MyViewHolder(View v) {
            super(v);
            cbTracking = (CheckBox) v.findViewById(R.id.cbTracking);
            tvNameTracking = (TextView) v.findViewById(R.id.tvNameTracking);
        }
    }
}
