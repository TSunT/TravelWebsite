package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteListDao;
import cn.itcast.travel.dao.impl.RoutDaoimpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RoutePageService;

public class RoutePageServiceimpl implements RoutePageService {
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize) {
        RouteListDao routeListDao = new RoutDaoimpl();
        PageBean<Route> routePageBean = new PageBean<Route>();
        routePageBean.setPageSize(pageSize);
        int totalcount = routeListDao.queryTotalElembyCid(cid);
        routePageBean.setTotalCount(totalcount);
        int totalpage = totalcount%pageSize==0? totalcount/pageSize: totalcount/pageSize+1;
        routePageBean.setTotalPage(totalpage);
        if (currentPage>totalpage) currentPage = 1;
        routePageBean.setCurrentPage(currentPage);
        int start = (currentPage-1)*pageSize;//计算开始查询的记录
        routePageBean.setList(routeListDao.queryListforRoute(cid,start,pageSize));
        return routePageBean;
    }
}
