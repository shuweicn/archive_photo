package shuwei;


import org.apache.commons.codec.digest.DigestUtils;
import shuwei.config.Config;
import shuwei.entity.Photo;
import shuwei.utils.ExifTools;
import shuwei.utils.FileTools;
import shuwei.utils.CheckTools;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class Main {

    public static void main( String[] args ) throws ParseException {

        CheckTools checker = new CheckTools();

        Integer c = 0;
        for (File file: FileTools.getFileList(new File(Config.PHOTO_PATH))) {
            Photo photo = ExifTools.getPhotoByFile(file);

            System.out.println(photo.toInfo());
            if (checker.needMove(photo)) {
                FileTools.movePhotoToDest(photo);
            }
            c += 1;
        }
        System.out.println(c);
    }

}



