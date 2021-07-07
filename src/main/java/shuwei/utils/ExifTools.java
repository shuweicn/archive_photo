package shuwei.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import shuwei.config.Config;
import shuwei.entity.Photo;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;


public class ExifTools {


    public static Photo getPhotoByFile(File file)  {

        String json = getJsonExifInfo(file);
        Photo[] photos;

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            photos =  objectMapper.readValue(json, Photo[].class);
            return photos[0];

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
