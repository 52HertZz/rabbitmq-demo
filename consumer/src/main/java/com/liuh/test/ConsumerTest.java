package com.liuh.test;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: liuhuan
 * @Date: 2023/8/8 14:13
 * @PackageName: com.liuh.test
 * @ClassName: ConsumerTest
 * @Description: TODO
 * @Version 1.0
 */
public class ConsumerTest {
    public static void main(String[] args) {
        // 1. 创建 rabbitmq 连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("123.249.106.95");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");

        Connection connection;
        try {
            connection = connectionFactory.newConnection();
            // 2. 创建通道
            Channel channel = connection.createChannel();

            // 3. 创建队列
            String queueName = "liu.queue";
            channel.queueDeclare(queueName, false, false, false, null);

            // 4. 获取消息
            channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String massage = new String(body);
                    System.out.println(massage);
                }
            });
            System.out.println("等待接收消息……");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
