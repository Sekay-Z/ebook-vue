package com.shukai.ebook.service;

import com.shukai.ebook.bean.Order_Detail;
import com.shukai.ebook.bean.Order_Master;
import com.shukai.ebook.dao.OrderDao;
import com.shukai.ebook.exception.OrderException;
import com.shukai.ebook.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BookServiceImpl bookService;


    @Override
    public void createOrder(Order_Master order_master) throws OrderException {
        String order_id = KeyUtil.genUniqueKey();
        double amount = 0;
        for (Order_Detail order_detail : order_master.getOrderDetailList()) {
            if (order_detail == null) {
                throw new OrderException("商品不存在!");
            } else {
                amount += order_detail.getBook_price() * order_detail.getBook_quantity();
                order_detail.setDetail_id(KeyUtil.genUniqueKey());
                order_detail.setOrder_id(order_id);
                orderDao.createOrderDetail(order_detail);
            }
        }
        order_master.setOrder_id(order_id);
        order_master.setOrder_amount(amount);
        orderDao.createOrder(order_master);
        //扣库存
        bookService.decreaseStock(order_master);
    }

    @Override
    public Order_Master findOneOrder(String order_id) throws OrderException {
        Order_Master order_master=orderDao.findOneOrder(order_id);
        if(order_master==null){
            throw new OrderException("订单不存在！");
        }else{
            return order_master;
        }
    }

    @Override
    public List<Order_Master> findAllOrder(String buyerOpenid) throws OrderException {
        List<Order_Master> list=orderDao.findAllOrder(buyerOpenid);
        if(list==null){
            throw new OrderException("订单列表为空!");
        }else {
            return list;
        }
    }

    @Override
    public List<Order_Master> findAllUserOrder() {
        return orderDao.findAllUserOrder();
    }

    @Override
    public List<Order_Master> searchOrder(String name) {
        return orderDao.searchOrder(name);
    }

    @Override
    public Order_Master cancle(Order_Master order_master) throws OrderException {
        if(order_master.getOrder_status()!=0){
            throw new OrderException("订单状态不正确！");
        }else{
            order_master.setOrder_status(2);
            orderDao.updateOrderStatus(order_master);
        }
        bookService.increaseStock(order_master);
        Order_Master master=new Order_Master();
        master=order_master;
        return master;
    }

    @Override
    public Order_Master finish(Order_Master order_master) throws OrderException {
        if(order_master.getOrder_status()!=0){
            throw new OrderException("订单状态不正确！");
        }else{
            order_master.setOrder_status(1);
            orderDao.updateOrderStatus(order_master);
        }
        Order_Master master=new Order_Master();
        master=order_master;
        return master;
    }

    @Override
    public Order_Master paid(Order_Master order_master) throws OrderException {
        if(order_master.getOrder_status()!=0){
            throw new OrderException("订单状态不正确!");
        }
        if(order_master.getPay_status()!=0){
            throw new OrderException("订单支付状态不正确!");
        }
        order_master.setPay_status(1);
        orderDao.updateOrderStatus(order_master);
        Order_Master master=new Order_Master();
        master=order_master;
        return master;
    }
}
