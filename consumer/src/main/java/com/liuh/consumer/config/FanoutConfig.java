package com.liuh.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: liuhuan
 * @Date: 2023/8/8 15:54
 * @PackageName: com.liuh.consumer.config
 * @ClassName: FanoutConfig
 * @Description: TODO
 * @Version 1.0
 */
@Configuration
public class FanoutConfig {

    /**
     * 创建名为 liu.fanout 的交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange getFanoutExchange() {
        return new FanoutExchange("liu.fanout");
    }

    /**
     * 创建名为 fanout.queue1 的队列
     *
     * @return
     */
    @Bean
    public Queue getFanoutQueue1() {
        return new Queue("fanout.queue1");
    }

    /**
     * 创建名为 fanout.queue2 的队列
     *
     * @return
     */
    @Bean
    public Queue getFanoutQueue2() {
        return new Queue("fanout.queue2");
    }

    /**
     * 将队列 fanout.queue1 和交换机 liu.fanout 进行绑定
     *
     * @param getFanoutQueue1   创建队列 1 的方法名
     * @param getFanoutExchange 创建交换机的方法名
     * @return
     */
    @Bean
    public Binding fanout2Binding1(Queue getFanoutQueue1, FanoutExchange getFanoutExchange) {
        return BindingBuilder.bind(getFanoutQueue1).to(getFanoutExchange);
    }

    /**
     * 将队列 fanout.queue2 和交换机 liu.fanout 进行绑定
     *
     * @param getFanoutQueue2   创建队列 2 的方法名
     * @param getFanoutExchange 创建交换机的方法名
     * @return
     */
    @Bean
    public Binding fanout2Binding2(Queue getFanoutQueue2, FanoutExchange getFanoutExchange) {
        return BindingBuilder.bind(getFanoutQueue2).to(getFanoutExchange);
    }
}
