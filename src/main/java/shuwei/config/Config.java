package shuwei.config;
import java.util.*;

public class Config {
    public static final Set<String> IGNORE_FILES = new HashSet<String>(Arrays.asList(".DS_Store"));
    public static final boolean USE_DEVICE_PATH = true;
    public static final boolean USE_DATE_PATH = true;

//    public static final String EXIFTOOLS = "/usr/local/bin/exiftool";
//    public static final String EXISTS_CHECK_FILE = "/Users/momo/Downloads/sha256.chk";
//    public static final String PHOTO_PATH = "/Users/momo/Downloads/02的副本";
//    public static final String DESTINATION_PATH = "/Users/momo/Downloads/dest";

    public static final String EXISTS_CHECK_FILE = "/data/smb/sha256.chk";
    public static String EXIFTOOLS = "/usr/local/Image-ExifTool-12.03/exiftool";
    public static String PHOTO_PATH = "/data/smb/chunyan-single-photo-202201";
    public static String DESTINATION_PATH = "/data/smb/chunyan-single-photo-202201-dest";

}
