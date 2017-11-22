package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 10/19/2017.
 */

public class OrderContractStatusGroup {

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    private boolean check=false;
    @SerializedName("order_contract_status_group_id")
    @Expose
    private Integer orderContractStatusGroupId;
    @SerializedName("order_no")
    @Expose
    private Integer orderNo;
    @SerializedName("order_contract_status_group_name")
    @Expose
    private String orderContractStatusGroupName;
    @SerializedName("order_contract_status")
    @Expose
    private List<OrderContractStatus> orderContractStatus = null;

    public Integer getOrderContractStatusGroupId() {
        return orderContractStatusGroupId;
    }

    public void setOrderContractStatusGroupId(Integer orderContractStatusGroupId) {
        this.orderContractStatusGroupId = orderContractStatusGroupId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderContractStatusGroupName() {
        return orderContractStatusGroupName;
    }

    public void setOrderContractStatusGroupName(String orderContractStatusGroupName) {
        this.orderContractStatusGroupName = orderContractStatusGroupName;
    }

    public List<OrderContractStatus> getOrderContractStatus() {
        return orderContractStatus;
    }

    public void setOrderContractStatus(List<OrderContractStatus> orderContractStatus) {
        this.orderContractStatus = orderContractStatus;
    }

}
