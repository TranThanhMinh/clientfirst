package anhpha.clientfirst.crm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.OrdersAdapter;
import anhpha.clientfirst.crm.adapter.adapter_salesprocess;
import anhpha.clientfirst.crm.adapter.adapter_salesprocess_sort;
import anhpha.clientfirst.crm.adapter.adapter_salesstatus;
import anhpha.clientfirst.crm.adapter.adapter_salesstatus_sort;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.MRequestBody;
import anhpha.clientfirst.crm.model.OrderContractStatus;
import anhpha.clientfirst.crm.model.OrderContractStatusGroup;
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

public class OrdersActivity extends BaseAppCompatActivity implements RecyclerTouchListener.ClickListener, Callback<MAPIResponse<List<MOrder>>>, View.OnClickListener, adapter_salesprocess_sort.Onclick, adapter_salesstatus_sort.Onclick {
    @Bind(R.id.textView0)
    TextView textView0;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer)
    LinearLayout mDrawerList;

    @Bind(R.id.ibOpen)
    ImageButton ibOpen;
    @Bind(R.id.ibClose)
    ImageButton ibClose;

    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    DynamicBox box;
    OrdersAdapter activityAdapter;
    List<MOrder> mOrders;
    List<MOrder> list = new ArrayList<>();
    List<MOrder> list2 = new ArrayList<>();
    List<MId> mIds;
    Preferences preferences;
    List<OrderContractStatusGroup> listOrder = new ArrayList<>();
    List<OrderContractStatusGroup> listsort = new ArrayList<>();
    int type, id_group;
    boolean load_order = true;
    private RecyclerView lvStatus;
    private Retrofit retrofit;
    Dialog dialog;
    List<OrderContractStatus> lvByGroup = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_orders);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);
        retrofit = getConnect();
        //set up
        lvStatus = (RecyclerView) findViewById(R.id.lvStatus);
        mDrawerList.getLayoutParams().width = Utils.getWidth(mContext) - 100;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_order);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ibClose.setOnClickListener(this);
        ibOpen.setOnClickListener(this);

        textView0.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, this));

    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_exchange)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void getContractStatus(int idGroup) {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvStatus.setHasFixedSize(true);
        lvStatus.setLayoutManager(manager);
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<OrderContractStatus>>> call = api.get_contract_status_by_group(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), idGroup);
        call.enqueue(new Callback<MAPIResponse<List<OrderContractStatus>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<OrderContractStatus>>> call, Response<MAPIResponse<List<OrderContractStatus>>> response) {
                lvByGroup = response.body().getResult();
                List<MId> mIds = new ArrayList<>();
                int i = 0;
                for(OrderContractStatus status:lvByGroup){

                    if(status.getOrderContractStatusTypeId() ==1){
                        mIds.add(new MId(status.getOrderContractStatusId()));
                    }
                    else {
//                        lvSort1.add(status);
                    }
                    i++;
                }

//                for (OrderContractStatus o : lvByGroup) {
//
//                    if (i == lvByGroup.size() - 1 || i == lvByGroup.size() - 2 || i == lvByGroup.size() - 3) {
//
//                    } else mIds.add(new MId(o.getOrderContractStatusId()));
//                    i++;
//                }

                MRequestBody mRequestBody = new MRequestBody();
                mRequestBody.setIds(mIds);


                GetRetrofit().create(ServiceAPI.class)
                        .getOrderByPartner(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                , mRequestBody
                        )
                        .enqueue(OrdersActivity.this);
                setProgressBarIndeterminateVisibility(true);
                setProgressBarVisibility(true);
                box.showLoadingLayout();

                LogUtils.d(TAG, "getUserActivities ", "start");

//                adapter_salesstatus_sort adapter = new adapter_salesstatus_sort(OrdersActivity.this, lvByGroup, OrdersActivity.this);
//                lvStatus.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<OrderContractStatus>>> call, Throwable t) {

            }
        });
    }

    public void getContractStatusGroupDefaut() {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        dialog = new Dialog(this, R.style.Dialog_No_Border);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_salesprocess);
        dialog.setCancelable(true);
        final RecyclerView lvSalesProcess = (RecyclerView) dialog.findViewById(R.id.lvSalesProcess);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvSalesProcess.setHasFixedSize(true);
        lvSalesProcess.setLayoutManager(manager);
        Call<MAPIResponse<List<OrderContractStatusGroup>>> call = api.get_contract_status_group(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), preferences.getStringValue(Constants.TOKEN, ""));
        call.enqueue(new Callback<MAPIResponse<List<OrderContractStatusGroup>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<OrderContractStatusGroup>>> call, Response<MAPIResponse<List<OrderContractStatusGroup>>> response) {
                List<OrderContractStatusGroup> list = response.body().getResult();
                TokenUtils.checkToken(OrdersActivity.this, response.body().getErrors());
                LogUtils.api("", call, response);
                box.hideAll();
                if (list != null && list.size() > 0) {
                    int a = 0;
                    for (OrderContractStatusGroup mO : list) {
                        if (a < list.size()) {
                            mO.setCheck(true);
                            id_group = mO.getOrderContractStatusGroupId();
                            getContractStatus(mO.getOrderContractStatusGroupId());
                            Log.d("getOrderContractStatusGroupId", mO.getOrderContractStatusGroupId() + "");
                            a = list.size();
                        } else mO.setCheck(false);
                        listOrder.add(mO);

//                        adapter_salesprocess_sort adapter = new adapter_salesprocess_sort(OrdersActivity.this, listOrder, OrdersActivity.this);
//                        lvSalesProcess.setAdapter(adapter);
                    }
//                    for(OrderContractStatus status:lvByGroup){
//
//                        if(status.getOrderContractStatusTypeId() ==1){
//                            lvSort.add(status);
//                        }
//                        else {
//                            lvSort1.add(status);
//                        }
//                    }
//                    lvAll.addAll(lvSort);
//                    lvAll.addAll(lvSort1);

                }
                // dialog.show();
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<OrderContractStatusGroup>>> call, Throwable t) {

            }
        });
    }

    public void getContractStatusGroup() {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        dialog = new Dialog(this, R.style.Dialog_No_Border);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.select_salesprocess);
        dialog.setCancelable(false);
        final RecyclerView lvSalesProcess = (RecyclerView) dialog.findViewById(R.id.lvSalesProcess);
        final Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvSalesProcess.setHasFixedSize(true);
        lvSalesProcess.setLayoutManager(manager);
        lvSalesProcess.setLayoutManager(manager);
        if (listOrder != null && listOrder.size() > 0) {
            adapter_salesprocess_sort adapter = new adapter_salesprocess_sort(OrdersActivity.this, listOrder, OrdersActivity.this);
            lvSalesProcess.setAdapter(adapter);
        } else lvSalesProcess.setAdapter(null);
        dialog.show();
