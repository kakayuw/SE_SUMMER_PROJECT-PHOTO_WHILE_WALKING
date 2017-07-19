package com.photowalking.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
    private static int BUFFSIZE = 1024;

    /**
     * @param path file for compression
     */
    public static void compress(String path) {
        File folder = new File(path);
        if (folder != null) {
            compressFolder(folder);
        } else {
            Log.w(TAG, "File was not found - " + path);
        }
    }

    /**
     * @param file for compression
     */
    public static void compress(File file) {

        if (file != null) {

            if (file.isDirectory()) {
                compressFolder(file);
            } else {
                compressFile(file);
            }

        } else {
            Log.w(TAG, "No file object - it's NULL");
        }
    }


    /**
     * @param filelist for compression
     */
    public static void compress(String id, List<String> filelist){
        File parent = new File(UrlPath.uploadTmpPath);
        if(!parent.exists())
            parent.mkdir();
        File folder = new File(UrlPath.uploadTmpPath+"/"+id);
        try {
            OutputStream out = new FileOutputStream(UrlPath.uploadTmpPath+"/"+id+".zip");
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));
            int size = filelist.size();
            for(int i = 0; i < size; ++i){
                File file = new File(filelist.get(i));
                Log.e(TAG, "add file : "+filelist.get(i));
                //TODO COMPRESS JPG FILE
                InputStream input = new FileInputStream(file);
                zos.putNextEntry(new ZipEntry(file.getName()));
                int len = 0;
                byte[] bytes = new byte[BUFFSIZE];
                while((len = input.read(bytes)) != -1){
                    zos.write(bytes, 0, len);
                }
                zos.closeEntry();
                input.close();
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
            Log.w(TAG, "File was not found - " + path);
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
        InputStream is;
        ZipInputStream zis;
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
            int count;

            while ((ze = zis.getNextEntry()) != null) {

                filename = ze.getName();

                //If our entry is directory we should create it
                if (ze.isDirectory()) {
                    File fmd = new File(path +"/"+ filename);
                    fmd.mkdirs();
                    continue;
                }

                //Here we set destination for extracted files
                FileOutputStream fout = new FileOutputStream(path +"/"+ filename);

                while ((count = zis.read(buffer)) != -1) {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static void compressFolder(File folder) {
        try {
            //Destination zipped folder
            OutputStream out = new FileOutputStream(folder.getPath() + ".zip");
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));

//            innerCompress(zos, folder);
//            File[] files = folder.listFiles();
//            for(int i = 0; i < files.length; ++i){
//                InputStream input = new FileInputStream(files[i]);
//                zos.putNextEntry(new ZipEntry(files[i].getName()));
//                int size = 0;
//                byte[] bytes = new byte[1024];
//                while((size = input.read(bytes)) != -1){
//                    zos.write(bytes, 0, size);
//                }
//                zos.closeEntry();
//                input.close();
//            }

            zos.flush();
            zos.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void compressFile(File file) {
        try {

            int size;

            OutputStream out = new FileOutputStream(file.getParent() + "/" + file.getName() + ".zip");
            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));

            BufferedInputStream origin;
            InputStream is;

            is = new FileInputStream(file.getPath());
            origin = new BufferedInputStream(is, 2*BUFFSIZE);

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


    private static void innerCompress(ZipOutputStream zos, File folder) {

        //Get folders and files of source folder
        List<File> folders = new ArrayList<File>();
        List<File> files = new ArrayList<File>();

        Log.e(TAG, "folder: "+folder.getAbsolutePath());

        File[] allFiles = folder.listFiles();
        for (int i = 0; i < allFiles.length; i++) {
            if (allFiles[i].isDirectory()) {
                folders.add(allFiles[i]);
            } else {
                files.add(allFiles[i]);
            }

        }

        try {

            int folderFilesCount = folder.listFiles().length;
            int count = folderFilesCount;

            int size = -1;

            InputStream is;

            //Now go through all files and folders
            for (int i = 0; i < count; ++i) {

                String filePath;
                String name;

                File currentFolder = null;

                //At first create entry for all folders
                int foldersCount = 0;
                if (folders.size() > i) {

                    currentFolder = folders.get(i);

                    filePath = currentFolder.getPath();
                    name = currentFolder.getName();
                } else {

                    if (folderFilesCount > 0)
                        foldersCount = folders.size();

                    filePath = files.get(i - foldersCount).getPath();
                    name = files.get(i - foldersCount).getName();

                }

                is = new FileInputStream(filePath);

                ZipEntry entry = new ZipEntry(name);
                zos.putNextEntry(entry);

                byte[] bytes = new byte[2048];
                while ((size = is.read(bytes)) != -1) {
                    zos.write(bytes, 0, size);
                }

                //Get files and folders of this inner folder
                File[] allCurrentFiles = folder.listFiles();

                //If this folder has folders or files then go inside and build structure for it
                if (allCurrentFiles.length > 0) {
                    ZipUtil.innerCompress(zos, currentFolder);
                }
                zos.closeEntry();

                is.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
