package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 10/30/2017.
 */

public class MDocuments {
    @SerializedName("documet_id")
    @Expose
    private Integer documetId;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("document_name")
    @Expose
    private String documentName;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("code")
    @Expose
    private Object code;
    @SerializedName("object_id")
    @Expose
    private Integer objectId;
    @SerializedName("order_no")
    @Expose
    private Integer orderNo;

    public Integer getDocumetId() {
        return documetId;
    }

    public void setDocumetId(Integer documetId) {
        this.documetId = documetId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

}
