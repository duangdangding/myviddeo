package com.lsh.myviddeo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.lsh.myviddeo.utils.ApiUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MyviddeoApplicationTests {

    private static final String preUrl = "http://www.yinghuacd.com/";
    private static final String seachUrl = preUrl + "search/";
    private static final String videoUrl1 = "";

    @Test
    void contextLoads() {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);//不启用ActiveX
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.getOptions().setDownloadImages(false);//不下载图片
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX

        HtmlPage page = null;
        try {
            page = webClient.getPage(seachUrl + URLEncoder.encode("斗罗大陆"));//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }

        webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束

        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串
        Document doc = Jsoup.parse(pageXml);
//        System.out.println(parse);
        Elements select = doc.select("div.lpic > ul li");
        for (Element element : select) {
            Element a = element.select("a").first();
            System.out.println(a);
        }
    }

    @Test
    void toVIdeoDesc() {
        String url = preUrl + "/show/4066.html";
        Document page = ApiUtil.getPage(url);
//        System.out.println(page);
        Element ul = page.select("div.movurl ul").first();
        Elements lis = ul.select("li");
        for (Element li : lis) {
            Element a = li.select("a").first();

        }
    }
    @Test
    void playVideo() {
        String url = preUrl + "v/4066-221.html";
        Document page = ApiUtil.getPage(url);
        System.out.println(page);
    }

    @Test
    void tttt() {
//        ?top=10&q=%E6%96%97%E7%BD%97%E5%A4%A7%E9%99%86
        String url = "http://119.91.129.193:7899/sssv.php?top=10&q=%E6%96%97%E7%BD%97%E5%A4%A7%E9%99%86";
//        Map<String,String> param = new HashMap<>();
//        param.put("top","10");
//        param.put("q",URLEncoder.encode("斗罗大陆"));
        Map<String,String> header = new HashMap<>();
        header.put("Host","119.91.129.193:7899");
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
        header.put("Origin","http://fjisu2.com");
        header.put("Referer","http://fjisu2.com/");
        header.put("Accept","*/*");
        header.put("Accept-Encoding","gzip, deflate");
        header.put("Connection","keep-alive");
        header.put("Accept-Language","zh-CN,zh;q=0.9");
        String s = ApiUtil.myHttpGet(url, header, null);
        System.out.println(s);
    }
}
