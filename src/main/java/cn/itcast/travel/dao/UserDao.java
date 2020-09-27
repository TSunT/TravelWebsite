package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 按照用户名查询一个用户
     * @param username
     * @return
     */
    abstract public User findbyUsername(String username);

    /**
     * 添加一个用户
     * @param user
     * @return
     */
    abstract  public Boolean addUser(User user);

    /**
     * 查询用户激活码
     * @param username
     * @return
     */
    abstract public String queryActiveCodeUser(String username);

    /**
     * 跟新用户激活信息
     * @param username
     * @return
     */
    abstract public boolean updateUserActiveStatus(String username);

    /**
     * 登录功能的用户查询
     * @param username
     * @param pwd
     * @return
     */
    abstract public User findUserforlogin(String username,String pwd);
}
