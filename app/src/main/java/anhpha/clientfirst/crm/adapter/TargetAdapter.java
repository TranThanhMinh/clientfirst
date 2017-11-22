package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.Focus_target;

/**
 * Created by Administrator on 8/18/2017.
 */

public class TargetAdapter extends BaseAdapter {
    private List<Focus_target> list;
    private Context context;
    private LayoutInflater lay;

    public TargetAdapter(Context context, List<Focus_target> list) {
        this.context = context;
        this.list = list;
        lay = LayoutInflater.from(context);
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
        View v = lay.inflate(R.layout.item_sp, null);
        TextView text1 = (TextView) v.findViewById(R.id.text);
        Focus_target focus_target = list.get(i);
        text1.setText(focus_target.getFocus_target_name()+"");
        return v;
    }
}
