package com.example.demo.Html.Test;/*
package com.example.demo.Html;




import com.example.demo.Html.model.Item;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.text.html.parser.Entity;
import java.util.List;

*/
/**
 * @author ：Q
 * @date ：Created in 2019/4/9 0:18
 * @description：${description}
 *//*

@RestController
public class Test {
    @org.junit.Test
    public void limit()throws Exception{
         int currentPage = 1;
         int pageSize = 10;
        EntityManager entityManager = JPAUtils.getEntityManager();
        String jpql = "select o from Item o";
        Query query = entityManager.createQuery(jpql);
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<Item> list = query.getResultList();
        for(Item item : list){
            System.out.println(item);
        }
    }
}
*/
