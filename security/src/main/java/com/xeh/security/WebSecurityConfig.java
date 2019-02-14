package com.xeh.security;

import com.xeh.security.service.CustomUserDetailService;
import com.xeh.security.service.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @Author:xeh
 * @Date:2018/11/16 14:35
 * @Description:
 */
@Configuration
@EnableWebSecurity  //使得Spring Security提供并且支持了Spring MVC的集成
@EnableGlobalMethodSecurity(prePostEnabled=true)  //开启方法拦截
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //用户无权限拦截处理类
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;

    //授权管理
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    //注册UserDetailsService 的bean，通过用户名加载与该用户的用户名、密码以及权限相关的信息
    @Bean
    UserDetailsService customUserService(){ //注册UserDetailsService 的bean
        return new CustomUserDetailService();
    }

    //java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
    //原因就是在之前版本中的NoOpPasswordEncoder被DelegatingPasswordEncoder取代了，而你保存在数据库中的密码没有指定加密方式
    //spring security 版本在5.0后就要加个 PasswordEncoder 验证
    /*定义认证规则*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(new MyPasswordEncoder()); //user Details Service验证
    }

    /**
     * 对URL进行权限配置
     * 该方法定义url的访问权限，登录路径，注销
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()  //任何人(包括没有经过验证的)都可以访问"/"和"/home"
                .antMatchers("/admin/**").access("hasRole('USER')")
                .anyRequest().authenticated()  //所有其他的URL都需要用户进行验证
                .and()
                // 配置被拦截时的处理
                .exceptionHandling()
                //添加无权限时的处理
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .formLogin()  //使用Java配置默认值设置了基于表单的验证。使用POST提交到"/login"时，需要用"username"和"password"进行验证
                .loginPage("/login")  //指定在需要登录时将用户发送到的URL
                .permitAll()  //用户可以访问formLogin()相关的任何URL
                .and()
                .logout()  //注销
                .permitAll();  //用户可以访问logout()相关的任何URL。

        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

    /*忽略静态资源*/
/*    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/static/**");
    }*/
}