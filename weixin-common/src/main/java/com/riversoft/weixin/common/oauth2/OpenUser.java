package com.riversoft.weixin.common.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.riversoft.weixin.common.user.Gender;
import com.riversoft.weixin.common.util.GenderDeserializer;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by exizhai on 12/17/2015.
 */
@Setter
@Getter
public class OpenUser {

    @JsonProperty(value = "openid")
    private String openId;

    @JsonProperty(value = "nickname")
    private String nickName;

    @JsonDeserialize(using = GenderDeserializer.class)
    private Gender sex;

    private String province;
    private String city;
    private String country;

    @JsonProperty(value = "headimgurl")
    private String headImgUrl;

    @JsonProperty(value = "unionid")
    private String unionId;

    private String[] privilege;

    public OpenUser() {
    }
}
