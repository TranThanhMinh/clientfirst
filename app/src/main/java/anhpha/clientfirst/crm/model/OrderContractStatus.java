package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 10/19/2017.
 */

public class OrderContractStatus implements Serializable {

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    private boolean check = false;
    @SerializedName("order_contract_status_id")
    @Expose
    private Integer orderContractStatusId;
    @SerializedName("order_contract_status_name")
    @Expose
    private String orderContractStatusName;
    @SerializedName("order_contract_status_group_id")
    @Expose
    private Integer orderContractStatusGroupId;
    @SerializedName("percent_done")
    @Expose
    private Integer percentDone;
    @SerializedName("order_contract_status_type_id")
    @Expose
    private Integer orderContractStatusTypeId;
    @SerializedName("note")
    @Expose
    private Object note;
    @SerializedName("order_no")
    @Expose
    private Integer orderNo;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    private double money;

    public Integer getOrderContractStatusId() {
        return orderContractStatusId;
    }

    public void setOrderContractStatusId(Integer orderContractStatusId) {
        this.orderContractStatusId = orderContractStatusId;
    }

    public String getOrderContractStatusName() {
        return orderContractStatusName;
    }

    public void setOrderContractStatusName(String orderContractStatusName) {
        this.orderContractStatusName = orderContractStatusName;
    }

    public Integer getOrderContractStatusGroupId() {
        return orderContractStatusGroupId;
    }

    public void setOrderContractStatusGroupId(Integer orderContractStatusGroupId) {
        this.orderContractStatusGroupId = orderContractStatusGroupId;
    }

    public Integer getPercentDone() {
        return percentDone;
    }

    public void setPercentDone(Integer percentDone) {
        this.percentDone = percentDone;
    }

    public Integer getOrderContractStatusTypeId() {
        return orderContractStatusTypeId;
    }

    public void setOrderContractStatusTypeId(Integer orderContractStatusTypeId) {
        this.orderContractStatusTypeId = orderContractStatusTypeId;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}
