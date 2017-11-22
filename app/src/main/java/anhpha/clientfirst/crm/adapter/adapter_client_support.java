package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.CompanyExist;
import anhpha.clientfirst.crm.model.MUser;

/**
 * Created by Administrator on 9/26/2017.
 */

public class adapter_client_support extends BaseAdapter {

    Context context; List<MUser> list;
    public interface Click{
        void onclick(int id);
    }

    public Click getClick() {
        return click;
    }

    public void setClick(Click click) {
        this.click = click;
    }

    private Click click;
    public adapter_client_support(Context context, List<MUser> list,Click click){
        this.context =context;
        this.list = list;
        this.click = click;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_client_support,null);
        TextView tvName = (TextView) v.findViewById(R.id.tvName);
        ImageView imDelete = (ImageView) v.findViewById(R.id.imDelete);
        final MUser companyExist = list.get(i);
        tvName.setText(companyExist.getUser_name());
        imDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onclick(companyExist.getUser_id());
            }
        });
        return v;
    }
}
