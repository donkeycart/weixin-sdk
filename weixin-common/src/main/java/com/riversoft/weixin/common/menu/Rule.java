package com.riversoft.weixin.common.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.riversoft.weixin.common.user.Gender;
import com.riversoft.weixin.common.util.GenderDeserializer;
import com.riversoft.weixin.common.util.GenderSerializer;
import lombok.Getter;
import lombok.Setter;

/**
 * @borball on 3/14/2016.
 */
@Setter
@Getter
public class Rule {

    @JsonProperty("group_id")
    private String group;

    @JsonDeserialize(using = GenderDeserializer.class)
    @JsonSerialize(using = GenderSerializer.class)
    private Gender sex;

    @JsonProperty("client_platform_type")
    private String platform;

    private String country;
    private String province;
    private String city;
    private String language;
}
