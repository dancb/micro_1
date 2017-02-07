package com.microservice.controller;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.model.JWTResponse;

import java.util.Map;

@RestController
public class HttpMicroServiceController extends BaseController {

    @RequestMapping("/account-request")
    public JWTResponse authBuilder(@RequestBody Map<String, Object> claims) {
        String jwt = createJwt(claims);

        return new JWTResponse(jwt);
    }
}
