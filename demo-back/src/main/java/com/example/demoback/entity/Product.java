package com.example.demoback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Product")
public class Product {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String image;
    private Double price;
    private String category;
    private String model;
    private Integer sales;
}
