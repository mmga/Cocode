package com.mmga.cocode.data.data.provider;


public class Cookie {
    private static String cookie;
    private static String cookieT="";
    private static String forumSession="";

    private Cookie() {}

    public static String getCookie() {
        cookie = "_t=" + cookieT + "; " + "_forum_session=" + forumSession;
        return cookie;
    }

    public static void setCookieT(String t) {
        Cookie.cookieT = t;
    }

    public static void setForumSession(String forumSession) {
        Cookie.forumSession = forumSession;
    }


}
