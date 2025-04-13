// OrderController.java 完整实现
package com.example.demoback.web;

import com.example.demoback.entity.Order;
import com.example.demoback.entity.OrderBar;
import com.example.demoback.entity.Result;
import com.example.demoback.mapper.OrderMapper;
import com.example.demoback.service.OrderService;
import com.example.demoback.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 创建订单接口（与ProductController保持相同风格）
     * @param userId 从Session获取的用户ID（需前端传递）
     * @param productId 商品ID（对应前端this.product.id）
     * @param quantity 购买数量（对应前端validQty）
     * @return 标准Result对象
     */
    @RequestMapping("create")
    public Result create(
        @RequestParam Integer userId,
        @RequestParam Integer productId,
        @RequestParam Integer quantity) {

        try {

            Order order = orderService.createOrder(userId, productId, quantity);

            if (order != null) {
                // 调用Service层并返回统一结果格式
                return new Result(1, "订单创建成功", order);
            }else {
                return new Result(0,"创建失败",null);
            }
        } catch (ServiceException e) {
            // 异常处理与ProductController完全一致
            return new Result(0, e.getMessage(), null);
        }
    }

    @RequestMapping("list")
    public Result getOrderList(@RequestParam Integer user_id) {
        try {
            List<OrderBar> orders = orderService.getOrdersWithProducts(user_id);
            return new Result(1, "查询成功", orders);
        } catch (ServiceException e) {
            return new Result(0, e.getMessage(), null);
        } catch (Exception e) {
            return new Result(0, "系统错误", null);
        }
    }

    @RequestMapping("cancel")
    public Result cancelOrder(@RequestParam Integer id) {
        try {
            int affectedRows = orderMapper.deleteById(id);
            if (affectedRows > 0) {
                return new Result(1, "订单删除成功", null);
            } else {
                return new Result(0, "订单不存在或删除失败", null);
            }
        } catch (Exception e) {
            return new Result(0, "系统错误：" + e.getMessage(), null);
        }
    }
}
