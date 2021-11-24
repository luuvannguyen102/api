/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.service;

import com.example.spring.boot.book.web.entities.BookEntity;
import com.example.spring.boot.book.web.repository.BookRepository;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author ASUS
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookEntity> getBooks(boolean flag) {
        return (List<BookEntity>) bookRepository.getBooks(flag);
    }

    public Page<BookEntity> getBooksByPage(boolean flag, Pageable pageable) {
        return bookRepository.getBooksByPage(flag, pageable);
    }

    public void save(BookEntity bookEntity) {
        bookRepository.save(bookEntity);
    }

    public void save(List<BookEntity> bookEntity) {
        bookRepository.saveAll(bookEntity);
    }

    public BookEntity findBookById(int id, boolean flag) {
        return bookRepository.findBookById(id, flag);
    }

    public List<BookEntity> findBookByAuthor(String author) {
        return bookRepository.findBookByAuthor(author);
    }

    public boolean checkExist(String author, String name) {
        List<BookEntity> books = this.findBookByAuthor(author);
        boolean check = false;
        for (BookEntity bookEntity : books) {
            if (bookEntity.getName().equals(name)) {
                check = true;
            }
        }
        return check;
    }

    public boolean deleteBook(BookEntity book) {
        book.setFlag(false);
        book.getCategory().setBooks(null);
        bookRepository.save(book);
        return book.isFlag();
    }

    public void delete(BookEntity book) {
        bookRepository.delete(book);
    }

    public Page<BookEntity> searchBooks(String str, boolean flag, Pageable pageable) {
        return bookRepository.searchBooks(str, flag, pageable);
    }

    public BookEntity checkSku(String sku, boolean flag) {
        return bookRepository.checkSku(sku, flag);
    }
    
    public Set<String> getListSku (boolean flag) {
        return bookRepository.getListSku(flag);
    }
}
