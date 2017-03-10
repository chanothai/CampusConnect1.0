package profilemof.zicure.company.com.profilemof.utilize;

import profilemof.zicure.company.com.profilemof.model.User;

/**
 * Created by 4GRYZ52 on 3/4/2017.
 */

public class ModelCartProfile {
    private static ModelCartProfile me = null;
    private User user = null;
    public ModelCartProfile(){
        user = new User();
    }

    public static ModelCartProfile getInstance(){
        if (me == null){
            me = new ModelCartProfile();
        }
        return me;
    }

    public User getAccount(){
        return user;
    }
}
