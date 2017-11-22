package anhpha.clientfirst.crm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.utils.DynamicBox;

/**
 * Created by Administrator on 10/12/2017.
 */

public class ActivityEmailContract extends BaseAppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView lvEmailContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_email_contact);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvEmailContact = (RecyclerView) findViewById(R.id.lvEmailContact);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.email);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvEmailContact.setHasFixedSize(true);
        lvEmailContact.setLayoutManager(manager);
        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id
                .switchButton);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true)
                    Toast.makeText(mContext, "chon", Toast.LENGTH_SHORT).show();
                else Toast.makeText(mContext, "khong chon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.done:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
