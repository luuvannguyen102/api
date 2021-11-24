/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.model;

import java.util.List;

/**
 *
 * @author Admin
 */
public class AddResponse<T, V> {
    private List<T> list;
    private V objectResponse;
    private int status;
    
    public AddResponse() {
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public V getObjectResponse() {
        return objectResponse;
    }

    public void setObjectResponse(V objectResponse) {
        this.objectResponse = objectResponse;
    } 

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
}
