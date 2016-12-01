package com.aug.controller;

import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/wechat/user")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WxCpService wxService;
	

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody() 
    public ResponseWrapper getUsers(){
		int departId = 1;
		boolean fetchChild = true;
		int status = 1; 
		
		try {
			List<WxCpUser> userList = this.wxService.userList(departId, fetchChild, status);
			return new ResponseWrapper(userList);
		} catch (WxErrorException e) {
			logger.error(e.getMessage());
		}
		return new ResponseWrapper();
	}
	
	 
}
