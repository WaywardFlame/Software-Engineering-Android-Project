package cs3773.groupproject.autoquestrentals.databaseCode;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A class with methods for encoding passwords.
 * Use before adding data to database, or use
 * to verify that password is correct.
 * --------------------------------------------
 * Credit to https://www.javatpoint.com/how-to-encrypt-password-in-java
 */
public class PasswordEncode {
    /**
     * Takes a string and turns it into a byte array
     * @param input The password to encode
     * @return A byte[] with the encoded password
     * @throws NoSuchAlgorithmException Don't why this is required, but it is. Be sure to catch this.
     */
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Takes a byte[] and turns into a String.
     * Useful for comparing a provided password, or getting
     * an encoded password as a String before adding it to account in database.
     * @param hash The byte[] hash with encoded password
     * @return A hexademical String of the encoded password in byte[]
     */
    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString());
        while(hexString.length() < 32){
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}
