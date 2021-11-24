/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.service;

import com.example.spring.boot.book.web.entities.OrdersEntity;
import com.example.spring.boot.book.web.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class OrdersService {
    
    @Autowired
    private OrdersRepository ordersRepository;
    
    public Page<OrdersEntity> getOrders (boolean flag, Pageable pageable) {
        return ordersRepository.getOrders(flag, pageable);
    }
    
    public void save(OrdersEntity orders) {
        ordersRepository.save(orders);
    }
    
    public Page<OrdersEntity> searchOrders (String str, boolean flag, Pageable pageable) {
        return ordersRepository.searchOrders(str, flag, pageable);
    }
    public OrdersEntity getOrderById (int id) {
        return ordersRepository.getOrderById(id);
    }
}
