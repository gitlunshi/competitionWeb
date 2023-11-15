package com.henu.competition.controller;

import com.henu.competition.common.controller.BaseController;
import com.henu.competition.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/apply")
    public String index(HttpServletRequest httpServletRequest, Model model) {
        HttpSession session = httpServletRequest.getSession();
        User loginUser = (User)session.getAttribute("user");
        if (loginUser==null){
            return "apply/login";
        }
        model.addAttribute("nickname",loginUser.getName());
        return "apply/index";
    }
    @GetMapping("/apply/login")
    public String login() {
        return "apply/login";
    }

    @GetMapping("/")
    public String firstPage() {
        return "index";
    }

    @GetMapping("/{staticFile}")
    public String staticFile(@PathVariable String staticFile) {
        String[] split = staticFile.split("\\.");
        return split[0];
    }
}