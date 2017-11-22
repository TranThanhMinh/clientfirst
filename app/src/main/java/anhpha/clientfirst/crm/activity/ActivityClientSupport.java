package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_client_support;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MUser;
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
 * Created by Administrator on 9/26/2017.
 */

public class ActivityClientSupport extends BaseAppCompatActivity implements adapter_client_support.Click{
    private Toolbar toolbar;
    private Retrofit retrofit;
    private Preferences preferences;
    MClient mClient = new MClient();
    private List<MUser> list;
    private ListView lvClientSupport;
    TextView tvName, tvAddress;
    List<MId> mIds = new ArrayList<>();
    List<Users> mUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_client_support);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvClientSupport = (ListView) findViewById(R.id.lvClientSupport);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        preferences = new Preferences(mContext);
        Intent intent = getIntent();
        mClient = (MClient) intent.getSerializableExtra("mClient");
        Log.d("AAA", mClient.getClient_id() + "");
        tvName.setText(mClient.getClient_name());
        tvAddress.setText(mClient.getDetail());
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.support_personnel);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        retrofit = getConnect();
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_user)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Override
    protected void onResume() {
        super.onResume();
        box.showLoadingLayout();
        getClientSupport();
    }

    public void funcAddSupport(Users list) {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<Result> call = api.set_users_manager_delail(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), mClient.getClient_id(), list);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                LogUtils.api("", call, response);
                if (response.body().getHasErrors() == false) {
                    getClientSupport();
                } else {
                    Toast.makeText(ActivityClientSupport.this, "Loi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
    public void funcDeleteSupport(int id) {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<Result> call = api.delete_user_manager_delail(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), mClient.getClient_id(), id);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                LogUtils.api("", call, response);
                if (response.body().getHasErrors() == false) {
                    getClientSupport();
                } else {
                    Toast.makeText(ActivityClientSupport.this, "Loi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }
    public void getClientSupport() {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<MUser>>> call = api.get_users_manager_delail(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), mClient.getClient_id());
        call.enqueue(new Callback<MAPIResponse<List<MUser>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<MUser>>> call, Response<MAPIResponse<List<MUser>>> response) {
                TokenUtils.checkToken(ActivityClientSupport.this, response.body().getErrors());
                LogUtils.api("", call, response);
                box.hideAll();
                list = response.body().getResult();
                if (list != null) {
                    adapter_client_support adapter = new adapter_client_support(ActivityClientSupport.this, list,ActivityClientSupport.this);
                    lvClientSupport.setAdapter(adapter);
                } else lvClientSupport.setAdapter(null);
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<MUser>>> call, Throwable t) {

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
                //
                startActivityForResult(new Intent(mContext, UsersActivity.class).putExtra("mIds", (Serializable) mIds), Constants.RESULT_USERS);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RESULT_USERS) {
            mIds = new ArrayList<>();
            mIds = (List<MId>) data.getSerializableExtra("mIds");
            if (mIds != null && mIds.size() > 0) {

                Users user = new Users();
                user.setUser_ids(mIds);
                funcAddSupport(user);
            }
        }
    }

    @Override
    public void onclick(int id) {
        funcDeleteSupport(id);
    }
}
