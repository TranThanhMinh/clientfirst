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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.TrackingValueDefautAdapter;
import anhpha.clientfirst.crm.adapter.ValueDefautAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MEmail;
import anhpha.clientfirst.crm.model.MOrder;
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

public class EmailContractActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MEmail>>, View.OnClickListener {

    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.tvShow)
    TextView tvShow;
    MEmail mEmail = new MEmail();
    MOrder mClient = new MOrder();
    Retrofit retrofit;
    private RecyclerView lvTracking;
    Preferences preferences;
    private List<Tracking_value_defaults> listTracking = new ArrayList<>();
    private List<Tracking_value_defaults> listTracking_userEmail = new ArrayList<>();
    UserEmail userEmail;
    int display = 1;
    boolean isHide = true;
    SwitchCompat switchCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_email);
        lvTracking = (RecyclerView) findViewById(R.id.lvTracking);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_email);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvTracking.setHasFixedSize(true);
        lvTracking.setLayoutManager(manager);
        Intent intent = getIntent();
        mEmail = (MEmail) intent.getSerializableExtra("mEmail");
        mClient = (MOrder) intent.getSerializableExtra("mOrder");
        tvClientName.setText(mClient.getOrder_contract_name());
//        tvAddress.setText(mClient.getAddress());
//        if (mClient.getAddress() != null && !mClient.getAddress().isEmpty()) {
//            tvAddress.setVisibility(View.VISIBLE);
//        }
        retrofit = getConnect();
         switchCompat = (SwitchCompat) findViewById(R.id
                .switchButton);

        if (mEmail == null) {
            mEmail = new MEmail();
            tvShow.setVisibility(View.GONE);
            getTracking_value_default();
            isHide = true;
            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b == true) {
                        display = 0;
                        //  Toast.makeText(mContext, " chon", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(mContext, "khong chon", Toast.LENGTH_SHORT).show();
                        display = 1;
                    }
                }
            });
        }
        if (mEmail.getUser_email_id() > 0) {
            Log.d("id_mail", mEmail.getOrder_contract_id() + "");
            isHide = false;
            tvShow.setVisibility(View.VISIBLE);
            tvShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            etContent.setText(mEmail.getContent_email());
            if (mEmail.getDisplay_type() == 0)
                switchCompat.setChecked(true);
            else switchCompat.setChecked(false);
            display=mEmail.getDisplay_type();
            etContent.setFocusable(false);
            getUserEmail();
        }


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
                mEmail.setClient_id(mClient.getClient_id());
                mEmail.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
                mEmail.setContent_email(etContent.getText().toString());
                mEmail.setLatitude(mLastLocation.getLatitude());
                mEmail.setLongitude(mLastLocation.getLongitude());
                mEmail.setValues_default(list);
                mEmail.setOrder_contract_id(mClient.getOrder_contract_id());
                mEmail.setUser_email_id(mEmail.getUser_email_id());
                mEmail.setDisplay_type(display);
                mEmail.setActivity_type(1);
                GetRetrofit().create(ServiceAPI.class)
                        .setUserEmail(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                , mClient.getClient_id()
                                , mEmail)
                        .enqueue(this);
                box.showLoadingLayout();
                LogUtils.d(TAG, "getUserActivities ", "start");
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.edit:
                if (preferences.getIntValue(Constants.USER_ID, 0) == userEmail.getUserId()) {
                    tvShow.setVisibility(View.GONE);
                    etContent.setFocusable(true);
                    etContent.setFocusableInTouchMode(true);
                    etContent.requestFocus();
                    isHide = true;
                    invalidateOptionsMenu();
                    getTracking_value_default();
                    switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b == true) {
                                display = 0;
                                //  Toast.makeText(mContext, " chon", Toast.LENGTH_SHORT).show();
                            } else {
                                //Toast.makeText(mContext, "khong chon", Toast.LENGTH_SHORT).show();
                                display = 1;
                            }
                        }
                    });

                }else
                    Toast.makeText(mContext, R.string.edit_activity, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_user)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void getTracking_value_default() {
        ServiceAPI apiTracking = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<Tracking_value_defaults>>> tracking_value_defaults = apiTracking.getTracking_value_defaults(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), 7, preferences.getStringValue(Constants.TOKEN, ""));
        tracking_value_defaults.enqueue(new Callback<MAPIResponse<List<Tracking_value_defaults>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<Tracking_value_defaults>>> call, Response<MAPIResponse<List<Tracking_value_defaults>>> response) {
                listTracking = response.body().getResult();
                LogUtils.api("", call, response);
                if (listTracking_userEmail != null && listTracking_userEmail.size() > 0) {
                    List<Tracking_value_defaults> list = new ArrayList<>();
                    for (Tracking_value_defaults email : listTracking) {
                        for (int i = 0; i < listTracking_userEmail.size(); i++) {
                            if (email.getTracking_value_default_id() == listTracking_userEmail.get(i).getTracking_value_default_id()) {
                                email.setTracking(true);
                                list.add(email);
                                i = listTracking_userEmail.size();
                            }
                            else {
                                if(i == listTracking_userEmail.size()-1) {
                                    email.setTracking(false);
                                    list.add(email);
                                }

                            }
                        }

                    }
                    listTracking = list;
                    TrackingValueDefautAdapter adapte = new TrackingValueDefautAdapter(EmailContractActivity.this, listTracking);
                    lvTracking.setAdapter(adapte);
                } else {
                    TrackingValueDefautAdapter adapte = new TrackingValueDefautAdapter(EmailContractActivity.this, listTracking);
                    lvTracking.setAdapter(adapte);
                }
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<Tracking_value_defaults>>> call, Throwable t) {
                LogUtils.api("", call, t.toString());
            }
        });
    }

    public void getUserEmail() {
        ServiceAPI apiTracking = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<UserEmail>> user_email = apiTracking.get_user_email(preferences.getIntValue(Constants.USER_ID, 0), mEmail.getUser_email_id(), preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.PARTNER_ID, 0));
        user_email.enqueue(new Callback<MAPIResponse<UserEmail>>() {
            @Override
            public void onResponse(Call<MAPIResponse<UserEmail>> call, Response<MAPIResponse<UserEmail>> response) {
                LogUtils.api("", call, response);
                userEmail=response.body().getResult();
                listTracking_userEmail = response.body().getResult().getValuesDefault();
                // listTracking = response.body().getResult().getValuesDefault();
                ValueDefautAdapter adapte = new ValueDefautAdapter(EmailContractActivity.this, listTracking_userEmail);
                lvTracking.setAdapter(adapte);
            }

            @Override
            public void onFailure(Call<MAPIResponse<UserEmail>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResponse(Call<MAPIResponse<MEmail>> call, Response<MAPIResponse<MEmail>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext, response.body().getErrors());
        if (response.body().isHasErrors()) {
            Utils.showError(coordinatorLayout, R.string.email_fail);
        } else {
            Utils.showDialogSuccess(mContext, R.string.email_done);
        }

    }

    @Override
    public void onFailure(Call<MAPIResponse<MEmail>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
        Utils.showError(coordinatorLayout, R.string.email_fail);

    }

    @Override
    public void onClick(View view) {
    }
}
