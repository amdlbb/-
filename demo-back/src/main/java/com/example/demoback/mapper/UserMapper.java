package com.example.demoback.mapper;

import com.example.demoback.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    @Select("select * from user")
    List<Map<String,Object>> selectAll();

    @Select("select * from user where username=#{username} and password=#{password}")
    User selectByUsernamePassword(@Param("username") String username,
                                  @Param("password") String password);

    @Insert("INSERT INTO user(username, password, head, email) " +
            "VALUES(#{user.username}, #{user.password}, #{user.head}, #{user.email})") // 修改点
    @Options(useGeneratedKeys = true, keyProperty = "user.id") // 修改点
    int create(@Param("user") User user);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    @Update("UPDATE user SET password=#{password}, email=#{email}, head=#{head} WHERE username=#{username}")
    int updateUser(
            @Param("head") String head,
            @Param("username") String username,
            @Param("password") String password,
            @Param("email") String email
    );

    @Update("UPDATE user SET head=#{headUrl} WHERE username=#{username}")
    int updateHead(
            @Param("username") String username,
            @Param("headUrl") String headUrl
    );
}
