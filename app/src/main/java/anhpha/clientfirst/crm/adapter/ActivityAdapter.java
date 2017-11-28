package anhpha.clientfirst.crm.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MActivityItem;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by mc975 on 2/6/17.
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {
    private List<MActivityItem> activityItemList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, client_name, content_activity, time;
        public ImageButton imageButton;

        public MyViewHolder(View view) {
            super(view);
            user_name = (TextView) view.findViewById(R.id.user_name);
            client_name = (TextView) view.findViewById(R.id.client_name);
            content_activity = (TextView) view.findViewById(R.id.content_activity);
            time = (TextView) view.findViewById(R.id.time);
            imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        }
    }

    public MActivityItem getItem(int position) {
        return activityItemList.get(position);
    }

    public ActivityAdapter(Context context, List<MActivityItem> activityItemList) {
        this.activityItemList = activityItemList;
        this.context = context;
    }

    public void setActivityItemList(List<MActivityItem> activityItemList) {
        this.activityItemList = activityItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MActivityItem activityItem = activityItemList.get(position);
        holder.content_activity.setText(activityItem.getActivity_content());
        if(activityItem.getType()==1){
            holder.client_name.setText(activityItem.getOrder_contract_name());
        }else holder.client_name.setText(activityItem.getClient_name());
        holder.user_name.setText(activityItem.getUser_name());
        holder.time.setText(Utils.formatTime(activityItem.getActivity_date()));
        holder.imageButton.setPadding(15,15,15,15);
        switch (activityItem.getActivity_type()) {
            case 1:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_99);
                break;
            case 2:
                holder.imageButton.setPadding(15,15,15,15);
                holder.imageButton.setImageResource(R.mipmap.ic_crm_29);
                holder.content_activity.setText(Utils.formatCurrency(activityItem.getActivity_amount()));
                break;
            case 3:
                holder.imageButton.setPadding(15,15,15,15);
                holder.imageButton.setImageResource(R.mipmap.ic_crm_28);
                break;
            case 9:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_31);
                holder.content_activity.setText(activityItem.getActivity_content());
                break;
            case 5:
                holder.imageButton.setPadding(15,15,15,15);
                holder.imageButton.setImageResource(R.mipmap.ic_crm_26);
                // holder.content_activity.setText(activityItem.getActivity_phone());
                holder.content_activity.setText("");
                break;
            case 6:
                holder.imageButton.setPadding(15,15,15,15);
                holder.imageButton.setImageResource(R.mipmap.ic_crm_32);
                break;
            case 7:
                holder.imageButton.setPadding(15,15,15,15);
                holder.imageButton.setImageResource(R.mipmap.ic_crm_58);
                break;
            case 10:
                holder.imageButton.setPadding(15,15,15,15);
                holder.imageButton.setImageResource(R.mipmap.ic_crm_104);
                if (activityItem.getFocus_status_id() == 1) {
                    if (activityItem.getActivity_content() != null && activityItem.getActivity_content().length() > 0) {
                        holder.content_activity.setText(context.getResources().getString(R.string.srtAchieved) + " (" + activityItem.getActivity_content() + ")");
                    } else
                        holder.content_activity.setText(context.getResources().getString(R.string.srtAchieved));
                }
                if (activityItem.getFocus_status_id() == 2) {
                    if (activityItem.getActivity_content() != null && activityItem.getActivity_content().length() > 0) {
                        holder.content_activity.setText(context.getResources().getString(R.string.srtNoAchieved) + " (" + activityItem.getActivity_content() + ")");
                    } else
                        holder.content_activity.setText(context.getResources().getString(R.string.srtNoAchieved));
                }
                if (activityItem.getFocus_status_id() == 3) {
                    if (activityItem.getActivity_content() != null && activityItem.getActivity_content().length() > 0) {
                        holder.content_activity.setText(context.getResources().getString(R.string.srtDelete) + " (" + activityItem.getActivity_content() + ")");
                    } else
                        holder.content_activity.setText(context.getResources().getString(R.string.srtDelete));
                }
                break;
            case 11:
                holder.imageButton.setPadding(12,12,12,12);
                holder.imageButton.setImageResource(R.mipmap.ic_cost);
                holder.content_activity.setText("$ "+Utils.formatCurrency(Double.parseDouble(activityItem.getActivity_content())));
                break;
            case 14:

                holder.imageButton.setPadding(2,2,2,2);
                holder.imageButton.setImageResource(R.mipmap.ic_crm_debt2);

                holder.content_activity.setText("$ "+Utils.formatCurrency(Double.parseDouble(activityItem.getActivity_content())));
                break;
            case 12:
                holder.imageButton.setPadding(12,12,12,12);
                holder.imageButton.setImageResource(R.mipmap.ic_crm_comment2);
                break;
            case 13:
                holder.imageButton.setPadding(12,12,12,12);
                holder.imageButton.setImageResource(R.mipmap.ic_crm_document2);
                break;
            case 15:
                holder.imageButton.setPadding(15,15,15,15);
                holder.imageButton.setImageResource(R.mipmap.ic_crm_29);
                holder.content_activity.setText(activityItem.getOrder_contract_status_name());
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
