package gallery.zicure.company.com.modellibrary.models;

/**
 * Created by BallOmO on 10/13/2016 AD.
 */
public class RequestToken {
    private String deviceID;
    private String username;
    private String password;
    private String deviceType;
    private String signUser;
    private String signPass;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getSignUser() {
        return signUser;
    }

    public void setSignUser(String signUser) {
        this.signUser = signUser;
    }

    public String getSignPass() {
        return signPass;
    }

    public void setSignPass(String signPass) {
        this.signPass = signPass;
    }
}
