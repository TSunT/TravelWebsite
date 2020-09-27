package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoimpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class UserServiceimpl implements UserService {
    @Override
    public Boolean checkUsername(String username) {
        UserDao userDao = new UserDaoimpl();
        User user = userDao.findbyUsername(username);
        return user != null;
    }

    @Override
    public Boolean registUser(Map userMap) {
        UserDao userDao = new UserDaoimpl();
        Boolean res = false;
        User user = new User();
        //生成验证码;利用uuidutil来生成唯一的邮件激活码
        user.setCode(UuidUtil.getUuid());
        try {
            BeanUtils.populate(user,userMap);
            res = userDao.addUser(user);
            if (res) {
                String content = "<a herf='http://localhost:80/travel/user/active?activecode=" + user.getCode() + "&username="+user.getUsername()+"'>点击激活<黑马旅游网></a>";
                MailUtils.sendMail(user.getEmail(), content, "黑马旅游网用户激活");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Boolean updateUser(Map userMap) {
        return null;
    }

    @Override
    public String login(String username, String password) {
        UserDao userDao = new UserDaoimpl();
        User user = null;
        user=userDao.findUserforlogin(username, password);
        if (user!=null){
            return username;
        }else {
            return null;
        }

    }

    @Override
    public Boolean logout(String username) {
        return null;
    }

    @Override
    public Boolean activeUser(String username, String activecode) {
        UserDao userDao= new UserDaoimpl();
        String activeCodeServer = userDao.queryActiveCodeUser(username);
        boolean isCheangedStatus = false;
        if (activecode.equals(activeCodeServer)){
            isCheangedStatus = userDao.updateUserActiveStatus(username);
        }
        return isCheangedStatus;
    }
}