//        Call<MAPIResponse<List<OrderContractStatusGroup>>> call = api.get_contract_status_group(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), preferences.getStringValue(Constants.TOKEN, ""));
//        call.enqueue(new Callback<MAPIResponse<List<OrderContractStatusGroup>>>() {
//            @Override
//            public void onResponse(Call<MAPIResponse<List<OrderContractStatusGroup>>> call, Response<MAPIResponse<List<OrderContractStatusGroup>>> response) {
//                List<OrderContractStatusGroup> list = response.body().getResult();
//                TokenUtils.checkToken(OrdersActivity.this, response.body().getErrors());
//                LogUtils.api("", call, response);
//                box.hideAll();
//                if (listOrder != null && listOrder.size() > 0) {
//                    adapter_salesprocess_sort adapter = new adapter_salesprocess_sort(OrdersActivity.this, listOrder, OrdersActivity.this);
//                    lvSalesProcess.setAdapter(adapter);
//                } else lvSalesProcess.setAdapter(null);
//                dialog.show();
//            }
//
//            @Override
//            public void onFailure(Call<MAPIResponse<List<OrderContractStatusGroup>>> call, Throwable t) {
//
//            }
//        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (load_order) {
//            MRequestBody mRequestBody = new MRequestBody();
//            List<MId> mIds = new ArrayList<>();
//            mIds.add(new MId(1));
//            mIds.add(new MId(2));
//            mIds.add(new MId(4));
//            mIds.add(new MId(6));
//            mRequestBody.setIds(mIds);
//
//
//            GetRetrofit().create(ServiceAPI.class)
//                    .getOrderByPartner(preferences.getStringValue(Constants.TOKEN, "")
//                            , preferences.getIntValue(Constants.USER_ID, 0)
//                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
//                            , mRequestBody
//                    )
//                    .enqueue(this);
//            setProgressBarIndeterminateVisibility(true);
//            setProgressBarVisibility(true);
//            box.showLoadingLayout();
//
//            LogUtils.d(TAG, "getUserActivities ", "start");
            getContractStatusGroupDefaut();
            load_order = false;
        } else getContractStatus(id_group);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_orders, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:
                Intent intent = new Intent(mContext, OrderActivity.class).putExtra("user_id", preferences.getIntValue(Constants.USER_ID, 0)).putExtra("client_name",preferences.getStringValue(Constants.USER_NAME,""));
                startActivity(intent);

                return true;

            case R.id.user:
                startActivityForResult(new Intent(mContext, UsersActivity.class), Constants.RESULT_USERS);
                load_order = false;
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.groupStatus:
                getContractStatusGroup();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MOrder>>> call, Response<MAPIResponse<List<MOrder>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext, response.body().getErrors());
        mOrders = response.body().getResult();
        list = new ArrayList<>();
        list.addAll(mOrders);
        Load(list);
    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MOrder>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibClose:
                mDrawerLayout.closeDrawers();
                break;
            case R.id.ibOpen:
                mDrawerLayout.openDrawer(mDrawerList);
                break;
            case R.id.textView0:
                type = 0;
                LoadData(list);
                mDrawerLayout.closeDrawers();

                break;
            case R.id.textView1:
                type = 1;
                list2 = new ArrayList<>();
                for (MOrder mOrder : list) {
                    if (mOrder.getOrder_status_id() == type) {
                        list2.add(mOrder);
                    }
                }
                LoadData(list2);
                mDrawerLayout.closeDrawers();

                break;
            case R.id.textView2:
                type = 2;
                list2 = new ArrayList<>();
                for (MOrder mOrder : list) {
                    if (mOrder.getOrder_status_id() == type) {
                        list2.add(mOrder);
                    }
                }
                LoadData(list2);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.textView3:
                type = 4;
                list2 = new ArrayList<>();
                for (MOrder mOrder : list) {
                    if (mOrder.getOrder_status_id() == type) {
                        list2.add(mOrder);
                    }
                }
                LoadData(list2);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.textView4:
                type = 6;
                list2 = new ArrayList<>();
                for (MOrder mOrder : list) {
                    if (mOrder.getOrder_status_id() == type) {
                        list2.add(mOrder);
                    }
                }
                LoadData(list2);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.textView5:
                sort(list);
                mDrawerLayout.closeDrawers();
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (resultCode == Constants.RESULT_USERS) {
            mIds = new ArrayList<>();
            mIds = (List<MId>) data.getSerializableExtra("mIds");
            if (mIds != null && mIds.size() > 0) {
                list = new ArrayList<>();
                for (MOrder mTracking : mOrders) {
                    for (MId id : mIds) {
                        if (mTracking.getUser_id() == id.getId()) {
                            list.add(mTracking);
                        }
                    }
                }
                LoadData(list);
                load_order = false;
            }

        }
    }

    private void LoadData(List<MOrder> list) {
        double order_status_0 = 0;
        double order_status_1 = 0;
        double order_status_2 = 0;
        double order_status_4 = 0;
        double order_status_6 = 0;
        double order_status_num_0 = 0;
        double order_status_num_1 = 0;
        double order_status_num_2 = 0;
        double order_status_num_4 = 0;
        double order_status_num_6 = 0;
        for (MOrder o : this.list) {
            order_status_0 += o.getAmount_payment();
            order_status_num_0++;
            switch (o.getOrder_status_id()) {
                case 1:
                    order_status_1 += o.getAmount_payment();
                    order_status_num_1++;
                    break;
                case 2:
                    order_status_2 += o.getAmount_payment();
                    order_status_num_2++;
                    break;
                case 4:
                    order_status_4 += o.getAmount_payment();
                    order_status_num_4++;
                    break;
                case 6:
                    order_status_6 += o.getAmount_payment();
                    order_status_num_6++;
                    break;
                default:
                    break;
            }
        }
        textView0.setText(getString(R.string.all) + " (" + Utils.formatCurrency(order_status_num_0) + ") - " + Utils.formatCurrency(order_status_0));
        textView1.setText(preferences.getStringValue(Constants.ORDER_STATUS_1, "") + " (" + Utils.formatCurrency(order_status_num_1) + ") - " + Utils.formatCurrency(order_status_1));
        textView2.setText(preferences.getStringValue(Constants.ORDER_STATUS_2, "") + " (" + Utils.formatCurrency(order_status_num_2) + ") - " + Utils.formatCurrency(order_status_2));
        textView3.setText(preferences.getStringValue(Constants.ORDER_STATUS_4, "") + " (" + Utils.formatCurrency(order_status_num_4) + ") - " + Utils.formatCurrency(order_status_4));
        textView4.setText(preferences.getStringValue(Constants.ORDER_STATUS_6, "") + " (" + Utils.formatCurrency(order_status_num_6) + ") - " + Utils.formatCurrency(order_status_6));

        activityAdapter = new OrdersAdapter(mContext, list);
        rvActivities.setAdapter(activityAdapter);
        activityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(mContext, OrderViewActivity.class);
        intent.putExtra("mOrder", activityAdapter.getItem(position));
        startActivity(intent);

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void click(int idGroup, String name, List<OrderContractStatus> lvByGroup) {
        //  Toast.makeText(mContext, idGroup + " " + name, Toast.LENGTH_SHORT).show();
        id_group = idGroup;
        getContractStatus(idGroup);
        dialog.cancel();
    }

    @Override
    public void Click(int idGroup) {
        list2 = new ArrayList<>();
        for (MOrder mOrder : list) {
            if (mOrder.getOrder_contract_status_id() == idGroup) {
                list2.add(mOrder);
            }
        }
        sort(list2);
        mDrawerLayout.closeDrawers();
    }

    public void sort(List<MOrder> list) {
        activityAdapter = new OrdersAdapter(mContext, list);
        rvActivities.setAdapter(activityAdapter);
        activityAdapter.notifyDataSetChanged();
    }

    public void Load(List<MOrder> list) {
        List<OrderContractStatus> lv = new ArrayList<>();
        List<MOrder> lv_status = new ArrayList<>();
        double money = 0;
        double money_sort = 0;
        OrderContractStatus order;
        int i = 0;


        for (OrderContractStatus status : lvByGroup) {
            order = new OrderContractStatus();
            int ii = 0;
            money_sort = 0;
            for (MOrder m : list) {

                if (status.getOrderContractStatusId() == m.getOrder_contract_status_id()) {//m.getOrder_contract_status_id()
                    ii++;
                    money_sort = money_sort + m.getAmount_payment();
                    order.setNum(ii);
                    order.setMoney(money_sort);

                }
            }
            order.setOrderContractStatusTypeId(status.getOrderContractStatusTypeId());
            order.setOrderContractStatusId(status.getOrderContractStatusId());
            order.setOrderContractStatusName(status.getOrderContractStatusName());
            lv.add(order);
        }
        final List<OrderContractStatus> lvSort= new ArrayList<>();
        final List<OrderContractStatus> lvSort1= new ArrayList<>();
        final List<OrderContractStatus> lvAll= new ArrayList<>();
        for(OrderContractStatus status:lv){
            if(status.getOrderContractStatusTypeId() ==1){
                lvSort.add(status);
            }
            else {
                lvSort1.add(status);
            }

        }
        lvAll.addAll(lvSort);
        lvAll.addAll(lvSort1);
        for (MOrder m : list) {
            for (OrderContractStatus status : lvByGroup) {
                if (status.getOrderContractStatusId() == m.getOrder_contract_status_id()) {
                    m.setOrder_contract_status_name(status.getOrderContractStatusName());
                }
            }
            lv_status.add(m);
            money = money + m.getAmount_payment();
            Log.d("tien", i + 1 + " " + Utils.formatCurrency(money));
            i++;
        }
        textView5.setText(getString(R.string.all) + " (" + Utils.formatCurrency(i) + ")   " + Utils.formatCurrency(money));

        adapter_salesstatus_sort adapter = new adapter_salesstatus_sort(OrdersActivity.this, lvAll, OrdersActivity.this);
        lvStatus.setAdapter(adapter);


        activityAdapter = new OrdersAdapter(mContext, lv_status);
        rvActivities.setAdapter(activityAdapter);
        activityAdapter.notifyDataSetChanged();


    }
}
