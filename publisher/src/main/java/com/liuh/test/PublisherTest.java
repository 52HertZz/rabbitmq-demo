package com.liuh.test;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: liuhuan
 * @Date: 2023/8/7 14:43
 * @PackageName: com.liuh.test
 * @ClassName: PublisherTest
 * @Description: TODO
 * @Version 1.0
 */
public class PublisherTest {

    public static void main(String[] args) {
        // 1. 创建 rabbitmq 连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("123.249.106.95");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        try {
            Connection connection = connectionFactory.newConnection();
            // 2. 创建通道
            Channel channel = connection.createChannel();

            // 3. 创建队列
            String queueName = "liu.queue";
            channel.queueDeclare(queueName, false, false, false, null);

            // 4. 发送消息
            String massage = "this is a massage, liu";
            channel.basicPublish("", queueName, null, massage.getBytes());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
