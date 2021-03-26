package com.abc.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by denghaofa
 * on 2021/2/8 2:46 PM
 */
class RemoveFlutterPic {
    private String projectPath;
    private String suffix = ".png";
    private final List<String> filterList = new ArrayList<>();

    public RemoveFlutterPic(String projectPath) {
        this.projectPath = projectPath;
    }

    public void addFilter(String filter) {
        filterList.add(filter);
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void start() {
        //图片资源目录
        String assetsRootPath = projectPath + File.separator + "assets";
        File assetsRootDir = new File(assetsRootPath);
        List<String> picAbsolutePathList = new ArrayList<>();
        getAllFilePath(picAbsolutePathList, assetsRootDir);

        //相对路径
        String parentPath = projectPath + File.separator;
        List<String> picPathList = new ArrayList<>();
        for (String absolutePath : picAbsolutePathList) {
            String path = absolutePath.substring(parentPath.length());
            //过滤不能删除的目录或者文件名
            if (inFilter(path)) {
                continue;
            }
            picPathList.add(path);
        }

        //.dart文件内容
        String dartRootPath = projectPath + File.separator + "lib";
        File dartRootDir = new File(dartRootPath);
        List<String> dartContentList = new ArrayList<>();
        getAllFileContent(dartContentList, dartRootDir);

        //使用过的图片资源
        Map<String, String> usedPicPathMap = new HashMap<>();
        for (String content : dartContentList) {
            for (String picPath : picPathList) {
                //文件内容包含图片路径
                if (content.contains(picPath)) {
                    if (usedPicPathMap.containsKey(picPath)) {
                        continue;
                    }
                    usedPicPathMap.put(picPath, picPath);
                }
            }
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("unUsedPicPathList");
        System.out.println();
        //用不上的图片资源
        for (String path : picPathList) {
            if (usedPicPathMap.containsKey(path)) {
                continue;
            }
            System.out.println(parentPath + path);
            File file = new File(parentPath + path);
            file.delete();
        }
    }

    private boolean inFilter(String path) {
        for (String filter : filterList) {
            if (path.contains(filter)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件夹里所有图片文件的路径
     *
     * @param pathList
     * @param file
     */
    private void getAllFilePath(List<String> pathList, File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File temp : files) {
                getAllFilePath(pathList, temp);
            }
        } else {
            String path = file.getAbsolutePath();
            if (path.endsWith(suffix)) {
                pathList.add(path);
            }
        }
    }

    /**
     * 获取文件夹里所有.dart文件的内容
     *
     * @param contentList
     * @param file
     */
    private void getAllFileContent(List<String> contentList, File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File temp : files) {
                getAllFileContent(contentList, temp);
            }
        } else {
            String path = file.getAbsolutePath();
            if (path.endsWith(".dart")) {
                String content = FileUtil.getStringContent(file);
                contentList.add(content);
            }
        }
    }
}
