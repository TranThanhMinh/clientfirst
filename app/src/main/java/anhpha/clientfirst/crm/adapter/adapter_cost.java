package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.Cost;
import anhpha.clientfirst.crm.model.Expend;

/**
 * Created by Administrator on 9/6/2017.
 */

public class adapter_cost extends RecyclerView.Adapter<adapter_cost.MyViewHolder> {
    private Context context;
    private List<Cost> list;

    public Click getClick() {
        return click;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    private Click click;
    public interface Click{
        void radioButton(int expend_group_type_id,int expend_type_id,int expend_id,int status);
    }
    public adapter_cost(Context context, List<Cost> list,Click click) {
        this.context = context;
        this.click = click;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cost, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Cost cost = list.get(position);
        holder.tvName.setText(cost.getExpendGroupTypeName());
        List<Expend> listexpend = cost.getExpends();
        holder.rgExpend.removeAllViews();
        for (int i = 0; i < listexpend.size(); i++) {
            final Expend expend = listexpend.get(i);
            RadioButton rb = new RadioButton(context);

            rb.setText(expend.getExpendTypeName());
            if (expend.isCheck()){
                rb.setChecked(true);
            }
            else rb.setChecked(false);
            holder.rgExpend.addView(rb);

            final int pos= i;
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    for (Cost cost1 : list) {
                        for(Expend expend1:cost1.getExpends()){
                            expend1.setCheck(false);
                        }
                        List<Expend> expend1 = cost.getExpends();
                        expend1.get(pos).setCheck(true);
                    }
                    click.radioButton(cost.getExpend_group_type_id(),expend.getExpendTypeId(),expend.getExpendId(),cost.getStatus_id());
                    //Toast.makeText(context,cost.getExpend_group_type_id()+" "+expend.getExpendTypeId()+" "+expend.getExpendTypeName(), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private RadioGroup rgExpend;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            rgExpend = (RadioGroup) itemView.findViewById(R.id.rgExpend);
        }
    }
}
