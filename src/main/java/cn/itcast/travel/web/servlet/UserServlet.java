package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceimpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "/user/*")
public class UserServlet extends BaseServlet {
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserServlet--login");
        request.setCharacterEncoding("utf-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username!=null&&password!=null) {
            UserService userService = new UserServiceimpl();
            String login = userService.login(username,password);
            ResultInfo resultInfo = new ResultInfo();
            if (login!=null){
                resultInfo.setFlag(true);
                request.getSession().setAttribute("user",login);
            }else {
                resultInfo.setFlag(false);
                resultInfo.setMsg("登录失败！");
            }
            response.setContentType("application/json;charset=utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getOutputStream(),resultInfo);
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();//销毁session
        response.sendRedirect(request.getContextPath()+"/login.html");//跳转至登录界面
    }

    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        response.setHeader("content-type","application/json");
        response.setHeader("charset","utf-8");
        ResultInfo resultInfo = new ResultInfo(true);
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        String[] checks = requestParameterMap.get("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        if(checkcode_server.toLowerCase().equals(checks[0].toLowerCase())){
            UserService userService = new UserServiceimpl();
            Boolean res = userService.registUser(requestParameterMap);
            if (res){
                resultInfo.setFlag(true);
            }else {
                resultInfo.setFlag(false);
                resultInfo.setMsg("注册失败！");
            }
        }else {
            resultInfo.setFlag(false);
            resultInfo.setMsg("验证码错误！");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),resultInfo);
    }

    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        ResultInfo resultInfo = new ResultInfo();
        Object user = request.getSession().getAttribute("user");
        if (user!=null){
            resultInfo.setFlag(true);
            resultInfo.setMsg(user.toString());
        }else {
            resultInfo.setFlag(false);
        }
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),resultInfo);
    }

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        String activecode = request.getParameter("activecode");
        String username = request.getParameter("username");
        UserService userService = new UserServiceimpl();
        if(activecode!=null||activecode.length()!=0) {
            if(userService.activeUser(username, activecode)) {
                response.sendRedirect(request.getContextPath() + "/index.html");
            }else{
                System.out.println("验证失败");
            }
        }
    }

    public void check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        Boolean checkUsername = new UserServiceimpl().checkUsername(username);
        ResultInfo resultInfo = new ResultInfo();
        if (checkUsername){
            resultInfo.setFlag(true);
            resultInfo.setMsg("这个用户名已注册！");
        }else {
            resultInfo.setFlag(false);
            resultInfo.setMsg("用户名正确");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),resultInfo);
    }
}
