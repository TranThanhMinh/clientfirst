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
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_cost;
import anhpha.clientfirst.crm.adapter.adapter_expend;
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
import anhpha.clientfirst.crm.model.Photo;
import anhpha.clientfirst.crm.model.Result_upload_photo;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
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

public class AddCostsContractActivity extends BaseAppCompatActivity implements View.OnClickListener, adapter_cost.Click, adapter_photo_expend.funcDelete_lvImage, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private EditText tvCosts, tvNote;
    private Toolbar toolbar;
    private ImageView imSelect_upload_photo;
    private Uri selectedImage;
    private Retrofit retrofit, retrofit_photo;
    private Preferences preferences;
    private List<Cost> listCost;
    private List<Expend> listExpend;
    private RecyclerView lvCost, lvPhoto;
    private int Expend_group_type_id = 0, Expend_id, Expend_type_id = 0, Status_id;
    MOrder mClient = new MOrder();
    MActivityItem mActivityItem = new MActivityItem();
    private double prePay;
    private List<Photo> lvImage = new ArrayList<>();
    private boolean result;
    private TextView tvName, tvAddress, tvImage;
    EditText tvDate;
    private int add;
    private int edit;
    private int check;
    private Expend expend;
    LinearLayout coordinatorLayout;
    int Year, Month, Day;
    private RelativeLayout realOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_costs);
        coordinatorLayout = (LinearLayout) findViewById(R.id.coordinatorLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvCost = (RecyclerView) findViewById(R.id.lvCost);
        realOff = (RelativeLayout) findViewById(R.id.realOff);
        lvPhoto = (RecyclerView) findViewById(R.id.lvPhoto);
        tvName = (TextView) findViewById(R.id.tvName);
        tvImage = (TextView) findViewById(R.id.tvImage);
        tvDate = (EditText) findViewById(R.id.tvDate);
        realOff.setVisibility(View.GONE);

        //   tvAddress = (TextView) findViewById(R.id.tvAddress);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvCost.setHasFixedSize(true);
        lvCost.setLayoutManager(manager);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL
                , false);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvPhoto.setHasFixedSize(true);
        lvPhoto.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        mClient = (MOrder) intent.getSerializableExtra("mOrder");
        add = (int) intent.getSerializableExtra("mAdd");
        mActivityItem = (MActivityItem) intent.getSerializableExtra("mExpend");
        tvName.setText(mClient.getOrder_contract_name());
        // tvAddress.setText(mClient.getAddress());
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
            edit = 0;

            getExpend();
            imSelect_upload_photo.setVisibility(View.GONE);

            Expend_id = mActivityItem.getExpend_id();
        } else {
            edit = 1;
            Expend_id = 0;
            check = 0;
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            String formattedDate = df.format(c.getTime());
            tvDate.setText(formattedDate);
            getCost();
            tvDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    Year = calendar.get(Calendar.YEAR);
                    Month = calendar.get(Calendar.MONTH);
                    Day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                            AddCostsContractActivity.this, Year, Month, Day);
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
                tvDate.setFocusable(false);
                lvImage = expend.getPhotos();
                listExpend.add(expend);
                if (lvImage != null && lvImage.size() > 0) {
                    lvPhoto.setVisibility(View.VISIBLE);
                    tvImage.setVisibility(View.VISIBLE);
                    getLoad_photo();
                } else {
                    lvPhoto.setVisibility(View.GONE);
                    tvImage.setVisibility(View.GONE);
                }

                adapter_expend adapter = new adapter_expend(AddCostsContractActivity.this, listExpend);
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
                TokenUtils.checkToken(AddCostsContractActivity.this, response.body().getErrors());
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
                    adapter_cost adapter = new adapter_cost(AddCostsContractActivity.this, listCost, AddCostsContractActivity.this);
                    lvCost.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<Cost>>> call, Throwable t) {

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
        cost.setExpend_date(tvDate.getText().toString());
        cost.setOrder_contract_id(mClient.getOrder_contract_id());
        cost.setNote(tvNote.getText().toString());
        cost.setActivity_type(1);
        cost.setType(1);
        cost.setDisplay_type(0);
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
        int id = item.getItemId();
        if (id == R.id.actionDone) {
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
                imSelect_upload_photo.setVisibility(View.VISIBLE);
                add = 1;
                invalidateOptionsMenu();
                getCost();
                tvDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();

                        Year = calendar.get(Calendar.YEAR);
                        Month = calendar.get(Calendar.MONTH);
                        Day = calendar.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                                AddCostsContractActivity.this, Year, Month, Day);
                        datePickerDialog.setThemeDark(false);
                        datePickerDialog.showYearPickerFirst(false);
                        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorApp));
                        datePickerDialog.setCancelText(getString(R.string.no));
                        datePickerDialog.setOkText(getString(R.string.yes));
                        datePickerDialog.setTitle(getString(R.string.choose_date));
                        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
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
                    .start(AddCostsContractActivity.this);
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
        dialog.setContentView(R.layout.activity_delete_show_photo);
        Button btDelete = (Button) dialog.findViewById(R.id.btDelete);
        Button btShow = (Button) dialog.findViewById(R.id.btShow);
        btShow.setVisibility(View.GONE);
