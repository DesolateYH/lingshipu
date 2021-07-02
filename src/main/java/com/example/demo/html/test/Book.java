package com.example.demo.html.test;/*
package com.example.demo.Html.Test;



import lombok.Data;

import javax.persistence.*;

*/
/**
 * @author ：Q
 * @date ：Created in 2019/4/10 23:31
 * @description：${description}
 *//*

@Entity
@Data
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false,name = "name")
    private String name;

    @Column(nullable = false,name = "isbn")
    private String isbn;

    @Column(nullable = false,name = "author")
    private String author;

    public Book (String name,String isbn,String author){
        this.name = name;
        this.isbn = isbn;
        this.author = author;
    }
    public Book(){

    }
    //此处省去get、set方法
}



*/
