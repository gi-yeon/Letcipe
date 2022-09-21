package com.ssafy.letcipe.api.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtService {

    private final String secretKey = "ssafySecret";
    private final int EXPIRE_MINUTES = 60 * 1;

    public String getJwtToken(HttpServletRequest request) {
        return request.getHeader("letcipe-token");
    }

}


