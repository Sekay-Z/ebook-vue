package com.shukai.ebook.controller;

import com.shukai.ebook.bean.Order_Master;
import com.shukai.ebook.exception.OrderException;
import com.shukai.ebook.service.OrderServiceImpl;
import com.shukai.ebook.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @PostMapping("/createOrder")
    public Result createOrder(@RequestBody Order_Master order_master) {
        try {
            orderServiceImpl.createOrder(order_master);
        } catch (OrderException e) {
            e.printStackTrace();
            Result.getInstance().setMessage(e.getMessage());
            return Result.getInstance();
        }
        Result.getInstance().setMessage("创建订单成功!");
        return Result.getInstance();
    }

    @GetMapping("/findOneOrder/{id}")
    public Result findOneOrder(@PathVariable("id") String id){
        try {
            Order_Master order_master=orderServiceImpl.findOneOrder(id);
            Result.getInstance().setObject(order_master);
            return  Result.getInstance();
        } catch (OrderException e) {
            e.printStackTrace();
            Result.getInstance().setMessage(e.getMessage());
            return Result.getInstance();
        }
    }

    @GetMapping("/findAllOrder")
    public Result findAllOrder(@RequestParam("account") String account){
        try {
            List<Order_Master> list=orderServiceImpl.findAllOrder(account);
            Result.getInstance().setObject(list);
            return Result.getInstance();
        } catch (OrderException e) {
            e.printStackTrace();
            Result.getInstance().setMessage(e.getMessage());
            return Result.getInstance();
        }
    }

    @GetMapping("/findAllUserOrder")
    public Result findAllUserOrder(){
            List<Order_Master> list=orderServiceImpl.findAllUserOrder();
            Result.getInstance().setObject(list);
            return Result.getInstance();
    }

    @GetMapping("/cancle")
    public Order_Master cancle(@RequestParam("order_id") String order_id) throws OrderException {
        Order_Master order_master=orderServiceImpl.findOneOrder(order_id);
        return orderServiceImpl.cancle(order_master);
    }

    @GetMapping("/finish")
    public Order_Master finish(@RequestParam("order_id") String order_id) throws OrderException {
        Order_Master order_master=orderServiceImpl.findOneOrder(order_id);
        return orderServiceImpl.finish(order_master);
    }

    @GetMapping("/searchOrder")
    public Result searchOrder(@RequestParam("name") String name){
         List<Order_Master> list=orderServiceImpl.searchOrder(name);
         Result.getInstance().setMessage(null);
         Result.getInstance().setObject(list);
         return Result.getInstance();
    }
}
