package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MReportCosts;
import anhpha.clientfirst.crm.model.MReportFocus;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by Administrator on 8/30/2017.
 */

public class adapter_report_costs extends RecyclerView.Adapter<adapter_report_costs.MyViewHolder> {
    Context context;
    List<MReportCosts> list;

    public adapter_report_costs(Context context, List<MReportCosts> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.report_list_row, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MReportCosts reportFocus = list.get(position);
        holder.name.setText(reportFocus.getName());
        holder.value.setText("$ "+ Utils.formatCurrency(reportFocus.getValue()) + "");
//        if (position == 0) {
            holder.color.setBackgroundColor(Color.rgb(42+((position+1)*50), 188+((position+1)*40), 186+((position+1)*30)));
//        } else if (position == 1) {
//            holder.color.setBackgroundColor(Color.rgb(250, 172, 88));
//        } else if (position == 2) {
//            holder.color.setBackgroundColor(Color.rgb(11, 11, 97));
//        } else if (position == 3) {
//            holder.color.setBackgroundColor(Color.rgb(223, 1, 1));
//        } else {
//            holder.color.setBackgroundColor(Color.rgb(137, 137, 137));
//        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, value, color;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            value = (TextView) view.findViewById(R.id.value);
            color = (TextView) view.findViewById(R.id.color);

        }

    }
}
