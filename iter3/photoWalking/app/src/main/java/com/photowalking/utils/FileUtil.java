package com.photowalking.utils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.String;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by yuhan on 2017/7/11.
 */

public class FileUtil {
    final static String TAG = "Fileutil:";
    private static final int BUFF_SIZE = 1024 * 1024; // 1M

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

    public String AfileToJson(String tracePath) {

        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(tracePath);
            InputStreamReader inputStreamReader = new InputStreamReader(
                    fileInputStream, "utf-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString;
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
        String path = UrlPath.tracePath + foldername;
        File dirFirstFolder = new File(path);
        if(!dirFirstFolder.exists()) {
            dirFirstFolder.mkdirs();//创建文件夹
        }
        return path + "/";
    }

    public static byte[] fileToByte(String path){
        File file = new File(path);
        byte[] ret = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //按ImageView大小压缩
            Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromFd(path,70,70);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            //减小质量压缩
//                    Bitmap bitmap = BitmapFactory.decodeFile(filelist.get(i));
//                    bitmap.compress(Bitmap.CompressFormat.JPEG,70,zos);//压缩30%
            //不压缩
//            FileInputStream fiStream = new FileInputStream(file);
//            byte[] buffer = new byte[1024];
//            int n;
//            while ((n = fiStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, n);
//            }
//            fiStream.close();
            ret = outputStream.toByteArray();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void deleteImage(Context context, String path){
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = context.getContentResolver();
            String where = MediaStore.Images.Media.DATA + "='" + path + "'";
            Log.e("delete File>>>",where);
            // 删除操作
            mContentResolver.delete(uri, where, null);
            //发送广播
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            context.sendBroadcast(intent);
        }
    }


    /**
     * 根据图片的绝对路径获取Uri
     */
    public static Uri getUri(Context context, String path){
        File file = new File(path);
        if(Build.VERSION.SDK_INT<24){
            return Uri.fromFile(file);
        } else {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
    }

    /**
     * 根据Uri获取图片的绝对路径
     *
     * @param context 上下文对象
     * @param uri     图片的Uri
     * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
     */
    public static String getRealPathFromUri(Context context, Uri uri) {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion >= 19) {
            return getRealPathFromUriAboveApi19(context, uri);
        } else {
            return getRealPathFromUriBelowAPI19(context, uri);
        }
    }

    private static String getRealPathFromUriBelowAPI19(Context context, Uri uri) {
        return getDataColumn(context, uri, null, null);
    }

    @TargetApi(19)
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                // 使用':'分割
                String id = documentId.split(":")[1];

                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            // 如果是 content 类型的 Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // 如果是 file 类型的 Uri,直接获取图片对应的路径
            filePath = uri.getPath();
        }
        return filePath;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
}
