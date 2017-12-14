package anhpha.clientfirst.crm.activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_cost;
import anhpha.clientfirst.crm.adapter.adapter_expend;
import anhpha.clientfirst.crm.adapter.adapter_orders;
import anhpha.clientfirst.crm.adapter.adapter_photo_expend;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.cropper.CropImage.CropImage;
import anhpha.clientfirst.crm.cropper.CropImage.CropImage_View;
import anhpha.clientfirst.crm.cropper.CropImage.FileUtils;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.Cost;
import anhpha.clientfirst.crm.model.Expend;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MActivityItem;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MCost;
import anhpha.clientfirst.crm.model.MExpend;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.MOrders;
import anhpha.clientfirst.crm.model.Photo;
import anhpha.clientfirst.crm.model.Result_upload_photo;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Administrator on 9/5/2017.
 */

public class AddCostsActivity extends BaseAppCompatActivity implements View.OnClickListener, adapter_cost.Click, adapter_photo_expend.funcDelete_lvImage, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, adapter_orders.Onclick {
    private EditText tvCosts, tvNote;
    private Toolbar toolbar;
    private ImageView imSelect_upload_photo;
    private Uri selectedImage;
    private Retrofit retrofit, retrofit_photo;
    private Preferences preferences;
    private List<Cost> listCost;
    private List<Expend> listExpend;
    private RecyclerView lvCost, lvPhoto, lvOrder;
    private int Expend_group_type_id = 0, Expend_id, Expend_type_id = 0, Status_id;
    MClient mClient = new MClient();
    MActivityItem mActivityItem = new MActivityItem();
    private double prePay;
    private List<Photo> lvImage = new ArrayList<>();
    private boolean result;
    private TextView tvName, tvAddress, tvImage;
    EditText tvDate;
    private int add;
    private int check;
    private Expend expend;
    LinearLayout coordinatorLayout;
    int Year, Month, Day, order_contract_id = 0;
    private TextView tvContract;
    SwitchCompat switchCompat;
    TextView tvShow;
    private int edit;
    private int id;
    adapter_orders adapter;
    LinearLayout layout_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_costs);
        coordinatorLayout = (LinearLayout) findViewById(R.id.coordinatorLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvCost = (RecyclerView) findViewById(R.id.lvCost);
        lvPhoto = (RecyclerView) findViewById(R.id.lvPhoto);
        lvOrder = (RecyclerView) findViewById(R.id.lvOrder);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvShow = (TextView) findViewById(R.id.tvShow);
        tvImage = (TextView) findViewById(R.id.tvImage);
        tvContract = (TextView) findViewById(R.id.tvContract);
        tvDate = (EditText) findViewById(R.id.tvDate);
        layout_order = (LinearLayout) findViewById(R.id.layout_order);
        switchCompat = (SwitchCompat) findViewById(R.id
                .switchButton);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvCost.setHasFixedSize(true);
        lvCost.setLayoutManager(manager);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL
                , false);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvPhoto.setHasFixedSize(true);
        lvPhoto.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL
                , false);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        lvOrder.setHasFixedSize(true);
        lvOrder.setLayoutManager(layoutManager1);
        mClient = (MClient) getIntent().getSerializableExtra("mClient");
        add = (int) getIntent().getSerializableExtra("mAdd");
        mActivityItem = (MActivityItem) getIntent().getSerializableExtra("mExpend");
        tvName.setText(mClient.getClient_name());
        tvAddress.setText(mClient.getAddress());
        imSelect_upload_photo = (ImageView) findViewById(R.id.imSelect_upload_photo);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (add == 0)
                getSupportActionBar().setTitle(R.string.costs);
            else getSupportActionBar().setTitle(R.string.addCost);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }

        tvCosts = (EditText) findViewById(R.id.tvCosts);
        tvNote = (EditText) findViewById(R.id.tvNote);
        imSelect_upload_photo.setOnClickListener(this);
        tvCosts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                tvCosts.removeTextChangedListener(this);
                prePay = Utils.tryParseDouble(editable.toString().replace(",", ""));
                Log.d("prePay", prePay + "");
                tvCosts.setText(Utils.formatCurrency(prePay));

