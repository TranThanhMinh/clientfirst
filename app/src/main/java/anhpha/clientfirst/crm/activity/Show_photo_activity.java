package anhpha.clientfirst.crm.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.Pager_Photo_adapter;
import anhpha.clientfirst.crm.model.Photo;

import static anhpha.clientfirst.crm.adapter.adapter_Photo.list_photo;


/**
 * Created by MinhTran on 7/12/2017.
 */

public class Show_photo_activity extends Activity implements View.OnClickListener {

    private ImageView imClose;
    private ImageView vpPhoto;
    Uri uri;
    String url;
    int photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_photo);

        imClose = (ImageView) findViewById(R.id.imClose);
        vpPhoto = (ImageView) findViewById(R.id.vpPhoto);
        imClose.setOnClickListener(this);
        photo = (int) getIntent().getSerializableExtra("photo");
        if (photo == 1) {
            uri = Uri.parse(getIntent().getStringExtra("crop"));
            Picasso.with(this)
                    .load(uri)
                    .fit().centerCrop()
                    .into(vpPhoto);
        } else {
            url = (String) getIntent().getSerializableExtra("crop");
            Picasso.with(this)
                    .load(url)
                    .fit().centerCrop()
                    .into(vpPhoto);
        }


    }


    @Override
    public void onClick(View view) {
        if (view == imClose) {
            finish();
        }
    }
}
