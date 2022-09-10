package com.lsh.myviddeo.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ApiUtil {

    private static final String ContentType = "Content-type";

    /**
     * 处理post请求
     * @param httpUrl 请求url
     * @param param 参数 name=v1&name2=v2 格式
     * @return
     */
    public static String postRequest(String httpUrl, String param,Map<String,String> header) {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            if (MapUtil.isNotEmpty(header)) {
                for (String key : header.keySet()) {
                    connection.setRequestProperty(key,header.get(key));
                }
            }
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(30000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            if (!header.containsKey("")) {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            // connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            if (StrUtil.isNotBlank(param)) {
                
                os.write(param.getBytes());
            }
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                StringBuilder sbf = new StringBuilder();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    String decode = URLDecoder.decode(temp, "utf-8");
                    System.out.println(decode);
                    sbf.append(temp);
                    // sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return result;
    }
    public static String postJsonRequest(String httpUrl, String jsonStr,Map<String,String> header,String charset) {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            if (MapUtil.isNotEmpty(header)) {
                for (String key : header.keySet()) {
                    connection.setRequestProperty(key,header.get(key));
                }
            }
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(30000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            if (!header.containsKey("")) {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            // connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            if (StrUtil.isNotBlank(jsonStr)) {
                if (StrUtil.isNotBlank(charset)) {
                    os.write(jsonStr.getBytes(charset));
                } else {
                    os.write(jsonStr.getBytes());
                }
            }
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                StringBuilder sbf = new StringBuilder();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    String decode = URLDecoder.decode(temp, "utf-8");
                    System.out.println(decode);
                    sbf.append(temp);
                    // sbf.append("\r\n");
                }
                result = sbf.toString();
            } else {
                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

                StringBuilder sbf = new StringBuilder();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    String decode = URLDecoder.decode(temp, "utf-8");
                    System.out.println(decode);
                    sbf.append(temp);
                    // sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return result;
    }
    public static HttpURLConnection postResponse(String httpUrl, String param,Map<String,String> header) {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            if (MapUtil.isNotEmpty(header)) {
                for (String key : header.keySet()) {
                    connection.setRequestProperty(key,header.get(key));
                }
            }
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(30000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);
            connection.setRequestProperty("Connection", "Keep-Alive");
            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            // connection.setDoInput(true);
            
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            // connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            // os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(param);
            writer.close();
            //连接
            connection.connect();
            return connection;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return null;
    }

    /**
     * 调用api接口并返回数据 get请求
     * @param requestUrl
     * @param params
     * @return
     */
    public static String getRequest(String requestUrl, Map params,Map<String,String> header) {
        //buffer用于接受返回的字符
        StringBuilder buffer = new StringBuilder();
        try {
            //建立URL，把请求地址给补全，其中urlencode（）方法用于把params里的参数给取出来
            if (MapUtil.isNotEmpty(params)) {
                requestUrl += "?"+urlencode(params);
            }
            URL url = new URL(requestUrl);
            //打开http连接
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            if (MapUtil.isNotEmpty(header)) {
                for (String key : header.keySet()) {
                    httpUrlConn.setRequestProperty(key,header.get(key));
                }
            }
            // 设置连接超时时间, 值必须大于0，设置为0表示不超时 单位为“毫秒”
            httpUrlConn.setConnectTimeout(30000);
            // 设置读超时时间, 值必须大于0，设置为0表示不超时 单位毫秒
            httpUrlConn.setReadTimeout(60000);
            // httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            int responseCode = httpUrlConn.getResponseCode();
            Map<String, List<String>> headerFields = httpUrlConn.getHeaderFields();
            //获得输入
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            //将bufferReader的值给放到buffer里
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            //关闭bufferReader和输入流
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            //断开连接
            httpUrlConn.disconnect();
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回字符串
        return null;
    }
    
    public static HttpURLConnection getResponse(String requestUrl, Map params,Map<String,String> header) {
        try {
            //建立URL，把请求地址给补全，其中urlencode（）方法用于把params里的参数给取出来
            if (MapUtil.isNotEmpty(params)) {
                requestUrl += "?"+urlencode(params);
            }
            URL url = new URL(requestUrl);
            //打开http连接
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            if (MapUtil.isNotEmpty(header)) {
                for (String key : header.keySet()) {
                    httpUrlConn.setRequestProperty(key,header.get(key));
                }
            }
            // 设置连接超时时间, 值必须大于0，设置为0表示不超时 单位为“毫秒”
            httpUrlConn.setConnectTimeout(30000);
            // 设置读超时时间, 值必须大于0，设置为0表示不超时 单位毫秒
            httpUrlConn.setReadTimeout(60000);
            // httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            int responseCode = httpUrlConn.getResponseCode();
            Map<String, List<String>> headerFields = httpUrlConn.getHeaderFields();
            //断开连接
            // httpUrlConn.disconnect();
            return httpUrlConn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String urlencode(Map<String,Object> data) {
        //将map里的参数变成像 showapi_appid=###&showapi_sign=###&的样子
        StringBuilder sb = new StringBuilder();
        data.forEach((key, value) -> {
            //                sb.append(key).append("=").append(URLEncoder.encode(value + "", "UTF-8")).append("&");
            sb.append(key).append("=").append(value).append("&");
        });
        return sb.substring(0, sb.length() - 1);
    }

    /**
     *
     * @author lushao
     * 2022/8/18 12:29
     * @param url
     * @param jsonStr
     * @param headerMap
     * @return String
     * @throws IOException
     */
    public static String newPostBody(String url,String jsonStr,Map<String,String> headerMap) throws IOException {
        String body = "";
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        //装填参数
        StringEntity s = new StringEntity(jsonStr, "utf-8");
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        //设置参数到请求对象中
        httpPost.setEntity(s);
        //设置header信息
        //指定报文头【Content-type】、【User-Agent】
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        if (headerMap.containsKey(ContentType)) {
            for (String key : headerMap.keySet()) {
                httpPost.setHeader(key, headerMap.get(key));
            }
        } else {
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        }
        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }
    public static String myHttpGet(String url, Map<String, String> headMap, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
            // 添加head参数
            if (headMap != null && !headMap.isEmpty()) {
                for (String key : headMap.keySet()) {
                    httpGet.addHeader(key, headMap.get(key));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return resultString;
    }

    public static Document getPage(String url) {
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
            page = webClient.getPage(url);//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }
        webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串
        Document doc = Jsoup.parse(pageXml);
        return doc;
    }

    public static void main(String[] args) {
        ApiUtil apiUtil = new ApiUtil();
//        String api = "https://www.80s.tw/";
//        int connect = apiUtil.isConnect(api);
        String url = "https://gitee.com/magerlu/source/raw/master/editor/wangeditor/emoji/giteeemoji.json";
//        String s = doPost(url, null);
//         String s = httpRequest(url, null);
//         System.out.println(s);

    }
    
}
