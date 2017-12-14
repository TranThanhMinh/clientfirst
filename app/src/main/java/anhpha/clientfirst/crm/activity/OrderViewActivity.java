package anhpha.clientfirst.crm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ActivityAdapter;
import anhpha.clientfirst.crm.adapter.OrderViewAdapter;
import anhpha.clientfirst.crm.adapter.adapterOrderView;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.fab.FloatingActionButton;
import anhpha.clientfirst.crm.fab.FloatingActionMenu;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.Expend;
import anhpha.clientfirst.crm.model.Focus;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MActivity;
import anhpha.clientfirst.crm.model.MActivityItem;
import anhpha.clientfirst.crm.model.MCall;
import anhpha.clientfirst.crm.model.MCheckin;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MClientLabel;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.model.MEmail;
import anhpha.clientfirst.crm.model.MEvent;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.MRequestBody;
import anhpha.clientfirst.crm.model.MWorkUser;
import anhpha.clientfirst.crm.model.OrderContractStatus;
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

public class OrderViewActivity extends BaseAppCompatActivity implements View.OnClickListener, RecyclerTouchListener.ClickListener {
    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.rvActivities1)
    RecyclerView rvActivities1;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.tvDate)
    TextView tvDate;

    @Bind(R.id.tvType)
    TextView tvType;
    @Bind(R.id.tvPrepay)
    TextView tvPrepay;
    @Bind(R.id.textView21)
    TextView textView21;
    @Bind(R.id.textView22)
    TextView textView22;
    @Bind(R.id.textView23)
    TextView textView23;
    @Bind(R.id.textView26)
    TextView textView26;
    @Bind(R.id.textView27)
    TextView textView27;

    @Bind(R.id.note)
    EditText note;

    @Bind(R.id.tvOrderCodeParent)
    TextView tvOrderCodeParent;
    @Bind(R.id.btDone)
    Button btDone;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    @Bind(R.id.etAddress)
    EditText etAddress;
    @Bind(R.id.etOrderCode)
    EditText etOrderCode;

    @Bind(R.id.etStatus)
    EditText etStatus;

    @Bind(R.id.etCustomer)
    EditText etCustomer;

    @Bind(R.id.etSuccessRate)
    EditText etSuccessRate;

    @Bind(R.id.etSalesProcess)
    EditText etSalesProcess;
    @Bind(R.id.etOrderName)
    EditText etOrderName;
    @Bind(R.id.etStaffincharge)
    EditText etStaffincharge;

    @Bind(R.id.etStaffSupport)
    EditText etStaffSupport;


    @Bind(R.id.etContact)
    EditText etContact;
    MOrder mOrder = new MOrder();
    List<MContract> MContracts = new ArrayList<>();
    List<MContract> MContractsOld = new ArrayList<>();
    List<MContract> MContractsNew = new ArrayList<>();
    adapterOrderView orderAdapter;
    Preferences preferences;
    boolean is_hide_menu = false;
    boolean isHide = false;
    @Bind(R.id.button1)
    RadioButton button1;
    @Bind(R.id.button2)
    RadioButton button2;
    @Bind(R.id.layout_client)
    LinearLayout layout_client;
    @Bind(R.id.layout_client2)
    RelativeLayout layout_client2;

    @Bind(R.id.line1)
    LinearLayout line1;
    @Bind(R.id.line2)
    LinearLayout line2;
    @Bind(R.id.line3)
    LinearLayout line3;
    @Bind(R.id.line4)
    LinearLayout line4;
    @Bind(R.id.line5)
    LinearLayout line5;
    @Bind(R.id.line6)
    LinearLayout line6;
    @Bind(R.id.line7)
    LinearLayout line7;

    @Bind(R.id.imageButton1)
    ImageButton imageButton1;
    @Bind(R.id.imageButton2)
    ImageButton imageButton2;
    @Bind(R.id.imageButton3)
    ImageButton imageButton3;
    @Bind(R.id.imageButton4)
    ImageButton imageButton4;
    @Bind(R.id.imageButton5)
    ImageButton imageButton5;
    @Bind(R.id.imageButton6)
    ImageButton imageButton6;
    @Bind(R.id.imageButton7)
    ImageButton imageButton7;
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
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.textView7)
    TextView textView7;
    @Bind(R.id.tvAmountExpend)
    TextView tvAmountExpend;
    @Bind(R.id.tvAmountDebt_number)
    TextView tvAmountDebt_number;
    @Bind(R.id.tvAmountExpend_number)
    TextView tvAmountExpend_number;
    @Bind(R.id.tvAmountDebt)
    TextView tvAmountDebt;

    @Bind(R.id.view_label)
    GridLayout view_label;
    @Bind(R.id.menu_item3)
    FloatingActionButton menu_item3;
    @Bind(R.id.menu_item4)
    FloatingActionButton menu_item4;
    @Bind(R.id.menu_item5)
    FloatingActionButton menu_item5;
    @Bind(R.id.menu_item6)
    FloatingActionButton menu_item6;
    @Bind(R.id.menu_item7)
    FloatingActionButton menu_item7;
    @Bind(R.id.menu_item8)
    FloatingActionButton menu_item8;
    @Bind(R.id.menu_item9)
    FloatingActionButton menu_item9;
    @Bind(R.id.menu)
    FloatingActionMenu menu;
    int type = 0;
    private int idGroup = 0, idByGroup = 0, Rate = 0;
    int object_id;
    String fromDate, toDate;
    ActivityAdapter activityAdapter;
    List<MActivityItem> mActivityItems = new ArrayList<>();
    List<MActivityItem> mActivityItems1 = new ArrayList<>();
    List<MId> mIds = new ArrayList<>();
    MActivity mActivity = new MActivity();
    private ImageView imCity;
    int status =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_order_view);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_order);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        imCity= (ImageView) findViewById(R.id.imCity);
        imCity.setVisibility(View.VISIBLE);
        imCity.setImageResource(R.mipmap.ic_crm_29);


