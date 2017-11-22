package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class adapter_salesprocess extends RecyclerView.Adapter<adapter_salesprocess.MyViewHolder> {
    private List<OrderContractStatusGroup> list;
    private List<OrderContractStatus> lvByGroup = new ArrayList<>();
    private Context context;

    public Onclick getOnclick() {
        return onclick;
    }

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }

    private Onclick onclick;
    public interface Onclick{
        void click(int idGroup,String name,List<OrderContractStatus> lvByGroup);
    }
    public adapter_salesprocess(Context context, List<OrderContractStatusGroup> list,Onclick onclick) {
        this.context = context;
        this.list = list;
        this.onclick = onclick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_salesprocess, null);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final OrderContractStatusGroup order = list.get(position);
        holder.tvName.setText(order.getOrderContractStatusGroupName());
        if (order.isCheck()) {
            holder.checkBox.setChecked(true);
        } else holder.checkBox.setChecked(false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (OrderContractStatusGroup o : list){
                   o.setCheck(false);
                }
                order.setCheck(true);
                notifyDataSetChanged();
                onclick.click(order.getOrderContractStatusGroupId(),order.getOrderContractStatusGroupName(),order.getOrderContractStatus());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);

            checkBox = (CheckBox) itemView.findViewById(R.id.check);
        }
    }
}
