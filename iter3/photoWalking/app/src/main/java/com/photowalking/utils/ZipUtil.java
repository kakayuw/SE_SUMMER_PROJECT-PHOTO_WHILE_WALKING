package com.photowalking.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static android.R.attr.path;

/**
 * Created by lioenl on 2017/7/17.
 */

public class ZipUtil {
    private static final String TAG = ZipUtil.class.getSimpleName();
    private static int BUFFSIZE = 2048;

    /**
     * @param filelist for compression
     */
    public static void compress(String id, List<String> filelist){
        File pparent = new File(UrlPath.APP_PATH+"/tmp");
        if(!pparent.exists())
            pparent.mkdir();
        File parent = new File(UrlPath.uploadTmpPath);
        if(!parent.exists())
            parent.mkdir();
        File zipfile = new File(UrlPath.uploadTmpPath+"/"+id+".zip");
        try {
            OutputStream out = new FileOutputStream(zipfile);
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));
            int size = filelist.size();
            for(int i = 0; i < size; ++i){
                Log.e(TAG, "add file : "+filelist.get(i));
                File file = new File(filelist.get(i));
                zos.putNextEntry(new ZipEntry(file.getName()));
                //COMPRESS JPG FILE
                if(file.getName().contains(".jpg")){
                    Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromFd(filelist.get(i),70,70);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,zos);
//                    Bitmap bitmap = BitmapFactory.decodeFile(filelist.get(i));
//                    bitmap.compress(Bitmap.CompressFormat.JPEG,70,zos);//压缩30%
                }else {
                    InputStream input = new FileInputStream(file);
                    int len = 0;
                    byte[] bytes = new byte[BUFFSIZE];
                    while((len = input.read(bytes)) != -1){
                        zos.write(bytes, 0, len);
                    }
                    input.close();
                }
                zos.closeEntry();
            }
            zos.flush();
            zos.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param filepath archive path
     */
    public static void decompress(String filepath) {
        File file = new File(filepath);
        if (file != null) {
            decompress(file);
        } else {
            Log.w(TAG, "File was not found: " + filepath);
        }
        file.delete();
    }


    /**
     * It extract files at the same parent directory where source archive is
     *
     * @param file archive
     * @return result of extracting
     */
    public static boolean decompress(File file){
        File pparent = new File(UrlPath.APP_PATH+"/tmp");
        if(!pparent.exists())
            pparent.mkdir();
        File parent = new File(UrlPath.downloadPath);
        if(!parent.exists())
            parent.mkdir();
        InputStream is;
        ZipInputStream zis;
        BufferedOutputStream bos;
        String path = file.getAbsolutePath().split("\\.")[0];
        Log.e(TAG, "create folder: "+path);
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();

        try {
            String filename;

            is = new FileInputStream(file);
            zis = new ZipInputStream(new BufferedInputStream(is));

            ZipEntry ze;
            byte[] buffer = new byte[BUFFSIZE];
            int len;

            while ((ze = zis.getNextEntry()) != null) {

                filename = ze.getName();
//                //If our entry is directory we should create it
//                if (ze.isDirectory()) {
//                    File fmd = new File(path +"/"+ filename);
//                    fmd.mkdirs();
//                    continue;
//                }

                //Here we set destination for extracted files
                FileOutputStream fout = new FileOutputStream(path +"/"+ filename);
                bos = new BufferedOutputStream(fout);

                while ((len = zis.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bos.flush();
                bos.close();
                fout.close();
            }
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private static void compressFile(File file) {
        try {

            int size;

            OutputStream out = new FileOutputStream(file.getParent() + "/" + file.getName() + ".zip");
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));

            BufferedInputStream origin;
            InputStream is;

            is = new FileInputStream(file.getPath());
            origin = new BufferedInputStream(is, BUFFSIZE);

            //Get format of each file
            String format = "";
            int formatIndex = file.getAbsolutePath().lastIndexOf(".");
            if (formatIndex != -1 && !file.isDirectory()) {
                format = file.getAbsolutePath().substring(formatIndex, file.getAbsolutePath().length());
            }

            //Create file entry
            ZipEntry entry = new ZipEntry(file.getName() + "." + format);
            zos.putNextEntry(entry);

            byte[] bytes = new byte[BUFFSIZE];
            while ((size = origin.read(bytes)) != -1) {
                zos.write(bytes, 0, size);
            }
            zos.closeEntry();

            is.close();

            zos.flush();
            zos.close();

            out.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }



}
