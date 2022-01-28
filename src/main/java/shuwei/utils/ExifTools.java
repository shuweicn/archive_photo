package shuwei.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import shuwei.config.Config;
import shuwei.entity.Photo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ExifTools {
    public static String getFileSHA256(File file) {
        try {
            return DigestUtils.sha256Hex(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Photo getPhotoByFile(File file)  {

        String exif_json = getJsonExifInfo(file);

        ObjectMapper objectMapper = new ObjectMapper();
        // because exif_json is list so use array2
        try {
            Photo[] photos =  objectMapper.readValue(exif_json, Photo[].class);
            Photo photo = photos[0];
            String sha256 = getFileSHA256(file);
            photo.setSha256(sha256);
            return photo;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String getJsonExifInfo(File file) {
        String[] cmd = {Config.EXIFTOOLS,"-j", file.getPath()};
        String output = "";

        try {
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output = output + line;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;

    }


}
