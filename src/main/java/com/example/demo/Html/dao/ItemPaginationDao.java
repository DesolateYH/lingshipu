package com.example.demo.Html.dao;

import com.example.demo.Html.model.Item;
import com.example.demo.Html.model.ItemPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author ：Q
 * @date ：Created in 2019/4/10 23:54
 * @description：${description}
 */
public interface ItemPaginationDao extends PagingAndSortingRepository<ItemPagination, Integer> {
    ItemPagination findByItemId(String itemId);
}
