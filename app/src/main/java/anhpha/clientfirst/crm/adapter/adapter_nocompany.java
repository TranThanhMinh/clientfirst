package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.CompanyExist;

/**
 * Created by Administrator on 9/22/2017.
 */

public class adapter_nocompany extends RecyclerView.Adapter<adapter_nocompany.MyViewHolder> {
    private List<CompanyExist> list;
    private Context context;
    private LayoutInflater lay;



    public adapter_nocompany(Context context, List<CompanyExist> list) {
        this.context = context;
        this.list = list;

        lay = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = lay.inflate(R.layout.item_company_exist, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CompanyExist companyExist = list.get(position);
        holder.tvName.setText(companyExist.getClientName());
        if(companyExist.isCheck())
            holder.check.setChecked(true);
        else  holder.check.setChecked(false);
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(companyExist.isCheck() == true)
                list.get(position).setCheck(false);
                else list.get(position).setCheck(true);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private CheckBox check;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            check = (CheckBox) itemView.findViewById(R.id.check);
        }
    }
}
