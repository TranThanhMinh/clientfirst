package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.TrackingValueDefautAdapter;
import anhpha.clientfirst.crm.adapter.ValueDefautAdapter;
import anhpha.clientfirst.crm.adapter.adapter_orders;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MCall;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MOrders;
import anhpha.clientfirst.crm.model.Tracking_value_defaults;
import anhpha.clientfirst.crm.model.UserEmail;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CallActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MCall>>, View.OnClickListener, adapter_orders.Onclick {

    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvContract)
    TextView tvContract;
    @Bind(R.id.tvSwitch)
    TextView tvSwitch;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.lvTracking)
    RecyclerView lvTracking;
    @Bind(R.id.realOff)
    RelativeLayout realOff;
    private List<Tracking_value_defaults> listTracking;
    private List<Tracking_value_defaults> listTracking_userCall = new ArrayList<>();
    MCall mCall = new MCall();
    MClient mClient = new MClient();
    Retrofit retrofit;
    Preferences preferences;
    int color_id;
    @Bind(R.id.lvOrder)
    RecyclerView lvOrder;
    private int order_contract_id = 0;
    private int id = 0;
    private int check = 0;
    @Bind(R.id.tvShow)
    TextView tvShow;
    private boolean isHide = false;
    LinearLayout layout_order;
    adapter_orders adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_call);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);
        layout_order = (LinearLayout) findViewById(R.id.layout_order);
        setSupportActionBar(toolbar);
        tvSwitch.setText(R.string.srtContent1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_call);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvTracking.setHasFixedSize(true);
        lvTracking.setLayoutManager(manager);
        LinearLayoutManager manager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        lvOrder.setHasFixedSize(true);
        lvOrder.setLayoutManager(manager1);
        Intent intent = getIntent();
        mCall = (MCall) intent.getSerializableExtra("mCall");
        mClient = (MClient) intent.getSerializableExtra("mClient");
        tvClientName.setText(mClient.getClient_name());
        tvAddress.setText(mClient.getAddress());
        if (mClient.getAddress() != null && !mClient.getAddress().isEmpty()) {
            tvAddress.setVisibility(View.VISIBLE);
        }
        retrofit = getConnect();
        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id
                .switchButton);
        if (mCall == null) {
            isHide = true;
            mCall = new MCall();
            tvShow.setVisibility(View.GONE);
            getTracking_value_default();
            getOrder();

        }
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    lvOrder.setVisibility(View.VISIBLE);
                    order_contract_id = id;
                    //  Toast.makeText(mContext, " chon", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(mContext, "khong chon", Toast.LENGTH_SHORT).show();
                    order_contract_id = 0;
                    lvOrder.setVisibility(View.GONE);
                }
            }
        });
        if (mCall.getUser_call_id() > 0) {
            isHide = false;
            tvShow.setVisibility(View.VISIBLE);
            tvShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            etContent.setText(mCall.getContent_call());
            etContent.setFocusable(false);
            getUserCall();
            if (mCall.getOrder_contract_id() > 0) {
                id = mCall.getOrder_contract_id();
                order_contract_id = mCall.getOrder_contract_id();
                tvContract.setText(mCall.getOrder_contract_name());
                layout_order.setVisibility(View.VISIBLE);
                switchCompat.setChecked(true);
            }
        }


    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_user)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void getOrder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_exchange)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<MOrders>>> call = api.get_orders(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0));
        call.enqueue(new Callback<MAPIResponse<List<MOrders>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<MOrders>>> call, Response<MAPIResponse<List<MOrders>>> response) {
                TokenUtils.checkToken(CallActivity.this, response.body().getErrors());
                LogUtils.api("", call, response);
                List<MOrders> orders = response.body().getResult();
                List<MOrders> orders1 = new ArrayList<MOrders>();
                if (orders != null && orders.size() > 0) {
                    orders.get(0).getOrderContractName();
//                    adapter_orders adapter = new adapter_orders(CallActivity.this, orders, CallActivity.this);
//                    lvOrder.setAdapter(adapter);
                    if (order_contract_id > 0) {
                        lvOrder.setVisibility(View.VISIBLE);
                        for (MOrders mOrders : orders) {
                            if (mOrders.getOrderContractId() == order_contract_id) {
                                mOrders.setCheck(true);
                            } else {
                                mOrders.setCheck(false);
                            }
                            orders1.add(mOrders);
                        }
                        adapter = new adapter_orders(CallActivity.this, orders1, CallActivity.this);
                        lvOrder.setAdapter(adapter);
                    } else {
                        adapter = new adapter_orders(CallActivity.this, orders, CallActivity.this);
                        lvOrder.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<MOrders>>> call, Throwable t) {

            }
        });
    }

    public void getTracking_value_default() {
        ServiceAPI apiTracking = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<Tracking_value_defaults>>> tracking_value_defaults = apiTracking.getTracking_value_defaults(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), 3, preferences.getStringValue(Constants.TOKEN, ""));
        tracking_value_defaults.enqueue(new Callback<MAPIResponse<List<Tracking_value_defaults>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<Tracking_value_defaults>>> call, Response<MAPIResponse<List<Tracking_value_defaults>>> response) {
                listTracking = response.body().getResult();
                LogUtils.api("", call, response);
//                TrackingValueDefautAdapter adapte = new TrackingValueDefautAdapter(CallActivity.this, listTracking);
//                lvTracking.setAdapter(adapte);
                if (listTracking_userCall != null && listTracking_userCall.size() > 0) {
                    List<Tracking_value_defaults> list = new ArrayList<>();
                    for (Tracking_value_defaults email : listTracking) {
                        for (int i = 0; i < listTracking_userCall.size(); i++) {
                            if (email.getTracking_value_default_id() == listTracking_userCall.get(i).getTracking_value_default_id()) {
                                email.setTracking(true);
                                list.add(email);
                                i = listTracking_userCall.size();
                            } else {
                                if (i == listTracking_userCall.size() - 1) {
                                    email.setTracking(false);
                                    list.add(email);
                                }

                            }
                        }

                    }
                    listTracking = list;
                    TrackingValueDefautAdapter adapte = new TrackingValueDefautAdapter(CallActivity.this, listTracking);
                    lvTracking.setAdapter(adapte);
                } else {
                    TrackingValueDefautAdapter adapte = new TrackingValueDefautAdapter(CallActivity.this, listTracking);
                    lvTracking.setAdapter(adapte);
                }
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<Tracking_value_defaults>>> call, Throwable t) {
                LogUtils.api("", call, t.toString());
            }
        });
    }

    public void getUserCall() {
        ServiceAPI apiTracking = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<UserEmail>> user_email = apiTracking.get_user_call(preferences.getIntValue(Constants.USER_ID, 0), mCall.getUser_call_id(), preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.PARTNER_ID, 0));
        user_email.enqueue(new Callback<MAPIResponse<UserEmail>>() {
            @Override
            public void onResponse(Call<MAPIResponse<UserEmail>> call, Response<MAPIResponse<UserEmail>> response) {
                LogUtils.api("", call, response);
                listTracking_userCall = response.body().getResult().getValuesDefault();
                check = response.body().getResult().getUserId();
                ValueDefautAdapter adapte = new ValueDefautAdapter(CallActivity.this, listTracking_userCall);
                lvTracking.setAdapter(adapte);
            }

            @Override
            public void onFailure(Call<MAPIResponse<UserEmail>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        if (isHide == false) {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.done)
                    menu.getItem(i).setVisible(false);
                if (menu.getItem(i).getItemId() == R.id.edit)
                    menu.getItem(i).setVisible(true);
            }
        } else for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getItemId() == R.id.done)
                menu.getItem(i).setVisible(true);
            if (menu.getItem(i).getItemId() == R.id.edit)
                menu.getItem(i).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                List<Tracking_value_defaults> list = new ArrayList<>();
                for (Tracking_value_defaults tracking : listTracking) {
                    if (tracking.isTracking()) {
                        Tracking_value_defaults track = new Tracking_value_defaults();
                        track.setTracking_value_default_id(tracking.getTracking_value_default_id());
                        track.setCreate_date(tracking.getCreate_date());
                        track.setTracking_value_default_content(tracking.getTracking_value_default_content());
                        track.setStatus_id(tracking.getStatus_id());
                        track.setTracking_user_type_id(tracking.getTracking_user_type_id());
                        track.setUser_id(tracking.getUser_id());
                        track.setPartner_id(tracking.getPartner_id());
                        Log.d("tracking", tracking.getTracking_value_default_content());
                        list.add(track);
                        tracking.setTracking(false);
                    }
                }
                box.showLoadingLayout();
                mCall.setValues_default(list);
                mCall.setClient_id(mClient.getClient_id());
                mCall.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
                mCall.setContent_call(etContent.getText().toString());
                mCall.setLatitude(mLastLocation.getLatitude());
                mCall.setLongitude(mLastLocation.getLongitude());
                mCall.setOrder_contract_id(order_contract_id);
                mCall.setUser_call_id(mCall.getUser_call_id());
                mCall.setDisplay_type(0);
                mCall.setActivity_type(0);
                GetRetrofit().create(ServiceAPI.class)
                        .setUserCall(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                , mClient.getClient_id()
                                , mCall)
                        .enqueue(this);

                LogUtils.d(TAG, "getUserActivities ", "start");
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.edit:
                if (preferences.getIntValue(Constants.USER_ID, 0) == check) {
                    tvShow.setVisibility(View.GONE);
                    etContent.setFocusable(true);
                    etContent.setFocusableInTouchMode(true);
                    etContent.requestFocus();
                    isHide = true;
                    invalidateOptionsMenu();
                    getTracking_value_default();
                    getOrder();
                    layout_order.setVisibility(View.GONE);
                    return true;
                }else Toast.makeText(mContext, R.string.edit_activity, Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<MCall>> call, Response<MAPIResponse<MCall>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext, response.body().getErrors());
        if (response.body().isHasErrors()) {
            Utils.showError(coordinatorLayout, R.string.call_fail);
        } else {
            Utils.showDialogSuccess(mContext, R.string.call_done);

        }

    }

    @Override
    public void onFailure(Call<MAPIResponse<MCall>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void Click(int id) {
        this.id = id;
        order_contract_id = id;
    }
}
