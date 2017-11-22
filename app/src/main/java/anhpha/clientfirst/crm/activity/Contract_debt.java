package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_expend;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.Expend;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MActivityItem;
import anhpha.clientfirst.crm.model.MDebt;
import anhpha.clientfirst.crm.model.MOrder;
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

/**
 * Created by Administrator on 10/27/2017.
 */

public class Contract_debt extends BaseAppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Retrofit retrofit;
    int Year, Month, Day;
    private List<Expend> listExpend;
    private Expend expend;
    EditText date;
    EditText note;
    EditText value;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    private Preferences preferences;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    private Toolbar toolbar;
    int add = 1;
    MOrder mOrder = new MOrder();
    MActivityItem mActivityItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.contract_debt);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        date = (EditText) findViewById(R.id.date);
        value = (EditText) findViewById(R.id.value);
        note = (EditText) findViewById(R.id.note);
        preferences = new Preferences(mContext);
        Intent intent = getIntent();
        mOrder = (MOrder) intent.getSerializableExtra("mOrder");
        add = (int) intent.getSerializableExtra("mAdd");
        mActivityItem = (MActivityItem) intent.getSerializableExtra("mDebt");
        tvClientName.setText(mOrder.getOrder_contract_name());
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.get_free);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        retrofit = getConnect();
        if (add == 1) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            String formattedDate = df.format(c.getTime());
            date.setText(formattedDate);
        } else {

            getDebt();
        }
//a
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton1.setChecked(true);
            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setChecked(false);
                radioButton3.setChecked(false);
                radioButton2.setChecked(true);
            }
        });
        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setChecked(false);
                radioButton2.setChecked(false);
                radioButton3.setChecked(true);
            }
        });
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
                value.setText(Utils.formatCurrency(prePay));
                value.setSelection(value.getText().length());
                value.addTextChangedListener(this);

            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                Year = calendar.get(Calendar.YEAR);
                Month = calendar.get(Calendar.MONTH);
                Day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        Contract_debt.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorApp));
                datePickerDialog.setCancelText(getString(R.string.no));
                datePickerDialog.setOkText(getString(R.string.yes));
                datePickerDialog.setTitle(getString(R.string.choose_date));
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });

    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.URL_exchange).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public void getDebt() {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<MDebt>> call = api.get_order_debt_by_id(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), mActivityItem.getOrder_contract_debt_id());
        call.enqueue(new Callback<MAPIResponse<MDebt>>() {
            @Override
            public void onResponse(Call<MAPIResponse<MDebt>> call, Response<MAPIResponse<MDebt>> response) {
                MDebt mDebt = response.body().getResult();
                LogUtils.api("", call, response);
                value.setText(Utils.formatCurrency(mDebt.getValue_debt()) + "");
                if (mDebt.getNote() != null && mDebt.getNote().length() > 0) {
                    note.setText(mDebt.getNote());
                } else {
                    note.setHint("");
                }
                date.setText(mDebt.getDate_debt());
                radioButton1.setChecked(false);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                if (mDebt.getDebt_type_id() == 1) {
                    radioButton1.setChecked(true);
                } else if (mDebt.getDebt_type_id() == 2) {
                    radioButton2.setChecked(true);
                } else if (mDebt.getDebt_type_id() == 3) {
                    radioButton3.setChecked(true);
                }


            }

            @Override
            public void onFailure(Call<MAPIResponse<MDebt>> call, Throwable t) {

            }
        });
    }

    public void funcAddDebt() {
        MDebt mDebt = new MDebt();
        mDebt.setOrder_contract_id(mOrder.getOrder_contract_id());
        mDebt.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
        if (radioButton1.isChecked()) {
            mDebt.setDebt_type_id(1);
        } else if (radioButton2.isChecked()) {
            mDebt.setDebt_type_id(2);
        } else if (radioButton3.isChecked()) {
            mDebt.setDebt_type_id(3);
        } else mDebt.setDebt_type_id(0);
        if (mDebt.getDebt_type_id() > 0 && value.getText().toString() != null) {
            mDebt.setDate_debt(date.getText().toString());
            mDebt.setValue_debt(Utils.tryParseDouble(value.getText().toString()));
            mDebt.setNote((note.getText().toString()));
            mDebt.setDisplay_type(0);
            mDebt.setActivity_type(1);
            ServiceAPI api = retrofit.create(ServiceAPI.class);
            Call<MAPIResponse<MDebt>> call = api.setOrderDebt(preferences.getStringValue(Constants.TOKEN, "")
                    , preferences.getIntValue(Constants.USER_ID, 0)
                    , preferences.getIntValue(Constants.PARTNER_ID, 0)
                    , mDebt
            );
            call.enqueue(new Callback<MAPIResponse<MDebt>>() {
                @Override
                public void onResponse(Call<MAPIResponse<MDebt>> call, Response<MAPIResponse<MDebt>> response) {
                    LogUtils.api(TAG, call, (response.body()));
                    TokenUtils.checkToken(mContext, response.body().getErrors());
                    Utils.showDialogSuccess(mContext, R.string.srtDone);
                }
                @Override
                public void onFailure(Call<MAPIResponse<MDebt>> call, Throwable t) {
                    Utils.showDialogSuccess(mContext, R.string.srtFalse);
                }
            });
        } else if(value.getText().toString() == null && value.getText().length()==0){
            Toast.makeText(mContext,R.string.srtAmount, Toast.LENGTH_SHORT).show();
        }else Toast.makeText(mContext,R.string.srtPayment, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_edit_history_order, menu);
        if (add == 0) {
            menu.getItem(0).setVisible(false);
            value.setEnabled(false);
            note.setEnabled(false);
            value.setTextColor(getResources().getColor(R.color.colorBlack));
            note.setTextColor(getResources().getColor(R.color.color));
        } else {
            menu.getItem(0).setVisible(true);
            value.setEnabled(true);
            note.setEnabled(true);
        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.actionDone:
                funcAddDebt();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Year, (Month + 1), Day);
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                Contract_debt.this,
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
        // tvDate.setText((Month) + "/" + Day + "/" + Year + " " + hourOfDay + ":" + minute);
        date.setText((Month) + "/" + Day + "/" + Year + " " + hourOfDay + ":" + minute);
    }
}
