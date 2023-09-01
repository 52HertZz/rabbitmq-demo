package com.liuh.consumer.constants;

/**
 * @Author: liuhuan
 * @Date: 2023/8/20 9:13
 * @PackageName: com.liuh.consumer.constants
 * @ClassName: RabbitMQConstant
 * @Description: TODO
 * @Version 1.0
 */
public class RabbitMqConstant {
    public final static String EXCHANGE_NAME = "hotel.exchange";
    public final static String DELETE_QUEUE_NAME = "hotel.delete.queue";
    public final static String INSERT_OR_UPDATE_QUEUE_NAME = "hotel.insertOrUpdate.queue";
    public final static String DELETE_KEY = "hotel.delete";
    public final static String INSERT_OR_UPDATE_KEY = "hotel.insertOrUpdate";
}
