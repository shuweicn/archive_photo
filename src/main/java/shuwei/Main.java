package shuwei;
import shuwei.config.Config;
import shuwei.entity.Photo;
import shuwei.utils.CheckTools;
import shuwei.utils.ExifTools;
import shuwei.utils.FileTools;

import java.io.File;
import java.text.ParseException;



public class Main {

    public static void main( String[] args ) throws ParseException {

        CheckTools checker = new CheckTools();

        Integer c = 0;
        for (File file: FileTools.getFileList(new File(Config.PHOTO_PATH))) {
            System.out.printf("start %s\n", file.getPath());
            Photo photo = ExifTools.getPhotoByFile(file);
            // System.out.println(photo.toInfo());
            if (checker.needMove(photo)) {
                FileTools.movePhotoToDest(photo);
            }
            System.out.println("---");
            c += 1;
        }
        System.out.println(c);

    }

}



