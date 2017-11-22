package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ChooseClientGroupAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClientGroup;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseClientGroupActivity extends BaseAppCompatActivity implements  RecyclerTouchListener.ClickListener,Callback<MAPIResponse<List<MClientGroup>>>, View.OnClickListener  {

    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    ChooseClientGroupAdapter activityAdapter;
    List<MClientGroup> mClientGroups = new ArrayList<>();
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_choose_clients);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.group_client);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, this));

        mClientGroups = (List<MClientGroup>) getIntent().getSerializableExtra("mClientGroups");

        if(mClientGroups.isEmpty()){
            mClientGroups = new ArrayList<>();
            GetRetrofit().create(ServiceAPI.class)
                    .getClientGroups(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                    )
                    .enqueue(this);
            setProgressBarIndeterminateVisibility(true);
            setProgressBarVisibility(true);
            box.showLoadingLayout();
            LogUtils.d(TAG, "getUserActivities ", "start");
        }

        activityAdapter = new ChooseClientGroupAdapter(mContext,mClientGroups);
        rvActivities.setAdapter(activityAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                setResult(Constants.RESULT_GROUP, new Intent().putExtra("mClientGroups", (Serializable) mClientGroups));
                finish();
                return true;

            case android.R.id.home:
                //   onBackPressed();
                mClientGroups = null;
                setResult(Constants.RESULT_GROUP, new Intent().putExtra("mClientGroups", (Serializable) mClientGroups));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MClientGroup>>> call, Response<MAPIResponse<List<MClientGroup>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        mClientGroups = response.body().getResult();
        activityAdapter.setActivityItemList(mClientGroups);
        activityAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MClientGroup>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onClick(View view, int position) {
        mClientGroups.get(position).setIs_select(!mClientGroups.get(position).is_select());
        activityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onBackPressed() {

        mClientGroups = null;
        setResult(Constants.RESULT_GROUP, new Intent().putExtra("mClientGroups", (Serializable) mClientGroups));
        finish();
    }
}
