package Utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
        File file = new File(path);
        if (!file.exists()) {
            Log.d(TAG,"FILE NOT EXIST");
            return null;
        }
        String jsonString = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            int read;
            byte b[]=new byte[1024];
            //读取文件，存入字节数组b，返回读取到的字符数，存入read,默认每次将b数组装满
            read=fileInputStream.read(b);
            while(read != -1) {
                read = fileInputStream.read(b);
                jsonString += new String(b);
            }
            fileInputStream.close();
            Log.d(TAG, "JSON STORED LOCAL SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
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
