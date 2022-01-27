package shuwei;


import shuwei.config.Config;
import shuwei.entity.Photo;
import shuwei.utils.ExifTools;
import shuwei.utils.FileTools;
import shuwei.utils.CheckTools;
import java.io.File;


public class Main {

    public static void main( String[] args ) {

//        CheckTools checker = new CheckTools();
//
//        Integer c = 0;
//        for (File file: FileTools.getFileList(new File(Config.PHOTO_PATH))) {
//            Photo photo = ExifTools.getPhotoByFile(file);
//
//            System.out.println(photo.toInfo());
//            if (checker.needMove(photo)) {
//                FileTools.movePhotoToDest(photo);
//            }
//            c += 1;
//        }
//        System.out.println(c);
        String f = "1234567890.xxx";
        Integer index = f.lastIndexOf(".");
        System.out.println(index);
        System.out.println(f.substring(index));

        String f2 = "1234567890xxx";
        Integer index2 = f2.lastIndexOf(".");
        System.out.println(index2);
    }

}



