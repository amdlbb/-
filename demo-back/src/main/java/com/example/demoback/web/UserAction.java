package com.example.demoback.web;

import com.example.demoback.entity.User;
import com.example.demoback.mapper.UserMapper;
import com.example.demoback.service.UserService;
import com.example.demoback.service.ServiceException;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ssl.DefaultSslBundleRegistry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demoback.entity.Result;

import javax.management.ServiceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("user")
public class UserAction {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    private DefaultSslBundleRegistry sslBundleRegistry;

    @RequestMapping("queryAll")
    List<Map<String, Object>> queryAll() {

        return userMapper.selectAll();
    }

    @RequestMapping("login")
    Result login(String username, String password, HttpSession session) {
        try {
            User user = userService.login(username, password);
            session.setAttribute("loginedUser", user);
            user.setSessionId(session.getId());
            return new Result(1, "登录成功", user);
        } catch (ServiceException e) {
            return new Result(0, e.getMessage(), null);

        }
    }

    @RequestMapping("Loginout") // 保持与前端请求路径一致
    public Result logout(HttpSession session) {
        try {
            // 移除登录用户属性
            session.removeAttribute("loginedUser");

            //session.invalidate();
            return new Result(1, "退出成功", null);
        } catch (Exception e) {
            return new Result(0, "退出失败：" + e.getMessage(), null);
        }
    }

    @RequestMapping("getLoginedUser")
    public Result getLoginedUser(HttpSession session) {
        User user = (User) session.getAttribute("loginedUser");
        if (user == null) {
            return new Result(0,"未登录",null);
        }
        user.setSessionId(session.getId());
        return new Result(1, "登录成功", user);
    }

    @RequestMapping("register")
    public Result register(String username, String password, String email, HttpSession session) {
        try {
            User user = userService.register(username, password, email);
            System.out.println( user.getId()+""+user.getHead()+""+user.getPassword());
            session.setAttribute("loginedUser", user);
            user.setSessionId(session.getId());
            return new Result(1, "注册成功", user);
        }catch (Exception e){
            return new Result(0, "注册失败", null);
        }

    }

    @RequestMapping("updateHead")
    public Result updateHead(
            @RequestParam String headUrl, // 接收前端传回的头像地址
            HttpSession session // 通过session获取当前用户
    ) {
        try {
            User currentUser = (User) session.getAttribute("loginedUser");
            if (currentUser == null) {
                return new Result(0, "用户未登录", null);
            }

            // 只更新头像字段
            int count = userService.updateHead(currentUser.getUsername(), headUrl);
            return count > 0
                    ? new Result(1, "头像更新成功", null)
                    : new Result(0, "头像更新失败", null);
        } catch (Exception e) {
            return new Result(0, "系统错误：" + e.getMessage(), null);
        }
    }

    @RequestMapping("update")
    public Result updateUser(
            @RequestParam(defaultValue = "https://tse3-mm.cn.bing.net/th/id/OIP-C.MOikIu0FMcBlYU9Xs-wAxwHaE8?w=282&h=188&c=7&r=0&o=5&dpr=1.3&pid=1.7")
            String head,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            HttpSession session
    ) {
        try {
            int count = userService.updateUser(head, username, password, email);
            if(!Objects.equals(head, "https://tse3-mm.cn.bing.net/th/id/OIP-C" +
                    ".MOikIu0FMcBlYU9Xs-wAxwHaE8?w=282&h=188&c=7&r=0&o=5&dpr=1.3&pid=1.7")){
                User currentUser = (User) session.getAttribute("loginedUser");
                currentUser.setHead(head); // 更新session存储的用户数据
                session.setAttribute("loginedUser", currentUser);
            }
            return count > 0 ? new Result(1, "修改成功",null) : new Result(0, "用户不存在", null);
        } catch (Exception e) {
            return new Result(0, "修改失败：" + e.getMessage(), null);
        }
    }


}


