package com.liuh.publisher;

import com.liuh.publisher.constants.RabbitMqConstant;
import com.liuh.publisher.entity.Hotel;
import com.liuh.publisher.mapper.HotelMapper;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: liuhuan
 * @Date: 2023/8/20 9:31
 * @PackageName: com.liuh.publisher
 * @ClassName: MysqlTests
 * @Description: TODO
 * @Version 1.0
 */
@SpringBootTest
public class MysqlTests {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 新增
     */
    @Test
    void insert() {
        Hotel hotel = getHotel();
        hotelMapper.insert(hotel);
        rabbitTemplate.convertAndSend(RabbitMqConstant.EXCHANGE_NAME, RabbitMqConstant.INSERT_OR_UPDATE_KEY, hotel.getId());
    }

    /**
     * 修改
     */
    @Test
    void update() {
        Hotel hotel = getHotel();
        hotel.setName("Test2");
        hotelMapper.updateById(hotel);
        rabbitTemplate.convertAndSend(RabbitMqConstant.EXCHANGE_NAME, RabbitMqConstant.INSERT_OR_UPDATE_KEY, hotel.getId());
    }

    /**
     * 删除
     */
    @Test
    void delete() {
        Hotel hotel = getHotel();
        hotelMapper.deleteById(hotel);
        rabbitTemplate.convertAndSend(RabbitMqConstant.EXCHANGE_NAME, RabbitMqConstant.DELETE_KEY, hotel.getId());
    }

    /**
     * 创建 hotel 实体类
     *
     * @return
     */
    Hotel getHotel() {
        Hotel hotel = new Hotel();
        hotel.setId(10L);
        hotel.setName("Test");
        hotel.setAddress("开福区");
        hotel.setPrice(998);
        hotel.setScore(10);
        hotel.setBrand("");
        hotel.setCity("长沙");
        hotel.setLatitude("90");
        hotel.setLongitude("100");
        return hotel;
    }
}
