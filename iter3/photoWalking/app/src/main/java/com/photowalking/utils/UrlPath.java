package com.photowalking.utils;

import android.os.Environment;

/**
 * Created by lionel on 2017/7/7.
 */

public class UrlPath {
    public static String ip = "http://115.159.74.217:8080/bzbp/rest";
    //public static String ip = "http://192.168.1.165:8080/bzbp/rest";

    static final String STORAGE_PATH = Environment.getExternalStorageDirectory().toString();
    public static String APP_PATH = STORAGE_PATH+"/bzbp";
    public static String uploadTmpPath = APP_PATH+"/tmp/utmp";
    public static String downloadPath = APP_PATH+"/tmp/dtmp";
    public static String dataPath = APP_PATH+"/data";
    public static String tracePath = dataPath+"/trace/";
    public static String avatarPath = APP_PATH+"/avatar";

    /* user urls */
    public static String loginUrl = ip+"/user/login";
    public static String signupUrl = ip+"/user/signup";
    public static String getPicUrl = ip+"/user/getPicture/";
    public static String getUserByNameUrl = ip+"/user/getUserByUsername/";
    public static String getUserByUidUrl = ip+"/user/getUserByUid/";

    /* friend urls */
    public static String getFriUrl = ip+"/friend/getAll/";
    public static String applyFriUrl = ip+"/friend/applyFriend";
    public static String addFriUrl = ip+"/friend/addFriend/";
    public static String deleteFriUrl = ip+"/friend/deleteFriend/";
    public static String searchUserUrl = ip+"/friend/search/";
    public static String checkFriUrl = ip +"/friend/check/";
    public static String chgRemarkUrl = ip +"/friend/changeRemark";

    /* share urls */
    public static String getShareFriUrl = ip+"/share/friendGetAll/";
    public static String getShareAllUrl = ip+"/share/getAll/";
    public static String getShareMineUrl = ip+"/share/myGetAll/";
    public static String uploadUrl = ip+"/share/addTrace";
    public static String uploadSitemUrl = ip+"/share/addShare/";
    public static String downloadUrl = ip+"/share/getPicFile/";
    public static String getPhotoUrl = ip+"/share/getPhoto/";
    public static String upVoteUrl = ip+"/share/upvote/";
    public static String cancelUpVoteUrl = ip+"/share/cancelUpvote/";
    public static String checkUpVoteUrl = ip+"/share/searchUpvote/";
    public static String allCommentUrl = ip+"/share/allComment/";
    public static String addCommentUrl = ip+"/share/addComment";
    public static String delCommentUrl = ip+"/share/delComment/";
    public static String addBrowseUrl = ip+"/share/browse/";

    public static String uploadPicUrl = ip+"/share/savePhoto/";
    public static String displayPicUrl = ip+"/share/getPhoto/";

    public static String wechatShareUrl = "http://115.159.74.217:8080/bzbp/bzbp/share.jsp?sid=";

    /* profile urls */
    public static String updateUserUrl = ip + "/user/updateUser";
    public static String chgUserPwd = ip + "/user/changePassword";
    public static String chgPhone = ip+"/user/changePhone";
    public static String chgUserPicUrl = ip+"/user/saveUserPicture/";

    public static String tmpPic = avatarPath+"/tmp.jpg";


}
