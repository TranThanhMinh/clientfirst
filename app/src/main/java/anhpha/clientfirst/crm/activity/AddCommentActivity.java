package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MActivityItem;
import anhpha.clientfirst.crm.model.MComment;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.Result;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 10/25/2017.
 */

public class AddCommentActivity extends BaseAppCompatActivity {
    private Toolbar toolbar;
    int add = 1;
    int edit = 0;
    MOrder mOrder = new MOrder();
    private EditText tvNote;
    private TextView tvClientName;
    private Preferences preferences;
    private Retrofit retrofit;
    MActivityItem mActivityItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_add_comment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvNote = (EditText) findViewById(R.id.tvNote);
        tvClientName = (TextView) findViewById(R.id.tvName);

        preferences = new Preferences(mContext);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.note);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        retrofit = getConnect();
        Intent intent = getIntent();
        mOrder = (MOrder) intent.getSerializableExtra("mOrder");
        add = (int) intent.getSerializableExtra("mAdd");
        mActivityItem = (MActivityItem) intent.getSerializableExtra("Comment");
        if (add == 1) {
            edit = 1;
            tvClientName.setText(mOrder.getOrder_contract_name());
        } else {
            edit = 0;
            tvClientName.setText(mOrder.getOrder_contract_name());
            getComment();
        }
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.URL_user).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public void getComment() {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<MComment>> call = api.get_user_comment(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), mActivityItem.getUser_comment_id());
        call.enqueue(new Callback<MAPIResponse<MComment>>() {
            @Override
            public void onResponse(Call<MAPIResponse<MComment>> call, Response<MAPIResponse<MComment>> response) {
                LogUtils.api("", call, response);
                MComment comment = response.body().getResult();
                tvNote.setText(comment.getContent_comment());
            }

            @Override
            public void onFailure(Call<MAPIResponse<MComment>> call, Throwable t) {

            }
        });
    }

    public void funcAddComment() {
        MComment mComment = new MComment();
        mComment.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
        mComment.setOrder_contract_id(mOrder.getOrder_contract_id());
        mComment.setContent_comment(tvNote.getText().toString());
        if (edit == 0) {
            mComment.setUser_comment_id(mActivityItem.getUser_comment_id());
        } else mComment.setUser_comment_id(0);

        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<Result> call = api.set_user_comment(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), mComment);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                LogUtils.api("", call, response);
                if (response.body().getHasErrors() == false) {
                    Utils.showDialogSuccess(mContext, R.string.srtDone);
                } else Utils.showDialogSuccess(mContext, R.string.srtFalse);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_edit_history_order, menu);
        if (add == 0) {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.actionDone)
                    menu.getItem(i).setVisible(false);
                if (menu.getItem(i).getItemId() == R.id.edit)
                    menu.getItem(i).setVisible(true);
            }

//            tvCosts.setEnabled(false);
            tvNote.setEnabled(false);
//            tvCosts.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNote.setTextColor(getResources().getColor(R.color.color));
        } else {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.actionDone)
                    menu.getItem(i).setVisible(true);
                if (menu.getItem(i).getItemId() == R.id.edit)
                    menu.getItem(i).setVisible(false);
            }
//            tvCosts.setEnabled(true);
            tvNote.setEnabled(true);
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
                funcAddComment();
                break;
            case R.id.edit:
                add = 1;
                invalidateOptionsMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
