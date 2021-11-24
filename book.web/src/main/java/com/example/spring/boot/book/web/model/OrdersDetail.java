/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.model;

import com.example.spring.boot.book.web.entities.BookEntity;
import com.example.spring.boot.book.web.entities.OrdersEntity;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class OrdersDetail implements Serializable{

    private int id;
    private int quantity;
    private double amount;
    private Book book;
    private Orders orders;

    public OrdersDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    
}
