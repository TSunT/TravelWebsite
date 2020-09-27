package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteListDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoutDaoimpl implements RouteListDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Integer queryTotalElembyCid(int cid) {
        String sql = "select count(rid) totalpage from tab_route where cid = ?";
        Integer query = template.queryForObject(sql, Integer.class, cid);
        return query;
    }

    @Override
    public List<Route> queryListforRoute(int cid, int start, int pageSize) {
        String sql = "select * from tab_route where cid = ? limit ? , ?";
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<>(Route.class), cid, start, pageSize);
        return query;
    }
}
