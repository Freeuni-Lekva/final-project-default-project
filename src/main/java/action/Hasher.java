package action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    // Array of chars used to produce strings
    public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!"
            .toCharArray();
    private MessageDigest md;
//	private String password;

    /*
     * Given a byte[] array, produces a hex String, such as "234a6f". with 2
     * chars for each byte in the array. (provided code)
     */
    public String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff; // remove higher bits, sign
            if (val < 16)
                buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    public String getHashedPassword(String password){
        md.update(password.getBytes());
        return this.hexToString(md.digest());
    }

    public Hasher() {
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}