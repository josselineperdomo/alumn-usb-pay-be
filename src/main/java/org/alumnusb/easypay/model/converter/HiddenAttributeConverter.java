package org.alumnusb.easypay.model.converter;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class HiddenAttributeConverter implements AttributeConverter<String, String> {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final byte[] KEY = "SurvivorsSuperSecretKey".getBytes();

    @Override
    public String convertToDatabaseColumn(String attribute) {
        SecretKey secretKey = new SecretKeySpec(KEY, "AES");
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.encodeBytes(cipher.doFinal(attribute.getBytes()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException e) {
            log.error("Error encrypting value");
            log.error(e.getMessage());
            throw new IllegalStateException("Error storing data");
        }
    }

    @Override
    public String convertToEntityAttribute(String data) {
        SecretKey secretKey = new SecretKeySpec(KEY, "AES");
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return Base64.encodeBytes(cipher.doFinal(data.getBytes()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException e) {
            log.error("Error decrypting value");
            log.error(e.getMessage());
            throw new IllegalStateException("Error retrieving data");
        }
    }
}