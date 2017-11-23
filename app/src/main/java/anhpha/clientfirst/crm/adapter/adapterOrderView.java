package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by Administrator on 11/9/2017.
 */

public class adapterOrderView extends RecyclerView.Adapter<adapterOrderView.MyViewHolder> {
    private Context mContext;
    private List<MContract> mData;

    public adapterOrderView(Context context, List<MContract> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        MContract activityItem = mData.get(position);
        holder.name.setText(activityItem.getContract_name());
        holder.type.setText(activityItem.getPrice_name().isEmpty()?mContext.getString(R.string.value):activityItem.getPrice_name());
        holder.tvDiscount.setText(Utils.formatCurrency(activityItem.getDiscount_price()));
        if(!Utils.isEmpty(activityItem.getNote())){
            holder.note.setText(activityItem.getNote());
            holder.note.setVisibility(View.VISIBLE);
        }

        holder.number.setText(activityItem.getNumber().toString());
        holder.total.setText(Utils.formatCurrency(activityItem.getPrice() * activityItem.getNumber() - activityItem.getDiscount_price() ));
        holder.price.setText(Utils.formatCurrency(activityItem.getPrice() * activityItem.getNumber()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number, total, price, note, type, tvDiscount;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            note = (TextView) itemView.findViewById(R.id.note);
            number = (TextView) itemView.findViewById(R.id.number);
            total = (TextView) itemView.findViewById(R.id.total);
            price = (TextView) itemView.findViewById(R.id.price);
            type = (TextView) itemView.findViewById(R.id.type);
            tvDiscount = (TextView) itemView.findViewById(R.id.tvDiscount);
        }
    }
}
