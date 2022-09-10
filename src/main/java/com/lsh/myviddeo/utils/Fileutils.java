package com.lsh.myviddeo.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Fileutils {

    /**
     * @author Jayvee
     * @Description: todo 获取网络文件的大小
     */
    public static int getFileLength(String url1) throws IOException {
        int length = 0;
        URL url;
        try {
            url = new URL(url1);
            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
            //根据响应获取文件大小
            length = urlcon.getContentLength();
            urlcon.disconnect();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return length;
    }
}
