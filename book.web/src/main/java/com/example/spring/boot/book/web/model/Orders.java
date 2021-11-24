/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.model;

import com.example.spring.boot.book.web.entities.OrdersDetailEntity;
import com.example.spring.boot.book.web.enums.OrderStatus;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author Admin
 */
public class Orders implements Serializable{

    private int id;
    private String code;
    private String note;
    private int discount;
    private OrderStatus orderStatus;
    private Date createDate;
    private boolean deleteFlag;
    private Set<OrdersDetail> ordersDetail;

    public Orders() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<OrdersDetail> getOrdersDetail() {
        return ordersDetail;
    }

    public void setOrdersDetail(Set<OrdersDetail> ordersDetail) {
        this.ordersDetail = ordersDetail;
    }

   
    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    
    
}
