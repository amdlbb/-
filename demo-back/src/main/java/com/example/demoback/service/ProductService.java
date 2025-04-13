package com.example.demoback.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demoback.entity.Product;
import com.example.demoback.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    ServiceException serviceException;

    @Autowired
    ProductMapper productMapper;

    public Product save(Product product)throws Exception{
        if(product.getName()==null||product.getName().isBlank()){
            throw new ServiceException("请输入商品名");
        }
        if(product.getPrice()<0){
            throw new ServiceException("请输入价格");
        }

        LambdaQueryWrapper<Product> lqw = new LambdaQueryWrapper<Product>();
        lqw.eq(Product::getName,product.getName());
        long cnt = productMapper.selectCount(lqw);
        if(cnt>0){
            throw new ServiceException("已有商品");
        }
        productMapper.insertOrUpdate(product);

        return product;
    }


}