//        btShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), Show_photo_activity1.class).putExtra("crop", (ArrayList<Photo2>) lvImage_crop).putExtra("list", (Serializable) lvImage_copy));
//                dialog.dismiss();
//            }
//        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  final Photo photo = lvImage.get(pos);
                lvImage.remove(pos);
//                for(int i =0;i<lvImage_crop.size();i++){
//                    if(lvImage_crop.get(i).getFilePart()==photo.getFilePart()){
//                        lvImage_crop.remove(i);
//                    }
//                }
                getLoad_photo();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //xóa photo đã oline.
    @Override
    public void Delete_photo_onl(final int pos) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_delete_show_photo);
        Button btDelete = (Button) dialog.findViewById(R.id.btDelete);
        Button btShow = (Button) dialog.findViewById(R.id.btShow);
        if(add==1){
            btShow.setVisibility(View.GONE);
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Photo photo = lvImage.get(pos);
                    ServiceAPI history_orders = retrofit_photo.create(ServiceAPI.class);
                    Call<Result_upload_photo> call = history_orders.getDelete_photo(photo.getName(), "avarta", photo.getPhotoId(), preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), "a", preferences.getIntValue(Constants.PARTNER_ID, 0), "ep", photo.getObjectId());
                    call.enqueue(new Callback<Result_upload_photo>() {
                        public void onResponse(Call<Result_upload_photo> call, Response<Result_upload_photo> response) {
                            LogUtils.api("", call, response);
                            if (response.body().getHasErrors() == false) {
                                lvImage.remove(pos);
//                                for(Photo1 photo1:lvImage_copy){
//                                    if(photo1.getPhotoId()==photo.getPhotoId()){
//                                        lvImage_copy.remove(photo1);
//                                    }
//                                }
//                                for(int i =0;i<lvImage_copy.size();i++){
//                                    if(lvImage_copy.get(i).getFilePart()==photo.getFilePart()){
//                                        lvImage_copy.remove(i);
//                                    }
//                                }
                                getLoad_photo();
                                dialog.dismiss();
                                Toast.makeText(AddCostsContractActivity.this, R.string.srtDone, Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(AddCostsContractActivity.this, R.string.srtFalse, Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<Result_upload_photo> call, Throwable t) {
                            Log.d("Update_staus2222", call.toString());
                        }
                    });
                }
            });

        }else {
            btDelete.setVisibility(View.GONE);
            btShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Show_photo_activity1.class).putExtra("list", (Serializable) lvImage));
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Year, (Month + 1), Day);
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                AddCostsContractActivity.this,
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
}
