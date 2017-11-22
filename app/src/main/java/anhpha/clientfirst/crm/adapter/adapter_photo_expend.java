package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.activity.Show_photo_activity;
import anhpha.clientfirst.crm.model.Photo;

/**
 * Created by Administrator on 9/6/2017.
 */

public class adapter_photo_expend extends RecyclerView.Adapter<adapter_photo_expend.MyViewHolder> {
    Context context;
    List<Photo> list;
    public interface funcDelete_lvImage{
        void Delete_photo_off(int pos);
    }
    private funcDelete_lvImage delete_lvImage;
    public adapter_photo_expend(Context context, List<Photo> list,funcDelete_lvImage delete_lvImage) {
        this.list = list;
        this.context = context;
        this.delete_lvImage = delete_lvImage;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Photo photo = list.get(position);
        if(photo.getUrl().contains("http")) {
            Picasso.with(context)
                    .load(photo.getUrl() + photo.getName())
                    .fit().centerCrop()
                    .into(holder.imHistory);
            holder.imHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   context.startActivity(new Intent(context, Show_photo_activity.class).putExtra("list", (Serializable) list));
                }
            });
        }

        else {
            Picasso.with(context)
                    .load(photo.getFilePart())
                    .fit().centerCrop()
                    .into(holder.imHistory);
            holder.imHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_lvImage.Delete_photo_off(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imHistory;


        public MyViewHolder(View view) {
            super(view);
            imHistory = (ImageView) view.findViewById(R.id.imHistory);

        }
    }
}
