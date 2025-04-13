package com.example.demoback.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String head;
    private String email;

    @TableField(exist = false)
    private String sessionId;
}
