package pcs.labsoft.agencia.components;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by leoiacovini on 23/11/16.
 */
public class Auth {

    public String secureHash(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hashed.length; i++) {
                sb.append(Integer.toString((hashed[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
