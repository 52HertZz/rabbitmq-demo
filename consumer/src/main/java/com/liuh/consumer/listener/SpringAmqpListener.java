package com.liuh.consumer.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author: liuhuan
 * @Date: 2023/8/8 10:23
 * @PackageName: com.liuh.consumer.listener
 * @ClassName: SpringAmqpListener
 * @Description: TODO
 * @Version 1.0
 */
@Component
public class SpringAmqpListener {

    /**
     * 监听消息队列 liu.queue 中的消息
     *
     * @param msg 消息
     */
//    @RabbitListener(queues = "liu.queue")
//    public void listenBasicQueueMassage(String msg) {
//        System.out.println("Spring AMQP 接收的消息为：" + msg);
//    }

    /**
     * 监听消息队列 liu.queue 中的消息
     *
     * @param msg 消息
     */
    @RabbitListener(queues = "liu.queue")
    public void listenBasicQueueMassage(Map<String, Object> msg) {
        System.out.println("Spring AMQP 接收的消息为：" + msg);
    }


    /**
     * 监听消息队列 liu.queue 中的消息 每隔 20 毫秒消费一条
     *
     * @param msg 消息
     */
    @RabbitListener(queues = "liu.queue")
    public void listenWorkQueueMassage1(String msg) {
        System.out.println("Spring AMQP 接收的 WorkQueue 消息为：" + msg + "_" + LocalDateTime.now());
        try {
            // 每隔 20 毫秒消费一条
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 监听消息队列 liu.queue 中的消息 每隔 100 毫秒消费一条
     *
     * @param msg 消息
     */
    @RabbitListener(queues = "liu.queue")
    public void listenWorkQueueMassage2(String msg) {
        System.err.println("Spring AMQP 接收的 WorkQueue 消息为__________" + msg + "_" + LocalDateTime.now());
        try {
            // 每隔 100 毫秒消费一条
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听消息队列 fanout.queue1 中的消息
     *
     * @param msg 消息
     */
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueueMassage1(String msg) {
        System.err.println("Spring AMQP 接收的 FanoutQueue1 消息为：" + msg);
    }

    /**
     * 监听消息队列 fanout.queue2 中的消息
     *
     * @param msg 消息
     */
    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueueMassage2(String msg) {
        System.err.println("Spring AMQP 接收的 FanoutQueue2 消息为：" + msg);
    }


    /**
     * 监听消息队列 direct.queue1 中的消息
     *
     * @param msg 消息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "liu.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenDirectQueueMassage1(String msg) {
        System.err.println("Spring AMQP 接收的 DirectQueue1 消息为：" + msg);
    }

    /**
     * 监听消息队列 direct.queue2 中的消息
     *
     * @param msg 消息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "liu.direct", type = ExchangeTypes.DIRECT),
            key = {"blue", "yellow"}
    ))
    public void listenDirectQueueMassage2(String msg) {
        System.err.println("Spring AMQP 接收的 DirectQueue2 消息为：" + msg);
    }

    /**
     * 监听消息队列 topic.queue1 中的消息
     *
     * @param msg 消息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "liu.topic", type = ExchangeTypes.TOPIC),
            key = {"China.#"}
    ))
    public void listenTopicQueueMassage1(String msg) {
        System.err.println("Spring AMQP 接收的 TopicQueue1 消息为：" + msg);
    }

    /**
     * 监听消息队列 topic.queue2 中的消息
     *
     * @param msg 消息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "liu.topic", type = ExchangeTypes.TOPIC),
            key = {"#.news"}
    ))
    public void listenTopicQueueMassage2(String msg) {
        System.err.println("Spring AMQP 接收的 TopicQueue2 消息为：" + msg);
    }
}
