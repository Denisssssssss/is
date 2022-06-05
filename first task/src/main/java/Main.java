import java.util.Random;

public class Main {

    public static void main(String[] args) {
        AesEncoder encoder = new AesEncoder();
        SaltGuesser saltGuesser = new SaltGuesser(encoder);
        System.out.println("Salt:\n" + encoder.getSalt());
        System.out.println("Guessed salt:\n" + saltGuesser.guess());
    }

}
