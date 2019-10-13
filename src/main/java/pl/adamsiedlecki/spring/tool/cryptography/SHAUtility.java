package pl.adamsiedlecki.spring.tool.cryptography;

import org.apache.commons.codec.digest.DigestUtils;

public class SHAUtility {

    public static String getSHA(String value) {
        byte[] hexByte = hexStringToByteArray(value);
        String hash = DigestUtils.sha256Hex(hexByte);
        hash = hash.substring(0, 32);

        return hash;
    }

    public static byte[] hexStringToByteArray(String hex) {
        if (!(hex.length() % 2 == 0)) {
            hex = "0" + hex;
        }
        int l = hex.length();
        byte[] data = new byte[l / 2];
        for (int i = 0; i < l; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
