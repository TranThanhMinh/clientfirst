package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 9/22/2017.
 */

public class CompanyExist {
    @SerializedName("client_id")
    @Expose
    private Integer client_id;
    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("position")
    @Expose
    private String position;

    public int getClient_structure_id() {
        return client_structure_id;
    }

    public void setClient_structure_id(int client_structure_id) {
        this.client_structure_id = client_structure_id;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    @SerializedName("client_type_id")
    @Expose
    public int client_structure_id ;
    @SerializedName("client_structure_id")
    @Expose
    private String   parent_name;
    @SerializedName("parent_name")
    @Expose
    private Integer clientTypeId;
    @SerializedName("client_group_id")
    @Expose
    private Integer clientGroupId;
    @SerializedName("client_area_id")
    @Expose
    private Integer clientAreaId;
    @SerializedName("latitude")
    @Expose
    private Integer latitude;
    @SerializedName("longitude")
    @Expose
    private Integer longitude;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("user_manager_id")
    @Expose
    private Integer userManagerId;
    @SerializedName("labels")
    @Expose
    private List<MLabel> labels = null;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    private boolean check = false;

    public Integer getClientId() {
        return client_id;
    }

    public void setClientId(Integer clientId) {
        this.client_id = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(Integer clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public Integer getClientGroupId() {
        return clientGroupId;
    }

    public void setClientGroupId(Integer clientGroupId) {
        this.clientGroupId = clientGroupId;
    }

    public Integer getClientAreaId() {
        return clientAreaId;
    }

    public void setClientAreaId(Integer clientAreaId) {
        this.clientAreaId = clientAreaId;
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

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getUserManagerId() {
        return userManagerId;
    }

    public void setUserManagerId(Integer userManagerId) {
        this.userManagerId = userManagerId;
    }

    public List<MLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<MLabel> labels) {
        this.labels = labels;
    }


}
