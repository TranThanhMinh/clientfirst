package anhpha.clientfirst.crm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.OrderAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.AdapterInterface;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MOrder;
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

public class OrderActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<List<MContract>>>, View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterInterface {

    @Bind(R.id.btAdd)
    Button btAdd;
    @Bind(R.id.spOrderStatus)
    Spinner spOrderStatus;
    @Bind(R.id.rvActivities)
    ListView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.etCustomer)
    EditText etCustomer;
    //    @Bind(R.id.tvAddress)
//    TextView tvClientName;
//        @Bind(R.id.tvClientName)
//    TextView tvAddress;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.etPrepay)
    EditText etPrepay;
    @Bind(R.id.textView21)
    TextView textView21;
    @Bind(R.id.textView22)
    EditText textView22;
    @Bind(R.id.textView23)
    TextView textView23;
    @Bind(R.id.note)
    EditText noteO;


    @Bind(R.id.etOrderCodeParent)
    EditText etOrderCodeParent;

    @Bind(R.id.etOrderCode)
    EditText etOrderCode;

    @Bind(R.id.etStatus)
    EditText etStatus;

    @Bind(R.id.etSuccessRate)
    EditText etSuccessRate;

    @Bind(R.id.etSalesProcess)
    EditText etSalesProcess;
    @Bind(R.id.etOrderName)
    EditText etOrderName;
    @Bind(R.id.etStaffincharge)
    EditText etStaffincharge;

    EditText note;
    EditText value;
    EditText etDiscount;
    EditText number;
    TextView total;
    TextView totalMoney;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    private int idGroup = 0, idByGroup = 0, Rate = 0, idGroup_edit = 0, idByGroup_edit = 0;
    int object_id;
    MOrder mOrder = new MOrder();
    MClient mClient = new MClient();
    List<MContract> MContracts = new ArrayList<>();
    List<MContract> MContractsOld = new ArrayList<>();
    List<MContract> MContractsNew = new ArrayList<>();
    OrderAdapter orderAdapter;
    String date = "";
    int type = 1;
    double totalMoney1;
    double sum, prePayDiscount = 0;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day;
    Preferences preferences;

    double totalAmountContract = 0;
    double vat = 0;
    double discountContract = 0;
    double discountOrder = 0;
    double prePay = 0;
    boolean is_edit = false;
    private List<OrderContractStatus> orderStatus = new ArrayList<>();
    private String SalesProcess;
    private String client_name;
    private int user_id;
    private int status = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_order);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_order);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        tvDate.setOnClickListener(this);
        etStatus.setOnClickListener(this);
        orderAdapter = new OrderAdapter(mContext, mOrder.getOrder_detail_contracts(), this);

        mOrder = (MOrder) getIntent().getSerializableExtra("mOrder");
        mClient = (MClient) getIntent().getSerializableExtra("mClient");
        client_name = (String) getIntent().getSerializableExtra("client_name");
        user_id = (int) getIntent().getSerializableExtra("user_id");


        if (mOrder == null) {
            mOrder = new MOrder();
            mOrder.setOrder_type_id(1);
            List<String> string = new ArrayList<String>();
            etStaffincharge.setText(client_name);
            object_id = user_id;
            Log.d("user_id", user_id + "");
            string.add(preferences.getStringValue(Constants.ORDER_STATUS_1, ""));
            ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
            spOrderStatus.setAdapter(adapter);
            status = 1;
        } else {
//            mOrder.setOrder_contract_name(etOrderName.getText().toString());//name contract
//            mOrder.setOrder_contract_status_group_id(idGroup);//quy trinh
//            mOrder.setPercent_done(Rate);//tỷ lệ
//            mOrder.setOrder_contract_status_id(idByGroup);//trạng thái
//            mOrder.setUser_manager_contract_id(object_id);//nhan viên pụ trách user_manager_contract_id
//            mOrder.setDiscount_contract_price(Utils.tryParseDouble(textView22.getText().toString().replace(",", "")));
            mClient = new MClient();
            mClient.setClient_id(mOrder.getClient_id());
            mClient.setClient_name(mOrder.getClient_name());
            mClient.setAddress(mOrder.getAddress());

            etOrderName.setText(mOrder.getOrder_contract_name());
            etOrderCode.setText(mOrder.getOrder_contract_code());
            etSalesProcess.setText(mOrder.getOrder_contract_status_group_name());
            SalesProcess = mOrder.getOrder_contract_status_group_name();
            etStatus.setText(mOrder.getOrder_contract_status_name());
            orderStatus = new ArrayList<>();
            etStaffincharge.setText(mOrder.getUser_manager_contract_name());
            idGroup = mOrder.getOrder_contract_status_group_id();
            idByGroup = mOrder.getOrder_contract_status_id();
            idGroup_edit = mOrder.getOrder_contract_status_group_id();
            idByGroup_edit = mOrder.getOrder_contract_status_id();
            object_id = mOrder.getUser_id();
            Rate = mOrder.getPercent_done();
            status = mOrder.getOrder_contract_status_type_id();
            Log.d("status",status+"");
            etSuccessRate.setText(mOrder.getPercent_done() + "%");
            //   etStaffincharge.setText(mOrder.getUser_name());
            mOrder.setDelivery_date(mOrder.getDelivery_date() == null ? "" : mOrder.getDelivery_date());
            tvDate.setText(mOrder.getDelivery_date().isEmpty() ? getString(R.string.choose_date) : mOrder.getDelivery_date());
            date = mOrder.getDelivery_date();
            noteO.setText(mOrder.getNote());
            LoadOrder();
            is_edit = true;
            spOrderStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int status = mOrder.getOrder_status_id();
                    if (position == 1) {
                        switch (mOrder.getOrder_status_id()) {
                            case 1:
                                status = 2;
                                break;
                            case 2:
                                status = 4;
                                break;
                            case 4:
                                status = 6;
                                break;
                            case 6:
                                status = 7;
                                break;
                            default:
                                break;
                        }
                        GetRetrofit().create(ServiceAPI.class)
                                .setStatusOrder(preferences.getStringValue(Constants.TOKEN, "")
                                        , preferences.getIntValue(Constants.USER_ID, 0)
                                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                        , mOrder.getOrder_contract_id()
                                        , status
                                )
                                .enqueue(new Callback<MAPIResponse<MId>>() {
                                    @Override
                                    public void onResponse(Call<MAPIResponse<MId>> call, Response<MAPIResponse<MId>> response) {
                                        LogUtils.api(TAG, call, (response.body()));
                                        box.hideAll();
                                        TokenUtils.checkToken(mContext, response.body().getErrors());
                                        if (!response.body().isHasErrors()) {
                                            Utils.showSuccess(coordinatorLayout, R.string.change_order_status_done);
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

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            List<String> string = new ArrayList<String>();
            ArrayAdapter adapter;
            switch (mOrder.getOrder_status_id()) {
                case 1:
                    string = new ArrayList<String>();
                    string.add(preferences.getStringValue(Constants.ORDER_STATUS_1, ""));
                    string.add(preferences.getStringValue(Constants.ORDER_STATUS_2, ""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);

                    break;
                case 2:
                    string = new ArrayList<String>();
                    string.add(preferences.getStringValue(Constants.ORDER_STATUS_2, ""));
                    string.add(preferences.getStringValue(Constants.ORDER_STATUS_4, ""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);

                    break;
                case 4:
                    string = new ArrayList<String>();
                    string.add(preferences.getStringValue(Constants.ORDER_STATUS_4, ""));
                    string.add(preferences.getStringValue(Constants.ORDER_STATUS_6, ""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);

                    break;
                case 6:
                    string = new ArrayList<String>();
                    string.add(preferences.getStringValue(Constants.ORDER_STATUS_6, ""));
                    string.add(preferences.getStringValue(Constants.ORDER_STATUS_7, ""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);
                    break;
                case 7:
                    string = new ArrayList<String>();
                    string.add(preferences.getStringValue(Constants.ORDER_STATUS_7, ""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);
                    break;
                case 3:
                    string = new ArrayList<String>();
                    string.add(preferences.getStringValue(Constants.ORDER_STATUS_3, ""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);
                    break;
                case 5:
                    string = new ArrayList<String>();
                    string.add(preferences.getStringValue(Constants.ORDER_STATUS_5, ""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);
                    break;
                default:
                    break;
            }

        }
        if (mClient == null) {
            mClient = new MClient();
        } else {
            etCustomer.setText(mClient.getClient_name());
//            tvClientName.setText(mClient.getClient_name());
//            tvAddress.setText(mClient.getAddress());
//            if (mOrder.getAddress() != null && !mClient.getAddress().isEmpty()) {
//                tvAddress.setVisibility(View.VISIBLE);
//            }
        }
        if (mClient.getClient_id() == 0) {
            etCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(mContext, ChooseClientsActivity.class), Constants.RESULT_CLIENT);
                }
            });
        }
        btAdd.setOnClickListener(this);
        etStaffincharge.setOnClickListener(this);
        etSalesProcess.setOnClickListener(this);

        rvActivities.setAdapter(orderAdapter);
        Utils.setListViewHeightBasedOnChildren(rvActivities);

        LoadContract();
        textView22.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                textView22.removeTextChangedListener(this);
                double prePay = Utils.tryParseDouble(editable.toString().replace(",", ""));
                textView22.setText(Utils.formatCurrency(prePay));
                textView22.setSelection(textView22.getText().length());
                textView22.addTextChangedListener(this);
                textView23.setText(Utils.formatCurrency(totalAmountContract - prePay));
            }
        });
        etOrderCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mOrder.setOrder_contract_code(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etOrderCodeParent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mOrder.setOrder_contract_code_parent(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        etPrepay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                etPrepay.removeTextChangedListener(this);
                double prePay = Utils.tryParseDouble(editable.toString().replace(",", ""));
                etPrepay.setText(Utils.formatCurrency(prePay));
                textView22.setText(Utils.formatCurrency((prePay)));
                mOrder.setPrepay(prePay);
                textView23.setText(Utils.formatCurrency(totalAmountContract - prePay));
                etPrepay.setSelection(etPrepay.getText().length());
                etPrepay.addTextChangedListener(this);
            }
        });
    }

    private void LoadContract() {
        if (mClient.getClient_id() > 0) {
            GetRetrofit().create(ServiceAPI.class)
                    .getContractByGroup(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                            , 0
                            , mClient.getClient_id()
                    )
                    .enqueue(this);
            setProgressBarIndeterminateVisibility(true);
            setProgressBarVisibility(true);
            box.showLoadingLayout();

            LogUtils.d(TAG, "getUserActivities ", "start");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                if (mClient.getClient_id() > 0 && mOrder.getOrder_detail_contracts().size() > 0 && etOrderName.getText().toString() != null && etOrderName.getText().toString().length() > 0
                        && idGroup > 0 && idByGroup > 0) {
                    box.showLoadingLayout();
                    List<MContract> MContracts = mOrder.getOrder_detail_contracts();
                    int id = mOrder.getOrder_contract_id();
                    mOrder = new MOrder();
                    mOrder.setOrder_detail_contracts(MContracts);
                    mOrder.setOrder_contract_name(etOrderName.getText().toString());//name contract
                    mOrder.setOrder_contract_status_group_id(idGroup);//quy trinh
                    mOrder.setPercent_done(Rate);//tỷ lệ
                    mOrder.setOrder_contract_status_id(idByGroup);//trạng thái
                    mOrder.setUser_manager_contract_id(object_id);//nhan viên pụ trách user_manager_contract_id
                    mOrder.setDiscount_contract_price(Utils.tryParseDouble(textView22.getText().toString().replace(",", "")));//chiet khau  discount_contract_price
                    mOrder.setNumber_client(0);//so khach hàng liên hệ
                    mOrder.setNumber_user(0);//so nhan viên hổ trợ
                    mOrder.setOrder_contract_id(id);
                    mOrder.setDelivery_date(date);
                    mOrder.setAmount_payment(totalAmountContract - Utils.tryParseDouble(textView22.getText().toString().replace(",", "")));
                    mOrder.setOrder_amount(totalAmountContract);
                    mOrder.setOrder_type_id(type);
                    mOrder.setPartner_id(preferences.getIntValue(Constants.PARTNER_ID, 0));
                    mOrder.setAmount_non_discount(totalAmountContract);
                    mOrder.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
                    mOrder.setClient_id(mClient.getClient_id());
                    mOrder.setClient_name(mClient.getClient_name());
                    mOrder.setAddress(mClient.getAddress());
                    mOrder.setOrder_status_id(1);
                    mOrder.setPrepay(Utils.tryParseDouble(etPrepay.getText().toString()));
                    mOrder.setOrder_contract_code((etOrderCode.getText().toString()));
                    mOrder.setOrder_contract_code_parent((etOrderCodeParent.getText().toString()));
                    mOrder.setPayment_type_id(type);
                    mOrder.setNote(noteO.getText().toString());
                    GetRetrofit().create(ServiceAPI.class)
                            .setOrder(preferences.getStringValue(Constants.TOKEN, "")
                                    , preferences.getIntValue(Constants.USER_ID, 0)
                                    , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                    , mClient.getClient_id()
                                    , mOrder
                            )
                            .enqueue(new Callback<MAPIResponse<MOrder>>() {
                                @Override
                                public void onResponse(Call<MAPIResponse<MOrder>> call, Response<MAPIResponse<MOrder>> response) {
                                    LogUtils.api(TAG, call, (response.body()));
                                    box.hideAll();
                                    TokenUtils.checkToken(mContext, response.body().getErrors());
                                    if (response.body().isHasErrors()) {
                                        if (!is_edit)
                                            Utils.showError(coordinatorLayout, R.string.order_fail);
                                        else
                                            Utils.showError(coordinatorLayout, R.string.order_edit_fail);
                                    } else {

                                        if (!is_edit)
                                            Utils.showDialogSuccess(mContext, R.string.order_done);
                                        else {
                                            if (idByGroup != idByGroup_edit || idGroup != idGroup_edit)
                                                funcEditStatusContract();
                                            else
                                                Utils.showDialogSuccess(mContext, R.string.order_edit_done);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MAPIResponse<MOrder>> call, Throwable t) {
                                    LogUtils.d(TAG, "getUserActivities ", t.toString());
                                    box.hideAll();
                                    if (!is_edit)
                                        Utils.showError(coordinatorLayout, R.string.order_fail);
                                    else
                                        Utils.showError(coordinatorLayout, R.string.order_edit_fail);
                                }
                            });
                } else {
                    if (etOrderName.getText().toString().equals(""))
                        Utils.showError(coordinatorLayout, R.string.name_contract);
                    else if (idGroup == 0)
                        Utils.showError(coordinatorLayout, R.string.sales_proces);
                    else if (idByGroup == 0)
                        Utils.showError(coordinatorLayout, R.string.status_proces);
                    else if (mClient.getClient_id() > 0)
                        Utils.showError(coordinatorLayout, R.string.require_contract);

                    else
                        Utils.showError(coordinatorLayout, R.string.require_client);
                }
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void funcEditStatusContract() {
        GetRetrofit().create(ServiceAPI.class)
                .setStatusOrder(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , mOrder.getOrder_contract_id()
                        , idByGroup
                )
                .enqueue(new Callback<MAPIResponse<MId>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<MId>> call, Response<MAPIResponse<MId>> response) {
                        LogUtils.api(TAG, call, (response.body()));

                        TokenUtils.checkToken(mContext, response.body().getErrors());
                        if (!response.body().isHasErrors()) {
                            Utils.showDialogSuccess(mContext, R.string.order_edit_done);
                        } else {
                            Utils.showError(coordinatorLayout, R.string.delete_order_fail);
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
    public void onResponse(Call<MAPIResponse<List<MContract>>> call, Response<MAPIResponse<List<MContract>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext, response.body().getErrors());
        MContracts = response.body().getResult();

    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MContract>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btAdd:
                if (mClient.getClient_id() > 0) {
                    Intent it = new Intent(mContext, ContractsActivity.class);
                    it.putExtra("MContracts", (Serializable) MContracts);
                    startActivityForResult(it, Constants.RESULT_PRODUCT);
                } else {
                    Utils.showError(coordinatorLayout, R.string.require_client);
                }
                break;
            case R.id.etStaffincharge:
                startActivityForResult(new Intent(mContext, ChooseUsersActivity.class), Constants.RESULT_USER);
                break;

            case R.id.etSalesProcess:
                startActivityForResult(new Intent(mContext, ActivitySalesProcess.class), Constants.RESULT_GROUP_BY);
                break;
            case R.id.etStatus:
                if (idGroup == 0)
                    Toast.makeText(mContext, R.string.sales_proces, Toast.LENGTH_SHORT).show();
                else {
                    if (status == 1)
                        startActivityForResult(new Intent(mContext, ActivitySalesStatus.class).putExtra("idGroup", idGroup).putExtra("lvByGroup", (Serializable) orderStatus).putExtra("SalesProcess", SalesProcess).putExtra("contract_order_id", 1), Constants.RESULT_USER);
                    else
                        Toast.makeText(mContext, getString(R.string.srtChanged) + " " + mOrder.getOrder_contract_status_name(), Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.tvDate:
                calendar = Calendar.getInstance();

                Year = calendar.get(Calendar.YEAR);
                Month = calendar.get(Calendar.MONTH);
                Day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = DatePickerDialog.newInstance(
                        OrderActivity.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorApp));
                datePickerDialog.setCancelText(getString(R.string.no));
                datePickerDialog.setOkText(getString(R.string.yes));
                datePickerDialog.setTitle(getString(R.string.choose_date));
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            default:
                break;
        }
    }

    private void LoadOrder() {
        orderAdapter.setActivityItemList(mOrder.getOrder_detail_contracts());
        orderAdapter.notifyDataSetChanged();
        Utils.setListViewHeightBasedOnChildren(rvActivities);

        mOrder.setClient_name(etCustomer.getText().toString());
        // mOrder.setAddress(tvAddress.getText().toString());

        totalAmountContract = 0;
        vat = 0;
        discountContract = 0;
        discountOrder = 0;
        etPrepay.setText(Utils.formatCurrency(mOrder.getDiscount_percent()));
        etOrderCode.setText((mOrder.getOrder_contract_code()));
        etOrderCodeParent.setText((mOrder.getOrder_contract_code_parent()));

        prePay = Utils.tryParseDouble(etPrepay.getText().toString());

        for (MContract pdd : mOrder.getOrder_detail_contracts()) {
            MContract d = Utils.getPriceContract(pdd, mContext);
            Log.d("totalMoney", pdd.getDiscount_price() + "");
            Log.d("percent", pdd.getDiscount_type() + "");
            totalAmountContract += pdd.getPrice() * pdd.getNumber() - pdd.getDiscount_price();
            ;
            prePay = mOrder.getDiscount_contract_price();

        }
        textView21.setText(Utils.formatCurrency(totalAmountContract));
        textView22.setText(Utils.formatCurrency((prePay)));
        textView23.setText(Utils.formatCurrency(totalAmountContract - prePay));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RESULT_PRODUCT) {
            MContractsNew = (List<MContract>) data.getSerializableExtra("MContractsNew");

            MContractsOld = new ArrayList<>();
            for (MContract p : mOrder.getOrder_detail_contracts()) {
                MContractsOld.add(p);
            }
            for (MContract p : MContractsNew) {
                int count = 0;
                for (MContract p2 : MContractsOld) {
                    if (p.getContract_id() == p2.getContract_id() && p.getNumber() > 0) {

                        double percent = p.getDiscount_percent() + p2.getDiscount_percent();
                        double number = p2.getNumber() + p.getNumber();
                        double price = p.getPrice();
                        p2.setNumber(number);
                        p2.setPrice(price);
                        p2.setDiscount_percent(percent);
                        p2.setDiscount_price(number * price - percent);
                        count++;

                        Log.d("number", number + "");
                        Log.d("price", (number * price) - percent + "");
                        Log.d("percent", percent + "");
                        break;
                    }
                }
                if (count == 0 && p.getNumber() > 0) {
                    MContractsOld.add(p);
                }

            }
            mOrder.setOrder_detail_contracts(MContractsOld);
            LoadOrder();

        } else if (resultCode == Constants.RESULT_CLIENT) {
            mClient = (MClient) data.getSerializableExtra("mClient");
            etCustomer.setText(mClient.getClient_name());
            //  tvAddress.setText(mClient.getAddress());
            if (mClient.getAddress() != null && !mClient.getAddress().isEmpty()) {
                //  tvAddress.setVisibility(View.VISIBLE);
            }
            etCustomer.setOnClickListener(this);
            LoadContract();

        } else if (resultCode == Constants.RESULT_USER) {
            //   mUser = (MUser) data.getSerializableExtra("mUser");

            try {
                if (Integer.parseInt(data.getSerializableExtra("mUser").toString()) > 0) {
                    object_id = Integer.parseInt(data.getSerializableExtra("mUser").toString());
                    etStaffincharge.setText(data.getSerializableExtra("mNameUser").toString());
                }
            } catch (Exception e) {
                //  mUser.setUser_id(0);
            }
            // tvManager.setText(mUser.getUser_name());
        } else if (resultCode == Constants.RESULT_GROUP_BY) {
            orderStatus = (List<OrderContractStatus>) data.getSerializableExtra("lvByGroup");
            etSalesProcess.setText(data.getStringExtra("name"));
            SalesProcess = data.getStringExtra("name");
            idGroup = (int) data.getSerializableExtra("idGroup");
            idByGroup = 0;
            etSuccessRate.setText("");
            etStatus.setText("");
        } else if (resultCode == Constants.RESULT_GROUP_BY_GROUP) {
            idByGroup = (int) data.getSerializableExtra("idByGroup");
            Log.d("idByGroup", idByGroup + "");
            etStatus.setText(data.getStringExtra("name"));
            Rate = (int) data.getSerializableExtra("percent");
            etSuccessRate.setText((int) data.getSerializableExtra("percent") + "%");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Year, (Month + 1), Day);
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                OrderActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );

        tpd.setThemeDark(false);
        tpd.setAccentColor(getResources().getColor(R.color.colorApp));
        tpd.setTitle(getString(R.string.choose_time));
        tpd.setCancelText(getString(R.string.no));
        tpd.setOkText(getString(R.string.yes));
        tpd.show(getFragmentManager(), "DatePickerDialog");
        this.Month = Month + 1;
        this.Year = Year;
        this.Day = Day;

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        tvDate.setText((Month) + "/" + Day + "/" + Year + " " + hourOfDay + ":" + minute);
        date = tvDate.getText().toString();
    }

    @Override
    public void buttonPressed(int i, final int position) {
        switch (i) {
            case 1:
                final MContract mContract = Utils.getPriceContract(mOrder.getOrder_detail_contracts().get(position), mContext);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                // ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_contract, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setTitle(mContract.getContract_name());
                dialogBuilder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        double unit_1 = Utils.tryParseDouble(number.getText().toString());
                        double unit_2 = Utils.tryParseDouble(value.getText().toString());
                        double unit_3 = Utils.tryParseDouble(etDiscount.getText().toString());
                        // double unit_4 = Utils.tryParseDouble(totalMoney.getText().toString());
                        String snote = note.getText().toString();
                        orderAdapter.getItem(position).setNumber(unit_1);
                        orderAdapter.getItem(position).setPrice(unit_2);
                        orderAdapter.getItem(position).setNote(snote);
                        orderAdapter.getItem(position).setDiscount_percent(totalMoney1);
                        orderAdapter.getItem(position).setDiscount_price(unit_3);
                        orderAdapter.getItem(position).setDiscount_type(1);
                        //  orderAdapter.notifyDataSetChanged();
                        rvActivities.setAdapter(orderAdapter);
                        LoadOrder();
                        Utils.setListViewHeightBasedOnChildren(rvActivities);

                    }
                });
                dialogBuilder.setNegativeButton(getResources().getString(R.string.no), null);
                number = (EditText) dialogView.findViewById(R.id.number);
                value = (EditText) dialogView.findViewById(R.id.value);
                note = (EditText) dialogView.findViewById(R.id.note);
                total = (TextView) dialogView.findViewById(R.id.total);
                etDiscount = (EditText) dialogView.findViewById(R.id.etDiscount);
                totalMoney = (TextView) dialogView.findViewById(R.id.totalMoney);
                prePayDiscount = mOrder.getOrder_detail_contracts().get(position).getDiscount_price();


                value.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        value.removeTextChangedListener(this);
                        double prePay = Utils.tryParseDouble(editable.toString().replace(",", ""));
                        sum = Utils.tryParseDouble(editable.toString().replace(",", ""));
                        value.setText(Utils.formatCurrency(prePay));
                        value.setSelection(value.getText().length());
                        value.addTextChangedListener(this);

                        total.setText(getString(R.string.total) + " " + Utils.formatCurrency(prePay * Utils.tryParseDouble(number.getText().toString())));
                        totalMoney.setText(getString(R.string.total_amount_i) + " " + Utils.formatCurrency(prePay * Utils.tryParseDouble(number.getText().toString()) - prePayDiscount));
                        totalMoney1 = prePay * Utils.tryParseDouble(number.getText().toString()) - prePayDiscount;
                    }
                });
                etDiscount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        etDiscount.removeTextChangedListener(this);
                        prePayDiscount = Utils.tryParseDouble(editable.toString().replace(",", ""));
                        etDiscount.setText(Utils.formatCurrency(prePayDiscount));
                        etDiscount.setSelection(etDiscount.getText().length());
                        etDiscount.addTextChangedListener(this);
                        totalMoney1 = (Utils.tryParseDouble(value.getText().toString().replace(",", "")) * Utils.tryParseDouble(number.getText().toString().replace(",", ""))) - prePayDiscount;
                        totalMoney.setText(getString(R.string.total_amount_i) + " " + Utils.formatCurrency((Utils.tryParseDouble(value.getText().toString().replace(",", "")) * Utils.tryParseDouble(number.getText().toString().replace(",", ""))) - prePayDiscount));
                    }
                });
                number.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        number.removeTextChangedListener(this);
                        double prePay = Utils.tryParseDouble(editable.toString().replace(",", ""));
                        number.setText(Utils.formatCurrency(prePay));
                        number.setSelection(number.getText().length());
                        number.addTextChangedListener(this);
                        total.setText(getString(R.string.total) + " " + Utils.formatCurrency(prePay * Utils.tryParseDouble(value.getText().toString())));
                        sum = prePay * Utils.tryParseDouble(value.getText().toString());
                        totalMoney.setText(getString(R.string.total_amount_i) + " " + Utils.formatCurrency((prePay * Utils.tryParseDouble(value.getText().toString())) - prePayDiscount));
                        totalMoney1 = prePay * Utils.tryParseDouble(value.getText().toString()) - prePayDiscount;
                    }
                });

                TextInputLayout aTIL = (TextInputLayout) dialogView.findViewById(R.id.input_layout_3);
                aTIL.setHint(mContract.getPrice_name().isEmpty() ? mContext.getString(R.string.value) : mContract.getPrice_name());
                value.setHint(mContract.getPrice_name().isEmpty() ? mContext.getString(R.string.value) : mContract.getPrice_name());
                number.setText((mContract.getNumber().intValue() == 0 ? 1 : mContract.getNumber().intValue()) + "");
                note.setText(mContract.getNote());
                value.setText(Utils.formatCurrency(mContract.getPrice()));
                sum = mContract.getPrice() * mContract.getNumber();
                etDiscount.setText(Utils.formatCurrency(prePayDiscount));
                totalMoney1 = sum - prePayDiscount;

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                break;
            case 3:
                mOrder.getOrder_detail_contracts().remove(position);
                orderAdapter = new OrderAdapter(mContext, mOrder.getOrder_detail_contracts(), this);
                rvActivities.setAdapter(orderAdapter);
                LoadOrder();
                break;
            default:
                break;
        }
    }
}
