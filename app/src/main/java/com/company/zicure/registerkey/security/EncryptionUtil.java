package com.company.zicure.registerkey.security;

import android.content.Context;
import android.provider.Settings;
import android.util.Base64;

import com.company.zicure.registerkey.models.RequestToken;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

import javax.crypto.Cipher;

/**
 * Created by BallOmO on 10/12/2016 AD.
 */
public class EncryptionUtil {
    public static final String ALGORITHM= "RSA";
    private Context context = null;
    private static EncryptionUtil me = null;

    public EncryptionUtil(Context context){
        this.context = context;
    }
    public static EncryptionUtil getInstance(Context context){
        if (me == null){
            me = new EncryptionUtil(context);
        }
        return me;
    }

    private static byte[] encrypt(String originalText, String pubDecode){
        try{
            byte[] bytesDecode = Base64.decode(pubDecode, Base64.DEFAULT);
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytesDecode);
            final Key encryptPubKey = KeyFactory.getInstance(ALGORITHM).generatePublic(publicKeySpec);

            //get RSA cipher object and provider
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            //encryption the original text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, encryptPubKey);
            byte[] cipherText = cipher.doFinal(originalText.getBytes());
            return cipherText;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static String decrypt(byte[] text, String keyDecode){
        try{
            byte[] bytesDecode = Base64.decode(keyDecode, Base64.DEFAULT);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(bytesDecode);
            final Key encryptPrivKey = KeyFactory.getInstance(ALGORITHM).generatePrivate(privateKeySpec);

            //get RSA cipher object and provider
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            //encryption the plain text using the private key
            cipher.init(Cipher.DECRYPT_MODE, encryptPrivKey);
            byte[] decryptedText = cipher.doFinal(text);
            return new String(decryptedText);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] sign(String buffer, String privKey){
        try{
            byte[] bytesDecode = Base64.decode(privKey, Base64.DEFAULT);
            PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(bytesDecode);
            final PrivateKey signPrivKey = KeyFactory.getInstance(ALGORITHM).generatePrivate(privSpec);

            Signature instance = Signature.getInstance("SHA1withRSA");
            instance.initSign(signPrivKey);
            instance.update((buffer).getBytes());
            byte[] signature = instance.sign();

            return signature;

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private static boolean verify(String text, Key key, byte[] sign){
        try{
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify((PublicKey) key);
            signature.update(text.getBytes());
            return signature.verify(sign);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (InvalidKeyException e){
            e.printStackTrace();
        }catch (SignatureException e){
            e.printStackTrace();
        }

        return false;
    }


    public ArrayList<RequestToken> getEncrypted(String username, String password, String privBase64, String pubBase64){
        ArrayList<RequestToken> arrEncrypt = new ArrayList<RequestToken>();
        RequestToken requestToken = new RequestToken();
        try{
            if (pubBase64 != null && privBase64 != null){

                byte[] encryptUser = encrypt(username,pubBase64);
                String encodeUser = Base64.encodeToString(encryptUser, Base64.DEFAULT);

                byte[] encryptPass = encrypt(password,pubBase64);
                String encodePass = Base64.encodeToString(encryptPass, Base64.DEFAULT);

                byte[] signUser = sign(username, privBase64);
                String encodeSignUser = Base64.encodeToString(signUser, Base64.DEFAULT);

                byte[] signPass = sign(password, privBase64);
                String encodeSignPass = Base64.encodeToString(signPass, Base64.DEFAULT);

                requestToken.setDeviceID(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
                requestToken.setDeviceType("Mobile");
                requestToken.setUsername(encodeUser);
                requestToken.setPassword(encodePass);
                requestToken.setSignUser(encodeSignUser);
                requestToken.setSignPass(encodeSignPass);

                arrEncrypt.add(0, requestToken);
            }
                return arrEncrypt;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
