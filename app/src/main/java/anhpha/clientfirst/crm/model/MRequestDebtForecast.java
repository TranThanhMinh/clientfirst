package anhpha.clientfirst.crm.model;

import java.util.List;

/**
 * Created by mc975 on 2/6/17.
 */
public class MRequestDebtForecast {
    public List<User> getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(List<User> user_ids) {
        this.user_ids = user_ids;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    private List<User> user_ids;
    private String to_date;
    private String from_date;




}