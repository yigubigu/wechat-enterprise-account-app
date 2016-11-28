package com.aug.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * wechat mp properties
 *
 * @author Binary Wang
 */
@ConfigurationProperties(prefix = "wechat.cp")
public class WechatCpProperties {
    
    private String corpId;

	private String corpSecret;
	
	private String agentId;
	
	private String token;
	
	private String aesKey;

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCorpSecret() {
		return corpSecret;
	}

	public void setCorpSecret(String corpSecret) {
		this.corpSecret = corpSecret;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAesKey() {
		return aesKey;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	@Override
	public String toString() {
		return "WechatCpProperties [corpId=" + corpId + ", corpSecret="
				+ corpSecret + ", agentId=" + agentId + ", token=" + token
				+ ", aesKey=" + aesKey + "]";
	}
	
	

}
