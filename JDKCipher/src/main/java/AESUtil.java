import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class AESUtil {
    //16bytes
    private static final String SLAT_KEY = "felixzh2020_ok!!";

    //16 bytes
    private static final IvParameterSpec IV_PARAMETER_SPEC = new IvParameterSpec("IV_PARAMETER_SPE".getBytes());


    private static String aesEncrypt(String password) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(SLAT_KEY.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IV_PARAMETER_SPEC);
        byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    private static String aesDecrypt(String password) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(SLAT_KEY.getBytes(StandardCharsets.UTF_8), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IV_PARAMETER_SPEC);
        byte[] encrypted = cipher.doFinal(Base64.getDecoder().decode(password));
        return new String(encrypted);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(aesEncrypt("felixzh"));
        System.out.println(aesDecrypt("5dW2+zj3Z91+esi2hI9EEg=="));
    }
}
