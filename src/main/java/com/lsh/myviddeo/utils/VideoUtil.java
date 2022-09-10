package com.lsh.myviddeo.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lsh.myviddeo.dtos.Video;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lushao
 * @Description $
 * @Date 21:28
 * @Version 1.0
 */
@Slf4j
public class VideoUtil {
    //    樱花动漫
    private static final String YingHua_1 = "http://www.yinghuacd.com/";
//域名更改为feijisu6.com,打不开访问防失联域名选线:feijisu.com
    private static final String FeiJiSu_2 = "http://fjisu2.com/";

    public static void yingHuaSearch(String keyword, List<Video> videos,Integer zqType) {
        String searchUrl = YingHua_1 + "search/" + keyword;
        log.info("查询链接：{}",searchUrl);
        Document page = ApiUtil.getPage(searchUrl);
        Elements select = page.select("div.lpic > ul li");

        for (Element element : select) {
            log.info("查询结果：{}",element);
            Element a = element.select("a").first();
            String descUrl = a.attr("href");
            Element imgE = a.select("img").first();
            String name = imgE.attr("alt");
            String imgSrc = imgE.attr("src");
            Video video = new Video();
            video.setZqType(zqType);
            video.setName(name);
            video.setContent(a.toString());
            video.setDescUrl(descUrl);
            video.setImgSrc(imgSrc);
            videos.add(video);
        }
    }
    public static void feiJiSuSearch(String keyword, List<Video> videos,Integer zqType) {
        String url = "http://119.91.129.193:7899/sssv.php";
        Map<String,Object> param = new HashMap<>();
        param.put("top",10);
        param.put("q",keyword);
        Map<String,String> header = new HashMap<>();
        header.put("Host","119.91.129.193:7899");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
        header.put("Origin","http://fjisu2.com");
        header.put("Referer","http://fjisu2.com/");
//        header.put("Content-Type","application/json");
        header.put("Accept","*/*");
        header.put("Accept-Language","zh-CN,zh;q=0.9");
        String request = ApiUtil.getRequest(url, param, header);
        if (JSONUtil.isJsonArray(request)) {
            JSONArray objects = JSONUtil.parseArray(request);
            for (Object object : objects) {
                JSONObject jsonObject = JSONUtil.parseObj(object);
                String lianzaijs = (String) jsonObject.get("lianzaijs");
                String title = (String) jsonObject.get("title");
                String thumb = (String) jsonObject.get("thumb");
                String descUrl = (String) jsonObject.get("url");
                Video video = new Video();
                video.setZqType(zqType);
                video.setName(title);
//                video.setContent(a.toString());
                video.setDescUrl(descUrl);
                video.setImgSrc(thumb);
                video.setLatest(lianzaijs);
                videos.add(video);
            }
        }

        /*String searchUrl = FeiJiSu_2 + "search/" + keyword;
        Document page = ApiUtil.getPage(searchUrl);
        Element ul = page.select("ul#result").first();
        Elements lis = ul.select("li");
        for (Element li : lis) {
            Element a = li.select("a").first();
            String latest = li.select("span").first().text();
            String href = a.attr("href");
            Element img = a.select("img").first();
            String name = img.attr("alt");
            String imgSrc = img.attr("src");
            Video video = new Video();
            video.setZqType(zqType);
            video.setName(name);
            video.setContent(a.toString());
            video.setDescUrl(href);
            video.setImgSrc(imgSrc);
            video.setLatest(latest);
            videos.add(video);
        }*/
    }
    @Deprecated
    public static void feiJiSuSearchbak(String keyword, List<Video> videos,Integer zqType) {
        String searchUrl = FeiJiSu_2 + "search/" + keyword;
        Document page = ApiUtil.getPage(searchUrl);
        Element ul = page.select("ul#result").first();
        Elements lis = ul.select("li");
        for (Element li : lis) {
            Element a = li.select("a").first();
            String latest = li.select("span").first().text();
            String href = a.attr("href");
            Element img = a.select("img").first();
            String name = img.attr("alt");
            String imgSrc = img.attr("src");
            Video video = new Video();
            video.setZqType(zqType);
            video.setName(name);
            video.setContent(a.toString());
            video.setDescUrl(href);
            video.setImgSrc(imgSrc);
            video.setLatest(latest);
            videos.add(video);
        }
    }

    /**
     * 获取樱花站点视频多少集
     * @author lushao
     * 2022/8/18 21:39
     * @param src
     * @return Map<String,Object>
     */
    public static void getYingHuaVideoList(String src,Map<String,Object> param) {
        Document page = ApiUtil.getPage(YingHua_1 + src);
        Element fire = page.select("div.fire").first();
        Element img = fire.select("div.thumb img").first();
        String imgSrc = img.attr("src");
        String alt = img.attr("alt");
        Element ul = fire.select("div.movurl ul").first();
        Elements lis = ul.select("li");
        List<Map<String,Object>> sums = new ArrayList<>();
        for (Element li : lis) {
            Element a = li.select("a").first();
            Map<String,Object> map = new HashMap<>();
            map.put("title",a.text());
            map.put("src",a.attr("href"));
            sums.add(map);
        }
        param.put("imgSrc",imgSrc);
        param.put("title",alt);
        param.put("sums",sums);
    }
    public static void getFeiJiSuVideoList(String src,Map<String,Object> param) {
        Document page = ApiUtil.getPage(src);
        Element top = page.select("div.top").first();
        Element left = top.select("div.top-left").first();
        Element right = top.select("div.top-right").first();
        Element img = left.select("img.lzimg").first();
        String imgSrc = img.attr("src");
        String alt = img.attr("alt");
// 资源列表
        Element zylbE = right.select("ul.playurl").first();
        List<String> zylbs = new ArrayList<>();
        Elements zylbLis = zylbE.select("li");
        for (Element zylbLi : zylbLis) {
            zylbs.add(zylbLi.text());
        }
// 获取对应的集数
        Elements jishus = right.select("div#qiyi-pl-list");
        List<List<Map<String,Object>>> allJS = new ArrayList<>();
        for (Element element : jishus) {
            List<Map<String,Object>> dgjs = new ArrayList<>();
            Elements jishuLis = element.select("ul li");
            for (Element jishuLi : jishuLis) {
                Element a = jishuLi.select("a").first();
                Map<String,Object> map = new HashMap<>();
                map.put("title",a.text());
                map.put("zq",2);
                map.put("src",a.attr("href"));
                dgjs.add(map);
            }
            allJS.add(dgjs);
        }
        param.put("imgSrc",imgSrc);
        param.put("title",alt);
        param.put("sourceList",zylbs);
        param.put("sums",allJS);
    }

    public static String yinghuaPlay(String src) {
        Document page = ApiUtil.getPage(YingHua_1 + src);
        Element playBox = page.select("div#playbox").first();
        String playUrl = playBox.attr("data-vid");
        return playUrl;
    }
    public static String feiJiSuPlay(String src) {
        Document page = ApiUtil.getPage(src);
        Element playBox = page.select("iframe#playiframe").first();
        String playUrl = playBox.attr("src");
        return playUrl;
    }

    public static String getZd(String zd) {
        String zdUrl;
        switch (zd) {
            case "1":
                zdUrl = YingHua_1;
                break;
            case "2":
                zdUrl = FeiJiSu_2;
                break;
            default:
                zdUrl = "";
                break;
        }
        return zdUrl;
    }
}
