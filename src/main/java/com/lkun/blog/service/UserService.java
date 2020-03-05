package com.lkun.blog.service;

import com.lkun.blog.po.User;

/**
 * @author LK
 * @date 2019/8/27 16:27:46
 * @description
 */
public interface UserService {

    User checkUser(String username, String password);
}
