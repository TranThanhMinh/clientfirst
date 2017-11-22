package anhpha.clientfirst.crm.model;

/**
 * Created by Administrator on 9/6/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MCost implements Serializable{

    @SerializedName("expend_id")
    @Expose
    private Integer expend_id;

    public int getOrder_contract_id() {
        return order_contract_id;
    }

    public void setOrder_contract_id(int order_contract_id) {
        this.order_contract_id = order_contract_id;
    }

    @SerializedName("order_contract_id")
    @Expose
    private int order_contract_id;
    @SerializedName("note")
    @Expose
    private String note;

    public String getExpend_date() {
        return expend_date;
    }

    public void setExpend_date(String expend_date) {
        this.expend_date = expend_date;
    }
    @SerializedName("expend_date")
    @Expose
    public String expend_date;

    public Integer getExpend_id() {
        return expend_id;
    }

    public void setExpend_id(Integer expend_id) {
        this.expend_id = expend_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getExpend_group_type_id() {
        return expend_group_type_id;
    }

    public void setExpend_group_type_id(Integer expend_group_type_id) {
        this.expend_group_type_id = expend_group_type_id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    @SerializedName("expend_group_type_id")
    @Expose
    private Integer expend_group_type_id;
    @SerializedName("expend_type_id")
    @Expose
    private Integer expendTypeId;
    @SerializedName("expend_group_type_name")
    @Expose
    private String expendGroupTypeName;
    @SerializedName("expend_type_name")
    @Expose
    private Object expendTypeName;
    @SerializedName("expend_date")
    @Expose
    private Object expendDate;
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
    private Integer status_id;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("expends")
    @Expose
    private List<Expend> expends = null;
    @SerializedName("photos")
    @Expose
    private List<Object> photos = null;


    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    boolean check = false;

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

    public Object getExpendTypeName() {
        return expendTypeName;
    }

    public void setExpendTypeName(Object expendTypeName) {
        this.expendTypeName = expendTypeName;
    }

    public Object getExpendDate() {
        return expendDate;
    }

    public void setExpendDate(Object expendDate) {
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


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<Expend> getExpends() {
        return expends;
    }

    public void setExpends(List<Expend> expends) {
        this.expends = expends;
    }

    public List<Object> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Object> photos) {
        this.photos = photos;
    }

}