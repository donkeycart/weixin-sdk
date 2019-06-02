package com.riversoft.weixin.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.riversoft.weixin.common.util.JsonMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @borball on 8/14/2016.
 */
@Setter
@Getter
public class AccessToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private long expiresIn;

    private long expiresTill;

    public static AccessToken fromJson(String json) {
        return JsonMapper.defaultMapper().fromJson(json, AccessToken.class);
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        this.expiresTill = System.currentTimeMillis() + (expiresIn * 1000) - 300000;
    }

    public boolean expired() {
        return System.currentTimeMillis() > expiresTill;
    }
}
