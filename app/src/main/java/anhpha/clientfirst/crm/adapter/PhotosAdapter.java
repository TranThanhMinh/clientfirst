package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MPhoto;
import anhpha.clientfirst.crm.model.Photo;

/**
 * Created by Nguyen on 6/9/2015.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private IPhotoCallback mCallback;

    private Context mContext;

    private List<Photo> photoList;
    public interface funcDelete_lvImage{
        void Delete_photo_off(int pos);
        void Delete_photo_onl(int pos);
    }
    private funcDelete_lvImage delete_lvImage;
    public PhotosAdapter(Context context, List<Photo> photos,funcDelete_lvImage delete_lvImage) {
        mContext = context;
        photoList = photos;
        this.delete_lvImage = delete_lvImage;
    }

    public void setPhotoList(List<Photo> photos) {
        photoList = photos;
    }

    @Override
    public int getItemCount() {
        return (photoList == null) ? 0 : photoList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Photo photo = photoList.get(position);
        if(photo.getUrl().contains("http")) {
            Picasso.with(mContext)
                    .load(photo.getUrl() + photo.getName())
                    .fit().centerCrop()
                    .into(holder.ivPhoto);
            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_lvImage.Delete_photo_onl(position);
                }
            });
        }

        else {
            Picasso.with(mContext)
                    .load(photo.getFilePart())
                    .fit().centerCrop()
                    .into(holder.ivPhoto);
            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_lvImage.Delete_photo_off(position);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_row, parent, false);
        return new ViewHolder(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView ivPhoto;

        public ViewHolder(View v) {
            super(v);
            ivPhoto = (ImageView) v.findViewById(R.id.ivPhoto);
        }
    }

    public interface IPhotoCallback {

        void select(int i);

    }
}
