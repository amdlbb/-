package com.example.demoback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demoback.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductMapper extends BaseMapper<Product> {

    @Select("<script>" +
            "SELECT * FROM product " +
            "<where>" +
            "  <if test='name != null'> AND name LIKE CONCAT('%',#{name},'%') </if>" + // AND 放在内部
            "  <if test='price1 > 0'> AND price &gt;= #{price1} </if>" +
            "  <if test='price2 > 0'> AND price &lt;= #{price2} </if>" +
            "  <if test='hot == 1'> AND sales &gt;= 500 </if>" +
            "</where>" +
            "  <if test='hot == 1'> ORDER BY sales DESC </if>" +
            " LIMIT #{begin},#{size}" +
            "</script>")
    List<Product> selectByNameAndPrice(
            @Param("name") String name,
            @Param("price1") int price1,
            @Param("price2") int price2,
            @Param("hot") int hot,
            @Param("begin") int begin,
            @Param("size") int size
    );

    @Select("<script>" +
            "SELECT COUNT(*) FROM product " +
            "<where>" +
            "<if test='name!=null'>name LIKE CONCAT('%',#{name},'%') </if>" +
            "<if test='price1>0'> AND price &gt;= #{price1} </if>" +
            "<if test='price2>0'> AND price &lt;= #{price2} </if>" +
            "<if test='hot==1'> AND sales &gt;= 500</if>" + // 同步计数条件
            "</where>" +
            "</script>")
    int countByNameAndPrice(

            @Param("name") String name,
            @Param("price1") int price1,
            @Param("price2") int price2,
            @Param("hot") int hot

    );
    @Select("<script>" +
            "SELECT * FROM product " +
            "<where>" +
            "id = #{id}" +
            "</where>" +
            "</script>")
    Product selectById(@Param("id") Integer id);

    @Update("<script>" +
            "UPDATE product " +
            "<set>" +
            "sales = sales + #{number} " +
            "</set>" +
            "<where>" +
            "id = #{productId}" +
            "</where>" +
            "</script>")
    int updateById(@Param("productId") Integer productId,
                   @Param("number") Integer number);

    @Select("<script>" +
            "SELECT * FROM product " +
            "<where>" +
            "category = #{category} " +
            "</where>" +
            "LIMIT #{begin},#{size}" +
            "</script>")
    List<Product> selectByCategory(@Param("category") String category,
                                   @Param("begin") int begin,
                                   @Param("size") int size);

    @Select("<script>" +
            "SELECT COUNT(*) FROM product " +
            "<where>" +
            "category = #{category} " +
            "</where>" +
            "</script>")
    int countByCategory(@Param("category") String category);


}
