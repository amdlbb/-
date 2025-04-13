package com.example.demoback.web;

import com.example.demoback.entity.Page;
import com.example.demoback.entity.Product;
import com.example.demoback.entity.Result;
import com.example.demoback.mapper.ProductMapper;
import com.example.demoback.service.ProductService;
import com.example.demoback.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductMapper productMapper;

    @RequestMapping("news")
    Object queryByNameAndPrice(@RequestParam(defaultValue = "") String name,
      @RequestParam(defaultValue = "0") int price1,
        @RequestParam(defaultValue = "0") int price2,
        @RequestParam(defaultValue = "0") int hot,
        Page page) {
            int begin = (page.getPage()-1)*page.getSize();
            List<Product> products = productMapper.selectByNameAndPrice(name, price1, price2, hot, begin, page.getSize());
            page.setData(products);
            int total = productMapper.countByNameAndPrice(name, price1, price2, hot);
            page.setTotal(total);
            return page;
    }


    @RequestMapping("cg")
    public Page queryByCategory(@RequestParam(defaultValue = "") String category,
                                Page page) {
        int begin = (page.getPage() - 1) * page.getSize();
        List<Product> products = productMapper.selectByCategory(category, begin, page.getSize());
        page.setData(products);
        int total = productMapper.countByCategory(category);
        page.setTotal(total);
        return page;
    }

    @Autowired
    ProductService productService;

    @RequestMapping("save")
    Result save(Product product) {
        try {
            productService.save(product);
            return new Result(1,"商品添加成功",product);
        } catch (Exception e) {
            return new Result(0,e.getMessage(),null);
        }

    }

    @RequestMapping("del")
    Result del(int id) {
        productMapper.deleteById(id);
        return new Result(1,"删除成功",null);
    }

    @RequestMapping("get")
    public Result getProductById(@RequestParam Integer id) {
        Product product = productMapper.selectById(id);
        if (product != null) {
            return new Result(1, "success", product);
        } else {
            return new Result(0, "商品不存在", null);
        }
    }

    @RequestMapping("updateSales")
    public Result updateSales(@RequestParam Integer product_id,
                              @RequestParam Integer number) {
        try {
            productMapper.updateById(product_id, number);
            return new Result(1, "销量更新成功", null);
        } catch (Exception e) {
            return new Result(0, "系统错误", null);
        }
    }

}
