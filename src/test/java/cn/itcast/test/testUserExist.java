package cn.itcast.test;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceimpl;
import org.junit.Assert;
import org.junit.Test;

public class testUserExist {
    @Test
    public void testExist(){
        UserService userService = new UserServiceimpl();
        Boolean zhangsan = userService.checkUsername("zhangsan");
        System.out.println(zhangsan);
        Assert.assertEquals(true,zhangsan);
    }
}
