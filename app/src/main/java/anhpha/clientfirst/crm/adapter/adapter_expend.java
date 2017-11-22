package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.Cost;
import anhpha.clientfirst.crm.model.Expend;

/**
 * Created by Administrator on 9/6/2017.
 */

public class adapter_expend extends RecyclerView.Adapter<adapter_expend.MyViewHolder> {
    private Context context;
    private List<Expend> list;


    public adapter_expend(Context context, List<Expend> list) {
        this.context = context;

        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cost, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Expend cost = list.get(position);
        holder.tvName.setText(cost.getExpendGroupTypeName());

        holder.rgExpend.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            final Expend expend = list.get(i);
            RadioButton rb = new RadioButton(context);
            rb.setText(expend.getExpendTypeName());
            rb.setChecked(true);
            holder.rgExpend.addView(rb);
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