//                textView22.setText(Utils.formatCurrency((prePay)));
//                mOrder.setPrepay(prePay);
//                textView23.setText(Utils.formatCurrency(totalAmountContract - prePay));
                tvCosts.setSelection(tvCosts.getText().length());
                tvCosts.addTextChangedListener(this);
            }
        });
        preferences = new Preferences(mContext);
        retrofit = getConnect();
        retrofit_photo = func_Upload_photo();
        if (add == 0) {
            Expend_id = mActivityItem.getExpend_id();
            edit = 0;
            getExpend();
            if (mActivityItem.getOrder_contract_id() > 0) {
                order_contract_id = mActivityItem.getOrder_contract_id();
                Log.d("order_contract_id",order_contract_id+"");
                id = mActivityItem.getOrder_contract_id();
                tvContract.setText(mActivityItem.getOrder_contract_name());
                layout_order.setVisibility(View.VISIBLE);
                switchCompat.setChecked(true);

            }
            tvShow.setVisibility(View.VISIBLE);
            tvShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            imSelect_upload_photo.setVisibility(View.GONE);
        } else {
            edit = 1;
            id=0;
            switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b == true) {
                        lvOrder.setVisibility(View.VISIBLE);
                        order_contract_id = id;
                        //  Toast.makeText(mContext, " chon", Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(mContext, "khong chon", Toast.LENGTH_SHORT).show();
                        order_contract_id = 0;
                        lvOrder.setVisibility(View.GONE);
                    }
                }
            });
            layout_order.setVisibility(View.GONE);
            tvShow.setVisibility(View.GONE);
            Expend_id = 0;
            check = 0;
            tvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();

                    Year = calendar.get(Calendar.YEAR);
                    Month = calendar.get(Calendar.MONTH);
                    Day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                            AddCostsActivity.this, Year, Month, Day);
                    datePickerDialog.setThemeDark(false);

                    datePickerDialog.showYearPickerFirst(false);

                    datePickerDialog.setAccentColor(getResources().getColor(R.color.colorApp));
                    datePickerDialog.setCancelText(getString(R.string.no));
                    datePickerDialog.setOkText(getString(R.string.yes));
                    datePickerDialog.setTitle(getString(R.string.choose_date));
                    datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                }
            });
            getCost();
            getOrder();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            String formattedDate = df.format(c.getTime());
            tvDate.setText(formattedDate);
        }


    }

    public Retrofit func_Upload_photo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_utils)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_partner)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public void getExpend() {
        listExpend = new ArrayList<>();
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<Expend>> call = api.get_partner_expend(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), mActivityItem.getExpend_id(), preferences.getStringValue(Constants.TOKEN, ""));
        call.enqueue(new Callback<MAPIResponse<Expend>>() {
            @Override
            public void onResponse(Call<MAPIResponse<Expend>> call, Response<MAPIResponse<Expend>> response) {
                expend = response.body().getResult();
                LogUtils.api("", call, response);
                Expend_group_type_id = expend.getExpendGroupTypeId();
                Expend_type_id = expend.getExpendTypeId();
                Status_id = expend.getStatusId();
                check = expend.getUserId();
                tvCosts.setText(Utils.formatCurrency(expend.getAmount()) + "");
                if (expend.getNote() != null && expend.getNote().length() > 0) {
                    tvNote.setText(expend.getNote());
                } else {
                    tvNote.setHint("");
                }
                tvDate.setText(expend.getExpendDate());
                lvImage = expend.getPhotos();
                if (lvImage != null && lvImage.size() > 0) {
                    lvPhoto.setVisibility(View.VISIBLE);
                    tvImage.setVisibility(View.VISIBLE);

                    getLoad_photo();
                } else {
                    lvPhoto.setVisibility(View.GONE);
                    tvImage.setVisibility(View.GONE);
                }
                listExpend.add(expend);

                adapter_expend adapter = new adapter_expend(AddCostsActivity.this, listExpend);
                lvCost.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MAPIResponse<Expend>> call, Throwable t) {

            }
        });

    }
    public void getCost() {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<Cost>>> call = api.get_expend_group_type(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), preferences.getStringValue(Constants.TOKEN, ""));
        call.enqueue(new Callback<MAPIResponse<List<Cost>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<Cost>>> call, Response<MAPIResponse<List<Cost>>> response) {
                TokenUtils.checkToken(AddCostsActivity.this, response.body().getErrors());
                LogUtils.api("", call, response);
                if (response.body() != null) {
                    listCost = new ArrayList<>();
                    listCost = response.body().getResult();
                    if (edit == 0) {
                        for (Cost cost : listCost) {
                            List<Expend> expends = cost.getExpends();
                            for (int i = 0; i < expends.size(); i++) {
                                if (expends.get(i).getExpendGroupTypeId() == Expend_group_type_id && expends.get(i).getExpendTypeId() == Expend_type_id) {
                                    expends.get(i).setCheck(true);
                                } else expends.get(i).setCheck(false);
                            }

                        }
                    }
                    adapter_cost adapter = new adapter_cost(AddCostsActivity.this, listCost, AddCostsActivity.this);
                    lvCost.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<Cost>>> call, Throwable t) {

            }
        });
    }

    public void getOrder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_exchange)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<MOrders>>> call = api.get_orders(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0));
        call.enqueue(new Callback<MAPIResponse<List<MOrders>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<MOrders>>> call, Response<MAPIResponse<List<MOrders>>> response) {
                TokenUtils.checkToken(AddCostsActivity.this, response.body().getErrors());
                LogUtils.api("", call, response);
                List<MOrders> orders = response.body().getResult();
                List<MOrders> orders1 = new ArrayList<MOrders>();
                if (orders != null && orders.size() > 0) {
                    orders.get(0).getOrderContractName();
//                    adapter_orders adapter = new adapter_orders(AddCostsActivity.this, orders, AddCostsActivity.this);
//                    lvOrder.setAdapter(adapter);
                    if (order_contract_id > 0) {
                        lvOrder.setVisibility(View.VISIBLE);
                        for (MOrders mOrders : orders) {
                            if (mOrders.getOrderContractId() == order_contract_id) {
                                mOrders.setCheck(true);
                            } else {
                                mOrders.setCheck(false);
                            }
                            orders1.add(mOrders);
                        }
                        adapter = new adapter_orders(AddCostsActivity.this, orders1, AddCostsActivity.this);
                        lvOrder.setAdapter(adapter);
                    } else {
                        adapter = new adapter_orders(AddCostsActivity.this, orders, AddCostsActivity.this);
                        lvOrder.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<MAPIResponse<List<MOrders>>> call, Throwable t) {

            }
        });
    }

    public void funcSendExpend() {

        Cost cost = new Cost();
        cost.setExpend_id(Expend_id);
        cost.setExpend_group_type_id(Expend_group_type_id);
        cost.setExpendTypeId(Expend_type_id);
        cost.setStatus_id(Status_id);
        cost.setAmount(prePay);
        cost.setNote(tvNote.getText().toString());
        cost.setActivity_type(0);
        cost.setType(0);
        cost.setOrder_contract_id(order_contract_id);
        cost.setExpend_date(tvDate.getText().toString());
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<MExpend>> call = api.set_partner_expend(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), mClient.getClient_id(), preferences.getStringValue(Constants.TOKEN, ""), cost);
        call.enqueue(new Callback<MAPIResponse<MExpend>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<MAPIResponse<MExpend>> call, Response<MAPIResponse<MExpend>> response) {
                LogUtils.api("", call, response);

                if (response.body() != null) {
                    MExpend expend = response.body().getResult();

                    if (lvImage.size() > 0) {
                        boolean b = funcUpload_photo(expend.getExpendId(), lvImage.size() - 1);
                        if (b == false) {
                            funcNotification(2);

                        } else {
                            funcNotification(1);

                        }
                    } else {
                        funcNotification(2);

                    }

                } else {
                    funcNotification(2);

                }
            }

            @Override
            public void onFailure(Call<MAPIResponse<MExpend>> call, Throwable t) {

            }
        });
    }

    public void funcNotification(int im) {

        if (im == 1) {
//            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                    .setTitleText(getResources().getString(R.string.srtNotifi))
//                    .setContentText(getResources().getString(R.string.srtFalse))
//                    .setConfirmText(getResources().getString(R.string.srtAgree))
//                    .show();
            Utils.showError(coordinatorLayout, R.string.srtFalse);
        } else {
//            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
//                    .setTitleText(getResources().getString(R.string.srtNotifi))
//                    .setContentText(getResources().getString(R.string.srtDone))
//                    .setConfirmText(getResources().getString(R.string.srtAgree))
//                    .show();
            Utils.showDialogSuccess(mContext, R.string.srtDone);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_edit_history_order, menu);

        if (add == 0) {
            tvCosts.setEnabled(false);
            tvNote.setEnabled(false);
            tvCosts.setTextColor(getResources().getColor(R.color.colorBlack));
            tvNote.setTextColor(getResources().getColor(R.color.color));
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.actionDone) {
                    menu.getItem(i).setVisible(false);
                }
                if (menu.getItem(i).getItemId() == R.id.edit) {
                    menu.getItem(i).setVisible(true);
                }
            }
        } else {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.actionDone) {
                    menu.getItem(i).setVisible(true);
                }
                if (menu.getItem(i).getItemId() == R.id.edit) {
                    menu.getItem(i).setVisible(false);
                }
            }
            tvCosts.setEnabled(true);
            tvNote.setEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id1 = item.getItemId();
        if (item.getItemId() == R.id.actionDone) {
            if (Expend_type_id > 0)

                funcSendExpend();

            else Toast.makeText(mContext, R.string.srtSelectTypeCosts, Toast.LENGTH_SHORT).show();
            //   funcAddFocus(mClient.getClient_id());
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.edit) {
            if (preferences.getIntValue(Constants.USER_ID, 0) == check) {
                tvImage.setVisibility(View.VISIBLE);
                lvPhoto.setVisibility(View.VISIBLE);
                tvShow.setVisibility(View.GONE);
                imSelect_upload_photo.setVisibility(View.VISIBLE);
                layout_order.setVisibility(View.GONE);
                add = 1;
                invalidateOptionsMenu();
                getCost();
                getOrder();
                tvDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();

                        Year = calendar.get(Calendar.YEAR);
                        Month = calendar.get(Calendar.MONTH);
                        Day = calendar.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                                AddCostsActivity.this, Year, Month, Day);
                        datePickerDialog.setThemeDark(false);

                        datePickerDialog.showYearPickerFirst(false);

                        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorApp));
                        datePickerDialog.setCancelText(getString(R.string.no));
                        datePickerDialog.setOkText(getString(R.string.yes));
                        datePickerDialog.setTitle(getString(R.string.choose_date));
                        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                    }
                });

                switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b == true) {
                            lvOrder.setVisibility(View.VISIBLE);
                            order_contract_id = id;
                            //  Toast.makeText(mContext, " chon", Toast.LENGTH_SHORT).show();
                        } else {
                            //Toast.makeText(mContext, "khong chon", Toast.LENGTH_SHORT).show();
                            order_contract_id = 0;
                            lvOrder.setVisibility(View.GONE);
                        }
                    }
                });
            } else Toast.makeText(mContext, R.string.edit_activity, Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == imSelect_upload_photo) {
            CropImage.activity(null).setGuidelines(CropImage_View.Guidelines.ON)
                    .start(AddCostsActivity.this);
        }
    }

    public void getLoad_photo() {
        adapter_photo_expend adapter = new adapter_photo_expend(this, lvImage, this);
        lvPhoto.setAdapter(adapter);
    }

    //upload photo đệ quy
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean funcUpload_photo(final int id, final int i) {
        if (lvImage.get(i).getUrl().contains("http")) {
            if (i == 0)
                result = false;
            else funcUpload_photo(id, i - 1);
        } else {
            ServiceAPI history_orders = retrofit_photo.create(ServiceAPI.class);
            File file = FileUtils.getFile(this, lvImage.get(i).getFilePart());
            if (file != null) {
                RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse(getContentResolver().getType(lvImage.get(i).getFilePart())),
                                file
                        );
                // create RequestBody instance from file
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
                Call<Result_upload_photo> call = history_orders.getUpload_photo("photo", id, preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), "a", preferences.getIntValue(Constants.PARTNER_ID, 0), "ep", filePart);
                call.enqueue(new Callback<Result_upload_photo>() {
                    @Override
                    public void onResponse(Call<Result_upload_photo> call, Response<Result_upload_photo> response) {
                        LogUtils.api("", call, response);
                        if (i == 0)
                            result = false;
                        else funcUpload_photo(id, i - 1);
                    }

                    @Override
                    public void onFailure(Call<Result_upload_photo> call, Throwable t) {
                        result = true;
                    }
                });
            } else result = false;

        }

        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                String a;
