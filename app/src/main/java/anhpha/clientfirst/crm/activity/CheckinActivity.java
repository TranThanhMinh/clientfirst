package anhpha.clientfirst.crm.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.PhotosAdapter;
import anhpha.clientfirst.crm.adapter.TrackingValueDefautAdapter;
import anhpha.clientfirst.crm.adapter.ValueDefautAdapter;
import anhpha.clientfirst.crm.adapter.adapter_orders;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.cropper.Crop;
import anhpha.clientfirst.crm.cropper.CropImage.CropImage;
import anhpha.clientfirst.crm.cropper.CropImage.CropImage_View;
import anhpha.clientfirst.crm.cropper.CropImage.FileUtils;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MCheckin;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MOrders;
import anhpha.clientfirst.crm.model.MPhoto;
import anhpha.clientfirst.crm.model.Photo;
import anhpha.clientfirst.crm.model.Result_upload_photo;
import anhpha.clientfirst.crm.model.Tracking_value_defaults;
import anhpha.clientfirst.crm.model.UserEmail;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckinActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MCheckin>>, View.OnClickListener, adapter_orders.Onclick, PhotosAdapter.funcDelete_lvImage {
    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvSwitch)
    TextView tvSwitch;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.ivCamera)
    ImageView ivCamera;
    @Bind(R.id.lvTracking)
    RecyclerView lvTracking;
    @Bind(R.id.lvOrder)
    RecyclerView lvOrder;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    MCheckin mCheckin = new MCheckin();
    MClient mClient = new MClient();
    @Bind(R.id.tvContract)
    TextView tvContract;
    Preferences preferences;
    protected List<Photo> photos;
    private Uri selectedImage;
    protected PhotosAdapter photosAdapter;
    private String filePath = null;
    private Uri mImageCaptureUri;
    private Retrofit retrofit;
    List<Tracking_value_defaults> listTracking;
    private List<Tracking_value_defaults> listTracking_userCheckin = new ArrayList<>();
    private int check = 0;
    @Bind(R.id.realOff)
    RelativeLayout realOff;
    private int order_contract_id = 0;
    private int id = 0;
    int user = 0;
    int check_edit = 0;

    @Bind(R.id.tvShow)
    TextView tvShow;
    private boolean isHide = false;
    private boolean result;
    LinearLayout layout_order;
    LinearLayout layCamera;
    adapter_orders adapter;
    private boolean edit = false;
    private Retrofit retrofit_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_checkin);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);
        layout_order = (LinearLayout) findViewById(R.id.layout_order);
        layCamera = (LinearLayout) findViewById(R.id.layCamera);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.meet);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        tvSwitch.setText(R.string.srtContent1);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvTracking.setHasFixedSize(true);
        lvTracking.setLayoutManager(manager);
        Intent intent = getIntent();
        mCheckin = (MCheckin) intent.getSerializableExtra("mCheckin");
        mClient = (MClient) intent.getSerializableExtra("mClient");
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        rvActivities.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm.setSmoothScrollbarEnabled(true);
        rvActivities.setLayoutManager(llm);
        rvActivities.setAdapter(photosAdapter);
        LinearLayoutManager manager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        lvOrder.setHasFixedSize(true);
        lvOrder.setLayoutManager(manager1);
        tvClientName.setText(mClient.getClient_name());
        tvAddress.setText(mClient.getAddress());
        if (mClient.getAddress() != null && !mClient.getAddress().isEmpty()) {
            tvAddress.setVisibility(View.VISIBLE);
        }
        retrofit = getConnect();
        retrofit_photo = func_Upload_photo();
        SwitchCompat switchCompat = (SwitchCompat) findViewById(R.id
                .switchButton);
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
        if (mCheckin == null) {
            user = 0;
            check_edit = 1;

            isHide = true;
            edit = true;
            mCheckin = new MCheckin();
            tvShow.setVisibility(View.GONE);
            getTracking_value_default();
            getOrder();
        }
        ivCamera.setOnClickListener(this);
        if (mCheckin.getUser_checkin_id() > 0) {
            edit = false;
            check_edit = 0;
            user = mCheckin.getUser_checkin_id();
            isHide = false;
            tvShow.setVisibility(View.VISIBLE);
            tvShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            if (mCheckin.getOrder_contract_id() > 0) {
                id = mCheckin.getOrder_contract_id();
                order_contract_id = mCheckin.getOrder_contract_id();
                tvContract.setText(mCheckin.getOrder_contract_name());
                layout_order.setVisibility(View.VISIBLE);
                switchCompat.setChecked(true);
            }
            getUserCheckin();
            etContent.setText(mCheckin.getContent_checkin());
            etContent.setFocusable(false);
            ivCamera.setVisibility(View.GONE);
            GetRetrofit().create(ServiceAPI.class)
                    .getUserCheckin(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                            , mCheckin.getUser_checkin_id())
                    .enqueue(new Callback<MAPIResponse<MCheckin>>() {
                        @Override
                        public void onResponse(Call<MAPIResponse<MCheckin>> call, Response<MAPIResponse<MCheckin>> response) {
                            TokenUtils.checkToken(mContext, response.body().getErrors());
                            mCheckin = response.body().getResult();
                            photos = mCheckin.getPhotos();
                            if (photos != null && photos.size() > 0) {
                                photosAdapter.setPhotoList(photos);
                                photosAdapter.notifyDataSetChanged();
                                layCamera.setVisibility(View.VISIBLE);
                            } else {
                                layCamera.setVisibility(View.GONE);
                                photos=new ArrayList<>();
                            }
                        }

                        @Override
                        public void onFailure(Call<MAPIResponse<MCheckin>> call, Throwable t) {

                        }
                    });
        }
        photos = new ArrayList<>();
        photosAdapter = new PhotosAdapter(this, photos, CheckinActivity.this);
        rvActivities.setAdapter(photosAdapter);


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
                .baseUrl(Url.URL_user)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
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
                TokenUtils.checkToken(CheckinActivity.this, response.body().getErrors());
                LogUtils.api("", call, response);
                List<MOrders> orders = response.body().getResult();
                List<MOrders> orders1 = new ArrayList<MOrders>();
                if (orders != null && orders.size() > 0) {
                    orders.get(0).getOrderContractName();
//                    adapter_orders adapter = new adapter_orders(CheckinActivity.this, orders, CheckinActivity.this);
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
                        adapter = new adapter_orders(CheckinActivity.this, orders1, CheckinActivity.this);
                        lvOrder.setAdapter(adapter);
                    } else {
                        adapter = new adapter_orders(CheckinActivity.this, orders, CheckinActivity.this);
                        lvOrder.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<MOrders>>> call, Throwable t) {

            }
        });
    }

    public void getTracking_value_default() {
        ServiceAPI apiTracking = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<Tracking_value_defaults>>> tracking_value_defaults = apiTracking.getTracking_value_defaults(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), 1, preferences.getStringValue(Constants.TOKEN, ""));
        tracking_value_defaults.enqueue(new Callback<MAPIResponse<List<Tracking_value_defaults>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<Tracking_value_defaults>>> call, Response<MAPIResponse<List<Tracking_value_defaults>>> response) {
                listTracking = response.body().getResult();
                LogUtils.api("", call, response);
//                TrackingValueDefautAdapter adapte = new TrackingValueDefautAdapter(CheckinActivity.this, listTracking);
//                lvTracking.setAdapter(adapte);
                if (listTracking_userCheckin != null && listTracking_userCheckin.size() > 0) {
                    List<Tracking_value_defaults> list = new ArrayList<>();
                    for (Tracking_value_defaults email : listTracking) {
                        for (int i = 0; i < listTracking_userCheckin.size(); i++) {
                            if (email.getTracking_value_default_id() == listTracking_userCheckin.get(i).getTracking_value_default_id()) {
                                email.setTracking(true);
                                list.add(email);
                                i = listTracking_userCheckin.size();
                            } else {
                                if (i == listTracking_userCheckin.size() - 1) {
                                    email.setTracking(false);
                                    list.add(email);
                                }
                            }
                        }
                    }
                    listTracking = list;
                    TrackingValueDefautAdapter adapte = new TrackingValueDefautAdapter(CheckinActivity.this, listTracking);
                    lvTracking.setAdapter(adapte);
                } else {
                    TrackingValueDefautAdapter adapte = new TrackingValueDefautAdapter(CheckinActivity.this, listTracking);
                    lvTracking.setAdapter(adapte);
                }
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<Tracking_value_defaults>>> call, Throwable t) {
                LogUtils.api("", call, t.toString());
            }
        });
    }

    public void getUserCheckin() {
        ServiceAPI apiTracking = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<UserEmail>> user_email = apiTracking.get_user_meeting(preferences.getIntValue(Constants.USER_ID, 0), mCheckin.getUser_checkin_id(), preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.PARTNER_ID, 0));
        user_email.enqueue(new Callback<MAPIResponse<UserEmail>>() {
            @Override
            public void onResponse(Call<MAPIResponse<UserEmail>> call, Response<MAPIResponse<UserEmail>> response) {
                LogUtils.api("", call, response);
                listTracking_userCheckin = response.body().getResult().getValuesDefault();
                check = response.body().getResult().getUserId();
                ValueDefautAdapter adapte = new ValueDefautAdapter(CheckinActivity.this, listTracking_userCheckin);
                lvTracking.setAdapter(adapte);
            }

            @Override
            public void onFailure(Call<MAPIResponse<UserEmail>> call, Throwable t) {
            }
        });
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
                mCheckin.setValues_default(list);
                mCheckin.setClient_id(mClient.getClient_id());
                mCheckin.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
                mCheckin.setContent_checkin(etContent.getText().toString());
                mCheckin.setLatitude(mLastLocation.getLatitude());
                mCheckin.setLongitude(mLastLocation.getLongitude());
                mCheckin.setOrder_contract_id(order_contract_id);
                mCheckin.setUser_checkin_id(user);
                mCheckin.setDisplay_type(0);
                mCheckin.setActivity_type(0);
                GetRetrofit().create(ServiceAPI.class)
                        .setUserCheckin(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                , mClient.getClient_id()
                                , mCheckin)
                        .enqueue(this);
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.edit:
                Log.d("User_id1", check + "");
                check_edit = 1;
                if (preferences.getIntValue(Constants.USER_ID, 0) == check) {
                    tvShow.setVisibility(View.GONE);
                    etContent.setFocusable(true);
                    etContent.setFocusableInTouchMode(true);
                    etContent.requestFocus();
                    isHide = true;
                    invalidateOptionsMenu();
                    getTracking_value_default();
                    getOrder();

                    ivCamera.setVisibility(View.VISIBLE);
                    layCamera.setVisibility(View.VISIBLE);
                    layout_order.setVisibility(View.GONE);
                } else {
                    Toast.makeText(mContext, R.string.edit_activity, Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //upload photo đệ quy
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean funcUpload_photo(final int id, final int i) {
        if (photos.get(i).getUrl().contains("http")) {
            if (i == 0)
                result = false;
            else funcUpload_photo(id, i - 1);
        } else {
            ServiceAPI history_orders = retrofit_photo.create(ServiceAPI.class);
            File file = FileUtils.getFile(this, photos.get(i).getFilePart());
            if (file != null) {
                RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse(getContentResolver().getType(photos.get(i).getFilePart())),
                                file
                        );
                // create RequestBody instance from file
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
                Call<Result_upload_photo> call = history_orders.upload_file(id, preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), "a", preferences.getIntValue(Constants.PARTNER_ID, 0), "ct", filePart);
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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private void uploadImage(final List<Photo> photos) {
        if (photos.size() == 0) {
            if (edit == true)
                Utils.showDialogSuccess(mContext, R.string.checkin_client_done);
            else Utils.showDialogSuccess(mContext, R.string.checkin_client_done1);
        }
        for (final Photo p : photos) {
            if (p.getFilePart() != null) {
                File file = FileUtils.getFile(this, p.getFilePart());
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part imagenPerfil = null;
                imagenPerfil = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                GetRetrofit().create(ServiceAPI.class)
                        .upLoadPhoto(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                , "photo"
                                , "a"
                                , "ci"
                                , mCheckin.getUser_checkin_id()
                                , imagenPerfil)
                        .enqueue(new Callback<MAPIResponse<MPhoto>>() {
                            @Override
                            public void onResponse(Call<MAPIResponse<MPhoto>> call, Response<MAPIResponse<MPhoto>> response) {
                                LogUtils.api(TAG, call, (response.body()));
                                TokenUtils.checkToken(mContext, response.body().getErrors());
                                photos.remove(p);
                                uploadImage(photos);
                            }
                            @Override
                            public void onFailure(Call<MAPIResponse<MPhoto>> call, Throwable t) {
                                LogUtils.d(TAG, "getUserActivities ", t.toString());
                                photos.remove(p);
                                uploadImage(photos);
                            }
                        });
                box.showLoadingLayout();
                LogUtils.d(TAG, "getUserActivities ", "start");
                break;
            } else {
                photos.remove(p);
                uploadImage(photos);
                break;
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResponse(Call<MAPIResponse<MCheckin>> call, Response<MAPIResponse<MCheckin>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext, response.body().getErrors());
        mCheckin = response.body().getResult();
        if (!response.body().isHasErrors()) {
              uploadImage(photos);
//            result = funcUpload_photo(mCheckin.getUser_checkin_id(), photos.size() - 1);
//            if (result == false)
//                Utils.showDialogSuccess(mContext, R.string.checkin_client_done);
//            else Utils.showDialogSuccess(mContext, R.string.checkin_client_done1);
        }
    }

    @Override
    public void onFailure(Call<MAPIResponse<MCheckin>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
        if (edit == true)
            Utils.showError(coordinatorLayout, R.string.checkin_client_fail);
        else Utils.showError(coordinatorLayout, R.string.checkin_client_fail1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCamera:
//                CharSequence[] charSequences = new CharSequence[2];
//                charSequences[0] = getString(R.string.camera);
//                charSequences[1] = getString(R.string.library);
//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//                builder.setTitle(getResources().getString(R.string.choose_image));
//                builder.setCancelable(true);
//                builder.setItems(charSequences, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        switch (i) {
//                            case 0:
//                                openCamera();
//                                break;
//                            case 1:
//                                openPictures();
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                });
//                builder.show();
                CropImage.activity(null).setGuidelines(CropImage_View.Guidelines.ON)
                        .start(CheckinActivity.this);
                break;
            default:
                break;
        }
    }

    private void openPictures() {
        Crop.pickImage(this);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(intent, Constants.PICK_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            LogUtils.e(TAG, "openCamera", e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                photos.add(photo);
                photosAdapter = new PhotosAdapter(this, photos, CheckinActivity.this);
                rvActivities.setAdapter(photosAdapter);

                Log.d("result.getUri", result.getUri().toString());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Lỗi file: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            if (data != null) {
                beginCrop(data.getData());
            }
        } else if (requestCode == Crop.REQUEST_CROP) {

            if (data != null) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
//                filePath = data.getStringExtra("file").toString();
//                MPhoto p = new MPhoto();
//                p.local = filePath;
//                photos.add(p);
//                photosAdapter.setPhotoList(photos);
//                photosAdapter.notifyDataSetChanged();
//                layCamera.setVisibility(View.VISIBLE);
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
                photos.add(photo);
                photosAdapter.notifyDataSetChanged();


            }
        } else if (requestCode == Constants.PICK_FROM_CAMERA) {
            if (mImageCaptureUri != null) {
                beginCrop(mImageCaptureUri);
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

    private void beginCrop(Uri source) {
        Intent intent = new Intent(getApplication(), CropperImage.class);
        intent.putExtra("source", source.toString());
        startActivityForResult(intent, Crop.REQUEST_CROP);
    }

    @Override
    public void Click(int id) {
        this.id = id;
        order_contract_id = id;
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
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                photos.remove(pos);

                photosAdapter = new PhotosAdapter(CheckinActivity.this, photos, CheckinActivity.this);
                rvActivities.setAdapter(photosAdapter);
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
        if(isHide == true){
            btShow.setVisibility(View.GONE);
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Photo photo = photos.get(pos);
                    ServiceAPI history_orders = retrofit_photo.create(ServiceAPI.class);
                    Call<Result_upload_photo> call = history_orders.getDelete_photo(photo.getName(), "avarta", photo.getPhotoId(), preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), "a", preferences.getIntValue(Constants.PARTNER_ID, 0), "ci", photo.getObjectId());
                    call.enqueue(new Callback<Result_upload_photo>() {
                        public void onResponse(Call<Result_upload_photo> call, Response<Result_upload_photo> response) {
                            LogUtils.api("", call, response);
                            if (response.body().getHasErrors() == false) {
                                photos.remove(pos);


                                photosAdapter = new PhotosAdapter(CheckinActivity.this, photos, CheckinActivity.this);
                                rvActivities.setAdapter(photosAdapter);
                                dialog.dismiss();
                                Toast.makeText(CheckinActivity.this, R.string.srtDone, Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(CheckinActivity.this, R.string.srtFalse, Toast.LENGTH_SHORT).show();
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
                    startActivity(new Intent(getApplicationContext(), Show_photo_activity1.class).putExtra("list", (Serializable) photos));
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }
}
