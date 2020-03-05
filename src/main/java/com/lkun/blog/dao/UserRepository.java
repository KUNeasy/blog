package com.lkun.blog.dao;

import com.lkun.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LK
 * @date 2019/8/27 16:33:00
 * @description
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

}
