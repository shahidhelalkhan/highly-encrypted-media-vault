package com.shahid.medialocker.utils;

import android.os.Environment;
import android.util.Log;

import com.scottyab.aescrypt.AESCrypt;
import com.shahid.medialocker.models.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.NoSuchPaddingException;

public class FileUtility {
    private static final String TAG = "FileUtility";
    public static boolean moveFile(File source, File dest) throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException {

        if(dest.exists()){

            Log.d(TAG, "File Found /"+ dest.getAbsolutePath());
            InputStream targetStream = new FileInputStream(source);
            Log.d(TAG, "moveFile: isExist");
            Log.d(TAG, "moveFile: path"+dest.getPath());
            File output = new File(dest.getAbsolutePath());
            Crypto.encryptToFile(Constants.MYKEY,Constants.MYSPECKEY,targetStream,new FileOutputStream(output) );
            source.delete();
        }



        return false;
    }

    public static void deleteDecryptedData(){
        File dir = new File(Environment.getExternalStorageDirectory().toString()+"/decrypted");
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                Log.d(TAG, "deleteDecryptedData: delete");
                new File(dir, children[i]).delete();
            }
        }
    }

    private static byte[] readFileToByteArray(File file){
        FileInputStream fis = null;

        byte[] bArray = new byte[(int) file.length()];
        try{
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();

        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
        return bArray;
    }

    private static File arrayToFile(File file,byte[] array){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(array);
            fos.flush();
            fos.close();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(file.getAbsolutePath());
    }

    public static byte[] encryptByteArray(byte[] array){
        String s = Base64.getEncoder().encodeToString(array);
        try {
            String encryptedText = AESCrypt.encrypt(Constants.KEY,s);
            byte[] encryptedByteArray = encryptedText.getBytes();
            return encryptedByteArray;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        return array;

    }


    public static String getExtension(String path){
        String extension = path.substring(path.lastIndexOf("."));
        return extension;
    }

    public static void decode() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IOException {
        Log.d(TAG, "decode: ");
        File mydir = new File(Environment.getExternalStorageDirectory().toString()+"/decrypted");
        ArrayList<String> list  = getFileList();
        for(int i=0;i<list.size()-1;i++){
            Log.d(TAG, "decode: "+list.get(i));
            File tempFile = new File(list.get(i));
            InputStream is = new FileInputStream(tempFile);
            File output = new File(mydir,tempFile.getName());
            FileOutputStream os = new FileOutputStream(output);
            Crypto.decryptToFile(Constants.MYKEY,Constants.MYSPECKEY,is,os);

        }
    }

    public static ArrayList<String> getFileList() {

        ArrayList<String> filepaths = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory()
                + File.separator + "MediaLocker";

        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            String extension = FileUtility.getExtension(files[i].getName());
            Log.d(TAG, "FetchImages: "+extension);
            String file_path = files[i].getAbsolutePath();

            Log.d(TAG, "FetchImages: IS IMAGE");
            filepaths.add(file_path);



        }

        return filepaths;
    }

    public static String stringFromFile(File file){
        String encryptedFile = file.toString();
        return encryptedFile;
    }

    public static byte[] decryptStringToByteArray(String string) throws GeneralSecurityException {
        String s = AESCrypt.decrypt(Constants.KEY,string);
        byte[] decryptedByte = s.getBytes();
        return decryptedByte;
    }

    public static File fileFromByteArray(byte[] array,File dest) throws FileNotFoundException {

        FileOutputStream fos = new FileOutputStream(dest);
        try {
            fos.write(array);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dest;

    }



}
