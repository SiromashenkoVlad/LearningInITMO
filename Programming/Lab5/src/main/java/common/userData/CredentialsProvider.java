package common.userData;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CredentialsProvider implements Serializable {
    private static final String PEPPER = "BPp64&*vBJKLBVPR8347BUpubPUVTC{34{}13<S C#*Q#^#";

    private String login;
    private String hashedPassword;

    public void setCredentials(String login, String rawPassword) {
        this.login = login;
        this.hashedPassword = hashPassword(PEPPER + rawPassword);
    }

    public String getLogin() { return login; }

    public String getPassword() { return hashedPassword; }

    private String hashPassword(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashed = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 не поддерживается", e);
        }
    }
}