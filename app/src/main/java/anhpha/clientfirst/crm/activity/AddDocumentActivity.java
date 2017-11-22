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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_Photo_document;
import anhpha.clientfirst.crm.adapter.adapter_photo_expend;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.cropper.CropImage.CropImage;
import anhpha.clientfirst.crm.cropper.CropImage.CropImage_View;
import anhpha.clientfirst.crm.cropper.CropImage.FileUtils;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.Document;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MActivityItem;
import anhpha.clientfirst.crm.model.MComment;
import anhpha.clientfirst.crm.model.MDocument;
import anhpha.clientfirst.crm.model.MDocuments;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.Photo;
import anhpha.clientfirst.crm.model.Result;
import anhpha.clientfirst.crm.model.Result_upload_photo;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static anhpha.clientfirst.crm.R.id.tvNote;

/**
 * Created by Administrator on 10/25/2017.
 */

public class AddDocumentActivity extends BaseAppCompatActivity implements View.OnClickListener, adapter_photo_expend.funcDelete_lvImage {
    private Toolbar toolbar;
    int add = 1;
    MOrder mOrder = new MOrder();
    private Uri selectedImage;
    private Preferences preferences;
    private Retrofit retrofit, retrofit_photo;
    private ImageView imSelect_upload_photo;
    private RecyclerView lvPhoto;
    private TextView tvClientName;
    private boolean result;
    private EditText etContent;
    MActivityItem mActivityItem;
    private List<Photo> lvImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_add_document);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvClientName = (TextView) findViewById(R.id.tvClientName);
        lvPhoto = (RecyclerView) findViewById(R.id.lvPhoto);
        etContent = (EditText) findViewById(R.id.etContent);
        imSelect_upload_photo = (ImageView) findViewById(R.id.imSelect_upload_photo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL
                , false);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        lvPhoto.setHasFixedSize(true);
        lvPhoto.setLayoutManager(layoutManager);

        preferences = new Preferences(mContext);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.document);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
//        tvClientName.setText(R.string.document);
        retrofit = getConnect();

        Intent intent =getIntent();
        mOrder = (MOrder) intent.getSerializableExtra("mOrder");
        add = (int) intent.getSerializableExtra("mAdd");
        mActivityItem = (MActivityItem) intent.getSerializableExtra("Document");
        retrofit_photo = func_Upload_photo();
        tvClientName.setText(mOrder.getOrder_contract_name());
        if (add == 1) {

        } else {
            getComment();
        }
        imSelect_upload_photo.setOnClickListener(this);
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Url.URL_user).addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public Retrofit func_Upload_photo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_utils)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void getComment() {
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<Document>> call =api.get_user_document(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), mActivityItem.getUser_document_id());
        call.enqueue(new Callback<MAPIResponse<Document>>() {
            @Override
            public void onResponse(Call<MAPIResponse<Document>> call, Response<MAPIResponse<Document>> response) {
                LogUtils.api("", call, response);
                Document mDocument = response.body().getResult();
                etContent.setText(mDocument.getDocumentSubject());

                List<MDocuments> image= mDocument.getDocuments();
                if (image != null && image.size()>0){
                    adapter_Photo_document adapter = new adapter_Photo_document(AddDocumentActivity.this, image);
                    lvPhoto.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MAPIResponse<Document>> call, Throwable t) {

            }
        });
    }

    public void funcAddDocument() {
        MDocument mComment = new MDocument();
        mComment.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
        mComment.setOrder_contract_id(mOrder.getOrder_contract_id());
        mComment.setDocument_subject(etContent.getText().toString());
        mComment.setUser_documnet_id(0);
        ServiceAPI api = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<MDocument>> call = api.set_user_document(preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), mComment);
        call.enqueue(new Callback<MAPIResponse<MDocument>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<MAPIResponse<MDocument>> call, Response<MAPIResponse<MDocument>> response) {
                LogUtils.api("", call, response);
                MDocument mDocument = response.body().getResult();
                if (mDocument.getUser_documnet_id() > 0) {
                    if (lvImage.size() > 0) {
                        boolean b = funcUpload_photo(mDocument.getUser_documnet_id(), lvImage.size() - 1);

                        if (b == true) {
                            Utils.showDialogSuccess(mContext, R.string.srtFalse);
                            Log.d("Upload","That bai");
                        } else {Utils.showDialogSuccess(mContext, R.string.srtDone);Log.d("Upload","Thanh cong");}
                    }else Utils.showDialogSuccess(mContext, R.string.srtDone);
                } else Utils.showDialogSuccess(mContext, R.string.srtFalse);
            }

            @Override
            public void onFailure(Call<MAPIResponse<MDocument>> call, Throwable t) {
                Utils.showDialogSuccess(mContext, R.string.srtFalse);
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_edit_history_order, menu);
        if (add == 0) {
            menu.getItem(0).setVisible(false);
            etContent.setEnabled(false);
            etContent.setTextColor(getResources().getColor(R.color.colorBlack));

        } else {

            menu.getItem(0).setVisible(true);
            etContent.setEnabled(true);

        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.actionDone:
                funcAddDocument();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == imSelect_upload_photo) {
            CropImage.activity(null).setGuidelines(CropImage_View.Guidelines.ON)
                    .start(AddDocumentActivity.this);
        }
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

    public void getLoad_photo() {
        adapter_photo_expend adapter = new adapter_photo_expend(this, lvImage, this);
        lvPhoto.setAdapter(adapter);
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
}
