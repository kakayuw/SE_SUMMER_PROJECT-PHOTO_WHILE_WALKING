package com.photowalking.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 10911 on 2017/8/15.
 */

public class UserInfoSharedPreference {

    private final static String FILE_NAME = "userinfo.common";

    private final static String UID = "sp.user.info.uid";
    private final static String UNAME = "sp.user.info.uname";

    private volatile static SharedPreferences SP;

    private static SharedPreferences with(final Context context) {
        if (SP == null) {
            synchronized (UserInfoSharedPreference.class) {
                if (SP == null) {
                    SP = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                }
            }
        }

        return SP;
    }

    public static boolean save(final Context context, final String uid, final String uname) {
        return with(context).edit()
                .putString(UID,uid)
                .putString(UNAME,uname)
                .commit();
    }

    public static String getUid(final Context context) {
        return with(context).getString(UID, null);
    }

    public static String getUname(final Context context) {
        return with(context).getString(UNAME, null);
    }

    public static boolean delete(final Context context) {
        return with(context).edit()
                .remove(UID)
                .remove(UNAME)
                .commit();
    }
}
