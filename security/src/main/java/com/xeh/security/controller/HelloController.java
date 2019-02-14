package com.xeh.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: xeh
 * @date: 2019/1/28 10:59
 * @description:
 */

@Controller
public class HelloController {
    @RequestMapping("/admin/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public String index(){
        return "hello";
    }

    @RequestMapping(value ={"","/","/home"})
    public String home(){
        return "home";
    }
}
