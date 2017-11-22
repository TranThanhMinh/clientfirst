package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 8/18/2017.
 */

public class Focus_target implements Serializable {

    @SerializedName("focus_target_group_id")
    @Expose
    private Integer focus_target_group_id;
    @SerializedName("status_id")
    @Expose
    private Integer status_id;
    @SerializedName("order_no")
    @Expose
    private Integer order_no;
    @SerializedName("focus_target_group_name")
    @Expose
    private String focus_target_group_name;

    public List<FocusGroup> getFocusGroup() {
        return focusGroup;
    }

    public void setFocusGroup(List<FocusGroup> focusGroup) {
        this.focusGroup = focusGroup;
    }

    @SerializedName("focus_group")
    @Expose
    private List<FocusGroup> focusGroup = null;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    private boolean check=false;

    public Integer getFocus_target_id() {
        return focus_target_group_id;
    }

    public void setFocus_target_id(Integer focus_target_id) {
        this.focus_target_group_id = focus_target_id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public Integer getOrder_no() {
        return order_no;
    }

    public void setOrder_no(Integer order_no) {
        this.order_no = order_no;
    }

    public String getFocus_target_name() {
        return focus_target_group_name;
    }

    public void setFocus_target_name(String focus_target_name) {
        this.focus_target_group_name = focus_target_name;
    }
}
