package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

import java.util.Map;

public interface UserService {
    /**
     * 检查用户名是否注册
     * @param username
     * @return
     */
    abstract public Boolean checkUsername(String username);

    /**
     * 添加用户
     * @param userMap
     * @return
     */
    abstract public Boolean registUser(Map userMap);

    /**
     * 更新用户信息
     * @param userMap
     * @return
     */
    abstract public Boolean updateUser(Map userMap);

    /**
     * 登录验证
     * @param username
     * @return
     */
    abstract public String login(String username, String password);

    /**
     * 退出登录
     * @param username
     * @return
     */
    abstract public Boolean logout(String username);

    /**
     * 注册激活
     * @param username
     * @param activecode
     * @return
     */
    abstract public Boolean activeUser(String username,String activecode);
}
