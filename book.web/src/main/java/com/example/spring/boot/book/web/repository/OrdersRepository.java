/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.repository;

import com.example.spring.boot.book.web.entities.OrdersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public interface OrdersRepository extends CrudRepository<OrdersEntity, Integer>{
    
    @Query(value = "Select o from OrdersEntity o where"
            + " o.deleteFlag = ?1")
    Page<OrdersEntity> getOrders(boolean flag, Pageable pageable);
    
    @Query(value = "Select o from OrdersEntity o"
            + " where o.code Like ?1%"
            + " and o.deleteFlag = ?2")
    Page<OrdersEntity> searchOrders(String str, boolean flag, Pageable pageable);
    
    @Query(value = "Select o from OrdersEntity o where "
            + " o.id = ?1")
    OrdersEntity getOrderById(int id);
}
