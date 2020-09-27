package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class UserDaoimpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User findbyUsername(String username) {
        String sql = "select * from tab_user where username = ?";
        User user = null;
        try {
            List<User> query = template.query(sql, new BeanPropertyRowMapper<User>(User.class), username);
            if (null!=query&&query.size()>0){
                user=query.get(0);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Boolean addUser(User user) {
        String sql = "insert into tab_user(username, password , name , birthday , sex , telephone , email, code ) values (?,?,?,?,?,?,?,?)";
        int check = 0;
        try {
            check = template.update(sql,
                    user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),user.getCode());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return check != 0;
    }

    @Override
    public String queryActiveCodeUser(String username) {
        String sql = "select code from tab_user where username=?";
        Map<String, Object> stringObjectMap = null;
        String code = null;
        try {
            stringObjectMap = template.queryForMap(sql, username);
            code = (String) stringObjectMap.get("code");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return code;
    }

    @Override
    public boolean updateUserActiveStatus(String username) {
        String sql = "update tab_user set status = 'Y' where username = ?";
        int update = template.update(sql, username);
        return update==1;
    }

    @Override
    public User findUserforlogin(String username, String pwd) {
        String sql = "select * from tab_user where username = ? and password = ?";
        User user = null;
        try {
            List<User> query = template.query(sql, new BeanPropertyRowMapper<User>(User.class), username,pwd);
            if (null!=query&&query.size()>0){
                user=query.get(0);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }
}
