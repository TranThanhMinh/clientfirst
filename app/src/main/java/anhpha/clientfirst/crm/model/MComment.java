package anhpha.clientfirst.crm.model;

import java.io.Serializable;

/**
 * Created by Administrator on 10/24/2017.
 */
//"user_comment_id":0,
//        "user_id":16,
//        "content_comment":"commnet first",
//        "order_contract_id":711,
public class MComment implements Serializable {
    private int user_comment_id;
    private int user_id;
    private String content_comment;

    public int getUser_comment_id() {
        return user_comment_id;
    }

    public void setUser_comment_id(int user_comment_id) {
        this.user_comment_id = user_comment_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent_comment() {
        return content_comment;
    }

    public void setContent_comment(String content_comment) {
        this.content_comment = content_comment;
    }

    public int getOrder_contract_id() {
        return order_contract_id;
    }

    public void setOrder_contract_id(int order_contract_id) {
        this.order_contract_id = order_contract_id;
    }

    private int order_contract_id;
}
