/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.utils;

import com.example.spring.boot.book.web.entities.BookEntity;
import com.example.spring.boot.book.web.entities.CategoryEntity;
import com.example.spring.boot.book.web.entities.OrdersDetailEntity;
import com.example.spring.boot.book.web.entities.OrdersEntity;
import com.example.spring.boot.book.web.model.Book;
import com.example.spring.boot.book.web.model.Category;
import com.example.spring.boot.book.web.model.Orders;
import com.example.spring.boot.book.web.model.OrdersDetail;
import com.example.spring.boot.book.web.model.PageResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;

/**
 *
 * @author ASUS
 */
public class ConvertUtils {

//    book
    public static Book convertBookFromBookEntity(BookEntity bookEntity) {
        Book book = new Book();
        book.setId(bookEntity.getId());
        book.setName(bookEntity.getName());
        book.setAuthor(bookEntity.getAuthor());
        book.setDescription(bookEntity.getDescription());
        book.setIsbn(bookEntity.getIsbn());
        book.setNumberOfPage(bookEntity.getNumberOfPage());
        book.setPrice(bookEntity.getPrice());
        book.setPublishDate(bookEntity.getPublishDate());
        book.setCreateDate(bookEntity.getCreateDate());
        book.setQuantity(bookEntity.getQuantity());
        book.setFlag(bookEntity.isFlag());
        book.setStatus(bookEntity.getStatus());
        book.setSku(bookEntity.getSku());

        Category category = new Category();
        category.setId(bookEntity.getCategory().getId());
        category.setName(bookEntity.getCategory().getName());

        OrdersDetail ordersDetailEntity = new OrdersDetail();
        Set<OrdersDetail> ordersDetails = new HashSet<>();
        for (OrdersDetailEntity entity : bookEntity.getOrdersDetails()) {
            OrdersDetail ordersDetail = new OrdersDetail();
            ordersDetail.setId(entity.getId());
            ordersDetail.setQuantity(entity.getQuantity());
            ordersDetail.setAmount(entity.getAmount());
            
            Orders orders = convertOrdersFromOrdersEntity(entity.getOrders());
            ordersDetail.setOrders(orders);
            ordersDetails.add(ordersDetail);
        }
        book.setCategory(category);
        book.setOrdersDetails(ordersDetails);
        return book;
    }

    public static BookEntity converBookEntityFromBook(Book book) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(book.getId());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setCreateDate(book.getCreateDate());
        bookEntity.setDescription(book.getDescription());
        bookEntity.setFlag(book.isFlag());
        bookEntity.setIsbn(book.getIsbn());
        bookEntity.setNumberOfPage(book.getNumberOfPage());
        bookEntity.setPrice(book.getPrice());
        bookEntity.setPublishDate(book.getPublishDate());
        bookEntity.setSku(book.getSku());
        bookEntity.setStatus(book.getStatus());
        bookEntity.setName(book.getName());
        bookEntity.setQuantity(book.getQuantity());

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(book.getCategory().getId());
        categoryEntity.setName(book.getCategory().getName());

        bookEntity.setCategory(categoryEntity);

        return bookEntity;
    }

    public static List<Book> convertListBookFromListBookEntity(List<BookEntity> book) {
        List<Book> books = new ArrayList<>();
        for (BookEntity bookEntity : book) {
            books.add(
                    ConvertUtils.convertBookFromBookEntity(bookEntity));
        }
        return books;
    }

    public static PageResponse<Book> convertPageBookFromListPageBookEntity(Page<BookEntity> bookEntitys) {
        List<BookEntity> bookEntitys1 = new ArrayList<>();
        for (BookEntity b : bookEntitys) {
            bookEntitys1.add(b);
        }
        List<Book> books = convertListBookFromListBookEntity(bookEntitys1);
        PageResponse<Book> pageResponse = new PageResponse<>();
        pageResponse.setObjectPage(books);
        pageResponse.setTotalPage(bookEntitys.getTotalPages());
        pageResponse.setTotalElement(bookEntitys.getTotalElements());
        pageResponse.setNumberOfElement(bookEntitys.getNumberOfElements());
        return pageResponse;
    }

    public static Category convertCategoryFromCategoryEntity(CategoryEntity categoryEntity) {
        Category category = new Category();
        category.setId(categoryEntity.getId());
        category.setName(categoryEntity.getName());
        return category;
    }

    public static List<Category> convertListCategoryFromListCategoryEntity(List<CategoryEntity> categorys) {
        List<Category> list = new ArrayList<>();
        for (CategoryEntity categoryEntity : categorys) {
            list.add(convertCategoryFromCategoryEntity(categoryEntity));
        }
        return list;
    }

    public static OrdersDetail convertOrdersDetailFromOrdersDetailEntity(OrdersDetailEntity detail) {
        OrdersDetail ordersDetail = new OrdersDetail();
        ordersDetail.setId(detail.getId());
        ordersDetail.setQuantity(detail.getQuantity());
        ordersDetail.setAmount(detail.getAmount());
        
        Book book = convertBookFromBookEntity(detail.getBook());
        ordersDetail.setBook(book);
        
        Orders orders = convertOrdersFromOrdersEntity(detail.getOrders());
        ordersDetail.setOrders(orders);
        return ordersDetail;
    }

    public static Set<OrdersDetail> convertListOrdersDetailFromListOrdersDetailEntity(Set<OrdersDetailEntity> detail) {
        Set<OrdersDetail> detailEntitys = new HashSet<>();
        for (OrdersDetailEntity detailEntity : detail) {
            detailEntitys.add(convertOrdersDetailFromOrdersDetailEntity(detailEntity));
        }
        return detailEntitys;
    }

