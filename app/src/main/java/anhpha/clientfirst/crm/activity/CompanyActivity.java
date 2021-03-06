package anhpha.clientfirst.crm.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_company_exist;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;


import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.CompanyExist;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.StringUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static anhpha.clientfirst.crm.R.id.container;

/**
 * Created by Administrator on 9/22/2017.
 */

public class CompanyActivity extends BaseAppCompatActivity implements adapter_company_exist.Click {
    private Toolbar toolbar;
    private RecyclerView lvCompanyExist;
    private Retrofit retrofit;
    Preferences preferences;
    List<CompanyExist> list;

    private int client_id;
    private String name_client;
    List<CompanyExist> list_search;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private Timer timer = new Timer();
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.actitvity_company);
        box = new DynamicBox(this, R.layout.actitvity_company);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvCompanyExist = (RecyclerView) findViewById(R.id.lvCompanyExist);
        preferences = new Preferences(mContext);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.add_client_company);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvCompanyExist.setHasFixedSize(true);
        lvCompanyExist.setLayoutManager(manager);
        retrofit = getConnect();
        getCompanyExist();
        box.showLoadingLayout();

    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void getCompanyExist() {
        list = new ArrayList<>();
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<CompanyExist>>> call = api.get_clients_parent(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), 0);
        call.enqueue(new Callback<MAPIResponse<List<CompanyExist>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<CompanyExist>>> call, Response<MAPIResponse<List<CompanyExist>>> response) {
                TokenUtils.checkToken(CompanyActivity.this, response.body().getErrors());
                LogUtils.api("", call, response);
                box.hideAll();
                list = response.body().getResult();
                if (list != null && list.size() > 0) {

                    adapter_company_exist adapter = new adapter_company_exist(CompanyActivity.this, list, CompanyActivity.this);
                    lvCompanyExist.setAdapter(adapter);
                } else lvCompanyExist.setAdapter(null);
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<CompanyExist>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(int id, String name) {
        client_id = id;
        name_client = name;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done_search, menu);
        mSearchAction = menu.findItem(R.id.find);

        return super.onCreateOptionsMenu(menu);
    }

    protected void handleMenuSearch() {
        ActionBar action = getSupportActionBar(); //get the actionbar

        if (isSearchOpened) { //test if the search is open
            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar
            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);
            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.ic_dms_46));
            isSearchOpened = false;
            doSearch("");
        } else { //open the search entry
            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title
            edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button

            edtSeach.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(final Editable editable) {
                    timer.cancel();
                    timer =new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    doSearch(editable.toString());
                                }
                            });
                        }
                    },1000*2);

                }
            });

            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.mipmap.ic_delete_search));

            isSearchOpened = true;
        }
    }

    private void doSearch(String search) {
        list_search = new ArrayList<>();
        if (search != null && search.length() > 0) {
            for (CompanyExist companyExist : list) {
                if ((companyExist.getClientName().toLowerCase()).contains((search).toLowerCase())) {
                    list_search.add(companyExist);
                }
            }
            adapter_company_exist adapter = new adapter_company_exist(CompanyActivity.this, list_search, CompanyActivity.this);
            lvCompanyExist.setAdapter(adapter);
        } else {
            adapter_company_exist adapter = new adapter_company_exist(CompanyActivity.this, list, CompanyActivity.this);
            lvCompanyExist.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                setResult(Constants.RESULT_COMPANY, new Intent().putExtra("client_id", (Serializable) client_id).putExtra("name_client", (Serializable) name_client));
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
            case R.id.find:
                handleMenuSearch();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (isSearchOpened) {
            handleMenuSearch();
            return;
        }
        super.onBackPressed();
    }
}
