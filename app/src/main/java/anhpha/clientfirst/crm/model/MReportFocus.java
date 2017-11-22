package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 8/30/2017.
 */

public class MReportFocus {
    @SerializedName("focus_status_id")
    @Expose
    private Integer focusStatusId;
    @SerializedName("focus_status_name")
    @Expose
    private String focusStatusName;
    @SerializedName("num_client")
    @Expose
    private Integer numClient;

    public Integer getFocusStatusId() {
        return focusStatusId;
    }

    public void setFocusStatusId(Integer focusStatusId) {
        this.focusStatusId = focusStatusId;
    }

    public String getFocusStatusName() {
        return focusStatusName;
    }

    public void setFocusStatusName(String focusStatusName) {
        this.focusStatusName = focusStatusName;
    }

    public Integer getNumClient() {
        return numClient;
    }

    public void setNumClient(Integer numClient) {
        this.numClient = numClient;
    }
}
