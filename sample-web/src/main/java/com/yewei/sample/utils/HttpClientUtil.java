package com.yewei.sample.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class HttpClientUtil {

    private static final String ENCODING = "UTF-8";

    private static RestTemplate restTemplate;

    @Resource
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static String sendRequest(String url, HttpEntity requestEntity, Map<String, String> headerMap)
            throws Exception {
        String responseString = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            httpPost.setEntity(requestEntity);
            if (headerMap != null) {
                for (Map.Entry<String, String> header : headerMap.entrySet()) {
                    httpPost.addHeader(header.getKey(), header.getValue());
                }
            }
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                responseString = EntityUtils.toString(entity, Charset.forName(ENCODING));
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            httpPost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return responseString;
    }

    public static String postFrom(String url, Map<String, String> paramsMap, Map<String, String> headerMap)
            throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (paramsMap != null) {
            for (Map.Entry<String, String> temp : paramsMap.entrySet()) {
                params.add(new BasicNameValuePair(temp.getKey(), temp.getValue()));
            }
        }
        return sendRequest(url, new UrlEncodedFormEntity(params, Charset.forName(ENCODING)), headerMap);
    }

    public static String postFrom(String url, Map<String, String> paramsMap, Map<String, String> headerMap,
            Map<String, String> cookieMap) throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (paramsMap != null) {
            for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                params.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
        }
        if (cookieMap != null) {
            if (headerMap == null) {
                headerMap = new HashMap<String, String>();
            }
            StringBuffer cookies = new StringBuffer();
            for (Map.Entry<String, String> cookie : cookieMap.entrySet()) {
                cookies.append(cookie.getKey());
                cookies.append("=");
                cookies.append(cookie.getValue());
                cookies.append(";");
            }
            headerMap.put("Cookie", cookies.toString());
        }
        return sendRequest(url, new UrlEncodedFormEntity(params, Charset.forName(ENCODING)), headerMap);
    }

    public static String postJson(String url, Object obj) throws Exception {
        return postJson(url, obj, null);
    }

    public static String postJson(String url, Object obj, Map<String, String> headerMap) throws Exception {
        String jsonString = JSON.toJSONString(obj);
        StringEntity requestEntity = new StringEntity(jsonString, Charset.forName(ENCODING));
        if (headerMap == null) {
            headerMap = new HashMap<String, String>();
        }
        headerMap.put("content-type", "application/json; encoding=utf-8");
        return sendRequest(url, requestEntity, headerMap);
    }

    public static byte[] postByte(String url, byte[] date) throws Exception {
        byte[] responseBytes = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            if (date != null) {
                httpPost.setEntity(new ByteArrayEntity(date, ContentType.DEFAULT_BINARY));
            }
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseBytes = EntityUtils.toByteArray(entity);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            httpPost.releaseConnection();
            try {
                httpClient.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return responseBytes;
    }


    /**
     *  拼接get参数
     */
    public static String buildGetParamStr(Map<String, ?> params){
        if (params==null || params.isEmpty()){
            return "";
        }
        StringBuilder sb = new StringBuilder("?");
        params.forEach((k, v)->{
            if (k!=null && v!=null){
                sb.append(k).append("=").append(v).append("&");
            }
        });
        if (sb.length()==1){
            return "";
        }
        return sb.toString();
    }

    public <T> T getRequest(String url, Map<String,Object> param, Class<T> targetClass){
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(url).append(buildGetParamStr(param));
        log.info("get for:{}",url);
        T forObject = restTemplate.getForObject(sbUrl.toString(), targetClass);

        log.info("response:{}", JSON.toJSONString(forObject));

        return forObject;
    }

    public <T> T postRequest(String url, Map<String,Object> param, Class<T> targetClass){
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(url).append(buildGetParamStr(param));
        log.info("get for:{}",url);
        T forObject = restTemplate.getForObject(sbUrl.toString(), targetClass);

        log.info("response:{}", JSON.toJSONString(forObject));

        return forObject;
    }

}
