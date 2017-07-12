package Utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.String;
/**
 * Created by yuhan on 2017/7/11.
 */

public class FileUtil {
    final static String TAG = "Fileutil:";
    public void jsonToFile(String pathname, String jsonString) {
        //判断文件是否存在
        File file = new File(pathname);
        if (file.exists()) {
            Log.d(TAG,"FILE EXISTS");
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "FILE CREATED SUCCESS");
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(jsonString.getBytes());
            fileOutputStream.close();
            Log.d(TAG, "JSON STORED LOCAL SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String fileToJson(String pathname) {
        String path = Environment.getExternalStorageDirectory().toString() + "/"
                + "bzbp" + "/" + "data" + "/"  + "trace" + "/" + pathname;

        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(
                    fileInputStream, "utf-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    public String createFolder(String foldername) {
        String path = Environment.getExternalStorageDirectory().toString() + "/"
                        + "bzbp" + "/" + "data" + "/"  + "trace" + "/" + foldername;
        File dirFirstFolder = new File(path);
        if(!dirFirstFolder.exists()) {
            dirFirstFolder.mkdirs();//创建文件夹
        }
        return path + "/";
    }
}
