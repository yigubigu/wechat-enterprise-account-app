package com.aug.controller;

import java.util.List;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpDepart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("weichat/depart")
public class DepartController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WxCpService wxService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseWrapper getDepart() {
		try {
			List<WxCpDepart> departs = this.wxService.departGet();
			return new ResponseWrapper(departs);
		} catch (WxErrorException e) {
			logger.error(e.getMessage());
		}
		return new ResponseWrapper();
	}
}
