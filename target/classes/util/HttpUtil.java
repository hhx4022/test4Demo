package util;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.*;

public class HttpUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 用来存取cookies信息的变量.
     */
    private static CookieStore cookieStore;

    /**
     *
     * @param httpUrl
     *            地址
     * @param paramMap
     *            参数
     *
     */
    public static String loginPost(String httpUrl, Map<String, Object> paramMap) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("请求httpUrl=" + httpUrl);
            log.info("请求params=" + JSONObject.toJSONString(paramMap));
        }

        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);

        // 设置参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (paramMap != null && paramMap.size() > 0) {
            Set<String> keySet = paramMap.keySet();
            for (String key : keySet) {
                params.add(new BasicNameValuePair(key, (paramMap.get(key).toString())));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
        }

        String response;
        DefaultHttpClient httpClient = null;
        try {
            httpClient = new DefaultHttpClient();
            String contentCharset = CoreProtocolPNames.HTTP_CONTENT_CHARSET;
            String soTimeOut = CoreConnectionPNames.SO_TIMEOUT;
            String connectTimeOut = CoreConnectionPNames.CONNECTION_TIMEOUT;
            httpClient.getParams().setParameter(contentCharset, "utf-8");
            httpClient.getParams().setParameter(soTimeOut, 60000);
            httpClient.getParams().setParameter(connectTimeOut, 60000);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            // 获得返回结果
            response = EntityUtils.toString(httpResponse.getEntity(),  Charset.forName("UTF-8"));
            // 获得cookie并存取
            cookieStore = httpClient.getCookieStore();
            List<Cookie> cookieList = cookieStore.getCookies();
            for (Cookie cookie : cookieList){
                String name =cookie.getName();
                String value = cookie.getValue();
                log.info("登陆成功，cookie name = " + name + ", cookie value = " + value);
            }
            if (log.isInfoEnabled()) {
                log.info("响应内容=" + response);
            }
            return response;
        } catch (Exception e) {
            log.error("HttpPost请求失败：" + e.getMessage(), e);
            throw new RuntimeException("HttpPost请求失败：" + e.getMessage(), e);
        } finally {
            if (null != httpClient) {
                httpClient.getConnectionManager().shutdown();
            }
        }
    }

    /**
     * 携带cookie发送业务请求，paramMap
     * @param url
     * @param paramMap
     * @return
     * @throws Exception
     */
    public static String requestPostWithCookie(String url, Map<String, Object> paramMap) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("post请求地址：" + url);
            log.info("post请求内容：" + paramMap);
        }
        long beginTime = System.currentTimeMillis();
        try {
           // CookieStore cookieStore = new BasicCookieStore();
            HttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpPost httpPost = new HttpPost(url);
            // 设置参数
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            if (paramMap != null && paramMap.size() > 0) {
                Set<String> keySet = paramMap.keySet();
                for (String key : keySet) {
                    params.add(new BasicNameValuePair(key, (paramMap.get(key).toString())));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            }
            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).build();
            httpPost.setConfig(requestConfig);
            // 发起请求
            HttpResponse response = httpClient.execute(httpPost);
            List<Cookie> cookieList = cookieStore.getCookies();
            for (Cookie cookie : cookieList){
                String name =cookie.getName();
                String value = cookie.getValue();
                log.info("cookie name = " + name + ", cookie value = " + value);
            }
            // 获取响应数据并转换成JSON
            String resString = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8") );
            log.info("post响应内容: " + resString);
            return resString;
        } catch (Exception e) {
            log.error("请求失败：" + e.getMessage(), e);
            throw e;
        } finally {
            log.info("结束post请求，耗时(ms)：" + (System.currentTimeMillis() - beginTime));
        }
    }

    /**
     * 携带cookie发送请求，json
     * @param url
     * @param json
     * @return
     * @throws Exception
     */

    public static String requestPostWithCookie(String url, Object json) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("post请求地址：" + url);
            log.info("post请求内容：" + json);
        }
        long beginTime = System.currentTimeMillis();
        try {
           // CookieStore cookieStore = new BasicCookieStore();
            HttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            HttpPost httpPost = new HttpPost(url);

            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            //response = httpClient.execute(httpPost);
            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).build();
            httpPost.setConfig(requestConfig);
            // 发起请求
            HttpResponse response = httpClient.execute(httpPost);
            List<Cookie> cookieList = cookieStore.getCookies();
            for (Cookie cookie : cookieList){
                String name =cookie.getName();
                String value = cookie.getValue();
                log.info("cookie name = " + name + ", cookie value = " + value);
            }
            // 获取响应数据并转换成JSON
            String resString = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8") );
            log.info("post响应内容: " + resString);
            return resString;
        } catch (Exception e) {
            log.error("请求失败：" + e.getMessage(), e);
            throw e;
        } finally {
            log.info("结束post请求，耗时(ms)：" + (System.currentTimeMillis() - beginTime));
        }
    }

    public static String login(String userName,String userPass) throws Exception {
        String loginUrl="http://localhost:8081/api/user/login";
        Map<String,Object> map=new HashMap<String, Object>(  );
        map.put( "userName",userName );
        map.put( "userPass",userPass );
        return loginPost(loginUrl,map);

    }

    public static String doPost(String userName,String userPass,String Url,Map<String, Object> paramMap) throws Exception {
        login(userName,userPass);
        return requestPostWithCookie(Url,paramMap  );
    }

    public static String doPost(String userName,String userPass,String Url,Object json) throws Exception {
        login(userName,userPass);
        return requestPostWithCookie(Url,json );
    }

}