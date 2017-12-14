package anhpha.clientfirst.crm.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ActivityAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.CustomMapView;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.fab.FloatingActionButton;
import anhpha.clientfirst.crm.fab.FloatingActionMenu;
import anhpha.clientfirst.crm.model.Expend;
import anhpha.clientfirst.crm.model.Focus;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MActivity;
import anhpha.clientfirst.crm.model.MActivityItem;
import anhpha.clientfirst.crm.model.MCall;
import anhpha.clientfirst.crm.model.MCheckin;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MClientLabel;
import anhpha.clientfirst.crm.model.MEmail;
import anhpha.clientfirst.crm.model.MEvent;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.MRequestBody;
import anhpha.clientfirst.crm.model.MWorkUser;
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

public class ClientActivity extends BaseAppCompatActivity implements RecyclerTouchListener.ClickListener, CompoundButton.OnCheckedChangeListener, Callback<MAPIResponse<MActivity>>, View.OnClickListener {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
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
    @Bind(R.id.spBusinessType)
    TextView spBusinessType;
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

    @Bind(R.id.menu_item1)
    FloatingActionButton menu_item1;
    @Bind(R.id.menu_item2)
    FloatingActionButton menu_item2;
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

    @Bind(R.id.tvAmount)
    TextView tvAmount;
    @Bind(R.id.tvAmount_number)
    TextView tvAmount_number;
    //    @Bind(R.id.tvDate)
//    TextView tvDate;
    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.button1)
    RadioButton button1;
    @Bind(R.id.button2)
    RadioButton button2;
    @Bind(R.id.view)
    RelativeLayout view;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.tvAmountDebt)
    TextView tvAmountDebt;
    @Bind(R.id.tvAmountFinish)
    TextView tvAmountFinish;
    @Bind(R.id.tvAmountDebt_number)
    TextView tvAmountDebt_number;
    @Bind(R.id.tvAmountFinish_number)
    TextView tvAmountFinish_number;
    @Bind(R.id.etPassport)
    EditText etPassport;
    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.etCode)
    EditText etCode;
    @Bind(R.id.etCompany_partner)
    EditText etCompany_partner;
    @Bind(R.id.etCompany_Con)
    EditText etCompany_Con;
    @Bind(R.id.etClient_support)
    EditText etClient_support;
    @Bind(R.id.etContact_client)

    EditText etContact_client;
    @Bind(R.id.etBirthday)
    EditText etBirthday;
    @Bind(R.id.etSalesYear)
    EditText etSalesYear;
    @Bind(R.id.etDateEstablished)
    EditText etDateEstablished;
    @Bind(R.id.etTaxCode)
    EditText etTaxCode;
    @Bind(R.id.etAddress)
    EditText etAddress;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etWeb)
    EditText etWeb;
    @Bind(R.id.etContact)
    EditText etContact;
    @Bind(R.id.etNumberDependent)
    EditText etNumberDependent;
    @Bind(R.id.etIncome)
    EditText etIncome;
    @Bind(R.id.etDescription)
    EditText etDescription;
    @Bind(R.id.etNote)
    EditText etNote;
    @Bind(R.id.etGender)
    EditText etGender;
    @Bind(R.id.spGroup)
    TextView spGroup;
    @Bind(R.id.spType)
    TextView spType;
    @Bind(R.id.spArea)
    TextView spArea;
    @Bind(R.id.spStatus)
    TextView spStatus;
    @Bind(R.id.spMarital)
    TextView spMarital;
    @Bind(R.id.view_label)
    GridLayout view_label;
    @Bind(R.id.tvAmountExpend)
    TextView tvAmountExpend;
    @Bind(R.id.tvAmountExpend_number)
    TextView tvAmountExpend_number;
    @Bind(R.id.tvManager)
    TextView tvManager;
    @Bind(R.id.relManager)
    LinearLayout relManager;
    @Bind(R.id.imCity)
    ImageView imCity;
    @Bind(R.id.layout_marital)
    LinearLayout layout_marital;
    CustomMapView mapView;
    GoogleMap map;
    @Bind(R.id.view11)
    View view11;
    private int mount = 0;
    boolean isHide = true;
    boolean isLoadClient = false;
    MActivity mActivity = new MActivity();
    MActivityItem mActivityItem = new MActivityItem();
    MClient mClient = new MClient();
    List<MId> mIds = new ArrayList<>();
    List<MActivityItem> mActivityItems = new ArrayList<>();
    List<MActivityItem> mActivityItems1 = new ArrayList<>();
    String toDate;
    String fromDate;
    int type = 0;
    int click = 1;
    Preferences preferences;
    ActivityAdapter activityAdapter;
    Timer timer = new Timer();
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_client);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);
        imCity.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mClient = (MClient) getIntent().getSerializableExtra("mClient");
        mActivityItem = (MActivityItem) getIntent().getSerializableExtra("mActivityItem");
        mapView = (CustomMapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        button1.setChecked(true);
        button1.setOnCheckedChangeListener(this);
        button2.setOnCheckedChangeListener(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        // LinearLayoutManager mLayoutManager = new anhpha.fieldwork.dms.llm.LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, this));

        activityAdapter = new ActivityAdapter(ClientActivity.this, getActivityItems(mActivityItems));
        if (mClient != null) {
            tvClientName.setText(mClient.getClient_name());
//            if (mClient.getClient_structure_id() == 2) {
//                tvAddress.setText(mClient.getAddress());
//                imCity.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_40));
//            } else {
//                if(mClient.getPosition()!=null) {
//                    if(mClient.getParent_name()!=null){
//                        tvAddress.setText(mClient.getPosition() + " " + mClient.getParent_name());
//                    }else  tvAddress.setText(mClient.getPosition());
//                }else {
//                    if(mClient.getParent_name()!= null){
//                        tvAddress.setText(mClient.getParent_name());
//                    }else  tvAddress.setText("");
//                }
//                imCity.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_26));
//            }
//
//            // tvAddress
//            if (mClient.getAddress() != null && !mClient.getAddress().isEmpty() || mClient.getPosition() != null && mClient.getPosition().length() > 0 || mClient.getParent_name() != null && mClient.getParent_name().length() > 0) {
//                tvAddress.setVisibility(View.VISIBLE);
//            }else  tvAddress.setVisibility(View.GONE);
            findViewById(R.id.ibArrow).setVisibility(View.GONE);
        } else {
            mClient = new MClient();
        }

        if (mActivityItem != null) {
            mClient.setClient_id(mActivityItem.getClient_id());
            mClient.setClient_name(mActivityItem.getClient_name());
            mClient.setAddress(mActivityItem.getAddress());

            tvClientName.setText(mClient.getClient_name());
            tvAddress.setText(mClient.getAddress());
            if (mClient.getAddress() != null && !mClient.getAddress().isEmpty()) {
                tvAddress.setVisibility(View.VISIBLE);
            }
            findViewById(R.id.ibArrow).setVisibility(View.GONE);
            type = mActivityItem.getActivity_type();

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
        }

        fromDate = "01/01/1992";
        toDate = "01/01/2099";

        line1.setOnClickListener(this);
        line2.setOnClickListener(this);
        line3.setOnClickListener(this);
        line4.setOnClickListener(this);
        line5.setOnClickListener(this);
        line6.setOnClickListener(this);
        line7.setOnClickListener(this);

        etContact_client.setOnClickListener(this);
        etCompany_Con.setOnClickListener(this);
        etCode.setOnClickListener(this);
        etCompany_partner.setOnClickListener(this);
        tvAmountExpend.setOnClickListener(this);
        etClient_support.setOnClickListener(this);
        tvAmountDebt.setOnClickListener(this);
        tvAmount.setOnClickListener(this);
        tvAmountFinish.setOnClickListener(this);

        menu_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, OrderActivity.class).putExtra("mClient", mClient).putExtra("user_id", preferences.getIntValue(Constants.USER_ID, 0)).putExtra("client_name", preferences.getStringValue(Constants.USER_NAME, "")));
                menu.close(true);
            }
        });
        menu_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, WorkActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, CheckinActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, CallActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, EmailActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, EventsClientActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, LabelsActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, HistoryFocusActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivityItem = null;
                mContext.startActivity(new Intent(mContext, AddCostsActivity.class).putExtra("mClient", mClient).putExtra("mExpend", mActivityItem).putExtra("mAdd", 1));
                menu.close(true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_calenda, menu);
        if (isHide) {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.edit)
                    menu.getItem(i).setVisible(false);
                if (menu.getItem(i).getItemId() == R.id.calendar)
                    menu.getItem(i).setVisible(true);
            }
        } else {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.edit)
                    menu.getItem(i).setVisible(true);
                if (menu.getItem(i).getItemId() == R.id.calendar)
                    menu.getItem(i).setVisible(false);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:
                startActivityForResult(new Intent(mContext, CalendarClientActivity.class), Constants.RESULT_CALENDAR);
                return true;

            case R.id.edit:
                Intent intent = new Intent(mContext, EditClientActivity.class);
                intent.putExtra("mClient", mClient);
                if (mClient.getParent_id() > 0) {
                    intent.putExtra("Structure", 1);
                    intent.putExtra("add", 2);
                    startActivity(intent);
                } else {
                    intent.putExtra("Structure", 2);
                    intent.putExtra("add", 2);
                    startActivity(intent);
                    // startActivityForResult(new Intent(mContext, EditClientActivity.class).putExtra("mClient", mClient).putExtra("Structure", 2).putExtra("add", 2), Constants.RESULT_COMPANY);
                }

                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<MActivity>> call, Response<MAPIResponse<MActivity>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext, response.body().getErrors());
        mActivity = response.body().getResult();
        mActivityItems = mActivity.getActivies();

        activityAdapter = new ActivityAdapter(ClientActivity.this, getActivityItems(mActivityItems));
        rvActivities.setAdapter(activityAdapter);
        activityAdapter.notifyDataSetChanged();
        tvAmount.setText("$ " + Utils.formatCurrency(mActivity.getSales_amount()));
        tvAmountExpend.setText("$ " + Utils.formatCurrency(mActivity.getExpend_amount()));
        tvAmountFinish.setText("$ " + Utils.formatCurrency(mActivity.getFinish_amount()));
        tvAmountDebt.setText("$ " + Utils.formatCurrency(mActivity.getDebt_amount()));

        tvAmount_number.setText(Utils.formatCurrency(mActivity.getOrder_count()) + " " + mContext.getResources().getString(R.string.potential));
        tvAmountExpend_number.setText(Utils.formatCurrency(mActivity.getExpend_count()) + " " + mContext.getResources().getString(R.string.expenditure));
        tvAmountFinish_number.setText(Utils.formatCurrency(mActivity.getOrder_finish_count()) + " " + mContext.getResources().getString(R.string.complete));
        tvAmountDebt_number.setText(Utils.formatCurrency(mActivity.getDebt_count()) + " " + mContext.getResources().getString(R.string.get));

        textView1.setText(mActivity.getOrder_status_count() + "");
        textView2.setText(mActivity.getAdd_client_count() + "");
        textView3.setText(mActivity.getCheckin_count() + "");
        textView4.setText(mActivity.getCall_count() + "");
        textView5.setText(mActivity.getWork_count() + "");
        textView7.setText(mActivity.getEmail_count() + "");
        textView6.setText(mActivity.getClient_focus_count() + "");

        view_label.removeAllViews();
        int width = (Utils.getWidth(mContext) / 3);
        int height = (int) (width / 3.5);
        for (MClientLabel mClientLabel : mClient.getLabels()) {
            Button valueTV = new Button(mContext);
            if (mClientLabel.getHex().isEmpty())
                valueTV.setBackgroundColor(Color.GRAY);
            else
                valueTV.setBackgroundColor(Color.parseColor(mClientLabel.getHex()));
            valueTV.setId((int) System.currentTimeMillis() + new Random().nextInt(255));
            valueTV.setTextColor(Color.WHITE);
            valueTV.setMaxLines(1);
            valueTV.setTransformationMethod(null);
            valueTV.setText(mClientLabel.getClient_label_name());
            valueTV.setLayoutParams(new android.app.ActionBar.LayoutParams(width, height));
            view_label.addView(valueTV);

        }
        if (click == 1) {
            if (!mClient.isShare_client() && mClient.getUser_manager_id() != preferences.getIntValue(Constants.USER_ID, 0)) {
                Utils.showError(coordinatorLayout, R.string.client_not_share);
                rvActivities.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
                isHide = true;
                invalidateOptionsMenu();
            } else {
                rvActivities.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
                isHide = false;
                invalidateOptionsMenu();
                setViewClient();
            }
        }

    }

    public Boolean isValidInteger(String value) {
        try {
            Integer val = Integer.valueOf(value);
            if (val != null)
                return true;
            else
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.button1:
                if (b == true) {
                    click = 1;
                    Log.d(compoundButton.getId() + "" + b, "abab");
                    if (!mClient.isShare_client() && mClient.getUser_manager_id() != preferences.getIntValue(Constants.USER_ID, 0)) {
                        Utils.showError(coordinatorLayout, R.string.client_not_share);
                        rvActivities.setVisibility(View.VISIBLE);
                        view.setVisibility(View.GONE);
                        isHide = true;
                        invalidateOptionsMenu();
                    } else {
                        rvActivities.setVisibility(View.GONE);
                        view.setVisibility(View.VISIBLE);
                        isHide = false;
                        invalidateOptionsMenu();
                        setViewClient();
                    }
                }
                break;
            case R.id.button2:

                if (b == true) {
                    click = 2;
                    rvActivities.setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                    isHide = true;
                    invalidateOptionsMenu();
                } else {
                    if (!mClient.isShare_client() && mClient.getUser_manager_id() != preferences.getIntValue(Constants.USER_ID, 0)) {
                        Utils.showError(coordinatorLayout, R.string.client_not_share);
                        rvActivities.setVisibility(View.VISIBLE);
                        view.setVisibility(View.GONE);
                        isHide = true;
                        invalidateOptionsMenu();
                    } else {
                        rvActivities.setVisibility(View.GONE);
                        view.setVisibility(View.VISIBLE);
                        isHide = false;
                        invalidateOptionsMenu();
                        setViewClient();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void setViewClient() {
        if (!mClient.getAddress().isEmpty()) {
            etAddress.setVisibility(View.VISIBLE);
            etAddress.post(new Runnable() {
                public void run() {

                    etAddress.setText(mClient.getDetail());
                    etAddress.setHint("");
                }
            });
        } else etAddress.setVisibility(View.GONE);
        if (!mClient.getTax_code().isEmpty()) {
            etTaxCode.setVisibility(View.VISIBLE);
            etTaxCode.post(new Runnable() {
                public void run() {
                    etTaxCode.setText(mClient.getTax_code());
                    etTaxCode.setHint("");
                }
            });
        } else etTaxCode.setVisibility(View.GONE);
        if (!mClient.getContact().isEmpty()) {
            etContact.setVisibility(View.GONE);
            etContact.post(new Runnable() {
                public void run() {
                    etContact.setText(mClient.getContact());
                    etContact.setHint("");
                }
            });
        }
        if (mClient.getNum_dependents() > 0) {
            if (mClient.getClient_structure_id() == 2)
                etNumberDependent.setVisibility(View.GONE);
            else
                etNumberDependent.setVisibility(View.VISIBLE);
            etNumberDependent.post(new Runnable() {
                public void run() {
                    etNumberDependent.setText(mClient.getNum_dependents() + "");
                    etNumberDependent.setHint("");
                }
            });
        } else etNumberDependent.setVisibility(View.GONE);
        if (mClient.getIncome() > 0) {
            if (mClient.getClient_structure_id() == 2) {
                etSalesYear.setVisibility(View.VISIBLE);
                etSalesYear.setVisibility(View.VISIBLE);
                etSalesYear.post(new Runnable() {
                    public void run() {
                        etSalesYear.setText(Utils.formatCurrency(mClient.getIncome()) + "");
                        etSalesYear.setHint("");
                    }
                });
            } else {

                etIncome.setVisibility(View.VISIBLE);
                etIncome.post(new Runnable() {
                    public void run() {
                        etIncome.setText(Utils.formatCurrency(mClient.getIncome()) + "");
                        etIncome.setHint("");
                    }
                });
            }
        } else {
            etSalesYear.setVisibility(View.GONE);
            etIncome.setVisibility(View.GONE);
        }
        if (!mClient.getNote().isEmpty()) {
            etNote.setVisibility(View.VISIBLE);
            etNote.post(new Runnable() {
                public void run() {
                    etNote.setText(mClient.getNote());
                    etNote.setHint("");
                }
            });
        } else etNote.setVisibility(View.GONE);
        if (mClient.getGender() > 0) {
            if (mClient.getClient_structure_id() == 1) {
                etGender.setVisibility(View.VISIBLE);
                if (mClient.getGender() == 1) {
                    etGender.post(new Runnable() {
                        public void run() {
                            etGender.setText(R.string.male);
                            etGender.setHint("");
                        }
                    });
                } else if (mClient.getGender() == 2) {
                    etGender.post(new Runnable() {
                        public void run() {
                            etGender.setText(R.string.female);
                            etGender.setHint("");
                        }
                    });
                } else if (mClient.getGender() == 3) {
                    etGender.post(new Runnable() {
                        public void run() {
                            etGender.setText(R.string.other);
                            etGender.setHint("");
                        }
                    });
                }
            }
        } else etGender.setVisibility(View.GONE);
        if (!mClient.getEmail().isEmpty()) {
            etEmail.setVisibility(View.VISIBLE);
            etEmail.post(new Runnable() {
                public void run() {
                    etEmail.setText(mClient.getEmail());
                    etEmail.setHint("");
                }
            });

        } else etEmail.setVisibility(View.GONE);
        if (!mClient.getClient_name().isEmpty()) {
            etName.setVisibility(View.GONE);
            etName.post(new Runnable() {
                public void run() {
                    etName.setText(mClient.getClient_name());
                    etName.setHint("");
                }
            });

        } else etName.setVisibility(View.GONE);

        if (!mClient.getBirthday().isEmpty()) {
            DateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            if (mClient.getClient_structure_id() == 2) {
                etDateEstablished.setVisibility(View.VISIBLE);


                try {
                    date = inputFormat.parse(mClient.getBirthday());
                    final String outputDateStr = outputFormat.format(date);
                    etDateEstablished.post(new Runnable() {
                        public void run() {
                            etDateEstablished.setText(outputDateStr);
                            etDateEstablished.setHint("");
                        }
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                etBirthday.setVisibility(View.VISIBLE);

                try {
                    date = inputFormat.parse(mClient.getBirthday());
                    final String outputDateStr = outputFormat.format(date);
                    etBirthday.post(new Runnable() {
                        public void run() {
                            etBirthday.setText(outputDateStr);
                            etBirthday.setHint("");
                        }
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } else {
            etDateEstablished.setVisibility(View.GONE);
            etBirthday.setVisibility(View.GONE);
        }
        if (!mClient.getPassport().isEmpty()) {
            etPassport.setVisibility(View.VISIBLE);
            etPassport.post(new Runnable() {
                public void run() {
                    etPassport.setText(mClient.getPassport());
                    etPassport.setHint("");
                }
            });

        } else etPassport.setVisibility(View.GONE);
        if (mClient.getClient_structure_id() == 2) {
            imCity.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_40));
            etCompany_partner.setVisibility(View.VISIBLE);
            etCompany_Con.setVisibility(View.VISIBLE);
            etContact_client.setVisibility(View.VISIBLE);
            etClient_support.setVisibility(View.VISIBLE);
            etContact_client.setText(mClient.getNumber_child_1() + " " + mContext.getResources().getString(R.string.contact1));
            etCompany_partner.setText(mClient.getParent_name());
            etCompany_Con.setText(mClient.getNumber_child_2() + " " + mContext.getResources().getString(R.string.ChildCompanies));
            etClient_support.setText(mClient.getNumber_user_manager_detail() + " " + mContext.getResources().getString(R.string.title_activity_users));

        } else {
            imCity.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_26));
            etCode.setVisibility(View.VISIBLE);
            etClient_support.setVisibility(View.VISIBLE);
            etCompany_partner.setText(mContext.getResources().getString(R.string.companyParent));
            etCode.setText(mClient.getParent_name());
            etClient_support.setText(mClient.getNumber_user_manager_detail() + " " + mContext.getResources().getString(R.string.title_activity_users));
        }

        if (!mClient.getWebsite().isEmpty()) {
            etWeb.setVisibility(View.VISIBLE);
            etWeb.post(new Runnable() {
                public void run() {
                    etWeb.setText(mClient.getWebsite());
                    etWeb.setHint("");
                }
            });
        } else etWeb.setVisibility(View.GONE);
        if (!mClient.getUser_manager_name().isEmpty()) {
            relManager.setVisibility(View.VISIBLE);
            tvManager.post(new Runnable() {
                public void run() {
                    tvManager.setText(mClient.getUser_manager_name());
                    tvManager.setHint("");
                }
            });
        }
        if (!mClient.getPhone().isEmpty()) {
            etPhone.setVisibility(View.VISIBLE);
            etPhone.post(new Runnable() {
                public void run() {
                    etPhone.setText(mClient.getPhone());
                    etPhone.setHint("");
                }
            });

        } else etPhone.setVisibility(View.GONE);
        if (!mClient.getDescription().isEmpty()) {
            etDescription.setVisibility(View.VISIBLE);
            etDescription.post(new Runnable() {
                public void run() {
                    etDescription.setText(mClient.getDescription());
                    etDescription.setHint(getString(R.string.description));
                }
            });

        } else etDescription.setVisibility(View.GONE);


        List<String> string = new ArrayList<String>();
        string.add(mClient.getClient_group_name());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, string);
        spGroup.setText(mClient.getClient_group_name());

        string = new ArrayList<>();
        string.add(mClient.getClient_type_name());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, string);
        spType.setText(mClient.getClient_type_name());

        string = new ArrayList<>();
        string.add(mClient.getStatus_id() == 1 ? getString(R.string.activity) : getString(R.string.in_activity));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, string);
        spStatus.setText(mClient.getStatus_id() == 1 ? getString(R.string.activity) : getString(R.string.in_activity));

        string = new ArrayList<>();
        string.add(mClient.getClient_area_name());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, string);
        spArea.setText(mClient.getClient_area_name());


//        string.add(getString(R.string.single));
//        string.add(getString(R.string.engaged));
//        string.add(getString(R.string.married));
//        string.add(getString(R.string.separated));
//        string.add(getString(R.string.divorced));
//        string.add(getString(R.string.widow));
//        string.add(getString(R.string.widower));
        if (mClient.getClient_structure_id() == 1) {
            if (mClient.getMarital_status_id() == 1)
                spMarital.setText(getString(R.string.single));
            else if (mClient.getMarital_status_id() == 2)
                spMarital.setText(getString(R.string.engaged));
            else if (mClient.getMarital_status_id() == 3)
                spMarital.setText(getString(R.string.married));
            else if (mClient.getMarital_status_id() == 4)
                spMarital.setText(getString(R.string.separated));
            else if (mClient.getMarital_status_id() == 5)
                spMarital.setText(getString(R.string.divorced));
            else if (mClient.getMarital_status_id() == 6)
                spMarital.setText(getString(R.string.widow));
            else if (mClient.getMarital_status_id() == 7)
                spMarital.setText(getString(R.string.widower));
            else layout_marital.setVisibility(View.GONE);
        } else {
            layout_marital.setVisibility(View.GONE);
            view11.setVisibility(View.GONE);
        }
        spBusinessType.setText(mClient.getClient_business_name());
        etEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + etEmail.getText().toString()));

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {

                }

            }
        });
        etPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
                    //No calling functionality
                    Toast.makeText(ClientActivity.this, R.string.srtNoCall, Toast.LENGTH_SHORT).show();
                } else {
                    //calling functionality
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ClientActivity.this);
                    builder1.setMessage(getResources().getString(R.string.srtCall) + " " + mClient.getPhone());
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            R.string.srtNo,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton(
                            R.string.srtCall,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    checkPermission(Manifest.permission.CALL_PHONE);
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    // callIntent.setPackage("com.android.phone");
                                    callIntent.setData(Uri.parse("tel:" + mClient.getPhone()));
                                    Log.d("SDT", mClient.getPhone() + "");
                                    if (ActivityCompat.checkSelfPermission(ClientActivity.this,
                                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    startActivity(callIntent);
                                    timer.schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mContext.startActivity(new Intent(mContext, CallActivity.class).putExtra("mClient", mClient));
                                                }
                                            });
                                        }
                                    }, 1000 * 2);
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }


            }
        });
        etWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = mClient.getWebsite();
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });


        mapView.setClickable(true);
        mapView.setFocusable(true);
        mapView.setDuplicateParentStateEnabled(false);

        if (mClient.getLatitude() != 0 || mClient.getLongitude() != 0) {
            // Gets to GoogleMap from the MapView and does initialization stuff
            map = mapView.getMap();
            if (map != null) {
                map.setBuildingsEnabled(true);
                map.setIndoorEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                map.setMyLocationEnabled(true);
                // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
                MapsInitializer.initialize(this);
                // Updates the location and zoom of the MapView
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(mClient.getLatitude(), mClient.getLongitude()), 15);
                map.animateCamera(cameraUpdate);
                LatLng center = map.getCameraPosition().target;
                Marker marker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(mClient.getLatitude(), mClient.getLongitude()))
                        .title(mClient.getClient_name())
                        .snippet(mClient.getAddress()));

                mapView.invalidate();
            }
        } else {
            mapView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onFailure(Call<MAPIResponse<MActivity>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        Utils.showDialogSuccess(mContext, R.string.srtNoti);
        box.hideAll();

    }

    @Override
    public void onClick(View view) {
        imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_16));
        imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_17));
        imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_96));
        imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_18));
        imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_66));
        imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_23));
        imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_20));
        tvAmount.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tvAmountExpend.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        tvAmountFinish.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
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
                if (type != Constants.ACTIVITY_TYPE_WORK) type = Constants.ACTIVITY_TYPE_WORK;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_WORK)
                    imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_66));
                else imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_7));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line6:
                if (type != Constants.ACTIVITY_TYPE_FOCUS)
                    type = Constants.ACTIVITY_TYPE_FOCUS;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_FOCUS)
                    imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_23));
                else imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_15));
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
            case R.id.etContact_client:
                mClient.setType(1);
                mContext.startActivity(new Intent(mContext, PersonnelActivity.class).putExtra("mClient", mClient));
                break;
            case R.id.etCompany_Con:
                mClient.setType(2);
                mContext.startActivity(new Intent(mContext, PersonnelActivity.class).putExtra("mClient", mClient));
                break;
            case R.id.etCompany_partner:
                if (mClient.getParent_name() != null && mClient.getParent_name().length() > 0) {
                    MClient client = new MClient();
                    client.setClient_id(mClient.getParent_id());
                    client.setClient_name(mClient.getParent_name());
                    client.setAddress(mClient.getAddress());
                    Intent intent = new Intent(mContext, ClientActivity.class).putExtra("mClient", client);
                    mContext.startActivity(intent);
                } else {

                }
                ;
                break;
            case R.id.etCode:
                if (mClient.getParent_name() != null && mClient.getParent_name().length() > 0) {
                    MClient client1 = new MClient();
                    client1.setClient_id(mClient.getParent_id());
                    client1.setClient_name(mClient.getParent_name());
                    client1.setAddress(mClient.getAddress());
                    Intent intent1 = new Intent(mContext, ClientActivity.class).putExtra("mClient", client1);
                    mContext.startActivity(intent1);
                } else {

                }
                break;
            case R.id.etClient_support:
                mContext.startActivity(new Intent(mContext, ActivityClientSupport.class).putExtra("mClient", mClient));
                break;
            case R.id.tvAmountFinish:
                if (type != Constants.AmountFinish) type = Constants.AmountFinish;
                else type = 0;
                if (type != Constants.AmountFinish)
                    tvAmountFinish.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                else tvAmountFinish.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.tvAmount:
                if (type != Constants.Amount) type = Constants.Amount;
                else type = 0;
                if (type != Constants.Amount)
                    tvAmount.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                else tvAmount.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
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
            case 14:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 14)
                        mActivityItems1.add(i);
                }
                break;
            case 15:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 15 && i.getOrder_status() == 2)
                        mActivityItems1.add(i);
                }

                break;
            case 16:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 2)
                        mActivityItems1.add(i);
                }

                break;
            default:
                break;
        }
        return mActivityItems1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (resultCode == Constants.RESULT_USERS) {
            mIds = new ArrayList<>();
            mIds = (List<MId>) data.getSerializableExtra("mIds");

        }
        if (resultCode == Constants.RESULT_CALENDAR) {
            toDate = data.getStringExtra("toDate");
            fromDate = data.getStringExtra("fromDate");
            //  tvDate.setText(data.getStringExtra("tvDate"));

        }
        if (resultCode == Constants.RESULT_COMPANY) {
            if (!mClient.isShare_client() && mClient.getUser_manager_id() != preferences.getIntValue(Constants.USER_ID, 0)) {
                Utils.showError(coordinatorLayout, R.string.client_not_share);
                rvActivities.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
                isHide = true;
                invalidateOptionsMenu();
            } else {
                rvActivities.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
                isHide = false;
                invalidateOptionsMenu();
                setViewClient();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mount = 0;
        Log.d("Client_id", mClient.getClient_id() + "");
        GetRetrofit().create(ServiceAPI.class)
                .getClient(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , mClient.getClient_id()
                )

                .enqueue(new Callback<MAPIResponse<MClient>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<MClient>> call, Response<MAPIResponse<MClient>> response) {
                        LogUtils.api(TAG, call, (response.body()));
                        box.hideAll();
                        TokenUtils.checkToken(mContext, response.body().getErrors());
                        mClient = response.body().getResult();
                        if (response.body().getErrors().getID() == 3)
                            Utils.showDialogSuccess(mContext, R.string.srtNoti);
                        else {
                            if (mClient.getClient_structure_id() == 2) {
                                tvAddress.setText(mClient.getDetail());
                                imCity.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_40));
                            } else {
                                if (mClient.getPosition() != null) {
                                    if (mClient.getParent_name() != null) {
                                        tvAddress.setText(mClient.getPosition() + " " + mClient.getParent_name());
                                    } else tvAddress.setText(mClient.getPosition());
                                } else {
                                    if (mClient.getParent_name() != null) {
                                        tvAddress.setText(mClient.getParent_name());
                                    } else tvAddress.setText("");
                                }
                                imCity.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_26));
                            }

                            // tvAddress
                            if (mClient.getDetail() != null && !mClient.getDetail().isEmpty() || mClient.getPosition() != null && mClient.getPosition().length() > 0 || mClient.getParent_name() != null && mClient.getParent_name().length() > 0) {
                                tvAddress.setVisibility(View.VISIBLE);
                            } else tvAddress.setVisibility(View.VISIBLE);
                            // setViewClient();
                        }
                    }

                    @Override
                    public void onFailure(Call<MAPIResponse<MClient>> call, Throwable t) {
                        LogUtils.d(TAG, "getUserActivities ", t.toString());
                        box.hideAll();
                    }
                });
        box.showLoadingLayout();


        MRequestBody mRequestBody = new MRequestBody();
        mRequestBody.setFrom_date(fromDate);
        mRequestBody.setTo_date(toDate);
        mRequestBody.setUser_ids(mIds);

        GetRetrofit().create(ServiceAPI.class)
                .getUserActivities(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , mClient.getClient_id()
                        , mRequestBody, 1, 0
                )
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();

        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onClick(View view, int position) {
        MActivityItem activityItem = activityAdapter.getItem(position);
        mClient.setClient_name(mActivityItems.get(position).getClient_name());
        mClient.setAddress(mActivityItems.get(position).getAddress());
        switch (activityItem.getActivity_type()) {

            case Constants.ACTIVITY_TYPE_ORDER:
                MOrder mOrder = new MOrder();
                mOrder.setClient_id(mClient.getClient_id());
                mOrder.setClient_name(mClient.getClient_name());
                mOrder.setAddress(mClient.getAddress());
                mOrder.setOrder_sheet_id(-1);
                mOrder.setOrder_contract_id(activityItem.getOrder_contract_id());
                mContext.startActivity(new Intent(mContext, OrderViewActivity.class).putExtra("mClient", mClient).putExtra("mOrder", mOrder));
                break;
            case Constants.ACTIVITY_TYPE_WORK:
                MWorkUser mWorkUser = new MWorkUser();
                mWorkUser.setWork_user_id(activityItem.getWork_user_id());
                mContext.startActivity(new Intent(mContext, WorkActivity.class).putExtra("mClient", mClient).putExtra("mWorkUser", mWorkUser));
                break;
            case Constants.ACTIVITY_TYPE_CHECKIN:
                MCheckin mCheckin = new MCheckin();
                mCheckin.setUser_checkin_id(activityItem.getMeeting_id());
                mCheckin.setContent_checkin(activityItem.getActivity_content());
                mCheckin.setOrder_contract_id(activityItem.getOrder_contract_id());
                mCheckin.setOrder_contract_name(activityItem.getOrder_contract_name());
                if (activityItem.getType() == 0)
                    mContext.startActivity(new Intent(mContext, CheckinActivity.class).putExtra("mClient", mClient).putExtra("mCheckin", mCheckin));
                else {
                    MOrder mOrder1 = new MOrder();
                    mOrder1.setOrder_contract_id(activityItem.getOrder_contract_id());
                    mOrder1.setOrder_contract_name(activityItem.getOrder_contract_name());
                    mOrder1.setClient_id(activityItem.getClient_id());
                    mContext.startActivity(new Intent(mContext, CheckinContractActivity.class).putExtra("mOrder", mOrder1).putExtra("mCheckin", mCheckin));

                }
                break;
            case Constants.ACTIVITY_TYPE_CALL:
                MCall mCall = new MCall();
                mCall.setUser_call_id(activityItem.getUser_call_id());
                mCall.setContent_call(activityItem.getActivity_content());
                mCall.setOrder_contract_id(activityItem.getOrder_contract_id());
                mCall.setOrder_contract_name(activityItem.getOrder_contract_name());
                if (activityItem.getType() == 0)
                    mContext.startActivity(new Intent(mContext, CallActivity.class).putExtra("mClient", mClient).putExtra("mCall", mCall));
                else {
                    MOrder mOrder1 = new MOrder();
                    mOrder1.setOrder_contract_id(activityItem.getOrder_contract_id());
                    mOrder1.setOrder_contract_name(activityItem.getOrder_contract_name());
                    mOrder1.setClient_id(activityItem.getClient_id());
                    mContext.startActivity(new Intent(mContext, CallContractActivity.class).putExtra("mOrder", mOrder1).putExtra("mCall", mCall));
                }
                break;
            case Constants.ACTIVITY_TYPE_EMAIL:
                MEmail mEmail = new MEmail();
                mEmail.setUser_email_id(activityItem.getUser_email_id());
                mEmail.setContent_email(activityItem.getActivity_content());
                mEmail.setOrder_contract_id(activityItem.getOrder_contract_id());
                mEmail.setOrder_contract_name(activityItem.getOrder_contract_name());
                if (activityItem.getType() == 0)
                    mContext.startActivity(new Intent(mContext, EmailActivity.class).putExtra("mClient", mClient).putExtra("mEmail", mEmail));
                else {
                    MOrder mOrder1 = new MOrder();
                    mOrder1.setOrder_contract_id(activityItem.getOrder_contract_id());
                    mOrder1.setOrder_contract_name(activityItem.getOrder_contract_name());
                    mOrder1.setClient_id(activityItem.getClient_id());
                    mContext.startActivity(new Intent(mContext, EmailContractActivity.class).putExtra("mOrder", mOrder1).putExtra("mEmail", mEmail));
                }
                break;
            case 15:
                MOrder mOrder2 = new MOrder();
                mOrder2.setClient_id(mClient.getClient_id());
                mOrder2.setClient_name(mClient.getClient_name());
                mOrder2.setAddress(mClient.getAddress());
                mOrder2.setOrder_sheet_id(-1);
                mOrder2.setOrder_contract_id(activityItem.getOrder_contract_id());
                mContext.startActivity(new Intent(mContext, OrderViewActivity.class).putExtra("mClient", mClient).putExtra("mOrder", mOrder2));
                break;
            case Constants.ACTIVITY_TYPE_EVENT:
                MEvent mEvent = new MEvent();
                mEvent.setEvent_id(activityItem.getEvent_id());
                mContext.startActivity(new Intent(mContext, EventClientActivity.class).putExtra("mClient", mClient).putExtra("mEvent", mEvent).putExtra("ShowMenu", false));
                break;
            case Constants.ACTIVITY_TYPE_FOCUS:
                Focus mFocus = new Focus();
                mFocus.setClientFocusId(activityItem.getClient_focus_id());
                mContext.startActivity(new Intent(mContext, HistoryFocusActivity.class).putExtra("mClient", mClient));
                break;
            case Constants.ACTIVITY_TYPE_EXPEND:
                Expend mExpend = new Expend();
                mExpend.setExpendId(activityItem.getExpend_id());
                Log.d("getExpend_id", activityItem.getExpend_id() + "");
                if (activityItem.getType() == 0)
                    mContext.startActivity(new Intent(mContext, AddCostsActivity.class).putExtra("mClient", mClient).putExtra("mExpend", activityItem).putExtra("mAdd", 0));
                else {
                    MOrder mOrder1 = new MOrder();
                    mOrder1.setOrder_contract_id(activityItem.getOrder_contract_id());
                    mOrder1.setOrder_contract_name(activityItem.getOrder_contract_name());
                    mOrder1.setClient_id(activityItem.getClient_id());
                    mContext.startActivity(new Intent(mContext, AddCostsContractActivity.class).putExtra("mOrder", mOrder1).putExtra("mExpend", activityItem).putExtra("mAdd", 0));
                }
                break;
            case Constants.ACTIVITY_TYPE_DEBT:
                MOrder mOrder3 = new MOrder();
                mOrder3.setOrder_contract_name(activityItem.getOrder_contract_name());
                mOrder3.setOrder_contract_id(activityItem.getOrder_contract_id());
                mContext.startActivity(new Intent(mContext, Contract_debt.class).putExtra("mOrder", mOrder3).putExtra("mDebt", activityItem).putExtra("mAdd", 0));
                break;
            case Constants.ACTIVITY_TYPE_DOCUMENT:
                MOrder mOrder1 = new MOrder();
                mOrder1.setOrder_contract_name(activityItem.getOrder_contract_name());
                mOrder1.setOrder_contract_id(activityItem.getOrder_contract_id());
                mContext.startActivity(new Intent(mContext, AddDocumentActivity.class).putExtra("mOrder", mOrder1).putExtra("Document", activityItem).putExtra("mAdd", 0));
                break;
            case Constants.ACTIVITY_TYPE_COMMENT:
                MOrder mOrder4 = new MOrder();
                mOrder4.setOrder_contract_name(activityItem.getOrder_contract_name());
                mOrder4.setOrder_contract_id(activityItem.getOrder_contract_id());
                mContext.startActivity(new Intent(mContext, AddCommentActivity.class).putExtra("mOrder", mOrder4).putExtra("Comment", activityItem).putExtra("mAdd", 0));
                break;
            default:
                break;
        }
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
