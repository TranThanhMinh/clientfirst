package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.FocusGroup;
import anhpha.clientfirst.crm.model.Focus_target;

/**
 * Created by Administrator on 8/29/2017.
 */

public class adapter_Choose_client_focus_group extends RecyclerView.Adapter<adapter_Choose_client_focus_group.MyViewHolder> {
    private Context context;
    private List<FocusGroup> list;
    private LayoutInflater lay;

    public adapter_Choose_client_focus_group(Context context, List<FocusGroup> list) {
        this.context = context;
        this.list = list;
        lay = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = lay.inflate(R.layout.item_choose_client_focus_group, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final FocusGroup focus_target = list.get(position);
        holder.tvGroupTarget.setText(focus_target.getFocus_target_name());
        holder.cbCheck.setChecked(focus_target.isCheck());
        holder.cbCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focus_target.setCheck(!focus_target.isCheck());
                notifyDataSetChanged();
                if (focus_target.isCheck()== true) {
                    Log.d("check",focus_target.getFocus_target_group_id()+"");
                } else {
                    focus_target.setCheck(false);
                    Log.d("check", "0");

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGroupTarget;
        private CheckBox cbCheck;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvGroupTarget = (TextView) itemView.findViewById(R.id.tvGroupTarget);
            cbCheck = (CheckBox) itemView.findViewById(R.id.cbCheck);
        }
    }

}