// State 1 -on



        LinearLayoutManager manager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setHasFixedSize(true);
        rvActivities.setLayoutManager(manager1);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities1.setHasFixedSize(true);
        rvActivities1.setLayoutManager(manager);
        rvActivities1.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities1, this));
        fromDate = "01/01/1992";
        toDate = "01/01/2099";
        mOrder = (MOrder) getIntent().getSerializableExtra("mOrder");
        if (mOrder == null) {
            mOrder = new MOrder();
        } else {
            // textView22.setText(Utils.formatCurrency(mOrder.getDiscount_percent()));
        }
        button1.setChecked(true);
        button1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                layout_client2.setVisibility(View.VISIBLE);
                layout_client.setVisibility(View.GONE);
                isHide = true;
                invalidateOptionsMenu();
            }
        });
        button2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                layout_client.setVisibility(View.VISIBLE);
                layout_client2.setVisibility(View.GONE);
                isHide = false;
                invalidateOptionsMenu();
            }
        });
        if (mOrder.getOrder_sheet_id() == -1)
            is_hide_menu = true;
        etCustomer.setText(mOrder.getClient_name());

        line1.setOnClickListener(this);
        line2.setOnClickListener(this);
        line3.setOnClickListener(this);
        line4.setOnClickListener(this);
        line5.setOnClickListener(this);
        line6.setOnClickListener(this);
        line7.setOnClickListener(this);
        etContact.setOnClickListener(this);
        etStaffSupport.setOnClickListener(this);
        tvAmountExpend.setOnClickListener(this);
        tvAmountDebt.setOnClickListener(this);
        etStatus.setOnClickListener(this);
        etCustomer.setOnClickListener(this);

        switch (type) {

            case Constants.ACTIVITY_TYPE_ORDER:
                imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_11));
                break;
            case Constants.ACTIVITY_TYPE_CLIENT:
                imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_3));
                break;
            case Constants.ACTIVITY_TYPE_CHECKIN:
                imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_97));
                break;
            case Constants.ACTIVITY_TYPE_CALL:
                imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_6));
                break;
            case Constants.ACTIVITY_TYPE_WORK:
                imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_7));
                break;
            case Constants.ACTIVITY_TYPE_FOCUS:
                imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_15));
                break;
            case Constants.ACTIVITY_TYPE_EMAIL:
                imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_1));
                break;
            case Constants.ACTIVITY_TYPE_EXPEND:
                tvAmountExpend.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                break;
            default:
                break;
        }
        menu_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MActivityItem activityItem = new MActivityItem();
                mContext.startActivity(new Intent(mContext, AddDocumentActivity.class).putExtra("mOrder", mOrder).putExtra("mDocument", (Serializable) activityItem).putExtra("mAdd", 1));
                menu.close(true);
            }
        });
        menu_item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, Contract_debt.class).putExtra("mOrder", mOrder).putExtra("mAdd", 1));
                menu.close(true);
            }
        });
        menu_item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, CheckinContractActivity.class).putExtra("mOrder", mOrder));
                menu.close(true);
            }
        });
        menu_item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, CallContractActivity.class).putExtra("mOrder", mOrder));
                menu.close(true);
            }
        });
        menu_item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, EmailContractActivity.class).putExtra("mOrder", mOrder));
                menu.close(true);
            }
        });
        menu_item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MActivityItem activityItem = new MActivityItem();
                mContext.startActivity(new Intent(mContext, AddCommentActivity.class).putExtra("mOrder", mOrder).putExtra("Comment", (Serializable) activityItem).putExtra("mAdd", 1));
                menu.close(true);
            }
        });
        menu_item9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MActivityItem activityItem = new MActivityItem();
                mContext.startActivity(new Intent(mContext, AddCostsContractActivity.class).putExtra("mOrder", mOrder).putExtra("mExpend", (Serializable) activityItem).putExtra("mAdd", 1));
                menu.close(true);
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
    protected void onResume() {
        super.onResume();
        getContract();
        getMainActivity();

    }

    public void getMainActivity() {
        LogUtils.d(TAG, "getUserActivities ", "start");
        MRequestBody mRequestBody = new MRequestBody();
        mRequestBody.setFrom_date(fromDate);
        mRequestBody.setTo_date(toDate);
        mRequestBody.setUser_ids(mIds);
        ServiceAPI api = getConnect().create(ServiceAPI.class);
        Call<MAPIResponse<MActivity>> call = api.getUserActivities(preferences.getStringValue(Constants.TOKEN, "")
                , preferences.getIntValue(Constants.USER_ID, 0)
                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                , 0
                , mRequestBody, 2, mOrder.getOrder_contract_id()
        );
        call.enqueue(new Callback<MAPIResponse<MActivity>>() {
            @Override
            public void onResponse(Call<MAPIResponse<MActivity>> call, Response<MAPIResponse<MActivity>> response) {
                LogUtils.api(TAG, call, (response.body()));
                box.hideAll();
                TokenUtils.checkToken(mContext, response.body().getErrors());
                mActivity = response.body().getResult();
                mActivityItems = mActivity.getActivies();

                activityAdapter = new ActivityAdapter(OrderViewActivity.this, getActivityItems(mActivityItems));
                rvActivities1.setAdapter(activityAdapter);
                activityAdapter.notifyDataSetChanged();


                tvAmountExpend.setText("$ " + Utils.formatCurrency(mActivity.getExpend_amount()));
                tvAmountDebt.setText("$ " + Utils.formatCurrency(mActivity.getDebt_amount()));
                tvAmountExpend_number.setText(Utils.formatCurrency(mActivity.getExpend_count()) + " " + mContext.getResources().getString(R.string.expenditure));
                tvAmountDebt_number.setText(Utils.formatCurrency(mActivity.getDebt_count()) + " " + mContext.getResources().getString(R.string.get));


                textView1.setText(mActivity.getOrder_status_count() + "");
                textView2.setText(mActivity.getAdd_client_count() + "");
                textView3.setText(mActivity.getCheckin_count() + "");
                textView4.setText(mActivity.getCall_count() + "");
                textView5.setText(mActivity.getComment_count() + "");
                textView7.setText(mActivity.getEmail_count() + "");
                textView6.setText(mActivity.getDocument_count() + "");

                view_label.removeAllViews();
                int width = (Utils.getWidth(mContext) / 3);
                int height = (int) (width / 3.5);
//                for (MClientLabel mClientLabel : mClient.getLabels()) {
//                    Button valueTV = new Button(mContext);
//                    if (mClientLabel.getHex().isEmpty())
//                        valueTV.setBackgroundColor(Color.GRAY);
//                    else
//                        valueTV.setBackgroundColor(Color.parseColor(mClientLabel.getHex()));
//                    valueTV.setId((int) System.currentTimeMillis() + new Random().nextInt(255));
//                    valueTV.setTextColor(Color.WHITE);
//                    valueTV.setMaxLines(1);
//                    valueTV.setTransformationMethod(null);
//                    valueTV.setText(mClientLabel.getClient_label_name());
//                    valueTV.setLayoutParams(new android.app.ActionBar.LayoutParams(width, height));
//                    view_label.addView(valueTV);
//
//                }


            }

            @Override
            public void onFailure(Call<MAPIResponse<MActivity>> call, Throwable t) {
                LogUtils.d(TAG, "getUserActivities ", t.toString());
                box.hideAll();
            }
        });
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();
    }

    public List<MActivityItem> getActivityItems(List<MActivityItem> mActivityItems) {
        mActivityItems1 = new ArrayList<>();
        switch (type) {
            case 0:
                mActivityItems1.addAll(mActivityItems);
                break;
            case 1:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 1)
                        mActivityItems1.add(i);
                }
                break;
            case 2:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 15)
                        mActivityItems1.add(i);
                }
                break;
            case 3:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 3)
                        mActivityItems1.add(i);
                }
                break;
            case 9:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 9)
                        mActivityItems1.add(i);
                }
                break;
            case 5:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 5)
                        mActivityItems1.add(i);
                }
                break;
            case 6:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 6)
                        mActivityItems1.add(i);
                }
                break;
            case 7:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 7)
                        mActivityItems1.add(i);
                }
                break;
            case 10:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 10)
                        mActivityItems1.add(i);
                }
                break;
            case 11:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 11)
                        mActivityItems1.add(i);
                }

                break;
            case 12:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 12)
                        mActivityItems1.add(i);
                }

                break;
            case 13:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 13)
                        mActivityItems1.add(i);
                }

                break;
            case 14:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 14)
                        mActivityItems1.add(i);
                }

                break;
            default:
                break;
        }
        return mActivityItems1;
    }

    public void getContract() {
        ServiceAPI api = getConnect().create(ServiceAPI.class);
        Call<MAPIResponse<MOrder>> call = api.getOrder(preferences.getStringValue(Constants.TOKEN, "")
                , preferences.getIntValue(Constants.USER_ID, 0)
                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                , mOrder.getOrder_contract_id()
        );
        call.enqueue(new Callback<MAPIResponse<MOrder>>() {
            @Override
            public void onResponse(Call<MAPIResponse<MOrder>> call, Response<MAPIResponse<MOrder>> response) {
                LogUtils.api(TAG, call, (response.body()));
//                box.hideAll();
                TokenUtils.checkToken(mContext, response.body().getErrors());
                mOrder = response.body().getResult();
                Log.d("Client name", mOrder.getClient_name());
                Log.d("Client id", mOrder.getClient_id() + "");
                orderAdapter = new adapterOrderView(mContext, mOrder.getOrder_detail_contracts());
                rvActivities.setAdapter(orderAdapter);
//                rvActivities.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Utils.setListViewHeightBasedOnChildren(rvActivities);
//                    }
//                }, 500);
               // Utils.setListViewHeightBasedOnChildren(rvActivities);
                if (mOrder.getAddress() != null && !mOrder.getAddress().isEmpty()) {
                    etAddress.setVisibility(View.VISIBLE);
                    etAddress.setText(mOrder.getAddress());

                }
                if (mOrder.getOrder_type_id() == 2) {
                    btDone.setVisibility(View.VISIBLE);
                    btDone.setOnClickListener(OrderViewActivity.this);
                }
                tvClientName.setText(mOrder.getOrder_contract_name());
                // etOrderName.setText(mOrder.getOrder_contract_name());
                status =mOrder.getOrder_contract_status_type_id();
                etSalesProcess.setText(mOrder.getOrder_contract_status_group_name());
                etStatus.setText(mOrder.getOrder_contract_status_name());
                etStaffincharge.setText(mOrder.getUser_manager_contract_name());
                etStaffSupport.setText(mOrder.getNumber_user() + " " + mContext.getResources().getString(R.string.title_activity_users));
                idGroup = mOrder.getOrder_contract_status_group_id();
                idByGroup = mOrder.getOrder_contract_status_id();
                object_id = mOrder.getUser_id();

                etSuccessRate.setText(mOrder.getPercent_done() + "%");
                etCustomer.setText(mOrder.getClient_name());
                note.setText(mOrder.getNote());
                //mOrder.setClient_name(tvClientName.getText().toString());
                tvDate.setText(mOrder.getDelivery_date());

                if(mOrder.getOrder_contract_code().isEmpty())
                    etOrderCode.setVisibility(View.GONE);
                else  etOrderCode.setText(mOrder.getOrder_contract_code());

                tvOrderCodeParent.setText(mOrder.getOrder_contract_code_parent());
                etContact.setText(mOrder.getNumber_client() + " " + getResources().getString(R.string.contact1));
                switch (mOrder.getOrder_status_id()) {
                    case 1:
                        tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_1, ""));
                        tvType.setTextColor(mContext.getResources().getColor(R.color.order_status_1));
                        break;
                    case 2:
                        tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_2, ""));
                        tvType.setTextColor(mContext.getResources().getColor(R.color.order_status_4));
                        break;


                    case 3:
                        tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_3, ""));
                        tvType.setTextColor(mContext.getResources().getColor(R.color.dark));
                        break;
                    case 4:
                        tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_4, ""));
                        tvType.setTextColor(mContext.getResources().getColor(R.color.order_status_4));
                        break;
                    case 5:
                        tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_5, ""));
                        tvType.setTextColor(mContext.getResources().getColor(R.color.dark));
                        break;
                    case 6:
                        tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_6, ""));
                        tvType.setTextColor(mContext.getResources().getColor(R.color.order_status_6));
                        break;
                    case 7:
                        tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_7, ""));
                        tvType.setTextColor(mContext.getResources().getColor(R.color.order_status_7));
                        break;
                    case 8:
                        tvType.setText(getString(R.string.delete));
                        tvType.setTextColor(mContext.getResources().getColor(R.color.dark));
                        break;
                    default:
                        break;
                }
                double totalAmountOrder = 0;
                double totalAmountContract = 0;
                double vat = 0;
                double discountContract = 0;
                double discountOrder = 0;
                double prePay = mOrder.getDiscount_percent();

                for (MContract pdd : mOrder.getOrder_detail_contracts()) {
                    MContract d = Utils.getPriceContract(pdd, mContext);
                    Log.d("getDiscount_percent", mOrder.getDiscount_percent() + "");
                    prePay = mOrder.getDiscount_contract_price();
                    totalAmountContract += pdd.getPrice() * pdd.getNumber() - pdd.getDiscount_price();
                }
                totalAmountOrder += totalAmountContract;
                tvPrepay.setText(Utils.formatCurrency((prePay)));
                textView21.setText(Utils.formatCurrency(totalAmountContract));
                textView22.setText(Utils.formatCurrency((prePay)));
                textView23.setText(Utils.formatCurrency(totalAmountContract - prePay));
                textView26.setText(Utils.formatCurrency(mOrder.getAmount_paymented()));
                textView27.setText(Utils.formatCurrency(totalAmountContract - prePay - mOrder.getAmount_paymented()));

            }

            @Override
            public void onFailure(Call<MAPIResponse<MOrder>> call, Throwable t) {
                LogUtils.d(TAG, "getUserActivities ", t.toString());
                box.hideAll();
            }
        });
        box.showLoadingLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_order_view, menu);
