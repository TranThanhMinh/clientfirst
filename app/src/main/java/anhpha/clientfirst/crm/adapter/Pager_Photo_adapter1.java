package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.Photo;
import anhpha.clientfirst.crm.model.Photo1;


/**
 * Created by MinhTran on 7/12/2016.
 */
public class Pager_Photo_adapter1 extends PagerAdapter {
    Context context;
    List<Photo1> lvPhotop;
    LayoutInflater lay;
    public Pager_Photo_adapter1(Context context, List<Photo1> lvPhotop){
        this.context =context;
        this.lvPhotop =lvPhotop;
        lay = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return lvPhotop.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v =lay.inflate(R.layout.item_show_photo,null);
        ImageView imPhoto = (ImageView) v.findViewById(R.id.imShow_photo);

        Photo1 photo = lvPhotop.get(position);


        if(photo.getUrl().contains("http")) {
            Picasso.with(context)
                    .load(photo.getUrl() + photo.getName())
                    .fit().centerCrop()
                    .into(imPhoto);

        }
        else {
            Log.d("image",photo.getFilePart().toString());
            Picasso.with(context)
                    .load(photo.getFilePart())
                    .fit().centerCrop()
                    .into(imPhoto);

        }
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
