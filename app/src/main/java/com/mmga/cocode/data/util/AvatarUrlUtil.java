package com.mmga.cocode.data.util;


public class AvatarUrlUtil {
    public static final int AVATAR_SIZE_SMALL = 50;
    public static final int AVATAR_SIZE_MEDIUM = 100;
    public static final int AVATAR_SIZE_LARGE = 200;
    public static final int AVATAR_SIZE_XLARGE = 500;


    //自定头像返回格式--> /user_avatar/cocode.cc/{size}/45/1441_1.png
    //系统默认头像返回格式--> https://avatars.discourse.org/letter/p/3ab097/{size}.png


    public static String getAvatarUrl(String string,int size) {
        String result;

        if (string.startsWith("/")) {
            string = "http://cocode.cc" + string;
        }

        result = string.replace("{size}", "" + size);
        return result;
    }
}