//        if (!is_hide_menu) {
//            for (int i = 0; i < menu.size(); i++) {
//                if (menu.getItem(i).getItemId() == R.id.edit)
//                    menu.getItem(i).setVisible(true);
//            }
//
//            for (int i = 0; i < menu.size(); i++) {
//                if (menu.getItem(i).getItemId() == R.id.cancel)
//                    menu.getItem(i).setVisible(true);
//            }
////            for (int i = 0; i < menu.size(); i++) {
////                if (menu.getItem(i).getItemId() == R.id.send)
////                    menu.getItem(i).setVisible(true);
////            }
////            for (int i = 0; i < menu.size(); i++) {
////                if (menu.getItem(i).getItemId() == R.id.history)
////                    menu.getItem(i).setVisible(true);
////            }
//        }
//        if (!preferences.getBooleanValue(Constants.permission_cancel_order, false)) {
//            for (int i = 0; i < menu.size(); i++) {
//                if (menu.getItem(i).getItemId() == R.id.cancel)
//                    menu.getItem(i).setVisible(false);
//            }
//        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_calenda, menu);
//        if (isHide) {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.edit)
                    menu.getItem(i).setVisible(true);
                if (menu.getItem(i).getItemId() == R.id.calendar)
                    menu.getItem(i).setVisible(false);
            }
