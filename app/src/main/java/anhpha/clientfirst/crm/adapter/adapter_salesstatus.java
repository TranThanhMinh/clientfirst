package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.OrderContractStatus;
import anhpha.clientfirst.crm.model.OrderContractStatusGroup;

/**
 * Created by Administrator on 10/12/2017.
 */

public class adapter_salesstatus extends RecyclerView.Adapter<adapter_salesstatus.MyViewHolder> {

    private List<OrderContractStatus> list;
    private Context context;

    public Onclick getOnclick() {
        return onclick;
    }

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }

    private Onclick onclick;

    public interface Onclick {
        void Click(int idGroup, String name, int percent);
    }

    public adapter_salesstatus(Context context, List<OrderContractStatus> list, Onclick onclick) {
        this.context = context;
        this.list = list;
        this.onclick = onclick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_salesstatus, null);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final OrderContractStatus order = list.get(position);
        holder.tvName.setText(order.getOrderContractStatusName());
        holder.tvPercent.setText(order.getPercentDone() + "%");
        if (position == list.size() - 3) {
            holder.tvText.setVisibility(View.VISIBLE);
            holder.tvPercent.setVisibility(View.GONE);
        }
       else if (position == list.size() - 2) {
            holder.tvPercent.setVisibility(View.GONE);
        }
        else if (position == list.size() - 1) {
            holder.tvPercent.setVisibility(View.GONE);
        }else {
            holder.tvText.setVisibility(View.GONE);
            holder.tvPercent.setVisibility(View.VISIBLE);
        }
        if (order.isCheck()) {
            holder.checkBox.setChecked(true);
        } else holder.checkBox.setChecked(false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (OrderContractStatus o : list) {
                    o.setCheck(false);
                }
                order.setCheck(true);
                notifyDataSetChanged();
                Log.d("idByGroup",order.getOrderContractStatusGroupId()+"");
                onclick.Click(order.getOrderContractStatusId(), order.getOrderContractStatusName(), order.getPercentDone());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPercent, tvText;
        private CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPercent = (TextView) itemView.findViewById(R.id.tvPercent);
            checkBox = (CheckBox) itemView.findViewById(R.id.check);
        }
    }
}
