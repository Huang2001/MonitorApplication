package com.Monitor.Connection;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpHost;
import org.apache.http.NoHttpResponseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class XiaoXiangProxy extends AbstractHttpConnect {
    private static final String ProxyUser = "777089687822422016";
    private static final String ProxyPass = "KERHWBGd";
    private static final String ProxyHost = "http-dynamic-S03.xiaoxiangdaili.com";
    private static final Integer ProxyPort = 10030;
    private static HttpHost proxy = null;
    private HttpClientBuilder clientBuilder = null;
    private HttpClientContext localContext = HttpClientContext.create();
    private StringBuilder builder = new StringBuilder("");

    public XiaoXiangProxy() {
        proxy = new HttpHost("http-dynamic-S03.xiaoxiangdaili.com", ProxyPort, "http");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("777089687822422016", "KERHWBGd"));
        SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(10000).build();
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(10000).setExpectContinueEnabled(false).setCookieSpec("standard").setProxy(proxy).build();
        this.clientBuilder = HttpClients.custom().setDefaultSocketConfig(socketConfig).disableAutomaticRetries().setDefaultRequestConfig(requestConfig).setDefaultCredentialsProvider(credsProvider);
    }

    private String getCookie() {
        this.builder.delete(0, this.builder.length());
        Iterator var1 = this.map.keySet().iterator();

        while(var1.hasNext()) {
            String key = (String)var1.next();
            this.builder.append(key);
            this.builder.append("=");
            this.builder.append((String)this.map.get(key));
            this.builder.append(";");
        }

        this.builder.deleteCharAt(this.builder.length() - 1);
        return this.builder.toString();
    }

    public String doGet(String url) {
        HttpGet httpGet = null;
        if (this.map.size() != 0) {
            httpGet = getGetHeader(url, this.getCookie());
        } else {
            httpGet = getGetHeader(url, (String)null);
        }

        CloseableHttpResponse httpResp = null;

        try {
            System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
            AuthCache authCache = new BasicAuthCache();
            authCache.put(proxy, new BasicScheme());
            this.localContext.setAuthCache(authCache);

            String content;
            try {
                httpResp = this.clientBuilder.build().execute(httpGet, this.localContext);
            } catch (NoHttpResponseException var19) {
                content = "refuse";
                return content;
            }

            List<Cookie> list = this.localContext.getCookieStore().getCookies();
            Iterator var23 = list.iterator();

            while(var23.hasNext()) {
                Cookie cookie = (Cookie)var23.next();
                this.map.put(cookie.getName(), cookie.getValue());
            }

            content = EntityUtils.toString(httpResp.getEntity(), "GB2312");
            String var24 = content;
            return var24;
        } catch (Exception var20) {
            var20.printStackTrace();
            String var5 = "false";
            return var5;
        } finally {
            try {
                if (httpResp != null) {
                    httpResp.close();
                }
            } catch (IOException var18) {
                var18.printStackTrace();
            }

        }
    }

    public static HttpGet getGetHeader(String url, String cookie) {
        HttpGet get = new HttpGet(url);
        get.setHeader("Host", "www.ti.com");
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:95.0) Gecko/20100101 Firefox/95.0");
        get.setHeader("Accept", "*/*");
        get.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        get.setHeader("Accept-Encoding", "gzip, deflate, br");
        get.setHeader("Referer", "https://www.ti.com/store/ti/zh/p/product/?p=THS6222IRGTR");
        get.setHeader("Content-Type", "application/json");
        get.setHeader("X-Sec-Clge-Req-Type", "ajax");
        get.setHeader("Connection", "keep-alive");
        if (cookie != null) {
            get.setHeader("Cookie", cookie);
        }

        get.setHeader("Sec-Fetch-Dest", "empty");
        get.setHeader("Sec-Fetch-Mode", "no-cors");
        get.setHeader("Sec-Fetch-Site", "same-origin");
        get.setHeader("Pragma", "no-cache");
        get.setHeader("Cache-Control", "no-cache");
        get.setHeader("TE", "trailers*/");
        return get;
    }
}
