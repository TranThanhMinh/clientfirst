package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_report_focus;
import anhpha.clientfirst.crm.charting.charts.PieChart;
import anhpha.clientfirst.crm.charting.data.Entry;
import anhpha.clientfirst.crm.charting.data.PieData;
import anhpha.clientfirst.crm.charting.data.PieDataSet;
import anhpha.clientfirst.crm.charting.data.PieEntry;
import anhpha.clientfirst.crm.charting.formatter.PercentFormatter;
import anhpha.clientfirst.crm.charting.highlight.Highlight;
import anhpha.clientfirst.crm.charting.interfaces.datasets.IDataSet;
import anhpha.clientfirst.crm.charting.listener.OnChartValueSelectedListener;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MColor;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MReport;
import anhpha.clientfirst.crm.model.MReportFocus;
import anhpha.clientfirst.crm.model.MRequestBody;
import anhpha.clientfirst.crm.model.MRequestFocus;
import anhpha.clientfirst.crm.model.MUser;
import anhpha.clientfirst.crm.model.User;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 8/30/2017.
 */

public class ReportFocusActivity extends BaseAppCompatActivity implements OnChartValueSelectedListener {
    private RecyclerView rvView;
    private Retrofit retrofit;
    private Preferences preferences;
    private List<MReportFocus> list;
    private List<User> lvUser;
    String toDate;
    String fromDate;
    private PieChart mChart;
    private TextView tvAmount, tvTitle, tvSelect;
    private Toolbar toolbar;
    private List<MId> mIds;
    Date fromDate1;
    Calendar calendar;
    Date toDate1;
    Date today1;
    DateFormat from_sdf = new SimpleDateFormat("MM/dd/yyyy 00:00");
    DateFormat to_sdf = new SimpleDateFormat("MM/dd/yyyy 23:59");
    double amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_chart_focus);
        rvView = (RecyclerView) findViewById(R.id.rvView);
        mChart = (PieChart) findViewById(R.id.chart1);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        tvSelect = (TextView) findViewById(R.id.tvSelect);
        preferences = new Preferences(mContext);
        toolbar = (Toolbar) findViewById(R.id.include);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_activity_report);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        list = new ArrayList<>();
        lvUser = new ArrayList<>();
        User user = new User();
        user.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
        lvUser.add(user);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvView.setHasFixedSize(true);
        rvView.setLayoutManager(manager);
        retrofit = getConnect();
        today1 = new Date();

        calendar = Calendar.getInstance();
        calendar.setTime(today1);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        toDate1 = calendar.getTime();

        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, 0);
        fromDate1 = calendar.getTime();

        toDate = to_sdf.format(toDate1);
        fromDate = from_sdf.format(fromDate1);
//        toDate = Utils.getToDate();
//        fromDate = Utils.getFromDate();
        Log.d("date", fromDate + " == " + toDate);
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_partner)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    //    public void funcLoad(){
//
//    }
    @Override
    protected void onResume() {
        super.onResume();
        box.showLoadingLayout();
        tvAmount.setText("");
        tvSelect.setText("");
        amount = 0;
        MRequestFocus mRequestBody = new MRequestFocus();
        mRequestBody.setFrom_date(fromDate);
        mRequestBody.setTo_date(toDate);
        mRequestBody.setUser_ids(lvUser);
        getReportFocus(mRequestBody);
        Log.d("date", mRequestBody.toString());
    }

    public void getReportFocus(MRequestFocus mRequestBody) {
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<MReportFocus>>> call = serviceAPI.get_client_focus_status(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), preferences.getStringValue(Constants.TOKEN, ""), mRequestBody);
        call.enqueue(new Callback<MAPIResponse<List<MReportFocus>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<MReportFocus>>> call, Response<MAPIResponse<List<MReportFocus>>> response) {
                list = response.body().getResult();
                TokenUtils.checkToken(ReportFocusActivity.this, response.body().getErrors());
                box.hideAll();
//                LogUtils.api("", call, response);
                setData(list);
                if (list != null && list.size() > 0) {
                    adapter_report_focus adapter = new adapter_report_focus(ReportFocusActivity.this, list);
                    rvView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<MReportFocus>>> call, Throwable t) {

            }
        });
    }

    private void setData(List<MReportFocus> mReports) {

        // tvSelect.setText("");
        //mChart.clearValues();
        mChart.clear();
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < mReports.size(); i++) {
            entries.add(new PieEntry((float) (mReports.get(i).getNumClient()), mReports.get(i).getFocusStatusName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "AP DMS");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < mReports.size(); i++) {

            colors.add(Color.rgb(42, 188, 186));
            colors.add(Color.rgb(250, 172, 88));
            colors.add(Color.rgb(11, 11, 97));
            colors.add(Color.rgb(223, 1, 1));
            amount = amount + mReports.get(i).getNumClient();
        }
        tvAmount.setText(Utils.formatCurrency(amount)  + "");

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());

        mChart.setData(data);

        mChart.setExtraOffsets(5, 10, 5, 5);
        // add a selection listener
        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        for (IDataSet<?> set : mChart.getData().getDataSets())
            set.setDrawValues(!set.isDrawValuesEnabled());

        mChart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.calendar) {
            startActivityForResult(new Intent(mContext, CalendarActivity.class), Constants.RESULT_CALENDAR);
        }
        if (id == R.id.user) {
            startActivityForResult(new Intent(mContext, UsersActivity.class), Constants.RESULT_USERS);
        }
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (resultCode == Constants.RESULT_USERS) {
            mIds = new ArrayList<>();

            mIds = (List<MId>) data.getSerializableExtra("mIds");
            if (mIds != null && mIds.size() > 0) {
                lvUser = new ArrayList<>();
                for (MId mId : mIds) {
                    User user = new User();
                    user.setUser_id(mId.getId());
                    lvUser.add(user);
                }
            }

        } else if (resultCode == Constants.RESULT_CALENDAR) {
            toDate = data.getStringExtra("toDate");
            fromDate = data.getStringExtra("fromDate");
            tvTitle.setText(data.getStringExtra("tvDate"));
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        double percent = list.get((int) h.getX()).getNumClient() / amount * 100;
        tvSelect.setText(list.get((int) h.getX()).getFocusStatusName() + "\n" + Utils.formatCurrency(list.get((int) h.getX()).getNumClient()) + "\n" + Utils.formatPercent(percent) + " %");

    }

    @Override
    public void onNothingSelected() {
        tvSelect.setText("");
    }
}