//                ((ImageView) findViewById(R.id.quick_start_cropped_image)).setImageURI(result.getUri());
                if (result.getUri().toString().contains("file")) {
                    a = result.getUri().toString().replace("file://", "");
                    selectedImage = getImageContentUri(this, new File(Uri.parse(a).toString()));
                    Log.d("uri", getImageContentUri(this, new File(Uri.parse(a).toString())) + "");
                }
                Photo photo = new Photo();
                photo.setCode("");
                photo.setHeight(0);
                photo.setWidth(0);
                photo.setObjectId(0);
                photo.setOrderNo(0);
                photo.setPhotoId(0);
                photo.setType(0);
                photo.setName("");
                photo.setUrl("");
                photo.setFilePart(selectedImage);
                lvImage.add(photo);
                getLoad_photo();

                Log.d("result.getUri", result.getUri().toString());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Lỗi file: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            cursor.close();
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    @Override
    public void radioButton(int expend_group_type_id, int expend_type_id, int expend_id, int status_id) {
        Expend_group_type_id = expend_group_type_id;
        Expend_type_id = expend_type_id;
//        Expend_id = expend_id;
        Status_id = status_id;
    }

    //xóa photo chưa được gửi lên.
    @Override
    public void Delete_photo_off(final int pos) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_delete_photo);
        Button btDelete = (Button) dialog.findViewById(R.id.btDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvImage.remove(pos);
                getLoad_photo();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Year, (Month + 1), Day);
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                AddCostsActivity.this,
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
        // date.setText(tvDate.getText().toString());
    }

    @Override
    public void Click(int id) {
        order_contract_id = id;
        this.id = id;
        Log.d("order_contract_id", id + "");
    }
}
