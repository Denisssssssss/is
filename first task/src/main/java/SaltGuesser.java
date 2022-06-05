import java.nio.charset.StandardCharsets;

public class SaltGuesser {

    private final AesEncoder encoder;
    private final int size = 16;

    public SaltGuesser(AesEncoder encoder) {
        this.encoder = encoder;
    }

    public String guess() {
        StringBuilder salt = new StringBuilder();
        char symbol = next("");
        while (symbol != '-') {
            salt.append(symbol);
            symbol = next(salt.toString());
        }
        return salt.toString();
    }

    private char next(String tempSalt) {
        int saltLength = tempSalt.getBytes(StandardCharsets.UTF_8).length;
        String comparisonMessage = "a".repeat(size - (saltLength % size) - 1);
        String correctPart = comparisonMessage.concat(tempSalt);
        for (int i = 0; i < 26; i++) {
            char symbol = (char) ('a' + i);
            if (isEquals(encoder.encode(comparisonMessage), encoder.encode(correctPart + symbol), (saltLength / size) * size)) {
                return symbol;
            }
        }
        return '-';
    }

    private boolean isEquals(byte[] a, byte[] b, int start) {
        for (int i = start; i < start + size; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

}
