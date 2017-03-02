package com.company.zicure.registerkey;

import android.net.Uri;

/**
 * Created by 4GRYZ52 on 10/26/2016.
 */
public class ProtocolFormatter {
    public static String formatRequest(String address, int port, boolean secure, Position position) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme(secure ? "https" : "http").encodedAuthority(address + ':' + port)
                .appendQueryParameter("id", position.getDeviceId())
                .appendQueryParameter("timestamp", String.valueOf(position.getTime().getTime() / 1000))
                .appendQueryParameter("lat", String.valueOf(position.getLatitude()))
                .appendQueryParameter("lon", String.valueOf(position.getLongitude()))
                .appendQueryParameter("speed", String.valueOf(position.getSpeed()))
                .appendQueryParameter("bearing", String.valueOf(position.getCourse()))
                .appendQueryParameter("altitude", String.valueOf(position.getAltitude()))
                .appendQueryParameter("batt", String.valueOf(position.getBattery()));

        return builder.build().toString();
    }
}
