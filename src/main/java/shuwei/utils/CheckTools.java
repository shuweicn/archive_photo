package shuwei.utils;

import shuwei.config.Config;
import shuwei.entity.Photo;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class CheckTools {
    private static Map<String, String> exists = new HashMap<>();
    private static Map<String, String> existsRuntime = new HashMap<>();
    private static Map<String, String> existDest = new HashMap<>();

    public CheckTools() {
        File checkFile = new File(Config.EXISTS_CHECK_FILE);
        try {
            BufferedReader br = new BufferedReader(new FileReader(checkFile));
            String line;

            while ((line = br.readLine()) != null) {
                String[] lines = line.split("  ");
                exists.put(lines[0], lines[1]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean isExists(Photo photo) {
        if (exists.containsKey(photo.getSha256())) {
            System.out.println(String.format("exist_checksum %s == %s", photo.getFileName(),
                    exists.get(photo.getSha256())));
            return true;
        } else {
            return false;
        }
    }

    private Boolean isExistsRuntime(Photo photo) {
        /**
        本次启动移动过的文件， 收集到这里
         */
        if (existsRuntime.containsKey(photo.getSha256())) {
            System.out.println(String.format("exist_runtime %s == %s",
                    photo.getFile(),
                    existsRuntime.get(photo.getSha256())));
            return true;
        } else {
            existsRuntime.put(photo.getSha256(), photo.getFile().getPath());
            return false;
        }
    }

    public Boolean needMove(Photo photo) {
        if (Config.IGNORE_FILES.contains(photo.getFile().getName())) {
            System.out.println(String.format("ignore %s",
                    photo.getFile(),
                    existsRuntime.get(photo.getSha256())));
            return false;
        }
        // 检查文件中存在不需要移动
        if (isExists(photo)) {
            return false;
        }
        // 本次启动移动过， 不需要移动
        if (isExistsRuntime(photo)) {
            return false;
        }
        return true;
    }


}
