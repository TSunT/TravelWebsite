package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RoutePageService;
import cn.itcast.travel.service.impl.RoutePageServiceimpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cidstr = request.getParameter("cid");
        String currentPagestr = request.getParameter("currentPage");
        String pageSizestr = request.getParameter("pageSize");
        int cid;
        if (cidstr==null || "".equals(cidstr)){
            cid=1;
        }else {
            cid=Integer.parseInt(cidstr);
            if (cid<=0) cid=1;
        }
        int currentPage;
        if (currentPagestr==null || "".equals(currentPagestr)){
            currentPage=1;
        }else {
            currentPage=Integer.parseInt(currentPagestr);
        }
        int pageSize;
        if (pageSizestr==null||"".equals(pageSizestr)){
            pageSize=5;
        }else {
            pageSize=Integer.parseInt(pageSizestr);
        }

        RoutePageService routePageService = new RoutePageServiceimpl();
        PageBean<Route> routePageBean = routePageService.pageQuery(cid, currentPage, pageSize);

        writeValue(routePageBean,request,response);
    }

}
