import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Main {

    static final String AES = "AES";
    static final String KEY = "YELLOW SUBMARINE";
    static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    static final byte[] ZEROS = "0".repeat(16).getBytes(StandardCharsets.UTF_8);

    public static void main(String[] args) throws InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);

        byte[] message = new byte[0];
        byte[] warning = new byte[0];

        try {
            cipher = Cipher.getInstance(TRANSFORMATION);
            message = encode("alert(\"You are pwned!\");", cipher);
            warning = encode("alert(\"Hello world!\");", cipher);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        int newLength = warning.length + message.length;
        byte[] newArray = new byte[newLength];

        for (int i = 0; i < message.length; i++) {
            newArray[i] = message[i];
            newArray[i + message.length] = warning[i];
        }
        System.out.println(new String(warning));
        System.out.println(new String(decode(newArray, cipher)));
    }

    public static byte[] encode(String text, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), AES), new IvParameterSpec(ZEROS));
        return cipher.doFinal((text).getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] decode(byte[] bytes, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), AES), new IvParameterSpec(ZEROS));
        return cipher.doFinal(bytes);
    }
}