package com.microservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.model.AccountResponse;
import com.microservice.services.AccountService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RestrictedController extends BaseController {

    @Autowired
    AccountService accountService;


    @RequestMapping("/restricted")
    public AccountResponse restricted(HttpServletRequest req) {
        return accountService.getAccount(req);
    }
}
