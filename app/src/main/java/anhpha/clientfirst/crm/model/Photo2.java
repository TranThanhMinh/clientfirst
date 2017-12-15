package anhpha.clientfirst.crm.model;

/**
 * Created by MinhTran on 7/11/2017.
 */

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Photo2 implements Parcelable {

    @SerializedName("photo_id")
    @Expose
    private Integer photoId;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("object_id")
    @Expose
    private Integer objectId;
    @SerializedName("order_no")
    @Expose
    private Integer orderNo;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;

    protected Photo2(Parcel in) {
        url = in.readString();
        name = in.readString();
        code = in.readString();
        filePart = in.readParcelable(Uri.class.getClassLoader());
    }
    public Photo2(){
    }
    public static final Creator<Photo2> CREATOR = new Creator<Photo2>() {
        @Override
        public Photo2 createFromParcel(Parcel in) {
            return new Photo2(in);
        }

        @Override
        public Photo2[] newArray(int size) {
            return new Photo2[size];
        }
    };

    public Uri getFilePart() {
        return filePart;
    }

    public void setFilePart(Uri filePart) {
        this.filePart = filePart;
    }

    private Uri filePart;

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(name);
        parcel.writeString(code);
        parcel.writeParcelable(filePart, i);
    }
}