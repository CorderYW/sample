package com.yewei.sample.constant;

/**
 * Created by robin.wu on 2018/12/13.
 */
public class Constant {
    public static String ORDER_EXCHANGE = "orderExchangeV4";

    public static final String MQ_QUEUE_ORDER_NOTIFY_PAYED = "c2cOrderNotifyPayedTimeQueueV4";
    public static final String MQ_QUEUE_ORDER_NOTIFY_PAYED_DEAD_LETTER = "c2cOrderNotifyPayedTimeDeadLetterQueueV4";

    public static final String ROUTING_KEY_MQ_ORDER_NOTIFY_PAYED = "notifyPayedV4";

    public static final String MQ_QUEUE_USER = "userMQ1";
    public static final String ROUTING_KEY_MQ_USER = "userRoutingKey";

}
