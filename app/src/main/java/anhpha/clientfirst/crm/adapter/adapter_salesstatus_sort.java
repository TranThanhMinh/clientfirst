package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.OrderContractStatus;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by Administrator on 10/12/2017.
 */

public class adapter_salesstatus_sort extends RecyclerView.Adapter<adapter_salesstatus_sort.MyViewHolder> {

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
        void Click(int idGroup);
    }

    public adapter_salesstatus_sort(Context context, List<OrderContractStatus> list, Onclick onclick) {
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
        holder.tvName.setText(order.getOrderContractStatusName()+" ("+order.getNum() +")  "+ Utils.formatCurrency(order.getMoney()));
        holder.tvName.setTextColor(context.getResources().getColor(R.color.colorWhite));
        holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorApp));
        holder.tvPercent.setVisibility(View.GONE);
        holder.view.setVisibility(View.GONE);
        holder.view.setVisibility(View.GONE);
        holder.checkBox.setVisibility(View.INVISIBLE);
        if (position == list.size() - 3) {
            holder.linearLayout.setVisibility(View.GONE);
        }
       else if (position == list.size() - 2) {
            holder.linearLayout.setVisibility(View.GONE);
        }
        else if (position == list.size() - 1) {
            holder.linearLayout.setVisibility(View.GONE);
        }
//        }else {
//            holder.tvText.setVisibility(View.GONE);
//            holder.tvPercent.setVisibility(View.VISIBLE);
//        }

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.Click(order.getOrderContractStatusId());
               // Toast.makeText(context,order.getOrderContractStatusId()+"", Toast.LENGTH_SHORT).show();
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
        private LinearLayout linearLayout;
        private View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPercent = (TextView) itemView.findViewById(R.id.tvPercent);
            checkBox = (CheckBox) itemView.findViewById(R.id.check);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            view = (View) itemView.findViewById(R.id.view);
        }
    }
}
