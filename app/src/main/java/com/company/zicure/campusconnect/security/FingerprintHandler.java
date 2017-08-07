package com.company.zicure.campusconnect.security;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.widget.Toast;

import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

/**
 * Created by Pakgon on 5/30/2017 AD.
 */
@TargetApi(23)
public class FingerprintHandler implements FingerPrintAuthCallback {
    private Context context = null;
    private FingerPrintAuthHelper mFingerPrintAuthHelper = null;

    public FingerprintHandler(Context context) {
        this.context = context;
    }

    public FingerPrintAuthHelper initFingerprint() {
        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(context, this);
        return mFingerPrintAuthHelper;
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Toast.makeText(context, "Fingerprint authentication permission not enable", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoFingerPrintRegistered() {
        Toast.makeText(context, "Must you register at least on fingerprint in Settings", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBelowMarshmallow() {
        Toast.makeText(context, "Device running below API 23 version of android that does not support finger print authentication", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        switch (errorCode) {    //Parse the error code for recoverable/non recoverable error.
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                //Cannot recognize the fingerprint scanned.
                Toast.makeText(context, "Cannot recognize the fingerprint scanned.", Toast.LENGTH_SHORT).show();
                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                //This is not recoverable error. Try other options for user authentication. like pin, password.
                Toast.makeText(context, "CThis is not recoverable error. Try other options for user authentication. like pin, password.", Toast.LENGTH_SHORT).show();
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                //Any recoverable error. Display message to the user.
                Toast.makeText(context, "Any recoverable error. Display message to the user.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
