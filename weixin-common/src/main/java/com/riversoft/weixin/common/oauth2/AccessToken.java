package com.riversoft.weixin.common.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by exizhai on 12/17/2015.
 */
@Setter
@Getter
public class AccessToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("openid")
    private String openId;

    @JsonProperty("unionid")
    private String unionId;

    private String scope;

    @JsonProperty("expires_in")
    private int expiresIn;

    private long expiresTill;

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
        this.expiresTill = System.currentTimeMillis() + (expiresIn * 1000) - 300000;
    }

}
