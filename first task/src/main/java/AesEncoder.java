import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class AesEncoder {

    private final Cipher cipher;
    private final String salt;

    @SneakyThrows
    public AesEncoder() {
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, generateKey());
        salt = generateSalt();
    }

    @SneakyThrows
    public byte[] encode(String originalMessage) {
        String message = originalMessage + salt;
        return cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
    }

    public String getSalt() {
        return salt;
    }

    @SneakyThrows
    private static SecretKey generateKey() {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        return generator.generateKey();
    }

    private String generateSalt() {
        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            Random random = new Random();
            salt.append((char) ('a' + random.nextInt(26)));
        }
        return salt.toString();
    }

}
