package com.henu.competition.controller;

import com.henu.competition.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * userController
 * 
 * @author Yalu Wang
 * @version 1.0.0 2023-08-02
 */
@Controller
public class SystemController extends BaseController {

    @GetMapping("/index")
    public String index(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String loginUser = (String)session.getAttribute("loginUser");
        if (loginUser==null){
            return "forward:/login";
        }
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String firstPage(HttpServletRequest request) {
        return "forward:/index";
    }

}