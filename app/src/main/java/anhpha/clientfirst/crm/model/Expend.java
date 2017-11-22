package anhpha.clientfirst.crm.model;

/**
 * Created by Administrator on 9/6/2017.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Expend {

    @SerializedName("expend_id")
    @Expose
    private Integer expendId;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("expend_group_type_id")
    @Expose
    private Integer expendGroupTypeId;
    @SerializedName("expend_type_id")
    @Expose
    private Integer expendTypeId;
    @SerializedName("expend_group_type_name")
    @Expose
    private String expendGroupTypeName;
    @SerializedName("expend_type_name")
    @Expose
    private String expendTypeName;
    @SerializedName("expend_date")
    @Expose
    private String expend_date;
    @SerializedName("client_name")
    @Expose
    private Object clientName;
    @SerializedName("user_name")
    @Expose
    private Object userName;
    @SerializedName("partner_id")
    @Expose
    private Integer partnerId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("expends")
    @Expose
    private List<Object> expends = null;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;


    public boolean isCheck() {
        return Check;
    }

    public void setCheck(boolean check) {
        Check = check;
    }

    private boolean Check =false;

    public Integer getExpendId() {
        return expendId;
    }

    public void setExpendId(Integer expendId) {
        this.expendId = expendId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getExpendGroupTypeId() {
        return expendGroupTypeId;
    }

    public void setExpendGroupTypeId(Integer expendGroupTypeId) {
        this.expendGroupTypeId = expendGroupTypeId;
    }

    public Integer getExpendTypeId() {
        return expendTypeId;
    }

    public void setExpendTypeId(Integer expendTypeId) {
        this.expendTypeId = expendTypeId;
    }

    public String getExpendGroupTypeName() {
        return expendGroupTypeName;
    }

    public void setExpendGroupTypeName(String expendGroupTypeName) {
        this.expendGroupTypeName = expendGroupTypeName;
    }

    public String getExpendTypeName() {
        return expendTypeName;
    }

    public void setExpendTypeName(String expendTypeName) {
        this.expendTypeName = expendTypeName;
    }

    public String getExpendDate() {
        return expend_date;
    }

    public void setExpendDate(String expendDate) {
        this.expend_date = expendDate;
    }

    public Object getClientName() {
        return clientName;
    }

    public void setClientName(Object clientName) {
        this.clientName = clientName;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<Object> getExpends() {
        return expends;
    }

    public void setExpends(List<Object> expends) {
        this.expends = expends;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

}