package com.ilucky.mybatis2.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ilucky.mybatis2.model.User;

/**
 * @author IluckySi
 * @since 20151013
 */
public interface UserService extends BaseService {

    /**
     * 创建User
     *
     * @param user
     */
    public void createUser(User user) throws Exception;

    /**
     * 创建User
     *
     * @param response
     * @param name
     * @param password
     * @param sex
     * @param birthday
     * @param userType
     */
    public void createUser(String name, String password, boolean sex, String birthday, String userType) throws Exception;

    /**
     * 创建User集合
     *
     * @param userList
     */
    public void createUserList(List<User> userList) throws Exception;

    /**
     * 修改User
     *
     * @param user
     */
    public void modifyUser(User user) throws Exception;

    /**
     * 修改User
     *
     * @param response
     * @param id
     * @param name
     * @param password
     * @param sex
     * @param birthday
     * @param userType
     */
    public void modifyUser(HttpServletResponse response, String id, String name, String password, boolean sex, String birthday, String userType) throws Exception;

    /**
     * 删除User
     *
     * @param user
     */
    public void deleteUser(String user);

    /**
     * 删除User
     *
     * @param response
     * @param user
     */
    public void deleteUser(HttpServletResponse response, String user) throws Exception;

    /**
     * 删除User集合
     *
     * @param userList
     */
    public void deleteUserList(List<String> userList) throws Exception;

    /**
     * 查询User
     *
     * @param user
     */
    public void getUser(String user) throws Exception;

    /**
     * 查询User集合
     */
    public void getUserList() throws Exception;

    /**
     * 根据name查询User
     */
    public void getUserByName(String name) throws Exception;

    /**
     * 查询User数量
     */
    public void getUserCount() throws Exception;
}
