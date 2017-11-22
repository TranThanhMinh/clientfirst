package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_client_contract;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.dialogplus.DialogPlus;
import anhpha.clientfirst.crm.dialogplus.Holder;
import anhpha.clientfirst.crm.dialogplus.OnCancelListener;
import anhpha.clientfirst.crm.dialogplus.OnClickListener;
import anhpha.clientfirst.crm.dialogplus.OnDismissListener;
import anhpha.clientfirst.crm.dialogplus.OnItemClickListener;
import anhpha.clientfirst.crm.dialogplus.ViewHolder;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.CompanyExist;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.Result;
import anhpha.clientfirst.crm.model.Users;
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
 * Created by Administrator on 9/20/2017.
 */

public class PersonnelContractActivity extends BaseAppCompatActivity implements adapter_client_contract.Click {
    Toolbar toolbar;
    private Retrofit retrofit;
    private Preferences preferences;
    Bundle b;
    private List<CompanyExist> list;
    private List<MId> mIds;
    private RecyclerView lvPersonnel;
    MOrder mClient = new MOrder();

    TextView tvName, tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_personnel);
        box = new DynamicBox(this, R.layout.activity_personnel);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvPersonnel = (RecyclerView) findViewById(R.id.lvPersonnel);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        preferences = new Preferences(mContext);
        Intent intent = getIntent();
        mClient = (MOrder) intent.getSerializableExtra("mOrder");
        Log.d("AAA", mClient.getClient_id() + "");
        tvName.setText(mClient.getOrder_contract_name());
        tvAddress.setVisibility(View.GONE);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.contact1);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvPersonnel.setHasFixedSize(true);
        lvPersonnel.setLayoutManager(manager);
        b = getIntent().getExtras();
        retrofit = getConnect();


    }

    @Override
    protected void onResume() {
        super.onResume();
        box.showLoadingLayout();
        getClientsByParent();
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_exchange)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public void funcAddClient(List<MId> list) {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<Result> call = api.set_order_contract_detail_clients(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), mClient.getOrder_contract_id(), list);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                LogUtils.api("", call, response);
                if (response.body().getHasErrors() == false) {
                    box.showLoadingLayout();
                    getClientsByParent();
                } else {
                    Toast.makeText(PersonnelContractActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
    public void getClientsByParent() {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<CompanyExist>>> call = api.get_order_contract_detail_clients(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), mClient.getOrder_contract_id());
        call.enqueue(new Callback<MAPIResponse<List<CompanyExist>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<CompanyExist>>> call, Response<MAPIResponse<List<CompanyExist>>> response) {
                TokenUtils.checkToken(PersonnelContractActivity.this, response.body().getErrors());
                LogUtils.api("", call, response);
                box.hideAll();
                list = response.body().getResult();
                if (list != null && list.size() > 0) {
                        List<CompanyExist> list1 = new ArrayList<CompanyExist>();
                        for(CompanyExist companyExist:list){
                            companyExist.setParent_name(mClient.getClient_name());
                            list1.add(companyExist);
                        }
                    adapter_client_contract adapter = new adapter_client_contract(PersonnelContractActivity.this, list1, PersonnelContractActivity.this);
                        lvPersonnel.setAdapter(adapter);

                } else lvPersonnel.setAdapter(null);
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<CompanyExist>>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_personnel, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:
//                showDialog1(
//                        Gravity.BOTTOM
//                );
                startActivityForResult(new Intent(PersonnelContractActivity.this, ClientExistActivity.class).putExtra("lvId", (Serializable) mIds), Constants.RESULT_CLIENT);

                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog1(int gravity) {

        Holder holder;

            holder = new ViewHolder(R.layout.show_add_personnel);

        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {

                    case R.id.btn1:

                            Intent intent = new Intent(mContext, EditClientActivity.class);
                            intent.putExtra("Structure", 1);
                            intent.putExtra("add",2);
                            intent.putExtra("client_id",mClient.getClient_id());
                            intent.putExtra("name_client",mClient.getClient_name());
                            intent.putExtra("hide",1);
                            startActivity(intent);

                        break;
                    case R.id.btn2:
                            startActivityForResult(new Intent(PersonnelContractActivity.this, ClientExistActivity.class).putExtra("lvId", (Serializable) mIds), Constants.RESULT_CLIENT);
                         break;
                    case R.id.btn3:
                        break;

                }
                dialog.dismiss();
            }
        };

        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                //TextView textView = (TextView) view.findViewById(R.id.text_view);
                // String clickedAppName = textView.getText().toString();
                dialog.dismiss();
                //        Toast.makeText(MainActivity.this, clickedAppName + " clicked", Toast.LENGTH_LONG).show();
            }
        };

        OnDismissListener dismissListener = new OnDismissListener() {
            @Override
            public void onDismiss(DialogPlus dialog) {
                //        Toast.makeText(MainActivity.this, "dismiss listener invoked!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        };

        OnCancelListener cancelListener = new OnCancelListener() {
            @Override
            public void onCancel(DialogPlus dialog) {
                dialog.dismiss();
                //        Toast.makeText(MainActivity.this, "cancel listener invoked!", Toast.LENGTH_SHORT).show();
            }
        };

        showNoFooterDialog(holder, gravity, clickListener, itemClickListener, dismissListener, cancelListener
        );
        return;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RESULT_CLIENT) {
            mIds = new ArrayList<>();
            mIds = (List<MId>) data.getSerializableExtra("lvId");
            if(mIds != null && mIds.size() >0){
                Users users = new Users();
                users.setIds(mIds);
                funcAddClient(mIds);
            }
        }
    }

    private void showNoFooterDialog(Holder holder, int gravity,
                                    OnClickListener clickListener, OnItemClickListener itemClickListener,
                                    OnDismissListener dismissListener, OnCancelListener cancelListener
    ) {
        DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setBackgroundColorResId(R.color.transparent)
                // .setHeader(R.layout.show_add_personnel)
                .setCancelable(true)
                .setGravity(gravity)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                    }
                })
                .setOnDismissListener(dismissListener)
                .setOnCancelListener(cancelListener)
                .setExpanded(false)
                .create();
        dialog.show();
    }
    public void funcDeleteClient(int id) {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<Result> call = api.delete_order_contract_detail_client(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), id, mClient.getOrder_contract_id());
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                LogUtils.api("", call, response);
                if (response.body().getHasErrors() == false) {
                    getClientsByParent();
                } else {
                    Toast.makeText(PersonnelContractActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
    @Override
    public void onClick(MClient mClient) {
        if(mClient.getType() == 1) {
            Intent intent = new Intent(mContext, ClientActivity.class).putExtra("mClient", mClient);
            mContext.startActivity(intent);
        }
        else {
            funcDeleteClient(mClient.getClient_id());
        }
    }
}
