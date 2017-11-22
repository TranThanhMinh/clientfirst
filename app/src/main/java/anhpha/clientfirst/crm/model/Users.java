package anhpha.clientfirst.crm.model;

import java.util.List;

/**
 * Created by Administrator on 8/30/2017.
 */

public class Users {


    public List<MId> getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(List<MId> user_ids) {
        this.user_ids = user_ids;
    }

    private List<MId> user_ids;

    public List<MId> getIds() {
        return ids;
    }

    public void setIds(List<MId> ids) {
        this.ids = ids;
    }

    private List<MId> ids;
}
