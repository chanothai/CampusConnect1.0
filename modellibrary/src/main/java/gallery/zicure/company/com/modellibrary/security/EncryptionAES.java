package gallery.zicure.company.com.modellibrary.security;

import android.util.Base64;
import android.util.Log;

import java.security.AlgorithmParameters;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by 4GRYZ52 on 11/25/2016.
 */

public class EncryptionAES {
    private byte[] ivBytes = null;
    private int blockSize = 256;
    private static EncryptionAES me = null;
    private String plainText;
    private SecretKey secretKey = null;
    private PBEKeySpec spec = null;
    private SecretKeyFactory skf = null;
    private SecretKeySpec secretKeySpec = null;
    private byte[] key = null;

    public EncryptionAES(byte[] key){
        this.key = key;
        ivBytes = new byte[16];
        secretKeySpec = new SecretKeySpec(key,"AES");
    }

    public static EncryptionAES newInstance(byte[] key){
        me = new EncryptionAES(key);
        return me;
    }

    public static byte[] generateKey(){
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
            return secretKey.getEncoded();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String encrypt(String plain){
        String encToStr;
        try{
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            AlgorithmParameters params = cipher.getParameters();
            ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
            Log.d("EncryptCart", "IV before: " + ivBytes.length);
            String ivEncode = Base64.encodeToString(ivBytes, Base64.NO_WRAP);

            encToStr = Base64.encodeToString(cipher.doFinal(plain.getBytes("UTF-8")), Base64.NO_WRAP) + "ByteIV"+ ivEncode;
            Log.d("EncryptCart", "SecretKeySpec: " + new String(secretKeySpec.getEncoded()));
            Log.d("RegisterResponse", new String(cipher.doFinal(plain.getBytes("UTF-8"))));
            Log.d("RegisterResponse", encToStr);
            return encToStr;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String plain, byte[] ivBytes){
        byte[] textBytes = Base64.decode(plain, Base64.NO_WRAP);
        ivBytes = Base64.decode(ivBytes, Base64.NO_WRAP);

        try{
            if (secretKeySpec != null){
                Log.d("DecryptCart", "text: " + new String(textBytes));
                Log.d("DecryptCart", "IV " + new String(ivBytes));

                IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
                Cipher cipher = null;
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
                return new String(cipher.doFinal(textBytes));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
