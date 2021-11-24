/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.controller;

import com.example.spring.boot.book.web.entities.BookEntity;
import com.example.spring.boot.book.web.entities.CategoryEntity;
import com.example.spring.boot.book.web.model.Book;
import com.example.spring.boot.book.web.model.Category;
import com.example.spring.boot.book.web.model.PageResponse;
import com.example.spring.boot.book.web.service.BookService;
import com.example.spring.boot.book.web.service.CategoryService;
import com.example.spring.boot.book.web.utils.ConvertUtils;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ASUS
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String home(Model model,
            @RequestParam(name = "mesType",
                    required = false) String mesType,
            @RequestParam(name = "mes",
                    required = false) String mes) {
        List<BookEntity> books = bookService.getBooks(true);
        model.addAttribute("books", books);
        model.addAttribute("mes", mes);
        model.addAttribute("mesType", mesType);
        return "home";
    }
    
    @RequestMapping(value = "/book")
    public String getBooks(Model model) {
        Page<BookEntity> book = bookService.getBooksByPage(true, PageRequest.of(0, 5));
       PageResponse<Book> res = ConvertUtils.convertPageBookFromListPageBookEntity(book);
        model.addAttribute("books", res.getObjectPage());
       
        return "newjsp";
    }
//    @RequestMapping(value = "/books/{id}")
//    public ResponseEntity<?> updateBook(
//            @PathVariable("id") int id,
//            @RequestBody Book book) {
//        BookEntity bookEntity = bookService.findBookById(id);
//        if (book != null && book.getId() > 0) {
//            CategoryEntity categoryEntity = categoryService.findCategoryById(book.getCategory_id());
//            if (categoryEntity != null && categoryEntity.getId() > 0) {
//                book.setCreateDate(new Date());
//                book.setId(id);
//                BookEntity bookEntity1 = ConvertUtils.converBookEntityFromBook(book);
//                bookEntity1.setCategory(categoryEntity);
//                try {
//                    bookService.save(bookEntity);
//                    return new ResponseEntity(HttpStatus.CREATED);
//                } catch (Exception e) {
//                    System.out.println(e);
//
//                }
//                return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
//
//            } else {
//                PageResponse pageResponse = new PageResponse();
//                return new ResponseEntity<PageResponse<Category>>(pageResponse, HttpStatus.NOT_FOUND);
//            }
//
//        } else {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//    }
//    @RequestMapping(value = "delete-book/{id}")
//    public String delete(@PathVariable("id") int id) {
//        BookEntity book = bookService.findBookById(id);
//             bookService.deleteBook(book); 
//        return "redirect:/home";
//    }

}
