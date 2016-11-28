package com.aug.handler;

import java.util.Map;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MsgHandler extends AbstractHandler  {

	 @Override
	    public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage,
	            Map<String, Object> context, WxCpService weixinService,
	            WxSessionManager sessionManager) {

	        String msg = String.format("type:%s, event:%s, key:%s",
	            wxMessage.getMsgType(), wxMessage.getEvent(),
	            wxMessage.getEventKey());
	        if (WxConsts.BUTTON_VIEW.equals(wxMessage.getEvent())) {
	            return null;
	        }

	        return WxCpXmlOutMessage.TEXT().content(msg)
	                .fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName())
	                .build();
	    }

}
