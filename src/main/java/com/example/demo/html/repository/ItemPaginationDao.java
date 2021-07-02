package com.example.demo.html.repository;

import com.example.demo.html.domian.ItemPagination;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author ：Q
 * @date ：Created in 2019/4/10 23:54
 * @description：${description}
 */
public interface ItemPaginationDao extends PagingAndSortingRepository<ItemPagination, Integer> {
    ItemPagination findByItemId(String itemId);
}
