package com.liuh.consumer.listener;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.liuh.consumer.constants.RabbitMqConstant;
import com.liuh.consumer.entity.Hotel;
import com.liuh.consumer.mapper.HotelMapper;
import com.liuh.consumer.vo.HotelVo;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: liuhuan
 * @Date: 2023/8/20 9:09
 * @PackageName: com.liuh.consumer.listener
 * @ClassName: MySQLToElasticsearchListener
 * @Description: TODO
 * @Version 1.0
 */
@Component
public class MysqlToElasticsearchListener {
    private RestClient restClient;
    private ElasticsearchTransport transport;
    private ElasticsearchClient client;

    private final String INDEX_NAME = "hotel";

    @Autowired
    private HotelMapper hotelMapper;


    /**
     * 在执行操作前建立连接
     */
    void setUp() {
        this.restClient = RestClient.builder(new HttpHost("123.249.106.95", 9200)).build();
        this.transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        this.client = new ElasticsearchClient(transport);
    }

    /**
     * 操作完成后关闭连接
     *
     * @throws IOException
     */
    void setDown() throws IOException {
        this.transport.close();
        this.restClient.close();
    }

    /**
     * 监听 MySQL 新增和修改
     *
     * @param id 数据 id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitMqConstant.INSERT_OR_UPDATE_QUEUE_NAME),
            exchange = @Exchange(name = RabbitMqConstant.EXCHANGE_NAME),
            key = {RabbitMqConstant.INSERT_OR_UPDATE_KEY}
    ))
    public void listenDirectQueueMassage1(Long id) {
        System.out.println("Elasticsearch 中需要新增或修改的文档 id 为:" + id);
        try {
            setUp();
            // 此处直接查询数据库，正常情况应该使用 feign 调用对应微服务的接口
            Hotel hotel = hotelMapper.selectById(id);
            HotelVo hotelVo = new HotelVo(hotel);
            IndexResponse response = client.index(i -> i
                    .index(INDEX_NAME)
                    .id(hotelVo.getId().toString())
                    .document(hotelVo)
            );
            setDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听 MySQL 删除
     *
     * @param id 数据 id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitMqConstant.DELETE_QUEUE_NAME),
            exchange = @Exchange(name = RabbitMqConstant.EXCHANGE_NAME),
            key = {RabbitMqConstant.DELETE_KEY}
    ))
    public void listenDirectQueueMassage2(Long id) {
        System.out.println("Elasticsearch 中需要删除的文档 id 为:" + id);
        try {
            setUp();
            client.delete(d -> d.index(INDEX_NAME).id(String.valueOf(id)));
            setDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
