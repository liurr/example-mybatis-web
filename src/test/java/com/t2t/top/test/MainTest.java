package com.t2t.top.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ilucky.mybatis2.model.User;
import com.ilucky.mybatis2.service.UserService;
import com.ilucky.mybatis2.model.UserType;
import com.ilucky.mybatis2.util.IdUtil;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author IluckySi
 * @since 20151013
 */

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/spring/spring-core.xml"})
@ContextConfiguration(locations = {"classpath:spring/spring-core.xml"})
public class MainTest extends BaseTest {

    public static Logger logger = Logger.getLogger(MainTest.class);

    @Autowired
    public UserService userService;

    /**
     * 保存单个对象
     */
    @Test
    public void createUser() {
        try {
            User user = new User(IdUtil.getId(), "name-0", "123456", true, new Date(), new Date(), UserType.COMMON);
            userService.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存多个对象
     */
    @Test
    public void createUserList() {
        try {
            List<User> userList = new ArrayList<User>();
            for (int i = 0; i < 10; i++) {
                int number = new Random().nextInt(24 * 3);//生日跨度是72个小时~=3天.
                int number2 = new Random().nextInt(24 * 3); //创建时间跨度是72个小时~=3天.
                Date birthday = new Date(new Date().getTime() - 1000 * 60 * 60 * number);
                Date createTime = new Date(new Date().getTime() - 1000 * 60 * 60 * number2);
                User user = new User(IdUtil.getId(), "name-" + i, "password-" + i, i % 2 == 0 ? true : false, birthday, createTime, i % 2 == 0 ? UserType.COMMON : UserType.SUPER);
                userList.add(user);
            }
            userService.createUserList(userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改对象
     */
    @Test
    public void modifyUser() {
        try {
            User user = new User("06f45bbc198643a18e6b7a14ba26090f", "name-1", "123456", false, new Date(), new Date(), UserType.SUPER);
            userService.modifyUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除单个对象
     */
    @Test
    public void deleteUser() {
        userService.deleteUser("06f45bbc198643a18e6b7a14ba26090f");
    }

    /**
     * 删除多个对象
     */
    @Test
    public void deleteUserList() {
        try {
            List<String> userList = new ArrayList<String>();
            userList.add("410b7cd8b9e54c2f9fcf53a69aa67558");
            userList.add("ee1df808e62d4a2f895b77d4fdf51800");
            userService.deleteUserList(userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询单个对象
     */
    @Test
    public void getUser() {
        try {
            userService.getUser("00b5d06624cd4ba694339824c8a65179");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有对象
     */
    @Test
    public void getUserList() {
        try {
            userService.getUserList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有对象个数
     */
    @Test
    public void getUserCount() {
        try {
            userService.getUserCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户名查询对象
     */
    @Test
    public void getUserByName() {
        try {
            userService.getUserByName("name-0");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
