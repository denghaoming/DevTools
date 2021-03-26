package com.abc.tools;

public class DevTools {
    public static void main(String[] args) {
        RemoveFlutterPic removeFlutterPic = new RemoveFlutterPic("/Users/goat/AndroidStudioProjects/BlockAbcProjects/TopRich");
        removeFlutterPic.addFilter("assets/i18n/");
        removeFlutterPic.addFilter("assets/rank/ic_ranking_");
        removeFlutterPic.addFilter("assets/cars/");
        removeFlutterPic.addFilter("assets/login/");
//        removeFlutterPic.setSuffix(".gif");
        removeFlutterPic.start();
    }
}