package com.ilucky.mybatis2.service.impl;

import com.ilucky.mybatis2.dao.UserDao;
import com.ilucky.mybatis2.model.User;
import com.ilucky.mybatis2.model.UserType;
import com.ilucky.mybatis2.service.UserService;
import com.ilucky.mybatis2.util.IdUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author IluckySi
 * @since 20151013
 */
@Service
@Transactional(rollbackFor = Exception.class)
class UserServiceImpl extends BaseServiceImpl implements UserService {

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userMapper;

    public void createUser(User user) {
        userMapper.createUser(user);
    }

    public void createUser(String name, String password, boolean sex, String birthday, String userType) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();

        Date birthdayDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
        User user = new User(IdUtil.getId(), name, password, sex, birthdayDate, new Date(), UserType.valueOf(userType));
        userMapper.createUser(user);
        result.put("result", "200");
        result.put("message", "创建成功");
    }

    public void createUserList(List<User> userList) {
        userMapper.createUserList(userList);
    }

    public void modifyUser(User user) {
        userMapper.modifyUser(user);
    }

    public void modifyUser(HttpServletResponse response, String id, String name, String password, boolean sex, String birthday, String userType) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Date birthdayDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            User user = new User(id, name, password, sex, birthdayDate, null, UserType.valueOf(userType));
            userMapper.modifyUser(user);
            result.put("result", "200");
            result.put("message", "修改成功");
        } catch (Exception e) {
            logger.error("异常" + e);
            result.put("result", "400");
            result.put("message", "修改失败");
            throw new RuntimeException("事务回滚");
        }
    }

    public void deleteUser(String user) {
        userMapper.deleteUser(user);
    }

    public void deleteUserList(List<String> userList) {
        userMapper.deleteUserList(userList);
    }

    public void deleteUser(HttpServletResponse response, String user) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            userMapper.deleteUserList(Arrays.asList(user.split(",")));
            result.put("result", "200");
            result.put("message", "删除成功");
        } catch (Exception e) {
            logger.error("异常" + e);
            result.put("result", "400");
            result.put("message", "删除失败");
            throw new RuntimeException("事务回滚");
        }
    }

    public void testTransaction() {
        User user1 = new User(IdUtil.getId(), "name-transaction1", "123456", true, new Date(), new Date(), UserType.COMMON);
        User user2 = new User(IdUtil.getId(), "name-transaction2", "123456", true, new Date(), new Date(), UserType.COMMON);
        userMapper.createUser(user1);
        int i = 1;
        logger.info("测试事务" + 100 / (i - 1));
        userMapper.createUser(user2);
    }

    public void getUser(String user) {
        User userResult = userMapper.getUser(user);
        logger.info(userResult);
    }

    public void getUserList() {
        List<User> userList = userMapper.getUserList(null);
        if (userList != null) {
            logger.info("共" + userList.size() + "条数据");
            for (int i = 0; i < userList.size(); i++) {
                logger.info(userList.get(i));
            }
        }
    }

    public void getUserByName(String name) {
        List<User> userList = userMapper.getUserByName2(name);
        if (userList != null) {
            logger.info("共" + userList.size() + "条数据");
            for (int i = 0; i < userList.size(); i++) {
                logger.info(userList.get(i));
            }
        }
    }

    public void getUserCount() {
        int count = userMapper.getUserCount();
        logger.info(count);
    }

    public void getUserListByPage() {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("first", 0);
        condition.put("count", 5);
        condition.put("userType", UserType.COMMON.name());
        int number = new Random().nextInt(24 * 3);//生日跨度是72个小时~=3天.
        Date smallBirthday = new Date(new Date().getTime() - 1000 * 60 * 60 * number);
        logger.info("smallBirthday=" + smallBirthday);
        condition.put("smallBirthday", smallBirthday);
        condition.put("bigBirthday", new Date());
        List<User> userList = userMapper.getUserList(condition);
        if (userList != null) {
            logger.info("共" + userList.size() + "条数据");
            for (int i = 0; i < userList.size(); i++) {
                logger.info(userList.get(i));
            }
        }
    }

    public void getUserListByPage(HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("first", 0);
            condition.put("count", 5);
            List<User> userList = userMapper.getUserList(condition);
            if (userList != null) {
                logger.info("共" + userList.size() + "条数据");
                for (int i = 0; i < userList.size(); i++) {
                    logger.info(userList.get(i));
                }
            }
            result.put("result", "200");
            result.put("message", userList);
        } catch (Exception e) {
            logger.error("异常" + e);
            result.put("result", "400");
            result.put("message", null);
            throw new RuntimeException("事务回滚");
        }
    }
}
