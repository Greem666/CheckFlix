package com.maciej.checkflix.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@RestController
@RequestMapping(value = "/v1/misc")
public class MiscController {

    @RequestMapping(method = RequestMethod.GET, value = "/userip")
    public String getMoviesBy(HttpServletRequest request) {
        String userIp = request.getRemoteAddr();
        if (userIp.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                return null;
            }
        }
        return null;
    }
}