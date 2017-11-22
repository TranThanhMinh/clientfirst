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
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.MOrders;
import anhpha.clientfirst.crm.model.OrderContractStatus;

/**
 * Created by Administrator on 10/12/2017.
 */

public class adapter_orders extends RecyclerView.Adapter<adapter_orders.MyViewHolder> {

    private List<MOrders> list;
    private Context context;

    public Onclick getOnclick() {
        return onclick;
    }

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }

    private Onclick onclick;

    public interface Onclick {
        void Click(int id);
    }

    public adapter_orders(Context context, List<MOrders> list, Onclick onclick) {
        this.context = context;
        this.list = list;
        this.onclick = onclick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_orders, null);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MOrders order = list.get(position);
        holder.tvName.setText(order.getOrderContractName());
        holder.tvPercent.setVisibility(View.GONE);

        if (order.isCheck()) {
            holder.checkBox.setChecked(true);
        } else holder.checkBox.setChecked(false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (MOrders o : list) {
                    o.setCheck(false);
                }
                order.setCheck(true);
                notifyDataSetChanged();
                onclick.Click(order.getOrderContractId());
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
