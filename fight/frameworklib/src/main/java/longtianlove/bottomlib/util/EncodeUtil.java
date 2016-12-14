package longtianlove.bottomlib.util;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * 加密工具类
 */

public class EncodeUtil {
    //密码，长度要是8的倍数
    public static final String key="g.crossorigiN)}Functiond(){Delete_[h],Ab";

    /**
     * desI加密后，再经过Base64.encodeToString
     * @param rawString
     * @return
     */
    public static String encodeDes(String rawString){
        SecureRandom random=new SecureRandom();
        try {
            DESKeySpec desKey=new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
            SecretKey secretKey=keyFactory.generateSecret(desKey);
            Cipher cipher=Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE,secretKey,random);
            return Base64.encodeToString(cipher.doFinal(rawString.getBytes()),1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
