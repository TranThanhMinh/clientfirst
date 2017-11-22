package anhpha.clientfirst.crm.model;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 10/25/2017.
 */

public class MDocument implements Serializable {

    private int user_document_id;
    private int user_id;
    private String document_subject;

    public List<MDocuments> getDocumentsList() {
        return documentsList;
    }

    public void setDocumentsList(List<MDocuments> documentsList) {
        this.documentsList = documentsList;
    }

    private List<MDocuments> documentsList;
    public int getUser_documnet_id() {
        return user_document_id;
    }

    public void setUser_documnet_id(int user_documnet_id) {
        this.user_document_id = user_documnet_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDocument_subject() {
        return document_subject;
    }

    public void setDocument_subject(String document_subject) {
        this.document_subject = document_subject;
    }

    public int getOrder_contract_id() {
        return order_contract_id;
    }

    public void setOrder_contract_id(int order_contract_id) {
        this.order_contract_id = order_contract_id;
    }

    private int order_contract_id;
}
