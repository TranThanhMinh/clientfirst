package anhpha.clientfirst.crm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.Pager_Photo_adapter;
import anhpha.clientfirst.crm.adapter.Pager_Photo_adapter1;
import anhpha.clientfirst.crm.model.Photo;
import anhpha.clientfirst.crm.model.Photo1;
import anhpha.clientfirst.crm.model.Photo2;


/**
 * Created by MinhTran on 7/12/2017.
 */

public class Show_photo_activity1 extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private int dotsCount;
    private ImageView imClose;
    private ImageView[] dots;
    private LinearLayout pager_indicator;
    private Pager_Photo_adapter pager_photo_adapter;
    private ViewPager vpPhoto;
//    List<Photo1> list = new ArrayList<>();
//    List<Photo2> list_crop = new ArrayList<>();
//    List<Photo> list_all = new ArrayList<>();
    List<Photo> list_all = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        imClose = (ImageView) findViewById(R.id.imClose);
        vpPhoto = (ViewPager) findViewById(R.id.vpPhoto);
        vpPhoto.setCurrentItem(0);
        vpPhoto.setOnPageChangeListener(this);
        imClose.setOnClickListener(this);
        list_all = (List<Photo>) getIntent().getSerializableExtra("list");
//        list = (List<Photo1>) getIntent().getSerializableExtra("list");
//        list_crop = getIntent().getParcelableArrayListExtra("crop");
//        for (Photo1 photo1 : list) {
//            Photo photo = new Photo();
//            photo.setUrl(photo1.getUrl());
//            photo.setName(photo1.getName());
//            photo.setFilePart(photo1.getFilePart());
//            list_all.add(photo);
//        }
//        for (Photo2 photo2 : list_crop) {
//            Photo photo1 = new Photo();
//            photo1.setUrl("");
//            photo1.setName("");
//            photo1.setFilePart(photo2.getFilePart());
//            list_all.add(photo1);
//        }

        getPhoto();
        setUiPageViewController();
    }

    public void getPhoto() {
        pager_photo_adapter = new Pager_Photo_adapter(Show_photo_activity1.this, list_all);
        vpPhoto.setAdapter(pager_photo_adapter);
    }

    private void setUiPageViewController() {
        dotsCount = pager_photo_adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dost));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    20,
                    15
            );
            params.setMargins(2, 0, 2, 0);
            if (dotsCount > 1) {
                pager_indicator.addView(dots[i], params);
            } else {
            }
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.dost_view));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dost));
        }
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.dost_view));
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        if (view == imClose) {
            finish();
        }
    }
}
