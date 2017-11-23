package anhpha.clientfirst.crm.model;

import java.io.Serializable;

/**
 * Created by mc975 on 2/6/17.
 */
public class MActivityItem implements Serializable {
    private int user_id;
    private int client_id;

    public int getExpend_id() {
        return expend_id;
    }

    public void setExpend_id(int expend_id) {
        this.expend_id = expend_id;
    }

    private int expend_id;
    private int user_document_id;

    public int getUser_document_id() {
        return user_document_id;
    }

    public void setUser_document_id(int user_document_id) {
        this.user_document_id = user_document_id;
    }

    public int getUser_comment_id() {
        return user_comment_id;
    }

    public void setUser_comment_id(int user_comment_id) {
        this.user_comment_id = user_comment_id;
    }

    private int  user_comment_id;

    private String user_name;
    private String client_name;
    private String address;
    private String activity_phone;
    private String activity_content;
    private Double activity_amount;
    private String activity_date;

    public String getOrder_contract_status_name() {
        return order_contract_status_name;
    }

    public void setOrder_contract_status_name(String order_contract_status_name) {
        this.order_contract_status_name = order_contract_status_name;
    }

    private String order_contract_status_name;
    private int event_id;
    private int order_contract_id;
    private int work_user_id;
    private int focus_status_id;
    private int client_focus_id;
    private int expend_count;

    public int getOrder_contract_debt_id() {
        return order_contract_debt_id;
    }

    public void setOrder_contract_debt_id(int order_contract_debt_id) {
        this.order_contract_debt_id = order_contract_debt_id;
    }

    private int order_contract_debt_id;

    public String getOrder_contract_name() {
        return order_contract_name;
    }

    public void setOrder_contract_name(String order_contract_name) {
        this.order_contract_name = order_contract_name;
    }

    private String order_contract_name;

    public int getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(int display_type) {
        this.display_type = display_type;
    }

    private int display_type;

    public int getExpend_count() {
        return expend_count;
    }

    public void setExpend_count(int expend_count) {
        this.expend_count = expend_count;
    }

    public int getExpend_amount() {
        return expend_amount;
    }

    public void setExpend_amount(int expend_amount) {
        this.expend_amount = expend_amount;
    }

    private int expend_amount;
    public int getFocus_status_id() {
        return focus_status_id;
    }

    public void setFocus_status_id(int focus_status_id) {
        this.focus_status_id = focus_status_id;
    }

    public int getClient_focus_id() {
        return client_focus_id;
    }

    public void setClient_focus_id(int client_focus_id) {
        this.client_focus_id = client_focus_id;
    }

    private int user_call_id;

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    private int order_status;
    private int inventory_contract_client_id;
    private int user_checkin_id;
    private int user_email_id;
    private int add_client_id;
    private int user_meeting_id;
    private int activity_type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public int getUser_email_id() {
        return user_email_id;
    }

    public void setUser_email_id(int user_email_id) {
        this.user_email_id = user_email_id;
    }

    public int getUser_meeting_id() {
        return user_meeting_id;
    }

    public void setUser_meeting_id(int user_meeting_id) {
        this.user_meeting_id = user_meeting_id;
    }

    public int getMeeting_id() {
        return user_meeting_id;
    }

    public void setMeeting_id(int meeting_id) {
        this.user_meeting_id = meeting_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(int activity_type) {
        this.activity_type = activity_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getActivity_phone() {
        return activity_phone;
    }

    public void setActivity_phone(String activity_phone) {
        this.activity_phone = activity_phone;
    }

    public String getActivity_content() {
        return activity_content;
    }

    public void setActivity_content(String activity_content) {
        this.activity_content = activity_content;
    }

    public Double getActivity_amount() {
        return activity_amount;
    }

    public void setActivity_amount(Double activity_amount) {
        this.activity_amount = activity_amount;
    }

    public String getActivity_date() {
        return activity_date;
    }

    public void setActivity_date(String activity_date) {
        this.activity_date = activity_date;
    }

    public int getOrder_contract_id() {
        return order_contract_id;
    }

    public void setOrder_contract_id(int order_contract_id) {
        this.order_contract_id = order_contract_id;
    }

    public int getWork_user_id() {
        return work_user_id;
    }

    public void setWork_user_id(int work_user_id) {
        this.work_user_id = work_user_id;
    }

    public int getUser_call_id() {
        return user_call_id;
    }

    public void setUser_call_id(int user_call_id) {
        this.user_call_id = user_call_id;
    }

    public int getInventory_contract_client_id() {
        return inventory_contract_client_id;
    }

    public void setInventory_contract_client_id(int inventory_contract_client_id) {
        this.inventory_contract_client_id = inventory_contract_client_id;
    }

    public int getUser_checkin_id() {
        return user_checkin_id;
    }

    public void setUser_checkin_id(int user_checkin_id) {
        this.user_checkin_id = user_checkin_id;
    }

    public int getAdd_client_id() {
        return add_client_id;
    }

    public void setAdd_client_id(int add_client_id) {
        this.add_client_id = add_client_id;
    }
}
