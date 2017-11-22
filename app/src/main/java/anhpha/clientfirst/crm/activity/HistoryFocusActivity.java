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

import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.GroupTargetAdapter;
import anhpha.clientfirst.crm.adapter.HistoryFocusAdapter;
import anhpha.clientfirst.crm.adapter.TargetAdapter;
import anhpha.clientfirst.crm.adapter.adapter_Focus;
import anhpha.clientfirst.crm.adapter.adapter_History_focus;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.AddFocus;
import anhpha.clientfirst.crm.model.Focus;
import anhpha.clientfirst.crm.model.FocusGroup;
import anhpha.clientfirst.crm.model.Focus_date;
import anhpha.clientfirst.crm.model.Focus_target;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.Result;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static anhpha.clientfirst.crm.utils.LogUtils.*;

/**
 * Created by Administrator on 8/2/2017.
 */

public class HistoryFocusActivity extends BaseAppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView lvHistory;
    private Retrofit retrofit;
    private Preferences preferences;
    private List<Focus_date> lv_focus;
    private Bundle b;
    private Spinner spTarget, spGroupTarget;
    private TextView tvName, tvAddress, tvNumberDate, tvCustomer;
    private int Target, Type, object_id;


    private Calendar cal;
    private int setDate;
    //    private DatePickerDialog date;
    MClient mClient = new MClient();
    int visibleItemCount = 0;
    int totalItemCount = 0;
    int pastVisibleItems = 0;
    private List<Focus_target> list;
    private List<FocusGroup> list_target;
    private EditText editNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_history_focus);
        box = new DynamicBox(this, R.layout.activity_history_focus);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        editNote = (EditText) findViewById(R.id.editNote);
        tvCustomer = (TextView) findViewById(R.id.tvCustomer);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvNumberDate = (TextView) findViewById(R.id.tvNumberDate);
        lvHistory = (RecyclerView) findViewById(R.id.lvHistory);
        spTarget = (Spinner) findViewById(R.id.spTarget);
        spGroupTarget = (Spinner) findViewById(R.id.spGroupTarget);


        Intent intent = getIntent();
        mClient = (MClient) intent.getSerializableExtra("mClient");
        tvName.setText(mClient.getClient_name());
        tvAddress.setText(mClient.getAddress());
        preferences = new Preferences(mContext);
        b = getIntent().getExtras();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.srtFocus);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvHistory.setHasFixedSize(true);
        lvHistory.setLayoutManager(manager);
        object_id = preferences.getIntValue(Constants.USER_ID, 0);
        Type = 2;


//        String[] arr = {"Gặp", "Xin cuộc hẹn", "Khác"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (
//                        this,
//                        R.layout.item_sp,
//                        arr
//                );
//        //phải gọi lệnh này để hiển thị danh sách cho Spinner
//        adapter.setDropDownViewResource
//                (R.layout.item_sp1);
//        //Thiết lập adapter cho Spinner
//        spTarget.setAdapter(adapter);

