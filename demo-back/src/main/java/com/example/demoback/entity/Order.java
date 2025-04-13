package com.example.demoback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

//订单实体

@Data
@TableName("order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer user_Id;  // 外键字段（对应 user 表的 id）
    private Integer product_id;
    private Integer number;
}
