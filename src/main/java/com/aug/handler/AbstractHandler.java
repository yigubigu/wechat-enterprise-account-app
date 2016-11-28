package com.aug.handler;

import me.chanjar.weixin.cp.api.WxCpMessageHandler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractHandler implements WxCpMessageHandler {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}