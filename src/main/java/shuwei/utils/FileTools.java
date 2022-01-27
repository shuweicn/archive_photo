package shuwei.utils;


import shuwei.config.Config;
import shuwei.entity.FileType;
import shuwei.entity.Photo;

import java.io.File;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTools {

    private static final Map<Pattern, SimpleDateFormat> patterns = new HashMap<>();
    static {
        //  20191021_141147
        patterns.put(
                Pattern.compile("(19|20)\\d{2}[0-1][0-9][0-3][0-9]_[0-2][0-9][0-5][0-9][0-5][0-9]"),
                new SimpleDateFormat("yyyyMMdd_HHmmss")
        );

        // 2019:10:21 14:11:47
        patterns.put(
                Pattern.compile("(19|20)\\d{2}:[0-1][0-9]:[0-3][0-9] [0-2][0-9]:[0-5][0-9]:[0-5][0-9]"),
                new SimpleDateFormat("yyyy:MM:dd HH:mm:ss")
        );

        // 20160520102919
        patterns.put(
                Pattern.compile("(19|20)\\d{2}[0-1][0-9][0-3][0-9][0-2][0-9][0-5][0-9][0-5][0-9]"),
                new SimpleDateFormat("yyyyMMddHHmmss")
        );

        // 2015-07-25-18-29-20
        patterns.put(
                Pattern.compile("(19|20)\\d{2}-[0-1][0-9]-[0-3][0-9]-[0-2][0-9]-[0-5][0-9]-[0-5][0-9]"),
                new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
        );

        // 2014_01_02_17_14_47
        patterns.put(
                Pattern.compile("(19|20)\\d{2}_[0-1][0-9]_[0-3][0-9]_[0-2][0-9]_[0-5][0-9]_[0-5][0-9]"),
                new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        );

        // 2014-07-15-152631
        patterns.put(
                Pattern.compile("(19|20)\\d{2}-[0-1][0-9]-[0-3][0-9]-[0-2][0-9][0-5][0-9][0-5][0-9]"),
                new SimpleDateFormat("yyyy-MM-dd-HHmmss")
        );

        // 2015-02-06 16.41.25
        patterns.put(
                Pattern.compile("(19|20)\\d{2}-[0-1][0-9]-[0-3][0-9] [0-2][0-9].[0-5][0-9].[0-5][0-9]"),
                new SimpleDateFormat("yyyy-MM-dd HH.mm.ss")
        );

        // 2013-10-06_09-33-06
        patterns.put(
                Pattern.compile("(19|20)\\d{2}-[0-1][0-9]-[0-3][0-9]_[0-2][0-9]-[0-5][0-9]-[0-5][0-9]"),
                new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
        );
    }

    public static Date getDateInFilename(File file) {

        for (Pattern pattern:patterns.keySet()) {
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.find()) {
                SimpleDateFormat format = patterns.get(pattern);
                try {
                    return format.parse(matcher.group());
                } catch (ParseException e) {
                    System.out.printf("error %s", pattern);
                }
            }
        }
        return null;
    }

    public static boolean isNeedChangeFilename(File file) {
        String filename = file.getName();

        Pattern pattern = Pattern.compile("^[1-2][0-9]\\d{2}[0-1][0-9][0-3][0-9]_[0-2][0-9][0-5][0-9][0-5][0-9]");

        Matcher matcher = pattern.matcher(filename);

        boolean find = matcher.find();
        return find == false;

    }

    public static List<File> getFileList(File path) {
        ArrayList<File> files = new ArrayList<>();
        getFileList(path, files);
        Collections.sort(files);
        return files;
    }

    private static void getFileList(File path, List<File> files) {
        for(File f: path.listFiles()) {
            if(f.isDirectory()) {
                getFileList(f, files);
            }
            if(f.isFile()) {
                files.add(f);
            }
        }
    }

    public static String parseDevicePath(Photo photo) {
        /**
         * 从照片中抽取设备信息
         */
        String device;
        if (photo.getModel() != null) {
            device = photo.getModel();
        } else if (photo.getFileType().equals(FileType.AAE)) {
            device = FileType.AAE;
        } else if (photo.getProfileCreator() != null){
            device = photo.getProfileCreator();
        } else {
            device = "Other";
        }
        return device;
    }



    public static File logicPhotoDestination(Photo photo) {

        String dateFilename;


        DateFormat dateFilenameFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");


        Date useDate = null;
        if (isNeedChangeFilename(photo.getFile()) == false) {
            // 匹配到此规则， 说明已经处理过的文件了
            // 不需要修改名称， 名字中必然有时间， 直接用这个时间就好了

            useDate = getDateInFilename(photo.getFile());
            dateFilename = photo.getFileName();

        } else {
            useDate = getDateInFilename(photo.getFile());

            if (useDate == null) {
                // 需要修改名字， 并且路径里面没有 日期， 使用 exif 中的创建日期
                useDate = photo.getDateTimeOriginal();
                dateFilename = String.format("%s_%s", dateFilenameFormat.format(photo.getDateTimeOriginal()), photo.getFileName());
            } else {
                // 需要求改名字， 但是路径中含有日期， 使用路径中的日期
                Integer index = photo.getFileName().lastIndexOf(".");

                if (index == -1) {
                    dateFilename = dateFilenameFormat.format(useDate) + "_" + photo.getFileName();
                } else {
                    dateFilename = dateFilenameFormat.format(useDate) + photo.getFileName().substring(index);
                }
            }
        }
        // 按需增加日期文件夹， 和设备文件夹
        List<String> paths = new ArrayList<String>();
        if (Config.USE_DATE_PATH) {
            DateFormat datePathFormat = new SimpleDateFormat("yyyy-MM");
            paths.add(datePathFormat.format(useDate));
        }

        if (Config.USE_DEVICE_PATH) {
            paths.add(parseDevicePath(photo));
        }

        paths.add(dateFilename);


        return Paths.get(Config.DESTINATION_PATH, paths.toArray(new String[paths.size()])).toFile();
    }

    public static void movePhotoToDest(Photo photo) {
        movePhotoToDest(photo, 0);
    }

    public static void movePhotoToDest(Photo photo, Integer count) {
        File dest = logicPhotoDestination(photo);

        File count_dest;

        // 提前创建好目录
        File destParent = new File(dest.getParent());
        if (destParent.exists() == false) {
            destParent.mkdirs();
        }

        if (count == 0) {
            count_dest = dest;
        } else {

            Integer index = dest.getPath().lastIndexOf(".");
            if (index == -1) {
                count_dest = new File(String.format("%s_%s", dest.getPath(), count));
            } else {
                String prefix = dest.getPath().substring(0, index);
                String staff = dest.getPath().substring(index);
                count_dest = new File(String.format("%s_%s%s", prefix, count, staff));
            }
        }

        if (count_dest.exists()) {
            movePhotoToDest(photo, count + 1);
        } else {
            System.out.println(String.format("move '%s' to '%s'", photo.getFile().getPath(), count_dest.getPath()));

            photo.getFile().renameTo(count_dest);
        }
    }

}
