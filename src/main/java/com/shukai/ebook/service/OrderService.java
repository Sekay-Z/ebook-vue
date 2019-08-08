package com.shukai.ebook.service;

import com.shukai.ebook.bean.Order_Master;
import com.shukai.ebook.exception.OrderException;

import java.util.List;

public interface OrderService {
    public void createOrder(Order_Master order_master) throws OrderException;
    public Order_Master findOneOrder(String order_id) throws OrderException;
    public List<Order_Master> findAllOrder(String buyer_account) throws OrderException;
    public List<Order_Master> findAllUserOrder();
    public List<Order_Master> searchOrder(String name);
    public Order_Master cancle(Order_Master order_master) throws OrderException;
    public Order_Master finish(Order_Master order_master) throws OrderException;
    public Order_Master paid(Order_Master order_master) throws OrderException;
}