//        editFromDate.setEnabled(false);
//        editToDate.setEnabled(false);
//        editFromDate.setOnClickListener(this);
//        editToDate.setOnClickListener(this);
        retrofit = getConnect();
        tvCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(mContext, ChooseUsersActivity.class), Constants.RESULT_USER);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RESULT_USER) {

            try {
                object_id = Integer.parseInt(data.getSerializableExtra("mUser").toString());
            } catch (Exception e) {
                object_id = 0;
            }

            if (object_id > 0) {
                tvCustomer.setText(data.getSerializableExtra("mNameUser").toString());
            } else {
                object_id = preferences.getIntValue(Constants.USER_ID, 0);
                tvCustomer.setText("Chọn");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        box.showLoadingLayout();
        getTarget();
        getHistoryFocus(mClient.getClient_id());
//        lvHistory_focus.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if(dy > 0) //check for scroll down
//                {
//                    visibleItemCount = manager.getChildCount();
//                    totalItemCount = manager.getItemCount();
//                    pastVisibleItems = manager.findFirstVisibleItemPosition();
//
//
//                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
//                        {
//
//                            Log.d("...", totalItemCount+"");
//                    }
//                }
//            }
//        });
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void funcAddFocus(int i) {
        List<AddFocus> lvAddfocus = new ArrayList<>();
        AddFocus focus = new AddFocus();
        focus.setClient_id(i);
        focus.setFocus_type_id(Type);
        focus.setFocus_target_id(Target);
        focus.setNote_start(editNote.getText().toString());
        focus.setUser_id(object_id);
        focus.setPartner_id(preferences.getIntValue(Constants.PARTNER_ID, 0));


        if (tvNumberDate.getText().toString().equals("")) {
            Toast.makeText(this, R.string.srtNumberdate, Toast.LENGTH_SHORT).show();
        } else {
            focus.setNumber_date(Integer.parseInt(tvNumberDate.getText().toString()));
            lvAddfocus.add(focus);
        }
        if (lvAddfocus.size() > 0) {
            ServiceAPI apiFocus = retrofit.create(ServiceAPI.class);
            Call<Result> result_focus = apiFocus.set_clients_focus(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), preferences.getStringValue(Constants.TOKEN, ""), lvAddfocus);
            result_focus.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    api("", call, response);
                    if (response.body().getHasErrors() == false) {
                        Toast.makeText(mContext, R.string.srtDone, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(mContext, R.string.srtFalse, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    api("", call, t.toString());
                    Toast.makeText(mContext, R.string.srtFalse, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void getTarget() {
        ServiceAPI target = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<Focus_target>>> result_target = target.get_client_focus_target(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), preferences.getStringValue(Constants.TOKEN, ""));
        result_target.enqueue(new Callback<MAPIResponse<List<Focus_target>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<Focus_target>>> call, Response<MAPIResponse<List<Focus_target>>> response) {
                api("", call, response);
                list = response.body().getResult();
                    //list group target.size >0 thi Target = list.get(position).getFocus_target_id()
                if (list != null && list.size() > 0) {
                    TargetAdapter adapter = new TargetAdapter(HistoryFocusActivity.this, list);
                    spGroupTarget.setAdapter(adapter);
                    Target = list.get(0).getFocus_target_id();
                    list_target = new ArrayList<FocusGroup>();
                    list_target = list.get(0).getFocusGroup();
                    if (list_target != null && list_target.size() > 0) {
                        GroupTargetAdapter adapter0 = new GroupTargetAdapter(HistoryFocusActivity.this, list_target);
                        spTarget.setAdapter(adapter0);
                        Target = list_target.get(0).getFocus_target_id();
                    }
                }
                spGroupTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        list_target = new ArrayList<FocusGroup>();
                        Target = list.get(i).getFocus_target_id();
                        list_target = list.get(i).getFocusGroup();
                        if (list_target != null && list_target.size() > 0) {
                            GroupTargetAdapter adapter = new GroupTargetAdapter(HistoryFocusActivity.this, list_target);
                            spTarget.setAdapter(adapter);
                            Target = list_target.get(0).getFocus_target_id();
                        }else  spTarget.setAdapter(null);

                        spTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                Target = list_target.get(i).getFocus_target_id();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<Focus_target>>> call, Throwable t) {

            }
        });
    }

    public void getHistoryFocus(int client_id) {
        ServiceAPI focus = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<Focus_date>>> result_focus = focus.get_clients_focus(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), client_id, preferences.getStringValue(Constants.TOKEN, ""));
        result_focus.enqueue(new Callback<MAPIResponse<List<Focus_date>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<Focus_date>>> call, Response<MAPIResponse<List<Focus_date>>> response) {
                box.hideAll();
                TokenUtils.checkToken(mContext, response.body().getErrors());
                api("", call, response);
                if (response.body() != null) {
                    lv_focus = response.body().getResult();
                    adapter_History_focus adapter_focus = new adapter_History_focus(HistoryFocusActivity.this, lv_focus);
                    lvHistory.setAdapter(adapter_focus);
                } else Toast.makeText(mContext, "No data", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<MAPIResponse<List<Focus_date>>> call, Throwable t) {
                api("minh", call, t.toString());
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        if (v == editFromDate) {
//            setDate = 1;
//            Calendar now = Calendar.getInstance();
//            date = DatePickerDialog.newInstance(this,
//                    now.get(Calendar.YEAR),
//                    now.get(Calendar.MONTH),
//                    now.get(Calendar.DAY_OF_MONTH)
//            );
//            date.show(getFragmentManager(), "Datepickerdialog");
//        }
//        if (v == editToDate) {
//            setDate = 2;
//            Calendar now = Calendar.getInstance();
//            date = DatePickerDialog.newInstance(this,
//                    now.get(Calendar.YEAR),
//                    now.get(Calendar.MONTH),
//                    now.get(Calendar.DAY_OF_MONTH)
//            );
//            date.show(getFragmentManager(), "Datepickerdialog");
//        }
//    }
//
//    @Override
//    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
//        cal = Calendar.getInstance();
//        cal.set(year, monthOfYear, dayOfMonth);
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        if (setDate == 1)
//            editFromDate.setText(sdf.format(cal.getTime()));
//        else editToDate.setText(sdf.format(cal.getTime()));
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_edit_history_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionDone) {
            funcAddFocus(mClient.getClient_id());
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
