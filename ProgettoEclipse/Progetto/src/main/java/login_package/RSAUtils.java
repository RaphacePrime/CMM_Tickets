package login_package;

import java.security.*;
import java.security.spec.*;
import javax.crypto.Cipher;
import java.util.Base64;
import java.nio.file.*;

public class RSAUtils {
    private static PublicKey publicKey;
    private static PrivateKey privateKey;

    private static final String PUBLIC_KEY_FILE = "publicKey.key";
    private static final String PRIVATE_KEY_FILE = "privateKey.key";
    private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";

    public static void generateKeys() throws Exception {
        if (!Files.exists(Paths.get(PUBLIC_KEY_FILE)) || !Files.exists(Paths.get(PRIVATE_KEY_FILE))) {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();
            publicKey = pair.getPublic();
            privateKey = pair.getPrivate();
            
            saveKey(PUBLIC_KEY_FILE, publicKey.getEncoded());
            saveKey(PRIVATE_KEY_FILE, privateKey.getEncoded());
        }
        loadKeys(); 
    }

    public static void loadKeys() throws Exception {
        publicKey = loadPublicKey(PUBLIC_KEY_FILE);
        privateKey = loadPrivateKey(PRIVATE_KEY_FILE);
    }

    private static void saveKey(String filePath, byte[] key) throws Exception {
        Files.write(Paths.get(filePath), key);
    }

    private static PublicKey loadPublicKey(String filePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    private static PrivateKey loadPrivateKey(String filePath) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    public static String encrypt(String text) throws Exception {
        if (publicKey == null) loadKeys();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedText) throws Exception {
        if (privateKey == null) loadKeys();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
}
