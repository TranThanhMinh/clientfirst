package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.CompanyExist;

/**
 * Created by Administrator on 9/22/2017.
 */

public class adapter_client_exist extends RecyclerView.Adapter<adapter_client_exist.MyViewHolder> {
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

    public interface Click {
        void onClick(int id, String name);
    }

    public adapter_client_exist(Context context, List<CompanyExist> list, Click click) {
        this.context = context;
        this.list = list;
        this.click = click;
        lay = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = lay.inflate(R.layout.item_company_exist, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CompanyExist companyExist = list.get(position);
        Log.d("Client_structure_id()",companyExist.getClient_structure_id()+"");
        if(companyExist.getClient_structure_id()==1)
        holder.image.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_crm_26));
        else
            holder.image.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_crm_40                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 ));
        holder.tvName.setText(companyExist.getClientName());
        if (companyExist.isCheck())
            holder.check.setChecked(true);
        else holder.check.setChecked(false);
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (companyExist.isCheck() == false) {
                    list.get(position).setCheck(true);

                } else {
                    list.get(position).setCheck(false);
                }
            }
        });
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (companyExist.isCheck() == false) {
                    list.get(position).setCheck(true);

                } else {
                    list.get(position).setCheck(false);
                }
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
        private ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            check = (CheckBox) itemView.findViewById(R.id.check);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
