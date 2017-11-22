package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 10/30/2017.
 */

public class Document {
    @SerializedName("user_document_id")
    @Expose
    private Integer userDocumentId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("content_document")
    @Expose
    private String contentDocument;
    @SerializedName("document_subject")
    @Expose
    private String documentSubject;
    @SerializedName("latitude")
    @Expose
    private Integer latitude;
    @SerializedName("longitude")
    @Expose
    private Integer longitude;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("update_status")
    @Expose
    private Integer updateStatus;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("update_time")
    @Expose
    private Integer updateTime;
    @SerializedName("order_contract_id")
    @Expose
    private Integer orderContractId;

    public String getOrder_contract_name() {
        return order_contract_name;
    }

    public void setOrder_contract_name(String order_contract_name) {
        this.order_contract_name = order_contract_name;
    }

    @SerializedName("order_contract_name")
    @Expose
    private String order_contract_name;
    @SerializedName("display_type")
    @Expose
    private Integer displayType;
    @SerializedName("activity_type")
    @Expose
    private Integer activityType;
    @SerializedName("values_default")
    @Expose
    private List<Object> valuesDefault = null;
    @SerializedName("documents")
    @Expose
    private List<MDocuments> documents = null;

    public Integer getUserDocumentId() {
        return userDocumentId;
    }

    public void setUserDocumentId(Integer userDocumentId) {
        this.userDocumentId = userDocumentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getContentDocument() {
        return contentDocument;
    }

    public void setContentDocument(String contentDocument) {
        this.contentDocument = contentDocument;
    }

    public String getDocumentSubject() {
        return documentSubject;
    }

    public void setDocumentSubject(String documentSubject) {
        this.documentSubject = documentSubject;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
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

    public List<Object> getValuesDefault() {
        return valuesDefault;
    }

    public void setValuesDefault(List<Object> valuesDefault) {
        this.valuesDefault = valuesDefault;
    }

    public List<MDocuments> getDocuments() {
        return documents;
    }

    public void setDocuments(List<MDocuments> documents) {
        this.documents = documents;
    }
}
