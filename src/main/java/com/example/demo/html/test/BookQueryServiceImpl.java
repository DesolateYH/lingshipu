package com.example.demo.html.test;/*
package com.example.demo.Html.Test;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

*/
/**
 * @author ：Q
 * @date ：Created in 2019/4/10 23:34
 * @description：${description}
 *//*

@Service( value="https://my.oschina.net/wangxincj/blog/bookQueryService" )
public class BookQueryServiceImpl implements BookQueryService{
    @Resource
    BookRepository bookRepository;

    @Override
    public Page< Book > findBookNoCriteria (Integer page, Integer size ) {
        Pageable pageable=new PageRequest( page,size, Sort.Direction.ASC,"id" );
        return bookRepository.findAll ( pageable );
    }

    @Override
    public Page < Book > findBookCriteria ( Integer page,Integer size,final BookQuery bookQuery ) {
        Pageable pageable=new PageRequest ( page,size,Sort.Direction.ASC,"id" );
        Page < Book > bookPage=bookRepository.findAll ( new Specification< Book >( ){
            @Override
            public javax.persistence.criteria.Predicate toPredicate (Root< Book > root, CriteriaQuery< ? > query, CriteriaBuilder criteriaBuilder ) {
                List<Predicate> list=new ArrayList< Predicate >( );
                if (null!=bookQuery.getName ( )&&! "".equals ( bookQuery.getName ( ) )) {
                    list.add ( criteriaBuilder.equal ( root.get ( "name" ).as ( String.class ),bookQuery.getName ( ) ) );
                }
                if (null!=bookQuery.getIsbn ( )&&! "".equals ( bookQuery.getIsbn ( ) )) {
                    list.add ( criteriaBuilder.equal ( root.get ( "isbn" ).as ( String.class ),bookQuery.getIsbn ( ) ) );
                }
                if (null!=bookQuery.getAuthor ( )&&! "".equals ( bookQuery.getAuthor ( ) )) {
                    list.add ( criteriaBuilder.equal ( root.get ( "author" ).as ( String.class ),bookQuery.getAuthor ( ) ) );
                }
                Predicate[] p=new Predicate[list.size ( )];
                return criteriaBuilder.and ( list.toArray ( p ) );
            }
        },pageable );
        return bookPage;
    }
}

*/
