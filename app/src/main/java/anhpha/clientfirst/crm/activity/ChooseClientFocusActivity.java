package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.GroupTargetAdapter;
import anhpha.clientfirst.crm.adapter.TargetAdapter;
import anhpha.clientfirst.crm.adapter.adapter_Choose_client_focus;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.FocusGroup;
import anhpha.clientfirst.crm.model.Focus_Group;
import anhpha.clientfirst.crm.model.Focus_target;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static anhpha.clientfirst.crm.utils.LogUtils.api;

/**
 * Created by Administrator on 8/29/2017.
 */

public class ChooseClientFocusActivity extends BaseAppCompatActivity {
    private Retrofit retrofit;
    private Preferences preferences;
    private List<Focus_target> list;
    private List<Focus_Group> lvGroup = new ArrayList<>();
    private RecyclerView lvTarget;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_choose_client_focus);
        preferences = new Preferences(mContext);
        retrofit = getConnect();
        lvTarget = (RecyclerView) findViewById(R.id.lvTarget);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.srtFocus);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvTarget.setHasFixedSize(true);
        lvTarget.setLayoutManager(manager);
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Override
    protected void onResume() {
        super.onResume();
        box.showLoadingLayout();
        getTarget();
    }

    public void getTarget() {
        ServiceAPI target = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<Focus_target>>> result_target = target.get_client_focus_target(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), preferences.getStringValue(Constants.TOKEN, ""));
        result_target.enqueue(new Callback<MAPIResponse<List<Focus_target>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<Focus_target>>> call, Response<MAPIResponse<List<Focus_target>>> response) {
                api("", call, response);
                box.hideAll();
                list = response.body().getResult();
                //list group target.size >0 thi Target = list.get(position).getFocus_target_id()
                if (list != null && list.size() > 0) {
                    adapter_Choose_client_focus adapter = new adapter_Choose_client_focus(ChooseClientFocusActivity.this, list);
                    lvTarget.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<Focus_target>>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionDone:
                for (Focus_target focus_target : list) {

                    List<FocusGroup> list1 = focus_target.getFocusGroup();
                    for (FocusGroup focusGroup : list1) {
                        if (focusGroup.isCheck()) {
                            Focus_Group focusGroup1 = new Focus_Group();
                            focusGroup1.setCheck(focusGroup.isCheck());
                            focusGroup1.setFocus_target_group_id(focusGroup.getFocus_target_group_id());
                            focusGroup1.setFocus_target_id(focusGroup.getFocus_target_id());
                            focusGroup1.setFocus_target_name(focusGroup.getFocus_target_name());
                            focusGroup1.setOrder_no(focusGroup.getOrder_no());
                            focusGroup1.setStatus_id(focusGroup.getStatus_id());
                            lvGroup.add(focusGroup1);
                        }
                    }
                }
                setResult(Constants.RESULT_FOCUS, new Intent().putExtra("mFocus", (Serializable) lvGroup));
                finish();
                return true;

            case android.R.id.home:
                //   onBackPressed();
                lvGroup = null;
                setResult(Constants.RESULT_FOCUS, new Intent().putExtra("mFocus", (Serializable) lvGroup));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_edit_history_order, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        lvGroup = null;
        setResult(Constants.RESULT_FOCUS, new Intent().putExtra("mFocus", (Serializable) lvGroup));
        finish();
    }
}
