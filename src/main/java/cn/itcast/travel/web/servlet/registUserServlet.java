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

@WebServlet("/registUserServlet")
public class registUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
