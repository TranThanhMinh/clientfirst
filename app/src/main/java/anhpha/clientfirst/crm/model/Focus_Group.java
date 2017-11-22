package anhpha.clientfirst.crm.model;


import java.io.Serializable;

/**
 * Created by Administrator on 8/24/2017.
 */

public class Focus_Group implements Serializable {


    private Integer focus_target_id;

    private Integer focus_target_group_id;

    private Integer status_id;

    private Integer order_no;

    private String focus_target_name;

    public boolean isCheck() {
        return Check;
    }

    public void setCheck(boolean check) {
        Check = check;
    }

    private boolean Check = false;

    public Integer getFocus_target_id() {
        return focus_target_id;
    }

    public void setFocus_target_id(Integer focus_target_id) {
        this.focus_target_id = focus_target_id;
    }

    public Integer getFocus_target_group_id() {
        return focus_target_group_id;
    }

    public void setFocus_target_group_id(Integer focus_target_group_id) {
        this.focus_target_group_id = focus_target_group_id;
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
        return focus_target_name;
    }

    public void setFocus_target_name(String focus_target_name) {
        this.focus_target_name = focus_target_name;
    }
}
