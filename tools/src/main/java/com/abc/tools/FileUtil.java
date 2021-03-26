package com.abc.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by denghaofa
 * on 2021/2/8 3:28 PM
 */
class FileUtil {
    public static String getStringContent(File file) {
        byte buff[] = new byte[4096];
        ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
        try {
            InputStream inputStream = new FileInputStream(file);
            do {
                int numread = inputStream.read(buff);
                if (numread <= 0) {
                    break;
                }
                fromFile.write(buff, 0, numread);
            } while (true);
            return fromFile.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
