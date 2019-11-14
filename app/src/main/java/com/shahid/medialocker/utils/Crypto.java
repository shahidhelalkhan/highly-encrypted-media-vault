package com.shahid.medialocker.utils;

import android.webkit.SafeBrowsingResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    public final static int READ_WRITE_BLOCK_BUFFER = 1024;
    private final static  String IMAGE_ENCRYPTER = "AES/CBC/PKCS5Padding";
    public final static String SECRET_KEY="AES";

    public static void encryptToFile(String keyStr, String specStr, InputStream in, OutputStream out) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        try{
            IvParameterSpec iv = new IvParameterSpec(specStr.getBytes("UTF-8"));
            SecretKeySpec keySpec = new SecretKeySpec(keyStr.getBytes("UTF-8"),SECRET_KEY);
            Cipher c = Cipher.getInstance(IMAGE_ENCRYPTER);
            c.init(Cipher.ENCRYPT_MODE,keySpec,iv);

            out = new CipherOutputStream(out,c);
            int count=0;
            byte[] buffer = new byte[READ_WRITE_BLOCK_BUFFER];
            while((count = in.read(buffer))>0)
                out.write(buffer,0,count);


        }
        finally {
            out.close();
        }
    }

    public static void decryptToFile(String keyStr, String specStr, InputStream in, OutputStream out) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        try{
            IvParameterSpec iv = new IvParameterSpec(specStr.getBytes("UTF-8"));
            SecretKeySpec keySpec = new SecretKeySpec(keyStr.getBytes("UTF-8"),SECRET_KEY);
            Cipher c = Cipher.getInstance(IMAGE_ENCRYPTER);
            c.init(Cipher.DECRYPT_MODE,keySpec,iv);

            out = new CipherOutputStream(out,c);
            int count=0;
            byte[] buffer = new byte[READ_WRITE_BLOCK_BUFFER];
            while((count = in.read(buffer))>0)
                out.write(buffer,0,count);


        }
        finally {
            out.close();
        }
    }

}
