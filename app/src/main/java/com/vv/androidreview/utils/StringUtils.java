package com.vv.androidreview.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Vv
 * 2016/9/1 0001.
 * Version 1.0
 * Description：
 */
public class StringUtils {
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
