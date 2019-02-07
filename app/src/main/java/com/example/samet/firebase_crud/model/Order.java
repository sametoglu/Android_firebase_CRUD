package com.example.samet.firebase_crud.model;

/**
 * Created by samet on 22.12.2017.
 */

public class Order {

    private String orderId;
    private String orderName;
    private int orderPrice;

    public Order(){


    }

    public Order(String orderId, String orderName, int orderPrice) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderPrice = orderPrice;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

}
