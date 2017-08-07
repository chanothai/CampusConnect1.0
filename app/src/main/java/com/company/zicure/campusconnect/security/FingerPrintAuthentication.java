package com.company.zicure.campusconnect.security;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Created by Pakgon on 5/30/2017 AD.
 */

public class FingerPrintAuthentication {
    private String TAG = "FingerPrint";
    private static final String KEY_NAME = "EDMTDEV";
    private KeyStore keyStore = null;
    private Cipher cipher = null;
    private Context context = null;
    private FingerprintManager mFingerprintManager = null;
    private KeyguardManager mKeyguardManager = null;

    public FingerPrintAuthentication(Context context) {
        this.context = context;
        mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        mFingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
    }

    @TargetApi(23)
    public void initFingerPrint() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        if (mFingerprintManager.isHardwareDetected()) {
            if (mFingerprintManager.hasEnrolledFingerprints()){
                if (mKeyguardManager.isKeyguardSecure()){
                    getKey();
                    if (cipherInit()){
                        FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                        FingerprintHandler helper = new FingerprintHandler(context);
//                        helper.startAuthentication(mFingerprintManager, cryptoObject);
                    }
                }else{
                    Log.d(TAG, "Lock screen security not enable in Settings");
                }
            }else{
                Log.d(TAG, "Must you register at least on fingerprint in Settings");
            }
        }else{
            Log.d(TAG, "Fingerprint authentication permission not enable");
        }
    }

    private boolean cipherInit() {
        try{
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"+KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try{
            keyStore.load(null);
            SecretKey key = (SecretKey)keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        }catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }catch (NoSuchAlgorithmException e2){
            e2.printStackTrace();
            return false;
        }catch (CertificateException e3){
            e3.printStackTrace();
            return false;
        }catch (UnrecoverableKeyException e4){
            e4.printStackTrace();
            return false;
        }catch (KeyStoreException e){
            e.printStackTrace();
            return false;
        }catch (InvalidKeyException e){
            e.printStackTrace();
            return false;
        }
    }

    @TargetApi(23)
    private void getKey(){
        try{
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        }catch (KeyStoreException e){
            e.printStackTrace();
        }

        KeyGenerator keyGenerator = null;

        try{
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        }catch (NoSuchAlgorithmException e){
            e.getLocalizedMessage();
        }catch (NoSuchProviderException e){
            e.printStackTrace();
        }

        try{
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            keyGenerator.generateKey();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (CertificateException e){
            e.printStackTrace();
        }catch (InvalidAlgorithmParameterException e){
            e.printStackTrace();
        }
    }
}
