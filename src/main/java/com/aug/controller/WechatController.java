package com.aug.controller;

import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;


import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wechat/portal")
public class WechatController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WxCpService wxService;

	@Autowired
	private WxCpMessageRouter router;

	@Autowired
	private WxCpConfigStorage wxCpConfigStorage;

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public @ResponseBody String authGet(
			@RequestParam(name = "msg_signature", required = false) String signature,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce,
			@RequestParam(name = "echostr", required = false) String echostr) {

		this.logger.info("\n接收到来自微信服务器的认证消息：[{},{},{},{}]", signature,
				timestamp, nonce, echostr);

		if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
			throw new IllegalArgumentException("请求参数非法，请核实~");
		}

		if (this.wxService.checkSignature(signature, timestamp, nonce, echostr)) {
			
			WxCpCryptUtil cryptUtil = new WxCpCryptUtil(this.wxCpConfigStorage);
		      String plainText = cryptUtil.decrypt(echostr);
		      // 说明是一个仅仅用来验证的请求，回显echostr
		      
			return plainText;
		}

		return "非法请求";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/xml; charset=UTF-8")
	public @ResponseBody String post(
			@RequestBody String requestBody,
			@RequestParam("signature") String signature,
			@RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce,
			@RequestParam(name = "encrypt_type", required = false) String encType,
			@RequestParam(name = "msg_signature", required = false) String msgSignature) {
		this.logger.info("\n接收微信请求：[{},{},{},{},{}]\n{} ", signature, encType,
				msgSignature, timestamp, nonce, requestBody);

		String out = null;

		// aes加密的消息
		WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(requestBody,
				this.wxCpConfigStorage, timestamp, nonce, msgSignature);

		this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
		WxCpXmlOutMessage outMessage = this.route(inMessage);
		if (outMessage == null) {
			return "";
		}

		out = outMessage.toEncryptedXml(this.wxCpConfigStorage);

		this.logger.debug("\n组装回复信息：{}", out);

		return out;
	}

	private WxCpXmlOutMessage route(WxCpXmlMessage message) {
		try {
			return this.router.route(message);
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
		}

		return null;
	}

}
