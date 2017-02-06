package com.zm.storm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zm.storm.service.IUserAccountService;

@Controller
@RequestMapping("/hello")
public class MyFirstController {
	
	@Autowired
	private IUserAccountService userAccountService;
	
	@RequestMapping("/world")
    public void listAllBoard(HttpServletRequest request, HttpServletResponse response) {
		System.out.println( request.toString() );
        System.out.println("call listAllBoard method.");
        System.out.println(userAccountService.getUserAccountById(1L).toString());
	}
	
}
