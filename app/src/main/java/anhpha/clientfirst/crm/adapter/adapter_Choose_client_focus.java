package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.FocusGroup;
import anhpha.clientfirst.crm.model.Focus_target;

/**
 * Created by Administrator on 8/29/2017.
 */

public class adapter_Choose_client_focus extends RecyclerView.Adapter<adapter_Choose_client_focus.MyViewHolder> {
    private Context context;
    private List<Focus_target> list;
    private LayoutInflater lay;

    public adapter_Choose_client_focus(Context context, List<Focus_target> list) {
        this.context = context;
        this.list = list;
        lay = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = lay.inflate(R.layout.item_choose_client_focus, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Focus_target focus_target = list.get(position);
        holder.tvGroupTarget.setText(focus_target.getFocus_target_name());
        List<FocusGroup> listFocusGroup = focus_target.getFocusGroup();
        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.lvItem.setHasFixedSize(true);
        holder.lvItem.setLayoutManager(manager);
        if(listFocusGroup.size()>0 && listFocusGroup != null) {
            adapter_Choose_client_focus_group adapter = new adapter_Choose_client_focus_group(context, listFocusGroup);
            holder.lvItem.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGroupTarget;
        private RecyclerView lvItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvGroupTarget = (TextView) itemView.findViewById(R.id.tvGroupTarget);
            lvItem = (RecyclerView) itemView.findViewById(R.id.lvItem);
        }
    }
}
