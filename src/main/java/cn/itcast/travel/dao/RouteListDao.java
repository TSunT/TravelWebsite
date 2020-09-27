package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteListDao {

    /**
     * 查询总记录数
     * @param cid
     * @return
     */
    abstract public Integer queryTotalElembyCid(int cid);

    /**
     * 查询当前页的记录数据
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    abstract public List<Route> queryListforRoute(int cid , int start, int pageSize);
}