//    Orders
    public static Orders convertOrdersFromOrdersEntity(OrdersEntity orders) {
        Orders orders1 = new Orders();
        orders1.setId(orders.getId());
        orders1.setCreateDate(orders.getCreateDate());
        orders1.setNote(orders.getNote());
        orders1.setDiscount(orders.getDiscount());
        orders1.setCode(orders.getCode());
        orders1.setOrderStatus(orders.getOrderStatus());
        orders1.setDeleteFlag(orders.isDeleteFlag());
//        Set<OrdersDetail> detail = convertListOrdersDetailFromListOrdersDetailEntity(orders.getOrdersDetail());
//
//        orders1.setOrdersDetail(detail);
        return orders1;
    }

    public static List<Orders> convertListOrdersFromListOrdersEntity(List<OrdersEntity> orders) {
        List<Orders> list = new ArrayList<>();
        for (OrdersEntity ordersEntity : orders) {
            list.add(convertOrdersFromOrdersEntity(ordersEntity));
        }
        return list;
    }

    public static PageResponse<Orders> converPageOrdersFromPageOrdersEntity(Page<OrdersEntity> orders) {
        List<OrdersEntity> list = new ArrayList<>();
        PageResponse<Orders> pageResponse = new PageResponse<>();
        for (OrdersEntity ordersEntity : orders) {
            list.add(ordersEntity);
        }

        List<Orders> orderses = convertListOrdersFromListOrdersEntity(list);
        pageResponse.setNumberOfElement(orders.getNumberOfElements());
        pageResponse.setObjectPage(orderses);
        pageResponse.setTotalElement(orders.getTotalElements());
        pageResponse.setTotalPage(orders.getTotalPages());
        return pageResponse;
    }

    public static OrdersEntity convertOrdersEntityFromOrders(Orders orders) {
        OrdersEntity ordersEntity = new OrdersEntity();
        ordersEntity.setId(orders.getId());
        ordersEntity.setCode(orders.getCode());
        ordersEntity.setCreateDate(orders.getCreateDate());
        ordersEntity.setDeleteFlag(orders.isDeleteFlag());
        ordersEntity.setDiscount(orders.getDiscount());
        ordersEntity.setOrderStatus(orders.getOrderStatus());
        
        if(orders.getOrdersDetail().size() > 0) {
              ordersEntity.setOrdersDetail(convertListOrdersDetailEntityFromListOrdersDetail(orders.getOrdersDetail()));
        }
      
        return ordersEntity;
    }

    public static List<OrdersEntity> convertListOrdersEntityFromListOrders(List<Orders> orders) {
        List<OrdersEntity> ordersEntitys = new ArrayList<>();
        for (Orders order : orders) {
            ordersEntitys.add(convertOrdersEntityFromOrders(order));
        }
        return ordersEntitys;
    }

    public static OrdersDetailEntity convertOrdersDetailEntityFromOrdersDetail(OrdersDetail detail) {
        OrdersDetailEntity detailEntity = new OrdersDetailEntity();
        detailEntity.setId(detail.getId());
        detailEntity.setAmount(detail.getAmount());
        detailEntity.setQuantity(detail.getQuantity());
        
        OrdersEntity orders = convertOrdersEntityFromOrders(detail.getOrders());
        detailEntity.setOrders(orders);
        
        BookEntity book = converBookEntityFromBook(detail.getBook());
        detailEntity.setBook(book);

        return detailEntity;
    }

    public static Set<OrdersDetailEntity> convertListOrdersDetailEntityFromListOrdersDetail(Set<OrdersDetail> details) {
        Set<OrdersDetailEntity> detailEntitys = new HashSet<>();
        if(details.size() > 0) {
            for (OrdersDetail detail : details) {
            detailEntitys.add(convertOrdersDetailEntityFromOrdersDetail(detail));
        }
        }
        

        return detailEntitys;
    }
}
