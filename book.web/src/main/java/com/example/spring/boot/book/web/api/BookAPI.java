/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.api;

import com.example.spring.boot.book.web.entities.BookEntity;
import com.example.spring.boot.book.web.entities.CategoryEntity;
import com.example.spring.boot.book.web.model.AddResponse;
import com.example.spring.boot.book.web.model.Book;
import com.example.spring.boot.book.web.model.Category;
import com.example.spring.boot.book.web.model.PageResponse;
import com.example.spring.boot.book.web.repository.BookRepository;
import com.example.spring.boot.book.web.service.BookService;
import com.example.spring.boot.book.web.service.CategoryService;
import com.example.spring.boot.book.web.utils.ConvertUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * @author ASUS
 */
@RestController
@RequestMapping("/api")
public class BookAPI {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/books")
    public ResponseEntity<PageResponse<Book>> getBooks() {
        Page<BookEntity> book = bookService.getBooksByPage(true, PageRequest.of(0, 5, Sort.by("id").descending()));
        PageResponse<Book> res = ConvertUtils.convertPageBookFromListPageBookEntity(book);
        if (res != null && res.getTotalElement() > 0) {
            res.setStatus(1);
        } else {
            res.setStatus(0);
        }
        return new ResponseEntity<PageResponse<Book>>(res, HttpStatus.OK);
    }

//    @CrossOrigin(origins = "http://localhost:4200")
//    @GetMapping(value = "/book/{index}/{quantity}")
//    public ResponseEntity<Page<BookEntity>> getBooks(@PathVariable("index") int index,
//            @PathVariable("quantity") int quantity) {
//        Page<BookEntity> book = bookService.getBooksByPage( PageRequest.of(index, quantity));
//        return new ResponseEntity<Page<BookEntity>>(book, HttpStatus.OK);
//    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/book/{index}/{quantity}")
    public ResponseEntity<PageResponse<Book>> getPageBook(@PathVariable("index") int index,
            @PathVariable("quantity") int quantity) {
        Page<BookEntity> book = bookService.getBooksByPage(true, PageRequest.of(index, quantity, Sort.by("id").descending()));
        PageResponse<Book> res = ConvertUtils.convertPageBookFromListPageBookEntity(book);
        if (book != null && book.getSize() > 0) {
            res.setStatus(1);
        } else {
            res.setStatus(0);
        }
        return new ResponseEntity<PageResponse<Book>>(res, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/book/{row}")
    public ResponseEntity<?> getBooksByRow(@PathVariable("row") int row
    ) {
        Page<BookEntity> book = bookService.getBooksByPage(true, PageRequest.of(0, row, Sort.by("id").descending()));
        PageResponse<Book> res = ConvertUtils.convertPageBookFromListPageBookEntity(book);
        res.setStatus(1);
        return new ResponseEntity<PageResponse<Book>>(res, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "books/{id}")
    public ResponseEntity<AddResponse<Category, Book>> getBook(
            @PathVariable("id") int id) {
        BookEntity bookEntity = bookService.findBookById(id, true);
        AddResponse<Category, Book> addResponse = new AddResponse<>();
        if (bookEntity != null && bookEntity.getId() > 0) {
            Book book = ConvertUtils.convertBookFromBookEntity(bookEntity);
            book.setCategory_id(book.getCategory().getId());
            List<Category> category = ConvertUtils.convertListCategoryFromListCategoryEntity(categoryService.getCategorys());
            addResponse.setObjectResponse(book);
            addResponse.setList(category);
            addResponse.setStatus(1);
        } else {
            addResponse.setStatus(0);
        }
        return new ResponseEntity<AddResponse<Category, Book>>(addResponse, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "book")
    public ResponseEntity<AddResponse<Category, Book>> getCategory() {
        AddResponse<Category, Book> addResponse = new AddResponse<>();
        List<Category> category = ConvertUtils.convertListCategoryFromListCategoryEntity(categoryService.getCategorys());
        Book book = new Book();
        addResponse.setObjectResponse(book);
        addResponse.setList(category);
        return new ResponseEntity<AddResponse<Category, Book>>(addResponse, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/books")
    public ResponseEntity<?> addBook(
            @Valid @RequestBody Book book,
            UriComponentsBuilder builder) {
        CategoryEntity categoryEntity = categoryService.findCategoryById(book.getCategory_id());
        AddResponse<Category, Book> addResponse = new AddResponse<>();
        if (categoryEntity != null && categoryEntity.getId() > 0) {
            if (bookService.checkExist(book.getAuthor(), book.getName())) {
                addResponse.setStatus(2);
            } else {
                book.setCreateDate(new Date());
                book.setFlag(true);
                BookEntity bookEntity = ConvertUtils.converBookEntityFromBook(book);
                bookEntity.setSku(bookEntity.autoCreateSku(categoryEntity));
                boolean a = true;
                while (a) {
                    BookEntity entity = bookService.checkSku(bookEntity.getSku(), true);
                    if (entity != null && entity.getId() > 0) {
                        bookEntity.setSku(bookEntity.autoCreateSku(categoryEntity));
                    } else {
                        a = false;
                    }
                }
                bookEntity.setCategory(categoryEntity);
                bookService.save(bookEntity);
                addResponse.setStatus(1);
            }
            return new ResponseEntity<AddResponse<Category, Book>>(addResponse, HttpStatus.OK);
        } else {
            addResponse.setStatus(0);
            return new ResponseEntity<AddResponse<Category, Book>>(addResponse, HttpStatus.OK);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(value = "/books/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable("id") int id,
            @Valid @RequestBody Book book,
            UriComponentsBuilder builder) {
        AddResponse<Category, Book> addResponse = new AddResponse<>();
        BookEntity bookEntity = bookService.findBookById(id, true);
        if (bookEntity != null && bookEntity.getId() > 0) {
            CategoryEntity categoryEntity = categoryService.findCategoryById(book.getCategory_id());
            if (categoryEntity != null && categoryEntity.getId() > 0) {
                if (!bookEntity.getAuthor().equals(book.getAuthor()) && !bookEntity.getName().equals(book.getName())) {
                    if (bookService.checkExist(book.getAuthor(), book.getName())) {
                        addResponse.setStatus(2);
                    }
                } else {
                    book.setCreateDate(new Date());
                    book.setId(id);
                    bookEntity = ConvertUtils.converBookEntityFromBook(book);
                    bookEntity.setCategory(categoryEntity);
                    try {
                        bookService.save(bookEntity);
                        addResponse.setStatus(1);
                    } catch (Exception e) {
                        System.out.println(e);
                        addResponse.setStatus(3);
                    }
                }

            } else {
                addResponse.setStatus(0);
            }
        } else {
            addResponse.setStatus(0);
        }
        return new ResponseEntity<AddResponse<Category, Book>>(addResponse, HttpStatus.CREATED);
    }
//
//    @RequestMapping(value = "/book", method = RequestMethod.PUT)
//    public ResponseEntity<?> updateBook(
//            @RequestBody BookEntity bookEntity,
//            UriComponentsBuilder builder) {
//        bookService.save(bookEntity);
//        if (bookEntity.getId() > 0) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setLocation(
//                    builder.path("/api/book/{id}")
//                            .buildAndExpand(bookEntity.getId())
//                            .toUri()
//            );
//
//            return new ResponseEntity(headers, HttpStatus.OK);
//        } else {
//            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
//        }
//    }
//

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping(value = "/books/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") int id) {
        BookEntity book = bookService.findBookById(id, true);
        AddResponse<Category, Book> addResponse = new AddResponse<>();
        if (book != null && book.getId() > 0) {
            try {
                if (!bookService.deleteBook(book)) {
                    addResponse.setStatus(1);
                } else {
                    addResponse.setStatus(2);
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        } else {
            addResponse.setStatus(0);
        }
        return new ResponseEntity<AddResponse<Category, Book>>(addResponse, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/search-books/{str}/{index}/{count}")
    public ResponseEntity<PageResponse<Book>> getSearchBooks(@PathVariable("str") String str,
            @PathVariable("index") int index,
            @PathVariable("count") int count) {
        Page<BookEntity> book = bookService.searchBooks(str, true, PageRequest.of(index, count, Sort.by("id").descending()));
        PageResponse<Book> res = ConvertUtils.convertPageBookFromListPageBookEntity(book);
        if (res != null && res.getTotalElement() > 0) {
            res.setStatus(1);
        } else {
            res.setStatus(0);
        }
        return new ResponseEntity<PageResponse<Book>>(res, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(value = "/list-sku")
    public ResponseEntity<?> getListSku() {
        Set<String> sku = bookService.getListSku(true);
        if (sku.size() > 0) {
            return new ResponseEntity<Set<String>>(sku, HttpStatus.OK);
        } else {
            return new ResponseEntity<Set<String>>(HttpStatus.NOT_FOUND);
        }
    }

}
