package com.lkun.blog.service;

import com.lkun.blog.Util.MD5Util;
import com.lkun.blog.dao.UserRepository;
import com.lkun.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LK
 * @date 2019/8/27 16:28:56
 * @description
 */
@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Util.code(password));
        return user;
    }


}
