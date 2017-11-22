package anhpha.clientfirst.crm.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mc975 on 2/9/17.
 */
public class MEmail implements Serializable {
    private int email_user_id;
    private int  client_id;
    private int client_delivery_id;
    private String content_email;
    private double latitude;
    private double longitude;
    private List<Tracking_value_defaults> values_default;
    private int user_id;
    private String user_name;

    public String getOrder_contract_name() {
        return order_contract_name;
    }

    public void setOrder_contract_name(String order_contract_name) {
        this.order_contract_name = order_contract_name;
    }

    private String order_contract_name;

    public int getOrder_contract_id() {
        return order_contract_id;
    }

    public void setOrder_contract_id(int order_contract_id) {
        this.order_contract_id = order_contract_id;
    }

    public int getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(int display_type) {
        this.display_type = display_type;
    }

    public int getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(int activity_type) {
        this.activity_type = activity_type;
    }

    private int  order_contract_id ;
    private int  display_type ;
    private int  activity_type ;

    public List<Tracking_value_defaults> getValues_default() {
        return values_default;
    }

    public void setValues_default(List<Tracking_value_defaults> values_default) {
        this.values_default = values_default;
    }



    public int getEmail_user_id() {
        return email_user_id;
    }

    public void setEmail_user_id(int email_user_id) {
        this.email_user_id = email_user_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getClient_delivery_id() {
        return client_delivery_id;
    }

    public void setClient_delivery_id(int client_delivery_id) {
        this.client_delivery_id = client_delivery_id;
    }

    public String getContent_email() {
        return content_email;
    }

    public void setContent_email(String content_email) {
        this.content_email = content_email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
