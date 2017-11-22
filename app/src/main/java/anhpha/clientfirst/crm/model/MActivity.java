package anhpha.clientfirst.crm.model;

import java.util.List;

/**
 * Created by mc975 on 2/6/17.
 */
public class MActivity {

    private String from_date;
    private String to_date;
    private Double sales_amount;
    private int order_count;

    public double getDebt_amount() {
        return debt_amount;
    }

    public void setDebt_amount(double debt_amount) {
        this.debt_amount = debt_amount;
    }

    public double getFinish_amount() {

        return finish_amount;
    }

    public void setFinish_amount(double finish_amount) {
        this.finish_amount = finish_amount;
    }

    public double finish_amount;
    public double debt_amount;

    public int getOrder_contract_id() {
        return order_contract_id;
    }

    public void setOrder_contract_id(int order_contract_id) {
        this.order_contract_id = order_contract_id;
    }

    private int order_contract_id;
    private int add_client_count;
    private int meeting_count;
    private int work_count;
    private int call_count;
    private int email_count;
    private int event_count;
    private int comment_count;

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getDocument_count() {
        return document_count;
    }

    public void setDocument_count(int document_count) {
        this.document_count = document_count;
    }

    private int document_count;

    private int client_focus_count;
    private int expend_count;
    private Double expend_amount;

    public Double getDebt_count() {
        return debt_count;
    }

    public void setDebt_count(Double debt_count) {
        this.debt_count = debt_count;
    }

    public Double getOrder_finish_count() {
        return order_finish_count;
    }

    public void setOrder_finish_count(Double order_finish_count) {
        this.order_finish_count = order_finish_count;
    }

    private Double debt_count;
    private Double order_finish_count;
    public Double getExpend_amount() {
        return expend_amount;
    }

    public void setExpend_amount(Double expend_amount) {
        this.expend_amount = expend_amount;
    }



    public int getExpend_count() {
        return expend_count;
    }

    public void setExpend_count(int expend_count) {
        this.expend_count = expend_count;
    }


    private List<MActivityItem> activies;

    public int getClient_focus_count() {
        return client_focus_count;
    }

    public void setClient_focus_count(int client_focus_count) {
        this.client_focus_count = client_focus_count;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public Double getSales_amount() {
        return sales_amount;
    }

    public void setSales_amount(Double sales_amount) {
        this.sales_amount = sales_amount;
    }

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }

    public int getAdd_client_count() {
        return add_client_count;
    }

    public void setAdd_client_count(int add_client_count) {
        this.add_client_count = add_client_count;
    }

    public int getCheckin_count() {
        return meeting_count;
    }

    public void setCheckin_count(int checkin_count) {
        this.meeting_count = checkin_count;
    }

    public int getWork_count() {
        return work_count;
    }

    public void setWork_count(int work_count) {
        this.work_count = work_count;
    }

    public int getCall_count() {
        return call_count;
    }

    public void setCall_count(int call_count) {
        this.call_count = call_count;
    }

    public int getEmail_count() {
        return email_count;
    }

    public void setEmail_count(int email_count) {
        this.email_count = email_count;
    }

    public int getEvent_count() {
        return event_count;
    }

    public void setEvent_count(int event_count) {
        this.event_count = event_count;
    }

    public List<MActivityItem> getActivies() {
        return activies;
    }

    public void setActivies(List<MActivityItem> activies) {
        this.activies = activies;
    }
}
