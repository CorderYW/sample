package com.yewei.sample.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by Fei.Huang on 2018/5/4.
 */
public final class TrackingUtils {

    // 对应log4j2.xml中 %X{trackingId}
    public static final String TRACKING_CHAIN = "tracking-chain";

    public static final String X_AMZN_TRACE_ID = "x-amzn-trace-id";

    public static final String SKYWLKING_ID = "skywalking-id";


    public static final String TRACE_ID = "traceId";

    public static final String AWS_TRACE_ROOT = "Root=";

    // tracking:type:context (例如: [tracking:job:a083188a-aeed-4e8f-a739-67f282b54629])
    public static final String SIMPLE_TEMPLATE = "%s:%s";

    /**
     * 入口开始处加入跟踪链
     *
     * @param type 跟踪类型
     * @param context 唯一标识内容
     */
    public static void putTracking(final String type, final String context) {
        //为了兼容老系统两个id都要set
        String finalTraceId=getTrackingChain();
        if(StringUtils.isNotBlank(finalTraceId)){
            if(containSystemPrefix(finalTraceId,context)){
                ThreadContext.put(TRACKING_CHAIN, String.format(SIMPLE_TEMPLATE, context, finalTraceId));
                ThreadContext.put(TRACE_ID, String.format(SIMPLE_TEMPLATE, context, finalTraceId));
            }else{
                ThreadContext.put(TRACKING_CHAIN, wrapAwsTrace(finalTraceId));
                ThreadContext.put(TRACE_ID, wrapAwsTrace(finalTraceId));
            }
        }else{
            ThreadContext.put(TRACKING_CHAIN, String.format(SIMPLE_TEMPLATE, type, wrapAwsTrace(context)));
            ThreadContext.put(TRACE_ID, String.format(SIMPLE_TEMPLATE, type, wrapAwsTrace(context)));
        }
    }

    public static void putTracking(final String context) {
        //为了兼容老系统两个id都要set
        String finalTraceId=getTrackingChain();
        //针对pnkweb，admin这些老系统带前缀的，那么继续保留
        if(containSystemPrefix(finalTraceId,context)){
            ThreadContext.put(TRACKING_CHAIN, String.format(SIMPLE_TEMPLATE, context, finalTraceId));
            ThreadContext.put(TRACE_ID, String.format(SIMPLE_TEMPLATE, context, finalTraceId));
        }else{
            ThreadContext.put(TRACKING_CHAIN, wrapAwsTrace(finalTraceId));
            ThreadContext.put(TRACE_ID, wrapAwsTrace(finalTraceId));
        }

    }
    //是否包含带有指定系统的前缀
    public  static boolean containSystemPrefix(String finalTraceId,String traceId){
        boolean flag=org.apache.commons.lang3.StringUtils.containsAny(traceId,"ExchangeWeb","BnbWeb","BnbAdmin"
                ,"gateway-anonymous","BinanceMgs","BnbAnonymous","pnk-anonymous","ExchangeAnonym","pnkadmin-anonymous");
        Boolean containFlag=org.apache.commons.lang3.StringUtils.containsOnly(finalTraceId,traceId)
                ||org.apache.commons.lang3.StringUtils.containsOnly(traceId,finalTraceId);
        return flag && !containFlag;
    }


//    /**
//     * 保存 sky walking的 traceId，use [%X{traceId}] in log parttern
//     */
//    public static void saveTraceId() {
//        String traceId=TraceContext.traceId();
//        if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(traceId,"Ignored")|| org.apache.commons.lang3.StringUtils.containsIgnoreCase(traceId,"N/A")){
//            traceId= UUID.randomUUID().toString();
//        }
//        ThreadContext.put(TRACE_ID, traceId);
//    }

    public static String getAwsTrace() {
        String awsTrace;
        String mark=StringUtils.EMPTY;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            awsTrace = request.getHeader(X_AMZN_TRACE_ID);
        } catch (Exception e) {
            awsTrace = org.apache.commons.lang3.StringUtils.EMPTY;
        }
        if (org.apache.commons.lang3.StringUtils.isNotBlank(awsTrace)) {
            try {
                awsTrace = awsTrace.substring(awsTrace.indexOf(AWS_TRACE_ROOT) + 5);
            } catch (Exception e) {
                // Do nothing
            }
            mark = "-(AWS=" + awsTrace + ")";
        }
        return mark;
    }

    public static String wrapAwsTrace(String currentTraceId) {
        if(org.apache.commons.lang3.StringUtils.isNotBlank(currentTraceId)&& currentTraceId.contains("AWS")){
            return currentTraceId;
        }else{
            return currentTraceId+getAwsTrace();
        }
    }



    /**
     * 删除保存的sky walking的 traceId
     */
    public static void removeTraceId() {
        ThreadContext.remove(TRACE_ID);
    }


    /**
     * 入口结束处释放跟踪链
     */
    public static void removeTracking() {
        ThreadContext.remove(TRACKING_CHAIN);
    }

    public static String getTrackingChain() {
        String finalTraceId=Strings.EMPTY;
        if(StringUtils.isNotBlank(ThreadContext.get(TRACKING_CHAIN))){
            finalTraceId=ThreadContext.get(TRACKING_CHAIN);
        }
        if(StringUtils.isNotBlank(ThreadContext.get(TRACE_ID))){
            finalTraceId=ThreadContext.get(TRACE_ID);
        }
        if(org.apache.commons.lang3.StringUtils.containsIgnoreCase(finalTraceId,"Ignored")|| org.apache.commons.lang3.StringUtils.containsIgnoreCase(finalTraceId,"N/A")){
           return Strings.EMPTY;
        }
        //TRACE_ID 优先级大于TRACKING_CHAIN
        return  finalTraceId;
    }

}
