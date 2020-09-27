package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoimpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategroyServiceimpl implements CategoryService {
    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> resList = null;
        if (categorys == null || categorys.size()==0) {
            //如果redis的缓存为空,进入调用dao来查找数据库中类容，并写入redis的sortedset
            CategoryDao categoryDao = new CategoryDaoimpl();
            resList = categoryDao.findAll();
            for (Category c:resList) {
                jedis.zadd("category",c.getCid(),c.getCname());
            }
        }else {
            //redis中有类型数据，就将categorys中的数据写入list
            resList = new ArrayList<Category>();
            for (Tuple c:categorys) {
                Category category = new Category();
                category.setCname(c.getElement());
                category.setCid((int) c.getScore());
                resList.add(category);
            }
        }
        return resList;
    }
}
