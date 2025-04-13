package com.example.demoback.service;

import com.example.demoback.entity.Order;
import com.example.demoback.entity.OrderBar;
import com.example.demoback.entity.Product;
import com.example.demoback.entity.User;
import com.example.demoback.mapper.OrderMapper;
import com.example.demoback.mapper.ProductMapper;
import com.example.demoback.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

/*    @Autowired
    private UserMapper userMapper;*/

    @Autowired
    private ProductMapper productMapper;

    @Transactional
    public Order createOrder(Integer userId, Integer productId, Integer number) throws ServiceException {
        // 参数校验（根据现有字段类型调整）
        if (userId == null || userId <= 0) {
            throw new ServiceException("无效的用户ID");
        }
        if (productId == null || productId <= 0) {
            throw new ServiceException("无效的商品ID");
        }
        if (number == null || number <= 0) {
            throw new ServiceException("购买数量必须大于0");
        }

        // 用户存在性验证（根据UserMapper现有方法调整）
        /*User user = userMapper.selectByUsernamePassword(
                "dummy", "dummy"); */
        // 注意：当前UserMapper缺少通过ID查询用户的方法
        // 建议添加UserMapper.selectById方法

        // 商品存在性验证（根据ProductMapper现有方法）
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new ServiceException("商品不存在");
        }

        // 构建订单对象（严格匹配Order实体字段）
        Order newOrder = new Order();
        newOrder.setUser_Id(userId);      // 注意字段名带下划线
        newOrder.setProduct_id(productId); // 注意字段名带下划线
        newOrder.setNumber(number);

        // 执行插入操作（使用OrderMapper现有方法）
        int affectedRows = orderMapper.create(newOrder);
        if (affectedRows == 0) {
            throw new ServiceException("订单创建失败");
        }

        return newOrder;
    }

    public List<OrderBar> getOrdersWithProducts(Integer userId) throws ServiceException {
        // 参数校验（根据业务需求补充）
        if (userId == null || userId <= 0) {
            throw new ServiceException("无效的用户ID");
        }
        return orderMapper.selectOrdersWithProduct(userId);
    }

}
