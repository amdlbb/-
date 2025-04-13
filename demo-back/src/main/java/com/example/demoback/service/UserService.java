package com.example.demoback.service;

import com.example.demoback.entity.User;
import com.example.demoback.mapper.UserMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 登录方法
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户对象，失败则抛出自定义业务异常
     */
    public User login(String username, String password) throws ServiceException {
        // 参数校验
        if (username == null || username.isBlank()) {
            throw new ServiceException("请输入用户名！");
        }
        if (password == null || password.isBlank()) {
            throw new ServiceException("请输入密码！");
        }
        if (password.length() < 6) {
            throw new ServiceException("密码长度至少是6个字符！");
        }

        // 数据库查询
        User user = userMapper.selectByUsernamePassword(username, password);
        if (user == null) {
            throw new ServiceException("用户名或密码错误！");
        }
        return user;
    }


    public User register(String username, String password, String email) throws Exception {
        // 检查用户名是否存在
        System.out.println(password+"qdqedqwdq");
        User existUser = userMapper.selectByUsername(username);
        if(existUser != null) {
            System.out.println("cunzai");
            throw new Exception("用户名已存在");

        }

        // 创建新用户
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setHead("https://tse3-mm.cn.bing.net/th/id/OIP-C.MOikIu0FMcBlYU9Xs-wAxwHaE8?w=282&h=188&c=7&r=0&o=5&dpr=1.3&pid=1.7");
        newUser.setEmail(email);
        System.out.println("cunai");
        userMapper.create(newUser);

        return newUser;
    }

    public int updateUser(String head, String username, String password, String email) {
        // 密码加密存储
        return userMapper.updateUser(head, username, password, email);
    }

    public int updateHead(String username, String headUrl) {
        return userMapper.updateHead(username, headUrl);
    }
}