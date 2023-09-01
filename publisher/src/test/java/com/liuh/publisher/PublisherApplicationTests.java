package com.liuh.publisher;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@SpringBootTest
class PublisherApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 基本消息队列发布者测试
     */
    @Test
    public void BasicQueuePublisherTest() {
        // liu.queue 之前已经创建好了
        String queueName = "liu.queue";
        String massage = "hello, spring amqp!";
        // 发送消息
        rabbitTemplate.convertAndSend(queueName, massage);
    }


    /**
     * 工作消息队列发布者测试 每隔 20 毫秒发送一条消息 一共发送 50 条
     */
    @Test
    public void WorkQueuePublisherTest() {
        // liu.queue 之前已经创建好了
        String queueName = "liu.queue";
        String massage = "hello, spring amqp__";
        for (int i = 1; i <= 50; i++) {
            // 发送消息
            rabbitTemplate.convertAndSend(queueName, massage + i + "_");
            try {
                // 每隔 20 毫秒发送一条
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发布订阅 -广播
     */
    @Test
    public void FanoutQueuePublisherTest() {
        String exchangeName = "liu.fanout";
        String massage = "hello, spring amqp --fanout";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "", massage);
    }

    /**
     * 发布订阅 -路由
     */
    @Test
    public void DirectQueuePublisherTest() {
        String exchangeName = "liu.direct";
        List<String> strings = Arrays.asList("blue", "red", "yellow");
        int index = new Random().nextInt(strings.size());
        String massage = "hello, spring amqp --" + strings.get(index);
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, strings.get(index), massage);
    }

    /**
     * 发布订阅 -主题
     */
    @Test
    public void TopicQueuePublisherTest() {
        String exchangeName = "liu.topic";
        String massage = "China NO.1";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "China.news", massage);
    }


    @Test
    public void QueueTest() {
        HashMap<String, Object> user = new HashMap<>();
        HashMap<String, Object> info = new HashMap<>();
        info.put("sex", "女");
        info.put("age", 18);
        user.put("name", "刘亦菲");
        user.put("info", info);
        String queueName = "liu.queue";
        rabbitTemplate.convertAndSend(queueName, user);
    }
}
