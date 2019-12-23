package com.yewei.sample.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;
@Slf4j
@Component
public class RestTemplateUtil {

    private static final String ENCODING = "UTF-8";

    private static RestTemplate restTemplate;

    @Resource
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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

    public <T> T postRequest(String url, Map<String, String> param, Class<T> targetClass) {
        final JSONObject requestObject = new JSONObject();
        requestObject.putAll(param);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded;charset=UTF-8");
        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_FORM_URLENCODED.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(requestObject.toString(), headers);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("key","4f900bac1ffc591db91d9ac8790dbca8");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        log.info("post for:{}", url);
        T forObject = restTemplate.postForObject(url, request, targetClass);
        log.info("response:{}", JSON.toJSONString(forObject));

        return forObject;
    }

}
