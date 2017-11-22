package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 10/26/2017.
 */

public class MExpend {
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
    private Object expendGroupTypeName;
    @SerializedName("expend_type_name")
    @Expose
    private Object expendTypeName;
    @SerializedName("expend_date")
    @Expose
    private String expendDate;
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
    @SerializedName("order_contract_id")
    @Expose
    private Integer orderContractId;
    @SerializedName("display_type")
    @Expose
    private Integer displayType;
    @SerializedName("activity_type")
    @Expose
    private Integer activityType;
    @SerializedName("expends")
    @Expose
    private List<Object> expends = null;
    @SerializedName("photos")
    @Expose
    private List<Object> photos = null;

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

    public Object getExpendGroupTypeName() {
        return expendGroupTypeName;
    }

    public void setExpendGroupTypeName(Object expendGroupTypeName) {
        this.expendGroupTypeName = expendGroupTypeName;
    }

    public Object getExpendTypeName() {
        return expendTypeName;
    }

    public void setExpendTypeName(Object expendTypeName) {
        this.expendTypeName = expendTypeName;
    }

    public String getExpendDate() {
        return expendDate;
    }

    public void setExpendDate(String expendDate) {
        this.expendDate = expendDate;
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

    public Integer getOrderContractId() {
        return orderContractId;
    }

    public void setOrderContractId(Integer orderContractId) {
        this.orderContractId = orderContractId;
    }

    public Integer getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public List<Object> getExpends() {
        return expends;
    }

    public void setExpends(List<Object> expends) {
        this.expends = expends;
    }

    public List<Object> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Object> photos) {
        this.photos = photos;
    }

}
