package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.CompanyExist;
import anhpha.clientfirst.crm.model.MClient;

/**
 * Created by Administrator on 9/22/2017.
 */

public class adapter_company extends RecyclerView.Adapter<adapter_company.MyViewHolder> {
    private List<CompanyExist> list;
    private Context context;
    private LayoutInflater lay;


    public Click_company getClick() {
        return click;
    }

    public void setClick(Click_company click) {
        this.click = click;
    }

    private Click_company click;
    public interface Click_company{
        void onClick(MClient mClient);
    }

    public adapter_company(Context context, List<CompanyExist> list,Click_company click) {
        this.context = context;
        this.list = list;
        this.click = click;
        lay = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = lay.inflate(R.layout.item_company, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CompanyExist companyExist = list.get(position);
        holder.tvName.setText(companyExist.getClientName());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MClient mClient = new MClient();
                mClient.setClient_id(companyExist.getClientId());
                mClient.setClient_name(companyExist.getClientName());
                mClient.setAddress(companyExist.getAddress());
                mClient.setClient_structure_id(2);
                mClient.setType(1);
                click.onClick(mClient);
            }
        });
       // holder.tvAddress.setText(companyExist.getAddress());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName,tvAddress;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
         //   tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);


        }
    }
}
