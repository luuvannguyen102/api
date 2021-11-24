/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.api;

import com.example.spring.boot.book.web.entities.BookEntity;
import com.example.spring.boot.book.web.entities.OrdersDetailEntity;
import com.example.spring.boot.book.web.entities.OrdersEntity;
import com.example.spring.boot.book.web.model.AddResponse;
import com.example.spring.boot.book.web.model.Book;
import com.example.spring.boot.book.web.model.Category;
import com.example.spring.boot.book.web.model.Orders;
import com.example.spring.boot.book.web.model.OrdersDetail;
import com.example.spring.boot.book.web.model.PageResponse;
import com.example.spring.boot.book.web.service.BookService;
import com.example.spring.boot.book.web.service.OrdersService;
import com.example.spring.boot.book.web.utils.ConvertUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api")
public class OrdersAPI {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private BookService bookService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/orders")
    public ResponseEntity<PageResponse<Orders>> getOrders() {
        Page<OrdersEntity> page = ordersService.getOrders(true, PageRequest.of(0, 5, Sort.by("createDate").descending()));
        PageResponse<Orders> response = ConvertUtils.converPageOrdersFromPageOrdersEntity(page);
        if (response != null && response.getTotalElement() > 0) {
            response.setStatus(1);
        } else {
            response.setStatus(0);
        }
        return new ResponseEntity<PageResponse<Orders>>(response, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrder(@PathVariable("id") int id) {
        OrdersEntity ordersEntity = ordersService.getOrderById(id);
        AddResponse<String, Orders> response = new AddResponse<>();
        if (ordersEntity.getId() < 0) {
            response.setStatus(0);
        } else {
            response.setObjectResponse(ConvertUtils.convertOrdersFromOrdersEntity(ordersEntity));
            response.setStatus(1);
        }
        return new ResponseEntity<AddResponse<String, Orders>>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/orders/{indexPage}/{row}")
    public ResponseEntity<PageResponse<Orders>> getPageOrders(@PathVariable("indexPage") int indexPage,
            @PathVariable("row") int row) {
        Page<OrdersEntity> page = ordersService.getOrders(true, PageRequest.of(indexPage, row, Sort.by("createDate").descending()));
        PageResponse<Orders> response = ConvertUtils.converPageOrdersFromPageOrdersEntity(page);
        if (response != null && response.getTotalElement() > 0) {
            response.setStatus(1);
        } else {
            response.setStatus(0);
        }
        return new ResponseEntity<PageResponse<Orders>>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/ordersByRow/{row}")
    public ResponseEntity<?> getOrdersByRow(@PathVariable("row") int row) {
        Page<OrdersEntity> page = ordersService.getOrders(true, PageRequest.of(0, row, Sort.by("createDate").descending()));
        PageResponse<Orders> response = ConvertUtils.converPageOrdersFromPageOrdersEntity(page);
        if (response != null && response.getTotalElement() > 0) {
            response.setStatus(1);
        } else {
            response.setStatus(0);
        }
        return new ResponseEntity<PageResponse<Orders>>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/checkSku/{sku}/{quantity}")
    public ResponseEntity<?> createOrders(@RequestBody List<OrdersDetail> ordersDetail,
            @PathVariable("sku") String sku,
            @PathVariable("quantity") int quantity
    ) {
        BookEntity bookEntity = bookService.checkSku(sku, true);
        AddResponse<OrdersDetail, Orders> res = new AddResponse<>();
        if (bookEntity != null && bookEntity.getId() > 0) {
            if (quantity > bookEntity.getQuantity()) {
                res.setStatus(3);
            } else {
                if (ordersDetail.size() > 0) {
                    for (OrdersDetail detail : ordersDetail) {
                        if (bookEntity.getSku().equals(detail.getBook().getSku())) {
                            detail.setQuantity(quantity + detail.getQuantity());
                            detail.setAmount(detail.getQuantity() * bookEntity.getPrice());

//                        Book book = ConvertUtils.convertBookFromBookEntity(bookEntity);
//                        detail.setBook(book);
                            bookEntity.setQuantity(bookEntity.getQuantity() - quantity);
                            bookService.save(bookEntity);
                            res.setList(ordersDetail);
                            res.setStatus(1);
                            return new ResponseEntity<AddResponse<OrdersDetail, Orders>>(res, HttpStatus.OK);
                        }
                    }

                    OrdersDetail detail = new OrdersDetail();
                    detail.setQuantity(quantity);
                    detail.setAmount(detail.getQuantity() * bookEntity.getPrice());
                    Book book = ConvertUtils.convertBookFromBookEntity(bookEntity);
                    detail.setBook(book);

                    ordersDetail.add(detail);

                    bookEntity.setQuantity(bookEntity.getQuantity() - quantity);
                    bookService.save(bookEntity);
                } else {
                    ordersDetail = new ArrayList<>();
                    OrdersDetail detail = new OrdersDetail();
                    detail.setQuantity(quantity);
                    detail.setAmount(detail.getQuantity() * bookEntity.getPrice());
                    Book book = ConvertUtils.convertBookFromBookEntity(bookEntity);
                    detail.setBook(book);
                    ordersDetail.add(detail);

                    bookEntity.setQuantity(bookEntity.getQuantity() - quantity);
                    try {
                        bookService.save(bookEntity);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
                res.setList(ordersDetail);
                res.setStatus(1);
            }
        } else {
            res.setStatus(2);
        }
        return new ResponseEntity<AddResponse<OrdersDetail, Orders>>(res, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "search/{str}")
    public ResponseEntity<?> search(@PathVariable("str") String str) {
        Page<OrdersEntity> orders = ordersService.searchOrders(str, true, PageRequest.of(0, 5, Sort.by("createDate").descending()));
        PageResponse<Orders> res = ConvertUtils.converPageOrdersFromPageOrdersEntity(orders);
        if (res != null && res.getTotalElement() > 0) {
            res.setStatus(1);
        } else {
            res.setStatus(2);
        }
        return new ResponseEntity<PageResponse<Orders>>(res, HttpStatus.OK);
    }
}
