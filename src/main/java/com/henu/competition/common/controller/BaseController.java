package com.henu.competition.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * 用于其他Controller继承的基础Controller
 *
 * @author Yalu Wang
 * @version 1.0.0 2023-08-02
 */
public abstract class BaseController {
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    private Environment environment;
}
