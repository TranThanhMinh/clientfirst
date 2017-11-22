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

public class adapter_client extends RecyclerView.Adapter<adapter_client.MyViewHolder> {
    private List<CompanyExist> list;
    private Context context;
    private LayoutInflater lay;

    public Click getClick() {
        return click;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    private Click click;
    public interface Click{
        void onClick(MClient mClient);
    }
    public adapter_client(Context context, List<CompanyExist> list, Click click) {
        this.context = context;
        this.list = list;
        this.click = click;
        lay = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = lay.inflate(R.layout.item_personnel, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CompanyExist companyExist = list.get(position);
        holder.tvName.setText(companyExist.getClientName());
        if(companyExist.getPosition()!= null&& companyExist.getPosition().length()>0)
        holder.tvPosition.setText(companyExist.getPosition());
        if(companyExist.getNote()!= null&& companyExist.getNote().length()>0)
        holder.tvNote.setText(companyExist.getNote());

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MClient mClient = new MClient();
                mClient.setClient_id(companyExist.getClientId());
                mClient.setClient_name(companyExist.getClientName());
                mClient.setAddress(companyExist.getAddress());
                mClient.setClient_structure_id(1);
                mClient.setPosition(companyExist.getPosition());
                mClient.setParent_name(companyExist.getParent_name());
                mClient.setType(1);
                click.onClick(mClient);
            }
        });
        holder.tvPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MClient mClient = new MClient();
                mClient.setClient_id(companyExist.getClientId());
                mClient.setClient_name(companyExist.getClientName());
                mClient.setAddress(companyExist.getAddress());
                mClient.setClient_structure_id(1);
                mClient.setPosition(companyExist.getPosition());
                mClient.setParent_name(companyExist.getParent_name());
                mClient.setType(1);
                click.onClick(mClient);
            }
        });
        holder.tvNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MClient mClient = new MClient();
                mClient.setClient_id(companyExist.getClientId());
                mClient.setClient_name(companyExist.getClientName());
                mClient.setAddress(companyExist.getAddress());
                mClient.setClient_structure_id(1);
                mClient.setParent_name(companyExist.getParent_name());
                mClient.setPosition(companyExist.getPosition());
                mClient.setType(1);
                click.onClick(mClient);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName,tvPosition,tvNote;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPosition = (TextView) itemView.findViewById(R.id.tvPosition);
            tvNote = (TextView) itemView.findViewById(R.id.tvNote);

        }
    }
}
