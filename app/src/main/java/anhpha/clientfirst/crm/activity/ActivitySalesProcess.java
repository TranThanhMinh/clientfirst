package anhpha.clientfirst.crm.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_salesprocess;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.OrderContractStatus;
import anhpha.clientfirst.crm.model.OrderContractStatusGroup;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 10/12/2017.
 */

public class ActivitySalesProcess extends BaseAppCompatActivity implements adapter_salesprocess.Onclick {
    private Toolbar toolbar;
    private RecyclerView lvSalesProcess;
    private Retrofit retrofit;
    private Preferences preferences;
    private List<OrderContractStatus> lvByGroup;
    private int idGroup = 0;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_salesprocess);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvSalesProcess = (RecyclerView) findViewById(R.id.lvSalesProcess);
        preferences = new Preferences(this);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.SalesProcess);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvSalesProcess.setHasFixedSize(true);
        lvSalesProcess.setLayoutManager(manager);
        retrofit = getConnect();
        box.showLoadingLayout();
        getContractStatusGroup();
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_exchange)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void getContractStatusGroup() {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<OrderContractStatusGroup>>> call = api.get_contract_status_group(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), preferences.getStringValue(Constants.TOKEN, ""));
        call.enqueue(new Callback<MAPIResponse<List<OrderContractStatusGroup>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<OrderContractStatusGroup>>> call, Response<MAPIResponse<List<OrderContractStatusGroup>>> response) {
                List<OrderContractStatusGroup> list = response.body().getResult();
                TokenUtils.checkToken(ActivitySalesProcess.this, response.body().getErrors());
                LogUtils.api("", call, response);
                box.hideAll();
                if (list != null && list.size() > 0) {
                    adapter_salesprocess adapter = new adapter_salesprocess(ActivitySalesProcess.this, list, ActivitySalesProcess.this);
                    lvSalesProcess.setAdapter(adapter);
                } else lvSalesProcess.setAdapter(null);
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<OrderContractStatusGroup>>> call, Throwable t) {

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
                if (idGroup == 0)
                    Toast.makeText(mContext, "Chon quy trình án hàng", Toast.LENGTH_SHORT).show();
                else {
                    setResult(Constants.RESULT_GROUP_BY, new Intent().putExtra("idGroup", idGroup).putExtra("name", name).putExtra("lvByGroup", (Serializable) lvByGroup));
                    finish();
                }
                break;
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void click(int idGroup, String name, List<OrderContractStatus> lvByGroup) {
        this.lvByGroup = lvByGroup;
        this.idGroup = idGroup;
        this.name = name;
    }
}
