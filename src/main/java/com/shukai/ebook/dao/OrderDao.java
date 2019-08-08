package com.shukai.ebook.dao;

import com.shukai.ebook.bean.Order_Detail;
import com.shukai.ebook.bean.Order_Master;

import java.util.List;

public interface OrderDao {
    public void createOrder(Order_Master order_master);
    public Order_Master findOneOrder(String order_id);
    public List<Order_Master> findAllOrder(String buyer_account);
    public void updateOrderStatus(Order_Master order_master);
    public void createOrderDetail(Order_Detail order_detail);
    public List<Order_Master> findAllUserOrder();
    public List<Order_Master> searchOrder(String name);
}