//        } else {
//            for (int i = 0; i < menu.size(); i++) {
//                if (menu.getItem(i).getItemId() == R.id.edit )
//                    menu.getItem(i).setVisible(true);
//                if (menu.getItem(i).getItemId() == R.id.calendar)
//                    menu.getItem(i).setVisible(false);
//            }
//        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:
                startActivityForResult(new Intent(mContext, CalendarClientActivity.class), Constants.RESULT_CALENDAR);
                return true;
            case R.id.edit:
                Intent intent = new Intent(mContext, OrderActivity.class);
                intent.putExtra("mOrder", mOrder);
                intent.putExtra("user_id", 0);
                intent.putExtra("name", tvClientName.getText().toString());
                startActivity(intent);

                return true;

//            case R.id.send:
//                    saveImage();
//                return true;
//            case R.id.print:
//
//                return true;
//            case R.id.history:
//                Intent in = new Intent(mContext,History_orders_activity.class).putExtra("mOrder",mOrder);
//
//                startActivity(in);
//                return true;
            case R.id.cancel:
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderViewActivity.this);
                builder.setTitle(getResources().getString(R.string.alert));
                builder.setCancelable(true);
                builder.setMessage(getResources().getString(R.string.delete_order_message));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        box.showLoadingLayout();
                        GetRetrofit().create(ServiceAPI.class)
                                .setStatusOrder(preferences.getStringValue(Constants.TOKEN, "")
                                        , preferences.getIntValue(Constants.USER_ID, 0)
                                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                        , mOrder.getOrder_contract_id()
                                        , 3
                                )
                                .enqueue(new Callback<MAPIResponse<MId>>() {
                                    @Override
                                    public void onResponse(Call<MAPIResponse<MId>> call, Response<MAPIResponse<MId>> response) {
                                        LogUtils.api(TAG, call, (response.body()));
                                        box.hideAll();
                                        TokenUtils.checkToken(mContext, response.body().getErrors());
                                        if (!response.body().isHasErrors()) {
                                            finish();
                                        } else {
                                            Utils.showError(coordinatorLayout, R.string.delete_order_fail);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MAPIResponse<MId>> call, Throwable t) {
                                        LogUtils.d(TAG, "getUserActivities ", t.toString());
                                        box.hideAll();
                                        Utils.showError(coordinatorLayout, R.string.delete_order_fail);
                                    }
                                });

                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.no), null);
                builder.show();

                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RESULT_CALENDAR) {
            toDate = data.getStringExtra("toDate");
            fromDate = data.getStringExtra("fromDate");
            //  tvDate.setText(data.getStringExtra("tvDate"));

        }
    }

    @Override
    public void onClick(View view) {
        imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_16));
        imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_17));
        imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_96));
        imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_18));
        imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_comment0));
        imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ic_document0));
        imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_20));
        tvAmountExpend.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tvAmountDebt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        switch (view.getId()) {
            case R.id.line1:
                if (type != Constants.ACTIVITY_TYPE_ORDER) type = Constants.ACTIVITY_TYPE_ORDER;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_ORDER)
                    imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_16));
                else imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_11));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line2:
                if (type != Constants.ACTIVITY_TYPE_CLIENT) type = Constants.ACTIVITY_TYPE_CLIENT;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_CLIENT)
                    imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_17));
                else imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_3));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line3:
                if (type != Constants.ACTIVITY_TYPE_CHECKIN) type = Constants.ACTIVITY_TYPE_CHECKIN;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_CHECKIN)
                    imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_96));
                else imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_97));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line4:
                if (type != Constants.ACTIVITY_TYPE_CALL) type = Constants.ACTIVITY_TYPE_CALL;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_CALL)
                    imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_18));
                else imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_6));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line5:
                if (type != Constants.ACTIVITY_TYPE_COMMENT) type = Constants.ACTIVITY_TYPE_COMMENT;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_COMMENT)
                    imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_comment0));
                else
                    imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_comment1));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line6:
                if (type != Constants.ACTIVITY_TYPE_DOCUMENT)
                    type = Constants.ACTIVITY_TYPE_DOCUMENT;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_DOCUMENT)
                    imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ic_document0));
                else
                    imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ic_document1));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line7:
                if (type != Constants.ACTIVITY_TYPE_EMAIL) type = Constants.ACTIVITY_TYPE_EMAIL;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_EMAIL)
                    imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_20));
                else imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_1));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.tvAmountExpend:
                if (type != Constants.ACTIVITY_TYPE_EXPEND) type = Constants.ACTIVITY_TYPE_EXPEND;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_EXPEND)
                    tvAmountExpend.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                else tvAmountExpend.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.tvAmountDebt:
                if (type != Constants.ACTIVITY_TYPE_DEBT) type = Constants.ACTIVITY_TYPE_DEBT;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_DEBT)
                    tvAmountDebt.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                else tvAmountDebt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.etContact:
                startActivityForResult(new Intent(OrderViewActivity.this, PersonnelContractActivity.class).putExtra("mOrder", mOrder), 11);
                break;
            case R.id.etStaffSupport:
                mContext.startActivity(new Intent(mContext, ActivityClientSupport_Contract.class).putExtra("mOrder", mOrder));
                break;
            case R.id.etStatus:
                if(status == 1) {
                    List<OrderContractStatus> orderStatus = new ArrayList<>();
                    mContext.startActivity(new Intent(mContext, ActivitySalesStatus.class).putExtra("idGroup", idGroup).putExtra("lvByGroup", (Serializable) orderStatus).putExtra("SalesProcess", mOrder.getClient_name()).putExtra("contract_order_id", mOrder.getOrder_contract_id()));
                }else Toast.makeText(mContext,getString(R.string.srtChanged)+" "+mOrder.getOrder_contract_status_name(), Toast.LENGTH_SHORT).show();

                break;
            case R.id.etCustomer:
                MClient mClient = new MClient();
                mClient.setClient_id(mOrder.getClient_id());
                mClient.setClient_name(mOrder.getClient_name());
                Intent intent = new Intent(mContext, ClientActivity.class).putExtra("mClient",mClient);
                startActivity(intent);
                break;
            case R.id.btDone:

                GetRetrofit().create(ServiceAPI.class)
                        .setStatusOrder(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                , mOrder.getOrder_contract_id()
                                , 7
                        )
                        .enqueue(new Callback<MAPIResponse<MId>>() {
                            @Override
                            public void onResponse(Call<MAPIResponse<MId>> call, Response<MAPIResponse<MId>> response) {
                                LogUtils.api(TAG, call, (response.body()));
                                box.hideAll();
                                TokenUtils.checkToken(mContext, response.body().getErrors());
                                if (!response.body().isHasErrors()) {
                                    Utils.showDialogSuccess(mContext, R.string.done_order_done);
                                } else {
                                    Utils.showError(coordinatorLayout, R.string.done_order_fail);
                                }
                            }

                            @Override
                            public void onFailure(Call<MAPIResponse<MId>> call, Throwable t) {
                                LogUtils.d(TAG, "getUserActivities ", t.toString());
                                box.hideAll();
                                Utils.showError(coordinatorLayout, R.string.done_order_fail);
                            }
                        });


                break;
            default:
                break;
        }
    }

    public static Bitmap getBitmapFromView(ScrollView view) {
        //Define a bitmap with the same size as the view
        ScrollView vv = view;
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;

//
//        view.measure(View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED);
//        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getMeasuredHeight(),
//                Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        view.layout(0, 0, view.getWidth(), view.getMeasuredHeight());
//        view.draw(canvas);
//        return bitmap;
    }

    void saveImage() {
        String FILE = Environment.getExternalStorageDirectory() + "";

        File myDir = new File(FILE);
        myDir.mkdirs();
        String fname = "Receipt.jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();

        try {
            FileOutputStream out = new FileOutputStream(file);

            getBitmapFromView(scrollView).compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            composeEmail(getString(R.string.order), FILE + "/Receipt.jpg");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void composeEmail(final String subject, final String flie) {
//        String[] strarray = new String[1];
//        List<String> value = new ArrayList<>();
//        value.add("buvianthuong.it@gmail.com");
//        value.toArray(strarray);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, strarray);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + flie));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view, int position) {
        MActivityItem activityItem = activityAdapter.getItem(position);
        mOrder.setClient_name(mActivityItems.get(position).getClient_name());
        mOrder.setAddress(mActivityItems.get(position).getAddress());
        switch (activityItem.getActivity_type()) {

            case Constants.ACTIVITY_TYPE_ORDER:
                MOrder mOrder1 = new MOrder();
                mOrder.setClient_id(mOrder1.getClient_id());
                mOrder.setClient_name(mOrder1.getClient_name());
                mOrder.setAddress(mOrder1.getAddress());
                mOrder1.setOrder_sheet_id(-1);
                mOrder1.setOrder_contract_id(activityItem.getOrder_contract_id());
                mContext.startActivity(new Intent(mContext, OrderViewActivity.class).putExtra("mClient", mOrder).putExtra("mOrder", mOrder));
                break;
            case Constants.ACTIVITY_TYPE_WORK:
                MWorkUser mWorkUser = new MWorkUser();
                mWorkUser.setWork_user_id(activityItem.getWork_user_id());
                mContext.startActivity(new Intent(mContext, WorkActivity.class).putExtra("mClient", mOrder).putExtra("mWorkUser", mWorkUser));
                break;
            case Constants.ACTIVITY_TYPE_CHECKIN:
                MCheckin mCheckin = new MCheckin();
                mCheckin.setUser_checkin_id(activityItem.getMeeting_id());
                mCheckin.setContent_checkin(activityItem.getActivity_content());
                mCheckin.setDisplay_type(activityItem.getDisplay_type());
                mCheckin.setOrder_contract_id(activityItem.getOrder_contract_id());
                mCheckin.setOrder_contract_name(activityItem.getOrder_contract_name());
                if (activityItem.getType() == 1)
                    mContext.startActivity(new Intent(mContext, CheckinContractActivity.class).putExtra("mOrder", mOrder).putExtra("mCheckin", mCheckin));
                else {
                    MClient mClient = new MClient();
                    mClient.setClient_name(activityItem.getClient_name());
                    mClient.setAddress(activityItem.getAddress());
                    mClient.setClient_id(activityItem.getClient_id());
                    mContext.startActivity(new Intent(mContext, CheckinActivity.class).putExtra("mClient", mClient).putExtra("mCheckin", mCheckin));
                }
                break;
            case Constants.ACTIVITY_TYPE_CALL:
                MCall mCall = new MCall();
                mCall.setUser_call_id(activityItem.getUser_call_id());
                mCall.setContent_call(activityItem.getActivity_content());
                mCall.setDisplay_type(activityItem.getDisplay_type());
                mCall.setOrder_contract_id(activityItem.getOrder_contract_id());
                mCall.setOrder_contract_name(activityItem.getOrder_contract_name());
                if (activityItem.getType() == 1)
                    mContext.startActivity(new Intent(mContext, CallContractActivity.class).putExtra("mOrder", mOrder).putExtra("mCall", mCall));
                else {
                    MClient mClient = new MClient();
                    mClient.setClient_name(activityItem.getClient_name());
                    mClient.setAddress(activityItem.getAddress());
                    mClient.setClient_id(activityItem.getClient_id());
                    mContext.startActivity(new Intent(mContext, CallActivity.class).putExtra("mClient", mClient).putExtra("mCall", mCall));
                }
                break;
            case Constants.ACTIVITY_TYPE_EMAIL:
                MEmail mEmail = new MEmail();
                mEmail.setUser_email_id(activityItem.getUser_email_id());
                mEmail.setContent_email(activityItem.getActivity_content());
                mEmail.setDisplay_type(activityItem.getDisplay_type());
                mEmail.setOrder_contract_id(activityItem.getOrder_contract_id());
                mEmail.setOrder_contract_name(activityItem.getOrder_contract_name());
                if (activityItem.getType() == 1)
                    mContext.startActivity(new Intent(mContext, EmailContractActivity.class).putExtra("mOrder", mOrder).putExtra("mEmail", mEmail));
                else {
                    MClient mClient = new MClient();
                    mClient.setClient_name(activityItem.getClient_name());
                    mClient.setAddress(activityItem.getAddress());
                    mClient.setClient_id(activityItem.getClient_id());
                    mContext.startActivity(new Intent(mContext, EmailActivity.class).putExtra("mClient", mClient).putExtra("mEmail", mEmail));
                }
                break;
            case Constants.ACTIVITY_TYPE_EVENT:
                MEvent mEvent = new MEvent();
                mEvent.setEvent_id(activityItem.getEvent_id());
                mContext.startActivity(new Intent(mContext, EventClientActivity.class).putExtra("mClient", mOrder).putExtra("mEvent", mEvent).putExtra("ShowMenu", false));

                break;
            case Constants.ACTIVITY_TYPE_FOCUS:
                Focus mFocus = new Focus();
                mFocus.setClientFocusId(activityItem.getClient_focus_id());
                mContext.startActivity(new Intent(mContext, HistoryFocusActivity.class).putExtra("mClient", mOrder));
                break;
            case Constants.ACTIVITY_TYPE_EXPEND:
                Expend mExpend = new Expend();
                mExpend.setExpendId(activityItem.getExpend_id());
                Log.d("getExpend_id", activityItem.getExpend_id() + "");
                if (activityItem.getType() == 1)
                    mContext.startActivity(new Intent(mContext, AddCostsContractActivity.class).putExtra("mOrder", mOrder).putExtra("mExpend", activityItem).putExtra("mAdd", 0));
                else {
                    MClient mClient = new MClient();
                    mClient.setClient_name(activityItem.getClient_name());
                    mClient.setAddress(activityItem.getAddress());
                    mClient.setClient_id(activityItem.getClient_id());
                    mContext.startActivity(new Intent(mContext, AddCostsActivity.class).putExtra("mClient", mClient).putExtra("mExpend", activityItem).putExtra("mAdd", 0));
                }
                break;
            case Constants.ACTIVITY_TYPE_DEBT:
                mContext.startActivity(new Intent(mContext, Contract_debt.class).putExtra("mOrder", mOrder).putExtra("mDebt", activityItem).putExtra("mAdd", 0));
                break;
            case Constants.ACTIVITY_TYPE_DOCUMENT:
                mContext.startActivity(new Intent(mContext, AddDocumentActivity.class).putExtra("mOrder", mOrder).putExtra("Document", activityItem).putExtra("mAdd", 0));
                break;
            case Constants.ACTIVITY_TYPE_COMMENT:
                mContext.startActivity(new Intent(mContext, AddCommentActivity.class).putExtra("mOrder", mOrder).putExtra("Comment", activityItem).putExtra("mAdd", 0));
                break;
            default:
                break;
        }
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
