package com.example.demoback.mapper;
import com.example.demoback.entity.Order;
import com.example.demoback.entity.OrderBar;
import com.example.demoback.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface OrderMapper {

    @Insert("INSERT INTO `order`(user_id, product_id, number) " +
            "VALUES(#{order.user_Id}, #{order.product_id}, #{order.number})")
    @Options(useGeneratedKeys = true, keyProperty = "order.id") // 回填自增主键
    int create(@Param("order") Order order);

    // 根据ID查询订单（示例方法）
    @Select("SELECT * FROM `order` WHERE id = #{id}")
    Order selectById(@Param("id") Integer id);

    @Select("<script>" +
            "SELECT " +
            "o.id AS id, " +
            "p.name AS name, " +
            "p.image AS image, " +
            "p.category AS category, " +
            "o.number AS number " +
            "FROM `order` o " +
            "LEFT JOIN product p ON o.product_id = p.id " +
            "<where>" +
            "o.user_id = #{userId}" +
            "</where>" +
            "</script>")
    List<OrderBar> selectOrdersWithProduct(@Param("userId") Integer userId);

    @Delete("<script>" +
            "DELETE FROM `order` " +
            "<where>" +
            "id = #{id}" +
            "</where>" +
            "</script>")
    int deleteById(@Param("id") Integer id);
}
