package shuwei;


import shuwei.config.Config;
import shuwei.entity.Photo;
import shuwei.utils.ExifTools;
import shuwei.utils.FileTools;
import shuwei.utils.CheckTools;
import java.io.File;


public class Main {

    public static void main( String[] args ) {

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



