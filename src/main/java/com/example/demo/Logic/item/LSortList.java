package com.example.demo.Logic.item;

import com.example.demo.db.DbManage;
import com.example.demo.entity.item.ItemSort;
import com.example.demo.entity.item.SortList;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LSortList {
    //物品
    public Map<String,Object> Lsortlist() {

        /*      *//*  三级json
        Contact contact = new Contact();
        contact.phone = "1234567989";
        contact.email = "12456@qq.com";*//*

        children children = new children();
        //   children.contact = contact;
        children.item_id = 1;
        children.child_id = 1;
        children.name = "洁面皂1";
        children.image = "http://mz.djmall.xmisp.cn/files/logo/20161208/148117972563.jpg";

        children children2 = new children();
        //   children.contact = contact;
        children2.item_id = 2;
        children2.child_id = 2;
        children2.name = "洁面皂2";
        children2.image = "http://mz.djmall.xmisp.cn/files/logo/20161208/148117972563.jpg";

        Group group = new Group();
        group.sort_id = 1;
        group.sort_name = "护肤1";
        group.ishaveChild = true;
        group.children.add(children);
        //group.children.add(children2);

        Group group2 = new Group();
        group2.sort_id = 2;
        group2.sort_name = "护肤2";
        group2.ishaveChild = true;
        group2.children.add(children);
        //group2.children.add(children2);*/

        String sqlTxt = "select * from sort_page";
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        List<SortList.Sort_page> sort_pages = new ArrayList<SortList.Sort_page>();
        int time = 1;
        int Flag = 1;
        for (Map<String, Object> map1 : list) {
            SortList.Sort_page sort_page = new SortList.Sort_page();
            // sort_page1.children1.add(this.getSonSort(time));
            sort_page.setSort_id(Integer.parseInt(map1.get("sort_id").toString()));
            sort_page.setSort_name(map1.get("sort_name").toString());
            sort_page.setIshaveChild(Integer.parseInt(map1.get("ishaveChild").toString()));
            int flag = Integer.parseInt(map1.get("sort_id").toString());
            if(Flag != flag) {
                continue;
            }else{
                sort_page.setChildren(this.getSonSort(time));

                if(StringUtils.isNotBlank(ObjectUtils.toString(sort_page.getChildren()))) {
                    System.out.println("bbbbbbbbbbb"  + sort_page.getChildren());
//                    sort_page.setIshaveChild(0);
                }
                Flag++;
            }
            sort_pages.add(sort_page);
            time++;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", sort_pages);

        return map;
    }

    private Map<String,Object> getSonSort(int time) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> lists = new ArrayList<>();

        String sqlTxt2 = "select item_id,item_name,item_pic_url from vx_homepage_more where item_sort = '" + time + "';";
        List<Map<String, Object>> list5 = jdbcTemplate.queryForList(sqlTxt2);
        List<SortList.Item> items = new ArrayList<SortList.Item>();
        for (Map<String, Object> map1 : list5) {
            SortList.Item item = new SortList.Item();
            item.setItem_id(Integer.parseInt(map1.get("item_id").toString()));
            item.setItem_name(map1.get("item_name").toString());
            item.setItem_pic_url(map1.get("item_pic_url").toString());
            items.add(item);
            // lists.add(items);
            map.put("aaa",items);
        }

        return map;
      /*  Map<String, List<Group>> children = new HashMap<String, List<Group>>();
        List<Group> groups = new ArrayList<Group>();
        groups.add(group);
        groups.add(group2);
        children.put("AllItems", (List<Group>) groups);*/
    }

    public Map<String, Object> showhomepage() {
        Map<String, Object> map = new HashMap<String, Object>();
        String sqlTxt = "SELECT item_name,item_id,item_pic_url,item_price,item_unit_price from vx_homepage_more where item_display_home_page = 1";
        List<ItemSort.homepage> itemSorts = new ArrayList<>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DbManage db = new DbManage();
        jdbcTemplate.setDataSource(db.getDataSource());
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlTxt);
        for(Map<String, Object> map1 : list){
            ItemSort.homepage ItemSort = new ItemSort.homepage();
            ItemSort.setId(map1.get("item_id").toString());
            ItemSort.setName(map1.get("item_name").toString());
            ItemSort.setPic_url(map1.get("item_pic_url").toString());
            ItemSort.setPrice(map1.get("item_price").toString());
            ItemSort.setItem_unit_price(map1.get("item_unit_price").toString());
            itemSorts.add(ItemSort);
        }
        map.put("list", itemSorts);
        return map;
    }
}
