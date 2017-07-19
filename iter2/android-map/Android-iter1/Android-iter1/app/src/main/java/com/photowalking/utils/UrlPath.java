package com.photowalking.utils;

import android.os.Environment;

/**
 * Created by lionel on 2017/7/7.
 */

public class UrlPath {
    public static String ip = "http://192.168.1.165:8080/bzbp/rest";

    static final String STORAGE_PATH = Environment.getExternalStorageDirectory().toString();
    public static String APP_PATH = STORAGE_PATH + "/bzbp/data/trace/";
    public static String ZIP_PATH = STORAGE_PATH + "/bzbp/temp/";

    public static String uploadTmpPath = ZIP_PATH+"utmp";
    public static String downloadPath = ZIP_PATH+"dtmp";
    public static String dataPath = APP_PATH+"/data";

    /* user urls */
    public static String loginUrl = ip + "/user/login";
    public static String signupUrl = ip + "/user/signup";
    public static String getPicUrl = ip + "/user/getPicture/";
    public static String getUserByNameUrl = ip + "/user/getUserByUsername/";
    public static String getUserByUidUrl = ip + "/user/getUserByUid/";

    /* friend urls */
    public static String getFriUrl = ip + "/friend/getAll/1";
    public static String addFriUrl = ip + "/friend/addFriend/";
    public static String deleteFriUrl = ip + "/friend/deleteFriend/";

    /* share urls */
    public static String getShareFriUrl = ip+"/share/friendGetAll/1";
    public static String getShareAllUrl = ip+"/share/getAll";
    public static String getShareMineUrl = ip+"/share/myGetAll/1";
    public static String uploadUrl = ip+"/share/addPicFile/123456";
    public static String downloadUrl = ip+"/share/getPicFile/123456";

    /* com.photowalking.profile urls */
    public static String updateUserUrl = ip + "/user/updateUser";
    public static String chgUserPwd = ip + "/user/changePassword";

    public static String shareItemUrl = ip + "/share/addShared";
}
