/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.repository;

import com.example.spring.boot.book.web.entities.BookEntity;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ASUS
 */
@Repository
public interface BookRepository
        extends CrudRepository<BookEntity, Integer> {
    
    @Query(value = "Select b from BookEntity b where"
            + " b.flag = ?1")
   List <BookEntity> getBooks (boolean flag);
   
   @Query(value = "Select b from BookEntity b where"
           + " b.flag = ?1"
         )
   Page <BookEntity> getBooksByPage (boolean flag, Pageable pageable) ;
   
   @Query(value = "Select b from BookEntity b where "
           + " b.author = ?1")
   List<BookEntity> findBookByAuthor(String author);
   
   @Query(value = "Select b from BookEntity b where"
           + " b.id = ?1 and"
           + " b.flag = ?2")
   BookEntity findBookById(int id, boolean flag);
   
   @Query(value = "Select b from BookEntity b where"
           + " (b.name Like  %?1% or"
           + " b.author Like  %?1% or"
           + " b.sku Like  %?1%) and"
           + " b.flag = ?2")
   Page <BookEntity> searchBooks(String  str, boolean flag,Pageable pageable);
   
   @Query(value = "Select b from BookEntity b where"
           + " b.sku = ?1"
           + " and b.flag = ?2")
   BookEntity checkSku(String sku, boolean flag);
   
   @Query(value = "Select b.sku from BookEntity b"
           + " where b.flag = ?1")
   Set<String> getListSku (boolean flag);

}
