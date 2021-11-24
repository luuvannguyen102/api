/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spring.boot.book.web.entities;

import com.example.spring.boot.book.web.enums.Commons;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "book")
public class BookEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String author;
    private String isbn;

    @Column(name = "number_of_page")
    private int numberOfPage;

    private double price;

    private String sku;

    private int quantity;

    @Temporal(TemporalType.DATE)
    @Column(name = "publish_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    
    @Size(max = 1000)
    private String description;
   
    @Column(name = "delete_flag")
    private boolean flag;

    @Enumerated(EnumType.STRING)
    private Commons status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<OrdersDetailEntity> ordersDetails;

    public BookEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public Commons getStatus() {
        return status;
    }

    public void setStatus(Commons status) {
        this.status = status;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Set<OrdersDetailEntity> getOrdersDetails() {
        return ordersDetails;
    }

    public void setOrdersDetails(Set<OrdersDetailEntity> ordersDetails) {
        this.ordersDetails = ordersDetails;
    }
    
    

    public String autoCreateSku(CategoryEntity category) {
        String sku = "" + category.getName().charAt(0) + category.getId();
        if (sku.length() == 2) {
            sku = sku + randomAlphaNumeric(3);
        } else if (sku.length() == 3) {
            sku = sku + randomAlphaNumeric(2);
        } else {
            sku = "";
        }

        return sku;
    }

    public String randomAlphaNumeric(int numberOfCharactor) {
        String digits = "0123456789"; // 0-9
        String ALPHA_NUMERIC = digits;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    public int randomNumber(int min, int max) {
        Random generator = new Random();
        return generator.nextInt((max - min) + 1) + min;
    }

    @Override
    public String toString() {
        return "BookEntity{" + "id=" + id + ", name=" + name + ", author=" + author + '}';
    }

}
