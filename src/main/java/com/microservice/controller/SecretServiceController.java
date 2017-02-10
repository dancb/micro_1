package com.microservice.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.model.JWTResponse;
import com.microservice.model.PublicCreds;
import com.microservice.services.SecretService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class SecretServiceController extends BaseController {

    @Autowired
    SecretService secretService;

    @RequestMapping("/refresh-my-creds")
    public PublicCreds refreshMyCreds() {
        return secretService.refreshMyCreds();
    }

    @RequestMapping("/get-my-public-creds")
    public PublicCreds getMyPublicCreds() {
        return secretService.getMyPublicCreds();
    }

    @RequestMapping("/add-public-creds")
    public PublicCreds addPublicCreds(@RequestBody PublicCreds publicCreds) {
        secretService.addPublicCreds(publicCreds);
        // just to prove that the key was successfully added
        return secretService.getPublicCreds(publicCreds.getKid());
    }

    @RequestMapping("/test-build")
    public JWTResponse testBuild() {
        String jws = Jwts.builder()
            .setHeaderParam("kid", secretService.getMyPublicCreds().getKid())
            .setIssuer("Dan")
            .setSubject("dcarrasco")
            .claim("name", "Daniel Carrasco")
            .claim("likesTraveling", true)
            .claim("likesProgramming", true)
            .setExpiration(new Date(new Date().getTime() + 1000*60*10)) // 10 minutes
            .signWith(
                SignatureAlgorithm.RS256,
                secretService.getMyPrivateKey()
            )
            .compact();
        return new JWTResponse(jws);
    }

    @RequestMapping("/test-parse")
    public JWTResponse testParse(@RequestParam String jwt) {
        Jws<Claims> jwsClaims = Jwts.parser()
            .setSigningKeyResolver(secretService.getSigningKeyResolver())
            .parseClaimsJws(jwt);

        return new JWTResponse(jwsClaims);
    }
}
