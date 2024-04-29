package com.liangshou.llmsrefactor.utils;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author X-L-S
 */
@Deprecated
@Slf4j
public class HttpClientUtil {

    public static String doGet(String url, Map<String, String> param, Map<String, String> headers) {
        CloseableHttpClient httpClient = null;
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建Httpclient对象
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(6000).setConnectionRequestTimeout(6000)
                    .setSocketTimeout(10000).build();
            httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .setSSLSocketFactory(new SSLConnectionSocketFactory(createSSLContext())).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (!CollectionUtils.isEmpty(param)) {
                param.forEach(builder::addParameter);
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            if (!CollectionUtils.isEmpty(headers)) {
                headers.forEach(httpGet::setHeader);
            }
            // 执行请求
            response = httpClient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (null != httpClient) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }


    public static String doPost(String url, Map<String, String> param, Map<String, String> headers) {
        // 创建Httpclient对象
        CloseableHttpResponse response = null;
        String resultString = "";
        CloseableHttpClient httpClient = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(6000).setConnectionRequestTimeout(6000)
                    .setSocketTimeout(10000).build();
            httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .setSSLContext(createSSLContext()).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            if (!CollectionUtils.isEmpty(headers)) {
                headers.forEach(httpPost::setHeader);
            }
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                httpPost.addHeader("Content-Type", "application/json");
                httpPost.setEntity(new StringEntity(JSON.toJSON(param).toString(), "utf-8"));
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultString;
    }


    private static SSLContext createSSLContext() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return SSLContexts.custom().loadTrustMaterial(null, (x509Certificates, s) -> true).build();
    }
}
