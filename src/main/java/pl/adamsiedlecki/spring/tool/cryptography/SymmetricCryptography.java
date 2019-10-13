package pl.adamsiedlecki.spring.tool.cryptography;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SymmetricCryptography {

    private static final Logger log = LoggerFactory.getLogger(SymmetricCryptography.class);

    public static byte[] encrypt(String data, String keyString) {
        Cipher aesCipher = null;
        SecretKey secKey = null;
        byte[] byteCipherText = null;
        keyString = SHAUtility.getSHA(keyString);
        try {
            byte[] key = keyString.getBytes(StandardCharsets.UTF_8);
            secKey = new SecretKeySpec(key, "AES");
            aesCipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            byteCipherText = aesCipher.doFinal(data.getBytes());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return byteCipherText;
    }

    public static String decrypt(byte[] data, String keyString) {
        keyString = SHAUtility.getSHA(keyString);
        Cipher aesCipher = null;
        SecretKey secKey;
        byte[] byteCipherText = null;
        try {
            aesCipher = Cipher.getInstance("AES");
            //byte[] decodedKey = Base64.getDecoder().decode(Config.getPrivateAES());
            // rebuild key using SecretKeySpec
            secKey = new SecretKeySpec(keyString.getBytes(), "AES");
            aesCipher.init(Cipher.DECRYPT_MODE, secKey);
            byteCipherText = aesCipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            //e.printStackTrace();
            // This is case when key is just bad: Such issues can arise if a bad key is used during decryption.
            log.info("PROVIDED KEY IS BAD");
            return Base64.getEncoder().encodeToString(data);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return new String(byteCipherText);
    }

}