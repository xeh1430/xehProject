package com.xeh.security.service;


import com.xeh.security.dao.UserMapper;
import com.xeh.security.model.Permission;
import com.xeh.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:xeh
 * @Date:2018/11/12 14:48
 * @Description: 自定义UserDetailsService 接口(认证管理器)，储存用户所有角色
 * 认证管理器把任务交给了Provider
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserMapper usersMapper;

    /**
     * 通过用户名加载与该用户的用户名、密码以及权限相关的信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws DisabledException {
        User user = usersMapper.findByUserName(username);
        if (user != null) {
            List<Permission> permissions = usersMapper.findByAdminUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
            for (Permission permission : permissions) {
                if (permission != null && permission.getName()!=null) {

                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            //这里我们不抛出UsernameNotFoundException因为Security会把我们抛出的该异常捕捉并换掉；
            //这里要明确Security抛出的异常无法被ControllerAdvice捕捉到，无法进行统一异常处理；
            //而我们只需要打印正确的异常消息即可，Security自动把异常添加到HttpServletRequest或HttpSession中
//            throw new UsernameNotFoundException("用户名不存在");
            throw new DisabledException("---->UserName :" + username + " not found!");
        }
    }
}
