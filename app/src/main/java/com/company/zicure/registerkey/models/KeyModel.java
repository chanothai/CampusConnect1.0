package com.company.zicure.registerkey.models;

/**
 * Created by 4GRYZ52 on 2/15/2017.
 */

public class KeyModel {
    private String token;
    private String username;
    private byte[] key;

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
