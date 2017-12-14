package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_salesprocess;
import anhpha.clientfirst.crm.adapter.adapter_salesstatus;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.OrderContractStatus;
import anhpha.clientfirst.crm.model.OrderContractStatusGroup;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static anhpha.clientfirst.crm.R.id.coordinatorLayout;

/**
 * Created by Administrator on 10/12/2017.
 */

public class ActivitySalesStatus extends BaseAppCompatActivity implements adapter_salesstatus.Onclick {
    private Toolbar toolbar;
    private RecyclerView lvSalesProcess;
    private List<OrderContractStatus> lvByGroup = new ArrayList<>();
    private int idGroup = 0, percent = 0, contract_order_id = 1;
    private String name, SalesProcess;
    private TextView tvName;
    private Retrofit retrofit;
    private Preferences preferences;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_salesstatus);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvName = (TextView) findViewById(R.id.tvName);
        lvSalesProcess = (RecyclerView) findViewById(R.id.lvSalesProcess);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        preferences = new Preferences(this);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.status);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        retrofit = getConnect();
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvSalesProcess.setHasFixedSize(true);
        lvSalesProcess.setLayoutManager(manager);
        lvByGroup = (List<OrderContractStatus>) getIntent().getSerializableExtra("lvByGroup");
        idGroup = (int) getIntent().getSerializableExtra("idGroup");
        contract_order_id = (int) getIntent().getSerializableExtra("contract_order_id");
        tvName.setText((String) getIntent().getSerializableExtra("SalesProcess"));
//        if (lvByGroup == null) {
//            getContractStatusGroup();
//        } else {
//            adapter_salesstatus adapter = new adapter_salesstatus(ActivitySalesStatus.this, lvByGroup, ActivitySalesStatus.this);
//            lvSalesProcess.setAdapter(adapter);
//        }
        getContractStatusGroup();
    }

    public void getContractStatusGroup() {
        final List<OrderContractStatus> lvSort= new ArrayList<>();
        final List<OrderContractStatus> lvSort1= new ArrayList<>();
        final List<OrderContractStatus> lvAll= new ArrayList<>();

        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<OrderContractStatus>>> call = api.get_contract_status_by_group(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), idGroup);
        call.enqueue(new Callback<MAPIResponse<List<OrderContractStatus>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<OrderContractStatus>>> call, Response<MAPIResponse<List<OrderContractStatus>>> response) {
                lvByGroup = response.body().getResult();
                TokenUtils.checkToken(ActivitySalesStatus.this, response.body().getErrors());
                LogUtils.api("", call, response);
                for(OrderContractStatus status:lvByGroup){

                    if(status.getOrderContractStatusTypeId() ==1){
                        lvSort.add(status);
                    }
                    else {
                        lvSort1.add(status);
                    }
                }
                lvAll.addAll(lvSort);
                lvAll.addAll(lvSort1);
                adapter_salesstatus adapter = new adapter_salesstatus(ActivitySalesStatus.this, lvAll, ActivitySalesStatus.this);
                lvSalesProcess.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<OrderContractStatus>>> call, Throwable t) {

            }
        });
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_exchange)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getItemId() == R.id.done)
                menu.getItem(i).setVisible(true);
            if (menu.getItem(i).getItemId() == R.id.edit)
                menu.getItem(i).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.done:
                if (id == 0) {
                    Toast.makeText(mContext, "Chọn trạng thái.", Toast.LENGTH_SHORT).show();
                } else {
                    if (contract_order_id == 1) {
                        setResult(Constants.RESULT_GROUP_BY_GROUP, new Intent().putExtra("idByGroup", idGroup).putExtra("name", name).putExtra("percent", percent));
                        finish();
                    } else
                        funcEditStatusContract();

                }
                break;
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void funcEditStatusContract() {
        GetRetrofit().create(ServiceAPI.class)
                .setStatusOrder(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , contract_order_id
                        , idGroup
                )
                .enqueue(new Callback<MAPIResponse<MId>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<MId>> call, Response<MAPIResponse<MId>> response) {
                        LogUtils.api(TAG, call, (response.body()));

                        TokenUtils.checkToken(mContext, response.body().getErrors());
                        if (!response.body().isHasErrors()) {
                            Utils.showDialogSuccess(mContext, R.string.srtDone);
                        } else {
                            Utils.showError(linearLayout, R.string.srtFalse);
                        }
                    }

                    @Override
                    public void onFailure(Call<MAPIResponse<MId>> call, Throwable t) {
                        LogUtils.d(TAG, "getUserActivities ", t.toString());
                        box.hideAll();
//                        Utils.showError(coordinatorLayout, R.string.delete_order_fail);
                    }
                });
    }

    @Override
    public void Click(int idGroup, String name, int percent) {
        this.idGroup = idGroup;
        this.name = name;
        this.percent = percent;
    }
}
