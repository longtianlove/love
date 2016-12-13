package longtianlove.bottomlib.util;

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
    public static String encodeDes(String rawString){
        SecureRandom random=new SecureRandom();
        try {
            DESKeySpec desKey=new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory=SecretKeyFactory.getInstance("DES");
            SecretKey secretKey=keyFactory.generateSecret(desKey);
            Cipher cipher=Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE,secretKey,random);
            return cipher.doFinal(rawString.getBytes()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
